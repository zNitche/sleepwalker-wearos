package com.sleepwalker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sleepwalker.services.HealthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HeartBeatViewModel(
    private val healthService: HealthService
): ViewModel() {
    val enabled = MutableStateFlow(true)
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
            enabled.collect {
                if (it) {
                    healthService.heartRateMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { heartBeat ->
                            _heartBeat.update { heartBeat }
                        }
                }
            }
        }
    }

    fun toggleEnabled() {
        enabled.update { !it }
    }
}


class HeartBeatViewModelFactory(
    private val healthService: HealthService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeartBeatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeartBeatViewModel(
                healthService = healthService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
