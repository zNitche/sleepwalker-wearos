package com.sleepwalker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.sleepwalker.presentation.HeartBeatViewModel
import com.sleepwalker.presentation.HeartBeatViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthService = (application as MainApplication).healthService

        setContent {
            val viewModel = viewModel<HeartBeatViewModel>(
                factory = HeartBeatViewModelFactory(healthService=healthService)
            )
            val heartBeatText = viewModel.heartBeatText.collectAsStateWithLifecycle().value

            HeartBeatApp(heartBeat = heartBeatText,
                         enabled = viewModel.enabled.collectAsStateWithLifecycle().value,
                         onEnabledButtonClick = { viewModel.toggleEnabled() })
        }
    }
}

@Composable
private fun HeartBeatApp(
    heartBeat: String,
    enabled: Boolean,
    onEnabledButtonClick: () -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            text = heartBeat
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onEnabledButtonClick() }
        ) {
            val buttonTextId = if (enabled) {
               "Stop"
            } else {
                "Start"
            }
            Text(buttonTextId)
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    HeartBeatApp(heartBeat = "0", enabled = false, onEnabledButtonClick = {})
}
