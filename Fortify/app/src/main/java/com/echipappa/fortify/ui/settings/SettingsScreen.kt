package com.echipappa.fortify.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echipappa.fortify.ui.theme.*

@Composable
fun SettingsScreen(
    userEmail: String = "",
    onResetData: () -> Unit = {}
) {
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Settings",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Manage your app preferences",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ACCOUNT",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextMuted,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                            .background(NavyCardLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Logged in as", fontSize = 12.sp, color = TextMuted)
                        Text(
                            text = userEmail.ifEmpty { "—" },
                            fontSize = 14.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DANGER ZONE",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = DangerRed,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = null,
                            tint = DangerRed,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Reset App Data", fontSize = 14.sp, color = DangerRed, fontWeight = FontWeight.SemiBold)
                        Text("Deletes all accounts, subscriptions and logs you out", fontSize = 12.sp, color = TextMuted)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showResetDialog = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reset All Data", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            containerColor = NavyCard,
            title = {
                Text("Reset App Data?", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "This will permanently delete all accounts, subscriptions and your login. This cannot be undone.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        onResetData()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reset", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            }
        )
    }
}
