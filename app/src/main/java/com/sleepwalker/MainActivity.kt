package com.sleepwalker

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sleepwalker.presentation.models.MainViewModel
import com.sleepwalker.presentation.models.MainViewModelFactory
import com.sleepwalker.presentation.models.SettingsViewModel
import com.sleepwalker.presentation.models.SettingsViewModelFactory
import com.sleepwalker.presentation.views.MainView
import com.sleepwalker.presentation.views.SettingsView


const val PERMISSIONS_REQUEST_CODE = 100
val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.BODY_SENSORS)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasPermissions()) {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "main") {
                composable("main") { CreateMainView(navController) }
                composable("settings") { CreateSettingsView(navController) }
            }
        }
    }

    @Composable
    private fun CreateMainView(navController: NavHostController) {
        val healthService = (application as MainApplication).healthService
        val mainViewModel = viewModel<MainViewModel>(
            factory = MainViewModelFactory(
                healthService=healthService,
                navController=navController,)
        )

        return MainView(viewModel = mainViewModel)
    }

    @Composable
    private fun CreateSettingsView(navController: NavHostController) {
        val viewModel = viewModel<SettingsViewModel>(
            factory = SettingsViewModelFactory(
                navController=navController,)
        )

        return SettingsView(viewModel = viewModel)
    }

    private fun hasPermissions(): Boolean {
        var state = true

        for (permission in REQUIRED_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                state = false
                break
            }
        }

        return state
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    finishAndRemoveTask()
                }
                return
            }

            else -> {}
        }
    }
}
