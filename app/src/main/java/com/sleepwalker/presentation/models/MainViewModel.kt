package com.sleepwalker.presentation.models

import android.hardware.Sensor
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sleepwalker.api.ApiClient
import com.sleepwalker.services.ConfigService
import com.sleepwalker.services.SensorsService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainViewModel(
    private val sensorsService: SensorsService,
    val navController: NavHostController,
    private val configService: ConfigService
): ViewModel() {
    private val config = configService.loadConfig()

    val isRunning = MutableStateFlow(false)
    private val _heartBeat = MutableStateFlow(0f)

    private val _accelerationChanged = MutableStateFlow(false)
    private val _accelerationX = mutableStateOf(0f)
    private val _accelerationY = mutableStateOf(0f)
    private val _accelerationZ = mutableStateOf(0f)

    private val _temperature = MutableStateFlow(0f)
    private val _humidity = MutableStateFlow(0f)
    private val _pressure = MutableStateFlow(0f)

    init {
        val apiClient = ApiClient.getInstance(config.apiAddress)
        if (apiClient != null) {
            GlobalScope.launch(Dispatchers.IO) {
                val response = apiClient.authCheck(config.apiKey)
            }
        }
    }

    val heartBeatText = _heartBeat.map {
        heartBeat -> "${heartBeat.toInt()} bpm"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "- bpm"
    )

    val accelerationText = _accelerationChanged.map {
        "${_accelerationX.value.toInt()} ${_accelerationY.value.toInt()} ${_accelerationZ.value.toInt()} m/s^2"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "- - - m/s^2"
    )

    val temperatureText = _temperature.map {
            temp -> "${temp.toInt()} °C"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "- °C"
    )

    val humidityText = _humidity.map {
            hum -> "${hum.toInt()} %"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "- %"
    )

    val pressureText = _pressure.map {
            pressure -> "${pressure.toInt()} hPa"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "- hPa"
    )

    init {
        viewModelScope.launch {
            initSensors()
        }
    }

    fun setIsRunning(state: Boolean) {
        isRunning.update { state }
    }

    fun initSensors() {
        sensorsService.initSensorById(Sensor.TYPE_ACCELEROMETER, { values -> processAccelerationValues(values) })
        sensorsService.initSensorById(Sensor.TYPE_HEART_RATE, { values -> processHearBeatValues(values) })
        sensorsService.initSensorById(Sensor.TYPE_AMBIENT_TEMPERATURE, { values -> processTemperatureValues(values) })
        sensorsService.initSensorById(Sensor.TYPE_RELATIVE_HUMIDITY, { values -> processHumidityValues(values) })
        sensorsService.initSensorById(Sensor.TYPE_PRESSURE, { values -> processPressureValues(values) })
    }

    private fun processHearBeatValues(values: List<Float>) {
        _heartBeat.update { values[0] }
    }

    private fun processAccelerationValues(values: List<Float>) {
        _accelerationChanged.update { true }

        _accelerationX.value = values[0]
        _accelerationY.value = values[1]
        _accelerationZ.value = values[2]

        _accelerationChanged.update { false }
    }

    private fun processTemperatureValues(values: List<Float>) {
        _temperature.update { values[0] }
    }

    private fun processHumidityValues(values: List<Float>) {
        _humidity.update { values[0] }
    }

    private fun processPressureValues(values: List<Float>) {
        _pressure.update { values[0] }
    }

    override fun onCleared() {
        sensorsService.tearDown()
        super.onCleared()
    }
}


class MainViewModelFactory(
    private val sensorsService: SensorsService,
    private val navController: NavHostController,
    private val configService: ConfigService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                sensorsService = sensorsService,
                navController = navController,
                configService = configService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
