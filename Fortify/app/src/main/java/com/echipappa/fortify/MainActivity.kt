package com.echipappa.fortify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.echipappa.fortify.ui.navigation.BottomNavBar
import com.echipappa.fortify.ui.navigation.NavGraph
import com.echipappa.fortify.ui.theme.FortifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FortifyTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}