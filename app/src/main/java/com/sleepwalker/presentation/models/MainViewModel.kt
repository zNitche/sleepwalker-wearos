package com.sleepwalker.presentation.models

import android.hardware.Sensor
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sleepwalker.api.ApiClient
import com.sleepwalker.api.SleepwalkerApi
import com.sleepwalker.api.interfaces.BodySensorsLog
import com.sleepwalker.api.interfaces.EnvironmentSensorsLog
import com.sleepwalker.services.ConfigService
import com.sleepwalker.services.SensorsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sensorsService: SensorsService,
    val navController: NavHostController,
    private val configService: ConfigService,
    private val vibrationsManager: VibratorManager
): ViewModel() {
    private val config = configService.loadConfig()

    val isRunning = MutableStateFlow(false)
    var sleepwalkingDetected = MutableStateFlow(false)

    val apiConnectionStatus = MutableStateFlow("500")
    private val logsSessionId = mutableStateOf("")

    private var apiClient: SleepwalkerApi? = null
    private val apiCallsDelay: Long = 2000
    private val vibrator = vibrationsManager.defaultVibrator

    private val _heartBeat = MutableStateFlow(0f)

    private val _accelerationChanged = MutableStateFlow(false)
    private val _accelerationX = mutableStateOf(0f)
    private val _accelerationY = mutableStateOf(0f)
    private val _accelerationZ = mutableStateOf(0f)

    private val _temperature = MutableStateFlow(0f)
    private val _humidity = MutableStateFlow(0f)
    private val _pressure = MutableStateFlow(0f)

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
        apiClient = ApiClient.getInstance(config.apiAddress)
        apiClient?.let { checkAPiConnectivity(it) }
        apiClient?.let { checkIfSleepwalkingDetected(it) }
        apiClient?.let { sendBodySensorsData(it) }
        apiClient?.let { sendEnvironmentSensorsData(it) }

        initSensors()
    }

    fun clearModel() {
        setIsRunning(false)
        viewModelScope.cancel()
    }

    private fun checkAPiConnectivity(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    val response = apiClient.authCheck(config.apiKey)
                    apiConnectionStatus.update { response.code().toString() }
                } catch (_: Exception) {
                    apiConnectionStatus.update { "500" }
                }

                delay(apiCallsDelay)
            }
        }
    }

    private fun checkIfSleepwalkingDetected(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (isRunning.value) {
                    try {
                        val response = apiClient.eventDetected(config.apiKey)
                        val detected = response.code() == 200

                        if (detected) {
                            vibrate()
                        }

                        sleepwalkingDetected.update { detected }
                    } catch (_: Exception) {
                        sleepwalkingDetected.update { false }
                    }

                    delay(apiCallsDelay)
                }
            }
        }
    }

    private fun initLogsSession(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiClient.initLogsSession(config.apiKey)

                if (response.code() == 201) {
                    logsSessionId.value = response.body()?.uuid ?: ""
                } else {
                    isRunning.update { false }
                }
            } catch (_: Exception) {
                isRunning.update { false }
            }
        }
    }

    private fun closeLogsSession(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiClient.closeLogsSession(config.apiKey, logsSessionId.value)

                if (response.code() == 200) {
                    logsSessionId.value = ""
                }
            } catch (_: Exception) {  }
        }
    }

    private fun resetLogging(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiClient.resetLogging(config.apiKey)
            } catch (_: Exception) {  }
        }
    }

    private fun sendBodySensorsData(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (isRunning.value) {
                    try {
                        apiClient.addBodySensorsLog(config.apiKey,
                            logsSessionId.value,
                            BodySensorsLog(_heartBeat.value,
                                           _accelerationX.value,
                                           _accelerationY.value,
                                           _accelerationZ.value)
                        )

                    } catch (_: Exception) {  }
                }

                delay(apiCallsDelay)
            }
        }
    }

    private fun sendEnvironmentSensorsData(apiClient: SleepwalkerApi) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                if (isRunning.value) {
                    try {
                        apiClient.addEnvironmentSensorsLog(config.apiKey,
                            logsSessionId.value,
                            EnvironmentSensorsLog(_temperature.value, _humidity.value)
                        )

                    } catch (_: Exception) {  }
                }

                delay(apiCallsDelay)
            }
        }
    }

    fun resetLoggingAction() {
        apiClient?.let { resetLogging(it) }
    }

    private fun vibrate() {
        val vibrationEffectSingle = VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffectSingle)
    }

    fun setIsRunning(state: Boolean) {
        handleLogsSession(state)
        isRunning.update { state }
        sleepwalkingDetected.update { false }
    }

    private fun handleLogsSession(running: Boolean) {
        if (running) {
            apiClient?.let { initLogsSession(it) }
        } else {
            apiClient?.let { closeLogsSession(it) }
        }
    }

    private fun initSensors() {
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
    private val configService: ConfigService,
    private val vibrationsManager: VibratorManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                sensorsService = sensorsService,
                navController = navController,
                configService = configService,
                vibrationsManager = vibrationsManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
