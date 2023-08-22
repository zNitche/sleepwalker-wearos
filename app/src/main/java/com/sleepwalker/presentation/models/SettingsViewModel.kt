package com.sleepwalker.presentation.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.sleepwalker.Config
import com.sleepwalker.services.ConfigService

class SettingsViewModel(
    val navController: NavHostController,
    val configService: ConfigService
): ViewModel() {
    var config = configService.loadConfig()

    val apiKeyValue = mutableStateOf(config.apiKey)
    val apiAddressValue = mutableStateOf(config.apiAddress)

    fun applySettings() {
        config = Config(apiKeyValue.value, apiAddressValue.value)
        configService.writeConfig(config)
    }
}


class SettingsViewModelFactory(
    private val navController: NavHostController,
    private val configService: ConfigService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                navController = navController,
                configService = configService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
