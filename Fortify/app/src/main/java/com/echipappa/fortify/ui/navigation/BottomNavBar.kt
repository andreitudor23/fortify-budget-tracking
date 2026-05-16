package com.echipappa.fortify.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.echipappa.fortify.ui.theme.*

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Default.Home, Routes.DASHBOARD),
        BottomNavItem("Subscriptions", Icons.Default.CreditCard, Routes.SUBSCRIPTIONS),
        BottomNavItem("Accounts", Icons.Default.Person, Routes.ACCOUNTS),
        BottomNavItem("Settings", Icons.Default.Settings, Routes.SETTINGS),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Routes.DASHBOARD,
        Routes.SUBSCRIPTIONS,
        Routes.ACCOUNTS,
        Routes.SETTINGS
    )

    if (showBottomBar) {
        NavigationBar(
            containerColor = NavyCard,
            tonalElevation = 0.dp,
            modifier = Modifier.height(100.dp)
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
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
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
