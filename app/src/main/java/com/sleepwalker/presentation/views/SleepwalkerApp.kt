package com.sleepwalker.presentation.views

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.sleepwalker.presentation.models.HeartBeatViewModel

@Composable
fun SleepwalkerApp(
    healthViewModel: HeartBeatViewModel,) {
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
            text = healthViewModel.heartBeatText.collectAsStateWithLifecycle().value
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { healthViewModel.toggleEnabled() }
        ) {
            val buttonTextId = if (healthViewModel.enabled.collectAsStateWithLifecycle().value) {
                "Stop"
            } else {
                "Start"
            }
            Text(buttonTextId)
        }
    }
}
