package com.sleepwalker.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.material.scrollAway
import com.sleepwalker.APP_TAG
import com.sleepwalker.presentation.models.MainViewModel

@Composable
fun MainView(viewModel: MainViewModel) {
    val listState = rememberScalingLazyListState()

    val heartBeatText = viewModel.heartBeatText.collectAsStateWithLifecycle().value
    val accelerationText = viewModel.accelerationText.collectAsStateWithLifecycle().value
    val temperatureText = viewModel.temperatureText.collectAsStateWithLifecycle().value
    val humidityText = viewModel.humidityText.collectAsStateWithLifecycle().value
    val pressureText = viewModel.pressureText.collectAsStateWithLifecycle().value

    val isRunning = viewModel.isRunning.collectAsStateWithLifecycle().value
    val apiConnectionStatus = viewModel.apiConnectionStatus.collectAsStateWithLifecycle().value

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.scrollAway(listState))
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = listState
        ) {

            item { AppTitleText(APP_TAG) }
            item { ApiConnectivityStatusChip(apiConnectionStatus) }
            item { ProcessingToggle(isRunning, viewModel::setIsRunning) }
            item { HBSChip(heartBeatText) }
            item { EnvironmentTemperatureChip(temperatureText) }
            item { RelativeHumidityChip(humidityText) }
            item { PressureChip(pressureText) }
            item { AccelerationChip(accelerationText) }
            item { SettingsNavButton(viewModel) }
        }
    }
}
