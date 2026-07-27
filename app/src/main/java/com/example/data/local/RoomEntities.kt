package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran_journal")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val dateFormatted: String,
    val userNote: String,
    val letterTitle: String,
    val letterContent: String,
    val featuredVerseRef: String,
    val featuredVerseText: String,
    val recommendedDua: String,
    val reflectionQuestion: String
)

@Entity(tableName = "quran_bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val surahName: String,
    val surahNumber: Int,
    val ayahNumber: Int,
    val arabicText: String,
    val turkishTranslation: String,
    val tefsirSummary: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val queryOrEmotion: String,
    val detectedEmotion: String,
    val timestamp: Long = System.currentTimeMillis()
)
