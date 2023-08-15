package com.sleepwalker.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HeartBeatViewModel: ViewModel() {
    private val _heartBeat = MutableStateFlow(0L)

    val heartBeatText = _heartBeat.map {
        heartBeat -> "$heartBeat bpm"
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "0 bpm"
    )

    init {
        getHeartBeatFlow().onEach { heartBeat ->
            _heartBeat.update { it + heartBeat }
        }.launchIn(viewModelScope)
    }

    private fun getHeartBeatFlow(): Flow<Long> {
        return flow {
            while(true) {
                emit(1)
                delay(1000)
            }
        }
    }
}
