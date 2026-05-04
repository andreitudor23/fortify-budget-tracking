package com.echipappa.fortify.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.echipappa.fortify.ui.theme.*

data class BottomNavItem(
    val label: String,
    val icon: String,
    val route: String
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Dashboard", "⊞", Routes.DASHBOARD),
        BottomNavItem("Subscriptions", "💳", Routes.SUBSCRIPTIONS),
        BottomNavItem("Accounts", "👤", Routes.ACCOUNTS),
        BottomNavItem("Risk", "🛡", Routes.RISK),
        BottomNavItem("Tips", "💡", Routes.TIPS),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Routes.DASHBOARD,
        Routes.SUBSCRIPTIONS,
        Routes.ACCOUNTS,
        Routes.RISK,
        Routes.TIPS
    )

    if (showBottomBar) {
        NavigationBar(
            containerColor = NavyCard,
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(100.dp)
                //.padding(bottom = 8.dp)
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(Routes.DASHBOARD) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Text(
                            text = item.icon,
                            fontSize = 20.sp
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) ElectricBlue else TextSecondary
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlue,
                        unselectedIconColor = TextSecondary,
                        indicatorColor = NavyCardLight
                    )
                )
            }
        }
    }
}