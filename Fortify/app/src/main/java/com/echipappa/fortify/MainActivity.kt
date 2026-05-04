package com.echipappa.fortify

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.echipappa.fortify.ui.accounts.AccountsScreen
import com.echipappa.fortify.ui.addaccount.AddAccountScreen
import com.echipappa.fortify.ui.auth.WelcomeScreen
import com.echipappa.fortify.ui.theme.FortifyTheme
import com.echipappa.fortify.ui.auth.SignInScreen
import com.echipappa.fortify.ui.auth.ForgotPasswordScreen
import com.echipappa.fortify.ui.auth.EmailVerificationScreen
import com.echipappa.fortify.ui.auth.BiometricScreen
import com.echipappa.fortify.ui.dashboard.DashboardScreen
import com.echipappa.fortify.ui.risk.RiskScreen
import com.echipappa.fortify.ui.subscriptions.SubscriptionsScreen
import com.echipappa.fortify.ui.tips.TipsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FortifyTheme {
                AddAccountScreen()
            }
        }
    }
}