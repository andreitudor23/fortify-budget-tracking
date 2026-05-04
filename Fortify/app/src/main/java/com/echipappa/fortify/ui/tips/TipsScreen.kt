package com.echipappa.fortify.ui.tips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

data class Recommendation(
    val title: String,
    val description: String,
    val priority: Priority,
    val icon: String
)

enum class Priority {
    HIGH, MEDIUM, LOW
}

@Composable
fun TipsScreen() {
    val recommendations = listOf(
        Recommendation(
            "Cancel Duolingo Super",
            "Not used in 87 days. Save \$83.88/year",
            Priority.HIGH,
            "🦉"
        ),
        Recommendation(
            "Cancel Adobe CC",
            "Not used in 45 days. Save \$659.88/year",
            Priority.HIGH,
            "🎨"
        ),
        Recommendation(
            "Separate your emails",
            "Using same email on 6 platforms increases risk",
            Priority.MEDIUM,
            "✉️"
        ),
        Recommendation(
            "Delete inactive Twitter account",
            "No activity in 3 months. Reduces attack surface",
            Priority.MEDIUM,
            "🐦"
        ),
        Recommendation(
            "Review Reddit account",
            "Consider deleting or securing this account",
            Priority.LOW,
            "🤖"
        ),
    )

    val highPriority = recommendations.filter { it.priority == Priority.HIGH }
    val mediumPriority = recommendations.filter { it.priority == Priority.MEDIUM }
    val lowPriority = recommendations.filter { it.priority == Priority.LOW }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Recommendations",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Personalized tips to optimize your digital presence",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Priority summary cards
            PrioritySummaryCard(
                icon = "⚡",
                label = "High Priority",
                count = highPriority.size,
                subtitle = "Take action now",
                iconBg = Color(0xFF2A0A0A),
                subtitleColor = DangerRed
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrioritySummaryCard(
                icon = "📈",
                label = "Medium Priority",
                count = mediumPriority.size,
                subtitle = "Consider soon",
                iconBg = Color(0xFF2A1F0A),
                subtitleColor = WarningYellow
            )
            Spacer(modifier = Modifier.height(12.dp))
            PrioritySummaryCard(
                icon = "✅",
                label = "Low Priority",
                count = lowPriority.size,
                subtitle = "Optional improvements",
                iconBg = Color(0xFF0A1F2A),
                subtitleColor = ElectricBlue
            )

            Spacer(modifier = Modifier.height(24.dp))

            // High Priority section
            if (highPriority.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "High Priority",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DangerRed
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                highPriority.forEach { rec ->
                    RecommendationItem(rec)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Medium Priority section
            if (mediumPriority.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📈", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Medium Priority",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarningYellow
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                mediumPriority.forEach { rec ->
                    RecommendationItem(rec)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Low Priority section
            if (lowPriority.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✅", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Low Priority",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                lowPriority.forEach { rec ->
                    RecommendationItem(rec)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun PrioritySummaryCard(
    icon: String,
    label: String,
    count: Int,
    subtitle: String,
    iconBg: Color,
    subtitleColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = TextSecondary)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$count",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = subtitleColor
        )
    }
}

@Composable
fun RecommendationItem(recommendation: Recommendation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = recommendation.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                PriorityBadge(recommendation.priority)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = recommendation.description,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NavyCardLight),
            contentAlignment = Alignment.Center
        ) {
            Text(recommendation.icon, fontSize = 20.sp)
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val (text, color, bg) = when (priority) {
        Priority.HIGH -> Triple("HIGH", DangerRed, Color(0xFF2A0A0A))
        Priority.MEDIUM -> Triple("MED", WarningYellow, Color(0xFF2A1F0A))
        Priority.LOW -> Triple("LOW", ElectricBlue, Color(0xFF0A1F2A))
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text, fontSize = 11.sp, color = color, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun TipsScreenPreview() {
    FortifyTheme {
        TipsScreen()
    }
}