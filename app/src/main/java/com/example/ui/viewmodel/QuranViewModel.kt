package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkEntity
import com.example.data.local.JournalEntity
import com.example.data.local.KnowledgeBase
import com.example.data.local.SearchHistoryEntity
import com.example.data.model.*
import com.example.data.repository.QuranRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class GuidanceUiState {
    object Idle : GuidanceUiState()
    object Loading : GuidanceUiState()
    data class Success(val data: EmotionAnalysisResult) : GuidanceUiState()
    data class Error(val message: String) : GuidanceUiState()
}

sealed class LetterUiState {
    object Idle : LetterUiState()
    object Generating : LetterUiState()
    data class Success(val letter: JournalEntity) : LetterUiState()
    data class Error(val message: String) : LetterUiState()
}

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "quran_ile_konus.db"
    ).build()

    private val repository = QuranRepository(db.quranDao())

    val journalEntries: StateFlow<List<JournalEntity>> = repository.allJournals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchHistory: StateFlow<List<SearchHistoryEntity>> = repository.searchHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _guidanceState = MutableStateFlow<GuidanceUiState>(GuidanceUiState.Idle)
    val guidanceState: StateFlow<GuidanceUiState> = _guidanceState.asStateFlow()

    private val _letterState = MutableStateFlow<LetterUiState>(LetterUiState.Idle)
    val letterState: StateFlow<LetterUiState> = _letterState.asStateFlow()

    val searchQuery = MutableStateFlow("")
    val selectedEmotionChip = MutableStateFlow<String?>(null)

    val selectedScenario = MutableStateFlow<LifeScenario?>(KnowledgeBase.LIFE_SCENARIOS.first())
    val selectedVerseChain = MutableStateFlow<VerseChain?>(KnowledgeBase.VERSE_CHAINS.first())
    val selectedMindmap = MutableStateFlow<VerseMindmap?>(KnowledgeBase.getMindmapForVerse("İnşirah", 5))

    // Audio Playback Simulation State
    private val _isAudioPlaying = MutableStateFlow(false)
    val isAudioPlaying: StateFlow<Boolean> = _isAudioPlaying.asStateFlow()

    private val _audioProgress = MutableStateFlow(0f)
    val audioProgress: StateFlow<Float> = _audioProgress.asStateFlow()

    private var audioJob: Job? = null

    init {
        // Load default initial guidance
        performGuidanceSearch("Çok yalnız hissettiğimde ne yapmalıyım?")
    }

    fun performGuidanceSearch(query: String) {
        if (query.isBlank()) return
        searchQuery.value = query
        viewModelScope.launch {
            _guidanceState.value = GuidanceUiState.Loading
            try {
                val result = repository.analyzeEmotionAndGetGuidance(query)
                _guidanceState.value = GuidanceUiState.Success(result)
            } catch (e: Exception) {
                _guidanceState.value = GuidanceUiState.Error("Arama gerçekleşirken bir hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun onEmotionChipSelected(emotionTitle: String) {
        selectedEmotionChip.value = emotionTitle
        val description = KnowledgeBase.EMOTIONS_MAP[emotionTitle] ?: emotionTitle
        performGuidanceSearch("$emotionTitle hissediyorum. $description")
    }

    fun generateQuranLetter(userNightlyNote: String) {
        if (userNightlyNote.isBlank()) return
        viewModelScope.launch {
            _letterState.value = LetterUiState.Generating
            try {
                val journal = repository.generateQuranLetter(userNightlyNote)
                _letterState.value = LetterUiState.Success(journal)
            } catch (e: Exception) {
                _letterState.value = LetterUiState.Error("Mektup oluşturulurken bir hata oluştu: ${e.localizedMessage}")
            }
        }
    }

    fun toggleBookmark(verse: QuranVerse) {
        viewModelScope.launch {
            repository.toggleBookmark(verse)
        }
    }

    fun removeBookmark(surahNumber: Int, ayahNumber: Int) {
        viewModelScope.launch {
            repository.removeBookmark(surahNumber, ayahNumber)
        }
    }

    fun deleteJournalEntry(id: Int) {
        viewModelScope.launch {
            repository.deleteJournalEntry(id)
        }
    }

    fun openMindmapForVerse(surahName: String, ayahNumber: Int) {
        selectedMindmap.value = KnowledgeBase.getMindmapForVerse(surahName, ayahNumber)
    }

    fun toggleAudioPlayback() {
        if (_isAudioPlaying.value) {
            _isAudioPlaying.value = false
            audioJob?.cancel()
        } else {
            _isAudioPlaying.value = true
            audioJob?.cancel()
            audioJob = viewModelScope.launch {
                while (_audioProgress.value < 1f && _isAudioPlaying.value) {
                    delay(300)
                    _audioProgress.value += 0.05f
                }
                _isAudioPlaying.value = false
                _audioProgress.value = 0f
            }
        }
    }
}
