package com.echipappa.fortify.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.echipappa.fortify.data.database.FortifyDatabase
import com.echipappa.fortify.ui.auth.*
import com.echipappa.fortify.ui.dashboard.DashboardScreen
import com.echipappa.fortify.ui.subscriptions.SubscriptionsScreen
import com.echipappa.fortify.ui.accounts.AccountsScreen
import com.echipappa.fortify.ui.addaccount.AddAccountScreen
import com.echipappa.fortify.ui.settings.SettingsScreen
import com.echipappa.fortify.viewmodel.AccountViewModel
import com.echipappa.fortify.viewmodel.SubscriptionViewModel

object Routes {
    const val WELCOME = "welcome"
    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"
    const val DASHBOARD = "dashboard"
    const val SUBSCRIPTIONS = "subscriptions"
    const val ACCOUNTS = "accounts"
    const val ADD_ACCOUNT = "add_account"
    const val SETTINGS = "settings"
}

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val db = remember { FortifyDatabase.getDatabase(context) }

    val subscriptionViewModel: SubscriptionViewModel = viewModel()
    val accountViewModel: AccountViewModel = viewModel()

    var loginError by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf("") }

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
                onSignIn = { navController.navigate(Routes.SIGN_IN) },
                onBack = { navController.popBackStack() },
                onRegister = { name, email, password ->
                    val success = db.registerUser(name, email, password)
                    if (success) {
                        db.setLoggedIn(true)
                        signUpError = ""
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.WELCOME) { inclusive = true }
                        }
                    } else {
                        signUpError = "Email already registered!"
                    }
                },
                errorMessage = signUpError
            )
        }
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onSignIn = { email, password ->
                    val success = db.loginUser(email, password)
                    if (success) {
                        db.setLoggedIn(true)
                        loginError = ""
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.WELCOME) { inclusive = true }
                        }
                    } else {
                        loginError = "Invalid email or password!"
                    }
                },
                onBack = { navController.popBackStack() },
                errorMessage = loginError
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                userName = db.getUserName(),
                subscriptionViewModel = subscriptionViewModel,
                accountViewModel = accountViewModel
            )
        }
        composable(Routes.SUBSCRIPTIONS) {
            SubscriptionsScreen(
                subscriptionViewModel = subscriptionViewModel,
                accountViewModel = accountViewModel
            )
        }
        composable(Routes.ACCOUNTS) {
            AccountsScreen(
                onAddAccount = { navController.navigate(Routes.ADD_ACCOUNT) },
                accountViewModel = accountViewModel
            )
        }
        composable(Routes.ADD_ACCOUNT) {
            AddAccountScreen(
                onAddAccount = { navController.popBackStack() },
                onBack = { navController.popBackStack() },
                accountViewModel = accountViewModel
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                userEmail = db.getLoggedInEmail(),
                onResetData = {
                    db.resetAllData()
                    accountViewModel.reload()
                    subscriptionViewModel.reload()
                    navController.navigate(Routes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
