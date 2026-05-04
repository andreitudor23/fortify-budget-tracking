package com.echipappa.fortify

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.echipappa.fortify.ui.auth.WelcomeScreen
import com.echipappa.fortify.ui.theme.FortifyTheme
import com.echipappa.fortify.ui.auth.SignInScreen
import com.echipappa.fortify.ui.auth.ForgotPasswordScreen
import com.echipappa.fortify.ui.auth.EmailVerificationScreen
import com.echipappa.fortify.ui.auth.BiometricScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FortifyTheme {
                BiometricScreen()
            }
        }
    }
}