package com.sleepwalker.presentation.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

class SettingsViewModel(
    val navController: NavHostController
): ViewModel() {
    val apiKeyValue = mutableStateOf("api_key")
    val apiAddressValue = mutableStateOf("address")
}


class SettingsViewModelFactory(
    private val navController: NavHostController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                navController = navController
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
