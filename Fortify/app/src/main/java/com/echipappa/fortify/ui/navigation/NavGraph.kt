package com.echipappa.fortify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.echipappa.fortify.ui.auth.*
import com.echipappa.fortify.ui.dashboard.DashboardScreen
import com.echipappa.fortify.ui.subscriptions.SubscriptionsScreen
import com.echipappa.fortify.ui.accounts.AccountsScreen
import com.echipappa.fortify.ui.risk.RiskScreen
import com.echipappa.fortify.ui.tips.TipsScreen
import com.echipappa.fortify.ui.addaccount.AddAccountScreen

object Routes {
    const val WELCOME = "welcome"
    const val SIGN_IN = "sign_in"
    const val FORGOT_PASSWORD = "forgot_password"
    const val EMAIL_VERIFICATION = "email_verification"
    const val BIOMETRIC = "biometric"
    const val DASHBOARD = "dashboard"
    const val SUBSCRIPTIONS = "subscriptions"
    const val ACCOUNTS = "accounts"
    const val RISK = "risk"
    const val TIPS = "tips"
    const val ADD_ACCOUNT = "add_account"
    const val SIGN_UP = "sign_up"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onCreateAccount = { navController.navigate(Routes.SIGN_UP) },
                onSignIn = { navController.navigate(Routes.SIGN_IN) }
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onSignUp = { navController.navigate(Routes.EMAIL_VERIFICATION) },
                onSignIn = { navController.navigate(Routes.SIGN_IN) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onSignIn = { navController.navigate(Routes.DASHBOARD) },
                onForgotPassword = { navController.navigate(Routes.FORGOT_PASSWORD) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onSendResetLink = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                onSignIn = { navController.navigate(Routes.SIGN_IN) }
            )
        }
        composable(Routes.EMAIL_VERIFICATION) {
            EmailVerificationScreen(
                onVerified = { navController.navigate(Routes.BIOMETRIC) },
                onVerifyLater = { navController.navigate(Routes.BIOMETRIC) }
            )
        }
        composable(Routes.BIOMETRIC) {
            BiometricScreen(
                onEnableBiometric = { navController.navigate(Routes.DASHBOARD) },
                onMaybeLater = { navController.navigate(Routes.DASHBOARD) }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen()
        }
        composable(Routes.SUBSCRIPTIONS) {
            SubscriptionsScreen()
        }
        composable(Routes.ACCOUNTS) {
            AccountsScreen()
        }
        composable(Routes.RISK) {
            RiskScreen()
        }
        composable(Routes.TIPS) {
            TipsScreen()
        }
        composable(Routes.ADD_ACCOUNT) {
            AddAccountScreen(
                onAddAccount = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}