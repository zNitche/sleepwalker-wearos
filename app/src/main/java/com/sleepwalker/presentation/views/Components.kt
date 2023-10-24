package com.sleepwalker.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalTextStyle
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.sleepwalker.R
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
        label = { Text("Logging") },
        toggleControl = {
            Icon(
                imageVector = ToggleChipDefaults.switchIcon(isRunning),
                contentDescription = "Logging"
            )
        }
    )
}

@Composable
fun ApiConnectivityStatusChip(status: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = "API Status: $status")
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Api,
                contentDescription = "Api connectivity",
                tint = Color.White,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun HBSChip(hbsText: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = hbsText)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Heart Beat",
                tint = Color.Red,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun AccelerationChip(accelerationText: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = accelerationText)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Flip,
                contentDescription = "Acceleration",
                tint = Color.White,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun EnvironmentTemperatureChip(tempText: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = tempText)
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Thermostat,
                contentDescription = "Environment temperature",
                tint = Color.White,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun RelativeHumidityChip(humidityText: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = humidityText)
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.humidity),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun PressureChip(pressureText: String) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        label = {
            Text(text = pressureText)
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.compress),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.wrapContentSize(align = Alignment.Center)
            )
        },
        colors = ChipDefaults.secondaryChipColors()
    )
}

@Composable
fun SettingsNavButton(viewModel: MainViewModel) {
    Button(
        onClick = {
            viewModel.clearModel()
            viewModel.navController.navigate("settings")
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.secondaryButtonColors()
    ) {
        Text(text = "Settings")
    }
}

@Composable
fun GoBackNavButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("main") },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.secondaryButtonColors()
    ) {
        Text(text = "Go Back")
    }
}

@Composable
fun ApplySettingsButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.secondaryButtonColors()
    ) {
        Text(text = "Apply")
    }
}

@Composable
fun TextInput(text: MutableState<String>, icon: @Composable() () -> Unit) {
    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it},
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .background(Color.DarkGray, RoundedCornerShape(percent = 30))
                    .padding(16.dp)
            ) {
                icon()
                Spacer(Modifier.width(16.dp))
                innerTextField()
            }
        },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

@Composable
fun ApiKeyTextInput(text: MutableState<String>) {
    TextInput(text, { Icon(Icons.Default.Lock, contentDescription = null) })
}

@Composable
fun ApiAddressTextInput(text: MutableState<String>) {
    TextInput(text, { Icon(Icons.Default.Api, contentDescription = null) })
}
