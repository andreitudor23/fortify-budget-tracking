package com.echipappa.fortify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.echipappa.fortify.ui.auth.WelcomeScreen
import com.echipappa.fortify.ui.theme.FortifyTheme
import com.echipappa.fortify.ui.auth.SignInScreen
import com.echipappa.fortify.ui.auth.ForgotPasswordScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FortifyTheme {
                ForgotPasswordScreen()
            }
        }
    }
}