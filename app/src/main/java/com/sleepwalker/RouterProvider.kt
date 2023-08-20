package com.sleepwalker

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sleepwalker.presentation.models.MainViewModel
import com.sleepwalker.presentation.models.MainViewModelFactory
import com.sleepwalker.presentation.models.SettingsViewModel
import com.sleepwalker.presentation.models.SettingsViewModelFactory
import com.sleepwalker.presentation.views.MainView
import com.sleepwalker.presentation.views.SettingsView


class RouterProvider(private val application: MainApplication) {
    @Composable
    fun getRouter() {
        val navController = rememberNavController()
        val nav = NavHost(navController = navController, startDestination = "main") {
            composable("main") { CreateMainView(navController) }
            composable("settings") { CreateSettingsView(navController) }
        }

        return nav
    }

    @Composable
    private fun CreateMainView(navController: NavHostController) {
        val healthService = application.healthService
        val mainViewModel = viewModel<MainViewModel>(
            factory = MainViewModelFactory(
                healthService=healthService,
                navController=navController)
        )

        return MainView(viewModel = mainViewModel)
    }

    @Composable
    private fun CreateSettingsView(navController: NavHostController) {
        val viewModel = viewModel<SettingsViewModel>(
            factory = SettingsViewModelFactory(
                navController=navController)
        )

        return SettingsView(viewModel = viewModel)
    }
}