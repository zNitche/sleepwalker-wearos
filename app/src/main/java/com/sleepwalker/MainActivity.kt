package com.sleepwalker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sleepwalker.presentation.models.HeartBeatViewModel
import com.sleepwalker.presentation.models.HeartBeatViewModelFactory
import com.sleepwalker.presentation.views.SleepwalkerApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthService = (application as MainApplication).healthService

        setContent {
            val healthViewModel = viewModel<HeartBeatViewModel>(
                factory = HeartBeatViewModelFactory(healthService=healthService)
            )

            SleepwalkerApp(healthViewModel = healthViewModel)
        }
    }
}
