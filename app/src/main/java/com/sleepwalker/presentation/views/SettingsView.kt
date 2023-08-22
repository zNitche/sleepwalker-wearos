package com.sleepwalker.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.sleepwalker.presentation.models.SettingsViewModel

@Composable
fun SettingsView(viewModel: SettingsViewModel) {
    val listState = rememberScalingLazyListState()
    val apiKeyValue = viewModel.apiKeyValue
    val apiAddressValue = viewModel.apiAddressValue

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
            item { AppTitleText("$APP_TAG - Settings") }
            item { ApiKeyTextInput(apiKeyValue) }
            item { ApiAddressTextInput(apiAddressValue) }
            item { ApplySettingsButton(viewModel::applySettings) }
            item { GoBackNavButton(viewModel.navController) }
        }
    }
}
