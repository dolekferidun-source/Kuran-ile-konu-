package com.example.data.repository

import com.example.BuildConfig
import com.example.data.local.*
import com.example.data.model.*
import com.example.data.remote.GeminiContent
import com.example.data.remote.GeminiNetworkClient
import com.example.data.remote.GeminiPart
import com.example.data.remote.GeminiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuranRepository(private val dao: QuranDao) {

    val allJournals: Flow<List<JournalEntity>> = dao.getAllJournalEntries()
    val allBookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val searchHistory: Flow<List<SearchHistoryEntity>> = dao.getRecentSearchHistory()

    suspend fun analyzeEmotionAndGetGuidance(userQuery: String): EmotionAnalysisResult = withContext(Dispatchers.IO) {
        // Record in search history
        val fallbackResult = KnowledgeBase.findGuidanceForInput(userQuery)
        dao.insertSearchHistory(
            SearchHistoryEntity(
                queryOrEmotion = userQuery,
                detectedEmotion = fallbackResult.detectedEmotion
            )
        )

        if (!GeminiNetworkClient.isApiKeyAvailable()) {
            return@withContext fallbackResult
        }

        try {
            val systemInstructionText = """
                Sen 'Kur'an ile Konuş' uygulamasının İslami İlimler ve Tefsir prensiplerine tam bağlı manevi rehber asistanısın.
                Kurallar:
                1. KESİNLİKLE KENDİ YORUMUNU DİNİ HÜKÜM VEYA FETVA GİBİ SUNMA.
                2. Kullanıcının yaşadığı duyguyu (Örn: Korku, Üzüntü, Yalnızlık, Öfke, Pişmanlık, Borç Stresi vb.) tespit et.
                3. Bu duyguyla doğrudan ilgili Kur'an ayetlerinin Türkçe meallerini ve tefsir özetlerini açıkça belirt.
                4. Varsa konuyla ilgili sahih hadisleri ve Peygamber Efendimizin (s.a.v.) sünnet uygulamalarını ekle.
                5. Her cevabın en altında açık kaynakları (Sure adı, Ayet No, Hadis Kaynağı, Tefsir Müellifi) belirt.
            """.trimIndent()

            val userPrompt = """
                Kullanıcı İfadesi: "$userQuery"
                Lütfen şu başlıklar altında yanıt ver:
                [DUYGU]: (Tespit edilen ana duygu)
                [ÖZET]: (Duyguya özel kısa manevi değerlendirme)
                [AYETLER]: (İlgili ayetler; Sure adı, Ayet No ve Türkçe meal)
                [TEFSİR]: (Muteber tefsir özeti)
                [HADİS]: (Varsa sahih hadis)
                [DUA]: (Önerilen dua)
            """.trimIndent()

            val request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(GeminiPart(text = userPrompt))
                    )
                ),
                systemInstruction = GeminiContent(
                    parts = listOf(GeminiPart(text = systemInstructionText))
                )
            )

            val apiKey = BuildConfig.GEMINI_API_KEY
            val response = GeminiNetworkClient.service.generateContent(apiKey, request)
            val generatedText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

            if (!generatedText.isNullOrBlank()) {
                parseGeminiGuidanceResponse(generatedText, fallbackResult)
            } else {
                fallbackResult
            }
        } catch (e: Exception) {
            fallbackResult
        }
    }

    private fun parseGeminiGuidanceResponse(
        generatedText: String,
        fallback: EmotionAnalysisResult
    ): EmotionAnalysisResult {
        // Dynamic parse or enhance fallback with generated AI text
        return fallback.copy(
            emotionalSummary = generatedText.take(400),
            tefsirSummary = if (generatedText.contains("[TEFSİR]")) {
                generatedText.substringAfter("[TEFSİR]").substringBefore("[").trim()
            } else fallback.tefsirSummary
        )
    }

    suspend fun generateQuranLetter(userNightlyNote: String): JournalEntity = withContext(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy, EEEE", Locale("tr"))
        val dateFormatted = dateFormat.format(Date())

        val selectedVerse = KnowledgeBase.VERSES.random()
        val letterTitle = "Kur'an'ın Sana Mektubu - $dateFormatted"
        
        var letterContent = """
            Sevgili Kulum / Ey İnanan Dostum,
            
            Bugün kalbinde taşıdığın hisleri ("$userNightlyNote") işiten ve bilen Rabbin var.
            
            "${selectedVerse.turkishTranslation}" (${selectedVerse.referenceString})
            
            Gece çöktüğünde ve zihin yalnız kaldığında unutma ki zorluklarla beraber kolaylıklar bitişiktir. Yaşadığın kırgınlık veya yorgunluk, kalbini dünyadan çekip sonsuz rahmete yöneltmek içindir.
        """.trimIndent()

        val recommendedDua = "Rabbim! Kalbime ferahlık ver, işimi kolaylaştır, göğsümü genişlet."
        val reflectionQuestion = "Yarın sabah uyandığında ilk olarak hangi nimet için şükretmek istersin?"

        if (GeminiNetworkClient.isApiKeyAvailable()) {
            try {
                val prompt = """
                    Kullanıcı bu gece günlüğüne şunu yazdı: "$userNightlyNote"
                    Buna karşılık 'Kur'an'ın Sana Mektubu' başlığıyla şefkatli, samimi ve teselli edici manevi bir okuma hazırla.
                    İçinde 2-3 ayet meali, kısa tefsir yorumu, bir dua ve yarın üzerinde düşüneceği tek bir soru bulunsun.
                """.trimIndent()

                val request = GeminiRequest(
                    contents = listOf(GeminiContent(parts = listOf(GeminiPart(text = prompt))))
                )
                val apiKey = BuildConfig.GEMINI_API_KEY
                val response = GeminiNetworkClient.service.generateContent(apiKey, request)
                val aiText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (!aiText.isNullOrBlank()) {
                    letterContent = aiText
                }
            } catch (e: Exception) {
                // Fallback used
            }
        }

        val journalEntity = JournalEntity(
            dateFormatted = dateFormatted,
            userNote = userNightlyNote,
            letterTitle = letterTitle,
            letterContent = letterContent,
            featuredVerseRef = selectedVerse.referenceString,
            featuredVerseText = selectedVerse.turkishTranslation,
            recommendedDua = recommendedDua,
            reflectionQuestion = reflectionQuestion
        )

        dao.insertJournalEntry(journalEntity)
        journalEntity
    }

    suspend fun toggleBookmark(verse: QuranVerse) = withContext(Dispatchers.IO) {
        val bookmark = BookmarkEntity(
            surahName = verse.surahName,
            surahNumber = verse.surahNumber,
            ayahNumber = verse.ayahNumber,
            arabicText = verse.arabicText,
            turkishTranslation = verse.turkishTranslation,
            tefsirSummary = verse.tefsirSummary
        )
        dao.insertBookmark(bookmark)
    }

    suspend fun removeBookmark(surahNumber: Int, ayahNumber: Int) = withContext(Dispatchers.IO) {
        dao.deleteBookmarkByVerse(surahNumber, ayahNumber)
    }

    fun isVerseBookmarked(surahNumber: Int, ayahNumber: Int): Flow<Boolean> {
        return dao.isVerseBookmarked(surahNumber, ayahNumber)
    }

    suspend fun deleteJournalEntry(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteJournalEntry(id)
    }
}
