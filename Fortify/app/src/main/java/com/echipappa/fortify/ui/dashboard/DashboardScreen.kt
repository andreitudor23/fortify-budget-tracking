package com.echipappa.fortify.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echipappa.fortify.ui.theme.*
import com.echipappa.fortify.viewmodel.AccountViewModel
import com.echipappa.fortify.viewmodel.SubscriptionViewModel

@Composable
fun DashboardScreen(
    userName: String = "",
    subscriptionViewModel: SubscriptionViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {
    val totalMonthlyCost by subscriptionViewModel.totalMonthlyCost.collectAsState()
    val allSubscriptions by subscriptionViewModel.allSubscriptions.collectAsState()
    val unusedSubscriptions by subscriptionViewModel.unusedSubscriptions.collectAsState()
    val accountCount by accountViewModel.accountCount.collectAsState()

    val yearlyTotal = (totalMonthlyCost ?: 0.0) * 12
    val unusedPenalty = minOf(unusedSubscriptions.size * 15, 60)
    val bloatPenalty = if (allSubscriptions.isNotEmpty() &&
        unusedSubscriptions.size * 2 > allSubscriptions.size) 10 else 0
    val budgetHealthScore = (100 - unusedPenalty - bloatPenalty).coerceIn(0, 100)

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
                text = "Welcome back${if (userName.isNotEmpty()) ", $userName" else ""}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(colors = listOf(ElectricBlue, ElectricBlueDark))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Monthly Cost", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${"$"}${"%.2f".format(totalMonthlyCost ?: 0.0)}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${"$"}${"%.2f".format(yearlyTotal)}/year",
                    fontSize = 14.sp,
                    color = ElectricBlue
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Subscriptions", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${allSubscriptions.size}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (unusedSubscriptions.isNotEmpty()) {
                    Text(
                        text = "${unusedSubscriptions.size} unused",
                        fontSize = 14.sp,
                        color = WarningYellow
                    )
                } else {
                    Text(text = "All active", fontSize = 14.sp, color = SuccessGreen)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Accounts", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$accountCount",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Platforms connected", fontSize = 14.sp, color = TextSecondary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            DashboardCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = when {
                                budgetHealthScore >= 90 -> SuccessGreen
                                budgetHealthScore >= 70 -> ElectricBlue
                                budgetHealthScore >= 50 -> WarningYellow
                                else -> DangerRed
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Budget Health", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$budgetHealthScore",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            budgetHealthScore >= 90 -> SuccessGreen
                            budgetHealthScore >= 70 -> ElectricBlue
                            budgetHealthScore >= 50 -> WarningYellow
                            else -> DangerRed
                        }
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
                    text = when {
                        budgetHealthScore >= 90 -> "Excellent — no wasted subscriptions"
                        budgetHealthScore >= 70 -> "Good — a few unused subscriptions"
                        budgetHealthScore >= 50 -> "Fair — review your unused subscriptions"
                        else -> "Poor — too many unused subscriptions"
                    },
                    fontSize = 14.sp,
                    color = when {
                        budgetHealthScore >= 90 -> SuccessGreen
                        budgetHealthScore >= 70 -> ElectricBlue
                        budgetHealthScore >= 50 -> WarningYellow
                        else -> DangerRed
                    }
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
