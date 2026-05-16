package com.echipappa.fortify.ui.addaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echipappa.fortify.ui.theme.*
import com.echipappa.fortify.viewmodel.AccountViewModel

private fun iconForPlatform(name: String): String = when (name.trim().lowercase()) {
    "netflix" -> "🎬"
    "spotify" -> "🎵"
    "apple", "icloud", "apple music", "apple tv" -> "🍎"
    "youtube", "youtube premium" -> "▶️"
    "amazon", "amazon prime", "prime" -> "📦"
    "github" -> "💻"
    "notion" -> "📝"
    "slack" -> "💬"
    "adobe", "adobe cc", "adobe creative cloud" -> "🎨"
    "linkedin", "linkedin premium" -> "💼"
    "twitter", "twitter/x", "x" -> "🐦"
    "google", "google one" -> "🔵"
    "disney", "disney+" -> "🏰"
    "hbo", "max", "hbo max" -> "📺"
    "reddit" -> "🤖"
    else -> "🌐"
}

data class PopularService(
    val name: String,
    val icon: String,
    val category: String
)

private val popularServices = listOf(
    PopularService("Google", "🔵", "Platform"),
    PopularService("Netflix", "🎬", "Entertainment"),
    PopularService("Spotify", "🎵", "Entertainment"),
    PopularService("Apple", "🍎", "Platform"),
    PopularService("GitHub", "💻", "Dev"),
    PopularService("Notion", "📝", "Productivity"),
    PopularService("Slack", "💬", "Productivity"),
    PopularService("Adobe CC", "🎨", "Productivity"),
    PopularService("YouTube", "▶️", "Entertainment"),
    PopularService("LinkedIn", "💼", "Platform"),
    PopularService("Twitter/X", "🐦", "Platform"),
    PopularService("Amazon", "📦", "Platform"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreen(
    onAddAccount: () -> Unit = {},
    onBack: () -> Unit = {},
    accountViewModel: AccountViewModel = viewModel()
) {
    var serviceName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("All", "Productivity", "Entertainment", "Platform", "Dev")

    val filteredServices = popularServices.filter { service ->
        (selectedCategory == "All" || service.category == selectedCategory) &&
            (searchQuery.isBlank() || service.name.contains(searchQuery, ignoreCase = true))
    }

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

            Text(
                "Add Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(Modifier.height(24.dp))

            if (showSuccessMessage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0A2A1A))
                        .padding(16.dp)
                ) {
                    Text(
                        "Account added successfully",
                        fontSize = 14.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(NavyCard)
                    .padding(20.dp)
            ) {
                Text(
                    "Add Manually",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Service Name", fontSize = 14.sp, color = TextSecondary)
                    Spacer(Modifier.width(8.dp))
                    Text(iconForPlatform(serviceName), fontSize = 16.sp)
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = serviceName,
                    onValueChange = { serviceName = it },
                    placeholder = { Text("e.g., Netflix, Spotify", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = NavyCardLight,
                        focusedContainerColor = NavyDark,
                        unfocusedContainerColor = NavyDark,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text("Email Address", fontSize = 14.sp, color = TextSecondary)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("your@email.com", color = TextMuted) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlue,
                        unfocusedBorderColor = NavyCardLight,
                        focusedContainerColor = NavyDark,
                        unfocusedContainerColor = NavyDark,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (serviceName.isNotEmpty() && email.isNotEmpty()) {
                            accountViewModel.addAccount(
                                platform = serviceName.trim(),
                                icon = iconForPlatform(serviceName),
                                email = email.trim()
                            )
                            serviceName = ""
                            email = ""
                            showSuccessMessage = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (serviceName.isNotEmpty() && email.isNotEmpty())
                            ElectricBlue else NavyCardLight
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Add Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Popular Services",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search services...", color = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlue,
                    unfocusedBorderColor = NavyCardLight,
                    focusedContainerColor = NavyCard,
                    unfocusedContainerColor = NavyCard,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                },
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ElectricBlue else NavyCard)
                            .border(
                                1.dp,
                                if (isSelected) ElectricBlue else NavyCardLight,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            filteredServices.chunked(2).forEach { rowServices ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowServices.forEach { service ->
                        ServiceCard(
                            service = service,
                            modifier = Modifier.weight(1f),
                            onClick = { serviceName = service.name }
                        )
                    }
                    if (rowServices.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
fun ServiceCard(
    service: PopularService,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(NavyCard)
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(NavyCardLight),
            contentAlignment = Alignment.Center
        ) {
            Text(service.icon, fontSize = 24.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = service.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}
