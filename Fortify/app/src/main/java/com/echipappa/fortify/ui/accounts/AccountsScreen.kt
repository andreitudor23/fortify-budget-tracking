package com.echipappa.fortify.ui.accounts

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

data class Account(
    val platform: String,
    val icon: String,
    val email: String,
    val lastActivity: String,
    val tag: AccountTag
)

enum class AccountTag {
    DUPLICATE, INACTIVE, RISK, NORMAL
}

@Composable
fun AccountsScreen() {
    val accounts = listOf(
        Account("Google", "🔵", "john.doe@gmail.com", "Today", AccountTag.NORMAL),
        Account("Netflix", "🎬", "john.doe@gmail.com", "2 days ago", AccountTag.DUPLICATE),
        Account("Spotify", "🎵", "johndoe87@yahoo.com", "Today", AccountTag.NORMAL),
        Account("Twitter/X", "🐦", "johndoe87@yahoo.com", "3 months ago", AccountTag.INACTIVE),
        Account("LinkedIn", "💼", "john.doe@gmail.com", "1 week ago", AccountTag.DUPLICATE),
        Account("Reddit", "🤖", "johndoe87@yahoo.com", "6 months ago", AccountTag.RISK),
        Account("GitHub", "💻", "john.doe@gmail.com", "Yesterday", AccountTag.NORMAL),
        Account("Apple", "🍎", "john.doe@gmail.com", "Today", AccountTag.NORMAL),
        Account("Amazon", "📦", "johndoe87@yahoo.com", "1 month ago", AccountTag.INACTIVE),
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
                text = "Connected Accounts",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Manage your digital presence across platforms",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatMiniCard(
                    modifier = Modifier.weight(1f),
                    icon = "🌐",
                    label = "Total Accounts",
                    value = "9",
                    subtitle = "Platforms connected"
                )
                StatMiniCard(
                    modifier = Modifier.weight(1f),
                    icon = "✉️",
                    label = "Duplicate Emails",
                    value = "2",
                    subtitle = "2 emails reused",
                    subtitleColor = WarningYellow
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // At Risk card
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
                            .background(Color(0xFF2A0A0A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⚠️", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("At Risk", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "3",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Inactive or risky accounts",
                    fontSize = 14.sp,
                    color = DangerRed
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "All Accounts",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            accounts.forEach { account ->
                AccountItem(account)
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun StatMiniCard(
    modifier: Modifier = Modifier,
    icon: String,
    label: String,
    value: String,
    subtitle: String,
    subtitleColor: androidx.compose.ui.graphics.Color = TextSecondary
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(NavyCardLight),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, fontSize = 12.sp, color = TextSecondary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = subtitleColor
        )
    }
}

@Composable
fun AccountItem(account: Account) {
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
            Text(account.icon, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = account.platform,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = account.email,
                fontSize = 12.sp,
                color = TextSecondary
            )
            Text(
                text = "Last active: ${account.lastActivity}",
                fontSize = 12.sp,
                color = TextMuted
            )
        }

        when (account.tag) {
            AccountTag.DUPLICATE -> TagBadge("Duplicate", WarningYellow, Color(0xFF2A1F0A))
            AccountTag.INACTIVE -> TagBadge("Inactive", TextSecondary, NavyCardLight)
            AccountTag.RISK -> TagBadge("Risk", DangerRed, Color(0xFF2A0A0A))
            AccountTag.NORMAL -> {}
        }
    }
}

@Composable
fun TagBadge(text: String, textColor: Color, bgColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountsScreenPreview() {
    FortifyTheme {
        AccountsScreen()
    }
}