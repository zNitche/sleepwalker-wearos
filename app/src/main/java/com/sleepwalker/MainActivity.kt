package com.sleepwalker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sleepwalker.presentation.models.HeartBeatViewModel
import com.sleepwalker.presentation.models.HeartBeatViewModelFactory
import com.sleepwalker.presentation.views.HeartBeat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthService = (application as MainApplication).healthService

        setContent {
            val viewModel = viewModel<HeartBeatViewModel>(
                factory = HeartBeatViewModelFactory(healthService=healthService)
            )

            HeartBeat(heartBeat = viewModel.heartBeatText.collectAsStateWithLifecycle().value,
                      enabled = viewModel.enabled.collectAsStateWithLifecycle().value,
                      onEnabledButtonClick = { viewModel.toggleEnabled() })
        }
    }
}
