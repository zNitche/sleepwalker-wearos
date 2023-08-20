package com.sleepwalker.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.sleepwalker.presentation.models.MainViewModel

@Composable
fun AppTitleText(text: String) {
    Text(
        color = Color.White,
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ProcessingToggle(isRunning: Boolean, setIsRunning: (Boolean) -> Unit) {
    ToggleChip(
        modifier = Modifier.fillMaxWidth(),
        checked = isRunning,
        colors = ToggleChipDefaults.toggleChipColors(),
        onCheckedChange = { enabled -> setIsRunning(enabled) },
        label = { Text("Running") },
        toggleControl = {
            Icon(
                imageVector = ToggleChipDefaults.switchIcon(isRunning),
                contentDescription = "Running"
            )
        }
    )
}

@Composable
fun HBSLabel(hbsText: String) {
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
            text = hbsText
        )
    }
}

@Composable
fun SettingsNavButton(viewModel: MainViewModel) {
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

@Composable
fun GoBackNavButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("main") },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
    ) {
        Text(text = "Go Back")
    }
}