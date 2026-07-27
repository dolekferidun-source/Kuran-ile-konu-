package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.QuranVerseCard
import com.example.ui.components.ReliabilityLayerCard
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.viewmodel.GuidanceUiState
import com.example.ui.viewmodel.QuranViewModel

@Composable
fun EmotionGuidanceScreen(
    viewModel: QuranViewModel,
    onNavigateToMindmap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val guidanceState by viewModel.guidanceState.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()
    val audioProgress by viewModel.audioProgress.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("emotion_guidance_screen")
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        when (val state = guidanceState) {
            is GuidanceUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("loading_box"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = EmeraldPrimary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Duygu Analizi Yapılıyor & Ayetler Getiriliyor...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldPrimary
                            )
                        )
                    }
                }
            }

            is GuidanceUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is GuidanceUiState.Success -> {
                val data = state.data

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Header: Detected Emotion Badge
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = EmeraldPrimary)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Psychology,
                                        contentDescription = null,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "SAPTHANAN DUYGU: ${data.detectedEmotion.uppercase()}",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = GoldAccent,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = data.emotionalSummary,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.White,
                                        lineHeight = 20.sp
                                    )
                                )
                            }
                        }
                    }

                    // Section Title: Quran Verses
                    item {
                        Text(
                            text = "📖 Kur'an Cevabı & İlgili Ayetler",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }

                    // Verses List
                    items(data.verses) { verse ->
                        val isBookmarked = bookmarks.any { 
                            it.surahNumber == verse.surahNumber && it.ayahNumber == verse.ayahNumber 
                        }

                        QuranVerseCard(
                            verse = verse,
                            isPlayingAudio = isAudioPlaying,
                            audioProgress = audioProgress,
                            isBookmarked = isBookmarked,
                            onBookmarkToggle = {
                                if (isBookmarked) {
                                    viewModel.removeBookmark(verse.surahNumber, verse.ayahNumber)
                                } else {
                                    viewModel.toggleBookmark(verse)
                                }
                            },
                            onAudioToggle = { viewModel.toggleAudioPlayback() },
                            onMindmapClick = {
                                viewModel.openMindmapForVerse(verse.surahName, verse.ayahNumber)
                                onNavigateToMindmap()
                            }
                        )
                    }

                    // Sahih Hadiths Section
                    if (data.hadiths.isNotEmpty()) {
                        item {
                            Text(
                                text = "📜 Sahih Hadis Desteği & Sünnet",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            data.hadiths.forEach { hadith ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp)
                                    ) {
                                        Text(
                                            text = hadith.source,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = GoldAccent,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "“${hadith.turkishText}”",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 13.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Reliability Layer Card
                    item {
                        ReliabilityLayerCard(reliability = data.reliability)
                    }

                    // Button to Mindmap Graph
                    item {
                        Button(
                            onClick = {
                                val firstVerse = data.verses.firstOrNull()
                                if (firstVerse != null) {
                                    viewModel.openMindmapForVerse(firstVerse.surahName, firstVerse.ayahNumber)
                                }
                                onNavigateToMindmap()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_open_mindmap"),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = null,
                                tint = GoldAccent
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ayet Zihinsel Haritasını İncele",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            GuidanceUiState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Bir duygu seçin veya arama yapın.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
