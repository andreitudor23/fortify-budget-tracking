package com.echipappa.fortify.ui.subscriptions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echipappa.fortify.data.model.AccountEntity
import com.echipappa.fortify.data.model.SubscriptionEntity
import com.echipappa.fortify.ui.theme.*
import com.echipappa.fortify.viewmodel.AccountViewModel
import com.echipappa.fortify.viewmodel.SubscriptionViewModel

private fun ordinalDay(day: Int): String {
    val suffix = when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
    return "$day$suffix"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionsScreen(
    subscriptionViewModel: SubscriptionViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {
    val subscriptions by subscriptionViewModel.allSubscriptions.collectAsState()
    val totalMonthlyCost by subscriptionViewModel.totalMonthlyCost.collectAsState()
    val unusedSubscriptions by subscriptionViewModel.unusedSubscriptions.collectAsState()
    val accounts by accountViewModel.allAccounts.collectAsState()

    var showAddSheet by remember { mutableStateOf(false) }
    var editingSubscription by remember { mutableStateOf<SubscriptionEntity?>(null) }

    val yearlyTotal = (totalMonthlyCost ?: 0.0) * 12
    val potentialSavings = unusedSubscriptions.sumOf { it.monthlyCost } * 12

    Box(modifier = Modifier.fillMaxSize().background(NavyDark)) {
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
                Column(Modifier.weight(1f)) {
                    Text(
                        "Subscriptions",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        "Track your recurring payments",
                        fontSize = 16.sp,
                        color = TextSecondary
                    )
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { showAddSheet = true },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("+ Add", fontSize = 13.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            StatCard(
                icon = Icons.Default.AttachMoney,
                label = "Monthly Total",
                value = "${"$"}${"%.2f".format(totalMonthlyCost ?: 0.0)}",
                useGradient = true
            )

            Spacer(Modifier.height(12.dp))

            StatCard(
                icon = Icons.Default.CalendarMonth,
                label = "Yearly Total",
                value = "${"$"}${"%.2f".format(yearlyTotal)}"
            )

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
                            .background(Color(0xFF2A1F0A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = WarningYellow,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text("Potential Savings", fontSize = 14.sp, color = TextSecondary)
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "${"$"}${"%.2f".format(potentialSavings)}",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text("From unused subscriptions", fontSize = 14.sp, color = WarningYellow)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Your Subscriptions",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))

            if (subscriptions.isEmpty()) {
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
                            "No subscriptions yet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Add an account first, then tap + Add",
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                subscriptions.forEach { subscription ->
                    key(subscription.id) {
                        var dismissed by remember { mutableStateOf(false) }
                        val dismissState = rememberSwipeToDismissBoxState()
                        LaunchedEffect(dismissState.currentValue) {
                            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart && !dismissed) {
                                dismissed = true
                                subscriptionViewModel.deleteSubscription(subscription)
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
                            SubscriptionItem(
                                subscription = subscription,
                                onToggleUnused = { subscriptionViewModel.toggleUnused(subscription) },
                                onClick = { editingSubscription = subscription }
                            )
                        }

                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }

    if (showAddSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = NavyCard,
            dragHandle = null
        ) {
            SubscriptionFormSheet(
                title = "Add Subscription",
                initial = null,
                accounts = accounts,
                onConfirm = { accountId, name, icon, cost, billingDate, email ->
                    subscriptionViewModel.addSubscription(accountId, name, icon, cost, billingDate, email)
                    showAddSheet = false
                },
                onDismiss = { showAddSheet = false }
            )
        }
    }

    editingSubscription?.let { sub ->
        ModalBottomSheet(
            onDismissRequest = { editingSubscription = null },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = NavyCard,
            dragHandle = null
        ) {
            SubscriptionFormSheet(
                title = "Edit Subscription",
                initial = sub,
                accounts = accounts,
                onConfirm = { accountId, name, icon, cost, billingDate, email ->
                    subscriptionViewModel.updateSubscription(
                        sub.copy(
                            accountId = accountId,
                            name = name,
                            icon = icon,
                            monthlyCost = cost,
                            billingDate = billingDate,
                            email = email
                        )
                    )
                    editingSubscription = null
                },
                onDismiss = { editingSubscription = null }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscriptionFormSheet(
    title: String,
    initial: SubscriptionEntity?,
    accounts: List<AccountEntity>,
    onConfirm: (accountId: Int, name: String, icon: String, cost: Double, billingDate: String, email: String) -> Unit,
    onDismiss: () -> Unit
) {
    val preselected = if (initial != null) accounts.find { it.id == initial.accountId } else null

    var selectedAccount by remember { mutableStateOf<AccountEntity?>(preselected) }
    var accountExpanded by remember { mutableStateOf(false) }
    var costText by remember {
        mutableStateOf(if (initial != null) "%.2f".format(initial.monthlyCost) else "")
    }
    var billingDay by remember {
        mutableStateOf(initial?.billingDate?.filter { it.isDigit() }?.toIntOrNull() ?: 1)
    }
    var billingDayExpanded by remember { mutableStateOf(false) }

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
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(20.dp))

        if (accounts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2A1F0A))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        "No accounts yet",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = WarningYellow
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Go to the Accounts tab and add a service before creating a subscription.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyCardLight),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Close", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            return@Column
        }

        Text("Account", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = accountExpanded,
            onExpandedChange = { accountExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedAccount?.let { "${it.icon}  ${it.platform}  ·  ${it.email}" } ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select an account…", color = TextMuted) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = accountExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(12.dp),
                colors = formFieldColors()
            )
            ExposedDropdownMenu(
                expanded = accountExpanded,
                onDismissRequest = { accountExpanded = false },
                modifier = Modifier.background(NavyCard)
            ) {
                accounts.forEach { account ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(account.icon, fontSize = 20.sp)
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        account.platform,
                                        color = if (account == selectedAccount) ElectricBlue else TextPrimary,
                                        fontWeight = if (account == selectedAccount) FontWeight.SemiBold else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                    Text(account.email, color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        },
                        onClick = {
                            selectedAccount = account
                            accountExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Monthly Cost", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = costText,
            onValueChange = { costText = it },
            placeholder = { Text("0.00", color = TextMuted) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = formFieldColors(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Text("Billing Day", fontSize = 14.sp, color = TextSecondary)
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = billingDayExpanded,
            onExpandedChange = { billingDayExpanded = it }
        ) {
            OutlinedTextField(
                value = ordinalDay(billingDay),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = billingDayExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(12.dp),
                colors = formFieldColors()
            )
            ExposedDropdownMenu(
                expanded = billingDayExpanded,
                onDismissRequest = { billingDayExpanded = false },
                modifier = Modifier.background(NavyCard)
            ) {
                (1..31).forEach { day ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                ordinalDay(day),
                                color = if (day == billingDay) ElectricBlue else TextPrimary,
                                fontWeight = if (day == billingDay) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            billingDay = day
                            billingDayExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        val isValid = selectedAccount != null
        Button(
            onClick = {
                selectedAccount?.let { acc ->
                    val cost = costText.toDoubleOrNull() ?: 0.0
                    onConfirm(acc.id, acc.platform, acc.icon, cost, ordinalDay(billingDay), acc.email)
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isValid) ElectricBlue else NavyCardLight
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (initial != null) "Save Changes" else "Add Subscription",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun formFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = ElectricBlue,
    unfocusedBorderColor = NavyCardLight,
    focusedContainerColor = NavyDark,
    unfocusedContainerColor = NavyDark,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary
)

@Composable
fun StatCard(icon: ImageVector, label: String, value: String, useGradient: Boolean = false) {
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
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (useGradient) Color.White else ElectricBlue,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(label, fontSize = 14.sp, color = TextSecondary)
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = value,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}

@Composable
fun SubscriptionItem(
    subscription: SubscriptionEntity,
    onToggleUnused: () -> Unit = {},
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
            Text(subscription.icon, fontSize = 22.sp)
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = subscription.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Bills on the ${subscription.billingDate}",
                fontSize = 13.sp,
                color = TextSecondary
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${"$"}${"%.2f".format(subscription.monthlyCost)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (subscription.isUnused) Color(0xFF2A1A0A) else Color(0xFF0A2A1A)
                    )
                    .clickable { onToggleUnused() }
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (subscription.isUnused) "Unused" else "Active",
                    fontSize = 11.sp,
                    color = if (subscription.isUnused) WarningYellow else SuccessGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
