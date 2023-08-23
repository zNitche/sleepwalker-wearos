package com.sleepwalker.presentation.models

import android.hardware.Sensor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.sleepwalker.services.SensorsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sensorsService: SensorsService,
    val navController: NavHostController
): ViewModel() {
    val isRunning = MutableStateFlow(false)
    private val _heartBeat = MutableStateFlow(0.0)

    val heartBeatText = _heartBeat.map {
        heartBeat -> "${heartBeat.toInt()} bpm"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000, 5000),
        "0 bpm"
    )

    init {
        viewModelScope.launch {
            isRunning.collect {
                if (it) {
                    initSensors()

                } else {
                    sensorsService.tearDown()
                }
            }
        }
    }

    fun setIsRunning(state: Boolean) {
        isRunning.update { state }
    }

    fun initSensors() {
        sensorsService.initSensorById(Sensor.TYPE_ACCELEROMETER, {})
        sensorsService.initSensorById(Sensor.TYPE_HEART_RATE,
            { values -> processHearBeatValues(values) }
        )
    }

    private fun processHearBeatValues(values: List<Float>) {
        val hbrValue = values[0].toDouble()
        _heartBeat.update { hbrValue }
    }

    override fun onCleared() {
        super.onCleared()
        sensorsService.tearDown()
    }
}


class MainViewModelFactory(
    private val sensorsService: SensorsService,
    private val navController: NavHostController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                sensorsService = sensorsService,
                navController = navController
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
