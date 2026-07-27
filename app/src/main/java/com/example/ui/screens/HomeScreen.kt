package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.KnowledgeBase
import com.example.ui.components.HeaderBannerCard
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldSecondary
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.viewmodel.QuranViewModel

@Composable
fun HomeScreen(
    viewModel: QuranViewModel,
    onNavigateToGuidance: () -> Unit,
    onNavigateToScenarios: () -> Unit,
    onNavigateToChains: () -> Unit,
    onNavigateToLetter: () -> Unit,
    onNavigateToMindmap: () -> Unit,
    modifier: Modifier = Modifier
) {
    var queryInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("home_screen_column")
    ) {
        // Top Bar Greeting
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ESSELÂMU ALEYKUM",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        letterSpacing = 1.2.sp
                    )
                )
                Text(
                    text = "Kur'an ile Konuş",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(GoldLight)
                    .border(1.dp, Color(0xFFBFC9BA), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profil",
                    tint = EmeraldPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Hero Header Banner
        HeaderBannerCard(
            title = "Seni Dinliyorum",
            subtitle = "Şu an kalbinden ne geçiyor? Duygularını veya bir sıkıntını yazabilirsin...",
            actionButtonText = "Gece Mektubunu Oluştur",
            onActionClick = onNavigateToLetter
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Guide Companion Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("daily_guide_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = GoldLight.copy(alpha = 0.6f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEmotionChipSelected("Sabır")
                        onNavigateToGuidance()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(EmeraldPrimary, CircleShape)
                        .testTag("btn_daily_sabir")
                ) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = "Sabır İkonu",
                        tint = GoldAccent
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "GÜNLÜK REHBER",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = EmeraldPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Bugün sabır üzerine düşünmek ister misin?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF222222)
                        )
                    )
                    Text(
                        text = "Şüphesiz Allah sabredenlerle beraberdir. (Bakara 153)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF555555),
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = EmeraldPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Emotion Search Input Bar
        Text(
            text = "Şu an ne hissediyorsun?",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = queryInput,
            onValueChange = { queryInput = it },
            placeholder = { Text("Örn: Çok yalnızım, borç yüküm var, öfkeliyim...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = EmeraldPrimary
                )
            },
            trailingIcon = {
                if (queryInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            viewModel.performGuidanceSearch(queryInput)
                            onNavigateToGuidance()
                        },
                        modifier = Modifier.testTag("btn_search_submit")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Gönder",
                            tint = EmeraldPrimary
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_emotion_search"),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EmeraldPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Emotion Quick Chips
        Text(
            text = "Duygu Seçenekleri",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.testTag("emotion_chips_row")
        ) {
            items(KnowledgeBase.EMOTIONS_MAP.keys.toList()) { emotionTitle ->
                FilterChip(
                    selected = false,
                    onClick = {
                        viewModel.onEmotionChipSelected(emotionTitle)
                        onNavigateToGuidance()
                    },
                    label = {
                        Text(
                            text = emotionTitle,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Core App Sections Grid
        Text(
            text = "Rehber Modülleri",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureModuleTile(
                title = "Hayat Senaryoları",
                subtitle = "İş, borç, yas, haksızlık",
                icon = Icons.Outlined.FamilyRestroom,
                badgeColor = EmeraldSecondary,
                onClick = onNavigateToScenarios,
                modifier = Modifier.weight(1f)
            )

            FeatureModuleTile(
                title = "Ayet Zinciri",
                subtitle = "Konu odaklı 15-20 ayet",
                icon = Icons.Outlined.FormatListNumbered,
                badgeColor = GoldAccent,
                onClick = onNavigateToChains,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureModuleTile(
                title = "Kur'an'ın Mektubu",
                subtitle = "Gece tefekkürü & günlük",
                icon = Icons.Outlined.MarkEmailRead,
                badgeColor = GoldAccent,
                onClick = onNavigateToLetter,
                modifier = Modifier.weight(1f)
            )

            FeatureModuleTile(
                title = "Zihinsel Harita",
                subtitle = "Ayetler arası bağlantılar",
                icon = Icons.Outlined.Hub,
                badgeColor = EmeraldSecondary,
                onClick = onNavigateToMindmap,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun FeatureModuleTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badgeColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .clickable(onClick = onClick)
            .testTag("feature_tile_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardBorder(1.dp, Color(0xFFE1E4D9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(badgeColor.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = badgeColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun CardBorder(width: androidx.compose.ui.unit.Dp, color: Color) = androidx.compose.foundation.BorderStroke(width, color)
