package com.sleepwalker.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.sleepwalker.presentation.models.MainViewModel

@Composable
fun MainView(viewModel: MainViewModel) {
    val heartBeatText = viewModel.heartBeatText.collectAsStateWithLifecycle().value
    val isRunning = viewModel.isRunning.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = Color.White,
            text = "SleepWalker",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        ToggleChip(
            modifier = Modifier.fillMaxWidth(),
            checked = isRunning,
            colors = ToggleChipDefaults.toggleChipColors(),
            onCheckedChange = { enabled -> viewModel.setIsRunning(enabled) },
            label = { Text("Running") },
            toggleControl = {
                Icon(
                    imageVector = ToggleChipDefaults.switchIcon(isRunning),
                    contentDescription = "Running"
                )
            }
        )
        if (isRunning) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Heart Beat",
                    tint = Color.Red
                )
                Text(
                    color = Color.White,
                    text = heartBeatText
                )
            }
        }
        Button(
            onClick = {
                viewModel.setIsRunning(false)
                viewModel.navController.navigate("settings")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text(text = "Settings")
        }
    }
}
