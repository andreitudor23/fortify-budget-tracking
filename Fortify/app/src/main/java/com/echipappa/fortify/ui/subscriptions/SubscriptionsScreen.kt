package com.echipappa.fortify.ui.subscriptions

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

data class Subscription(
    val name: String,
    val icon: String,
    val monthlyCost: Double,
    val billingDate: String,
    val isUnused: Boolean = false
)

@Composable
fun SubscriptionsScreen() {
    val subscriptions = listOf(
        Subscription("Netflix", "🎬", 15.99, "15th", false),
        Subscription("Spotify", "🎵", 9.99, "3rd", false),
        Subscription("Duolingo Super", "🦉", 6.99, "22nd", true),
        Subscription("iCloud+", "☁️", 2.99, "8th", false),
        Subscription("ChatGPT Plus", "🤖", 20.00, "1st", false),
        Subscription("Adobe CC", "🎨", 54.99, "12th", true),
    )

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
                text = "Subscriptions",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Track and manage your recurring payments",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Monthly Total Card
            StatCard(
                icon = "💲",
                label = "Monthly Total",
                value = "\$132.95",
                useGradient = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Yearly Total Card
            StatCard(
                icon = "📅",
                label = "Yearly Total",
                value = "\$1595.40"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Potential Savings Card
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
                            .background(Color(0xFF2A1F0A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📉", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Potential Savings", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "\$239.76",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "From unused subscriptions",
                    fontSize = 14.sp,
                    color = WarningYellow
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Subscriptions list
            Text(
                text = "Your Subscriptions",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            subscriptions.forEach { subscription ->
                SubscriptionItem(subscription)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun StatCard(icon: String, label: String, value: String, useGradient: Boolean = false) {
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
                    .background(
                        if (useGradient) Brush.linearGradient(
                            colors = listOf(ElectricBlue, ElectricBlueDark)
                        ) else Brush.linearGradient(
                            colors = listOf(NavyCardLight, NavyCardLight)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = TextSecondary)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = value,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Composable
fun SubscriptionItem(subscription: Subscription) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NavyCardLight),
            contentAlignment = Alignment.Center
        ) {
            Text(subscription.icon, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = subscription.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Bills on the ${subscription.billingDate}",
                fontSize = 13.sp,
                color = TextSecondary
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${subscription.monthlyCost}",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (subscription.isUnused) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF2A1A0A))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Unused",
                        fontSize = 11.sp,
                        color = WarningYellow,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF0A2A1A))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Active",
                        fontSize = 11.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionsScreenPreview() {
    FortifyTheme {
        SubscriptionsScreen()
    }
}