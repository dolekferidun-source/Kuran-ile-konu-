package com.example.data.model

data class QuranVerse(
    val surahName: String,
    val surahNumber: Int,
    val ayahNumber: Int,
    val arabicText: String,
    val turkishTranslation: String,
    val tefsirSummary: String,
    val keywords: List<String> = emptyList(),
    val relatedVerseRefs: List<String> = emptyList(),
    val audioUrl: String? = null
) {
    val referenceString: String get() = "$surahName Suresi, $ayahNumber. Ayet ($surahNumber:$ayahNumber)"
}

data class Hadith(
    val source: String,
    val turkishText: String,
    val context: String
)

data class LifeScenario(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val verses: List<QuranVerse>,
    val hadiths: List<Hadith>,
    val tefsirOverview: String,
    val recommendedPrayer: String,
    val moralAdvices: List<String>
)

data class VerseChain(
    val id: String,
    val topic: String,
    val description: String,
    val verses: List<QuranVerse>,
    val connectionExplanation: String,
    val synthesisResult: String
)

data class MindmapNode(
    val label: String,
    val type: String, // "SURAH", "ROOT_WORD", "RELATED_AYAH", "TEFSIR_THEME"
    val description: String
)

data class VerseMindmap(
    val verseRef: String,
    val verseText: String,
    val rootWords: List<String>,
    val relatedVerses: List<QuranVerse>,
    val nodes: List<MindmapNode>
)

data class ReliabilitySource(
    val verseRef: String,
    val hadithRef: String?,
    val tefsirSource: String,
    val neutralityNotice: String = "Yapay zekâ dinî hüküm veya fetva vermez. Cevaplar açık tefsir ve sahih hadis kaynaklarına dayalıdır."
)

data class EmotionAnalysisResult(
    val detectedEmotion: String,
    val emotionalSummary: String,
    val verses: List<QuranVerse>,
    val tefsirSummary: String,
    val hadiths: List<Hadith>,
    val reliability: ReliabilitySource,
    val spiritualReflection: String
)
