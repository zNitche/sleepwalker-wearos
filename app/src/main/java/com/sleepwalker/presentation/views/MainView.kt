package com.sleepwalker.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.rememberScalingLazyListState
import com.sleepwalker.APP_TAG
import com.sleepwalker.presentation.models.MainViewModel

@Composable
fun MainView(viewModel: MainViewModel) {
    val listState = rememberScalingLazyListState()

    val heartBeatText = viewModel.heartBeatText.collectAsStateWithLifecycle().value
    val isRunning = viewModel.isRunning.collectAsStateWithLifecycle().value

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        state = listState
    ) {

        item { AppTitleText(APP_TAG) }
        item { ProcessingToggle(isRunning, viewModel::setIsRunning) }

        if (isRunning) {
            item { HBSLabel(heartBeatText) }
        }

        item { SettingsNavButton(viewModel) }
    }
}
