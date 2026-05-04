package com.echipappa.fortify.ui.dashboard

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

@Composable
fun DashboardScreen() {
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

            // Header
            Text(
                text = "Welcome back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Here's your digital presence overview",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Monthly Cost Card
            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(ElectricBlue, ElectricBlueDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💲", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Monthly Cost", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$132.95",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📈", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "\$1595.40/year",
                        fontSize = 14.sp,
                        color = ElectricBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subscriptions Card
            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💳", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Subscriptions", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "6",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "2 unused",
                    fontSize = 14.sp,
                    color = WarningYellow
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Accounts Card
            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👥", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Accounts", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "9",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Platforms connected",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Risk Score Card
            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🛡", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Risk Score", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "72",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "/100",
                        fontSize = 18.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fair — room for improvement",
                    fontSize = 14.sp,
                    color = WarningYellow
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun DashboardCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .padding(20.dp),
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    FortifyTheme {
        DashboardScreen()
    }
}