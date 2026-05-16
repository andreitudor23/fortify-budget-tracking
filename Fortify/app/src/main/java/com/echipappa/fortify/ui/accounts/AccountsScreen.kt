package com.echipappa.fortify.ui.accounts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echipappa.fortify.data.model.AccountEntity
import com.echipappa.fortify.ui.theme.*
import com.echipappa.fortify.viewmodel.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onAddAccount: () -> Unit = {},
    accountViewModel: AccountViewModel = viewModel()
) {
    val accounts by accountViewModel.allAccounts.collectAsState()
    val accountCount by accountViewModel.accountCount.collectAsState()
    val duplicateCount by accountViewModel.duplicateCount.collectAsState()
    val atRiskCount by accountViewModel.atRiskCount.collectAsState()

    var editingAccount by remember { mutableStateOf<AccountEntity?>(null) }

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
            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Connected Accounts",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = onAddAccount,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("+ Add", fontSize = 13.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatMiniCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Group,
                    label = "Total Accounts",
                    value = "$accountCount",
                    subtitle = "Platforms connected"
                )
                StatMiniCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.AlternateEmail,
                    label = "Duplicate Emails",
                    value = "$duplicateCount",
                    subtitle = "$duplicateCount emails reused",
                    subtitleColor = WarningYellow
                )
            }

            Spacer(Modifier.height(12.dp))

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
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = DangerRed,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text("At Risk", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "$atRiskCount",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text("Inactive or risky accounts", fontSize = 14.sp, color = DangerRed)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "All Accounts",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))

            if (accounts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(NavyCard)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "No accounts yet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Tap + Add to connect a service",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                accounts.forEach { account ->
                    key(account.id) {
                        var dismissed by remember { mutableStateOf(false) }
                        val dismissState = rememberSwipeToDismissBoxState()
                        LaunchedEffect(dismissState.currentValue) {
                            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && !dismissed) {
                                dismissed = true
                                accountViewModel.deleteAccount(account)
                            }
                        }

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {
                                val bgColor by animateColorAsState(
                                    targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart)
                                        DangerRed else Color.Transparent,
                                    label = "swipe_bg"
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(bgColor)
                                        .padding(end = 20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        ) {
                            AccountItem(
                                account = account,
                                onClick = { editingAccount = account }
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }

    editingAccount?.let { acc ->
        ModalBottomSheet(
            onDismissRequest = { editingAccount = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = NavyCard,
            dragHandle = null
        ) {
            AccountEditSheet(
                account = acc,
                onConfirm = { platform, email, lastActivity ->
                    accountViewModel.updateAccount(
                        acc.copy(platform = platform, email = email, lastActivity = lastActivity)
                    )
                    editingAccount = null
                },
                onDismiss = { editingAccount = null }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountEditSheet(
    account: AccountEntity,
    onConfirm: (platform: String, email: String, lastActivity: String) -> Unit,
    onDismiss: () -> Unit
) {
    var platform by remember { mutableStateOf(account.platform) }
    var email by remember { mutableStateOf(account.email) }
    var lastActivity by remember { mutableStateOf(account.lastActivity) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 32.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Edit Account", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Platform", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = platform,
            onValueChange = { platform = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = editFieldColors(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Text("Email", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = editFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Text("Last Activity", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = lastActivity,
            onValueChange = { lastActivity = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = editFieldColors(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        val isValid = platform.isNotBlank() && email.isNotBlank()
        Button(
            onClick = { if (isValid) onConfirm(platform.trim(), email.trim(), lastActivity.trim()) },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isValid) ElectricBlue else NavyCardLight
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save Changes", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun editFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = ElectricBlue,
    unfocusedBorderColor = NavyCardLight,
    focusedContainerColor = NavyDark,
    unfocusedContainerColor = NavyDark,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary
)

@Composable
fun StatMiniCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String,
    subtitle: String,
    subtitleColor: Color = TextSecondary
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
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ElectricBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(label, fontSize = 12.sp, color = TextSecondary)
        }
        Spacer(Modifier.height(8.dp))
        Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(text = subtitle, fontSize = 12.sp, color = subtitleColor)
    }
}

@Composable
fun AccountItem(
    account: AccountEntity,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .clickable { onClick() }
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

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = account.platform, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(Modifier.height(2.dp))
            Text(text = account.email, fontSize = 12.sp, color = TextSecondary)
            Text(text = "Last active: ${account.lastActivity}", fontSize = 12.sp, color = TextMuted)
        }

        when (account.tag) {
            "DUPLICATE" -> TagBadge("Duplicate", WarningYellow, Color(0xFF2A1F0A))
            "INACTIVE" -> TagBadge("Inactive", TextSecondary, NavyCardLight)
            "RISK" -> TagBadge("Risk", DangerRed, Color(0xFF2A0A0A))
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
        Text(text = text, fontSize = 12.sp, color = textColor, fontWeight = FontWeight.Medium)
    }
}
