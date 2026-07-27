package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.JournalEntity
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.viewmodel.LetterUiState
import com.example.ui.viewmodel.QuranViewModel

@Composable
fun QuranLetterScreen(
    viewModel: QuranViewModel,
    modifier: Modifier = Modifier
) {
    var nightlyNoteInput by remember { mutableStateOf("") }
    val letterState by viewModel.letterState.collectAsState()
    val journalEntries by viewModel.journalEntries.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .testTag("quran_letter_screen")
    ) {
        Text(
            text = "Kur'an'ın Sana Mektubu",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = "Gece çöktüğünde kalbindekileri yaz; Kur'an ayetleriyle özel manevi mektubunu al.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input Box
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = nightlyNoteInput,
                    onValueChange = { nightlyNoteInput = it },
                    placeholder = { Text("Örn: Bugün çok kırıldım. Kimse beni anlamıyor...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("input_nightly_note"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.generateQuranLetter(nightlyNoteInput)
                        nightlyNoteInput = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_generate_letter"),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    enabled = nightlyNoteInput.isNotBlank() && letterState !is LetterUiState.Generating,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (letterState is LetterUiState.Generating) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mektubun Hazırlanıyor...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = GoldAccent
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mektubumu Hazırla", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Journal History / Saved Letters
        Text(
            text = "✉️ Manevi Mektuplarım & Günlüğüm",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (journalEntries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz bir mektup oluşturmadınız. Yukarıdaki kutuya hislerinizi yazarak ilk mektubunuzu alabilirsiniz.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(journalEntries) { entry ->
                    LetterJournalCard(
                        entry = entry,
                        onDelete = { viewModel.deleteJournalEntry(entry.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun LetterJournalCard(
    entry: JournalEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GoldLight.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.dateFormatted,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = EmeraldPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Mektubu Sil",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(
                text = "Senin Notun: “${entry.userNote}”",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF444444)
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = GoldAccent.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = entry.letterTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = EmeraldPrimary
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = entry.letterContent,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF222222),
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = EmeraldPrimary)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "🤲 Önerilen Dua:",
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = entry.recommendedDua,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "❓ Yarınki Tefekkür Sorusu:",
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = entry.reflectionQuestion,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                    )
                }
            }
        }
    }
}
