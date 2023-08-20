package com.sleepwalker.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.rememberScalingLazyListState
import com.sleepwalker.APP_TAG
import com.sleepwalker.presentation.models.SettingsViewModel

@Composable
fun SettingsView(viewModel: SettingsViewModel) {
    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        state = listState
    ) {

        item { AppTitleText("$APP_TAG - Settings") }
        item { GoBackNavButton(viewModel.navController) }
    }
}
