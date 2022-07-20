package com.example.services.jetpack_navigation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val uiEventsLiveData = MutableLiveData<UiEvents>()

    private val _uiEventsSharedFlow = MutableSharedFlow<UiEvents>()
    val uiEventsSharedFlow = _uiEventsSharedFlow.asSharedFlow()

    private val _uiEventsChannel = Channel<UiEvents>()
    val uiEventsChannel = _uiEventsChannel.receiveAsFlow()

    init {
        log("${this::class.java.name} - init is called")
    }

    fun next(){
//        viewModelScope.launch {
//            _uiEventsSharedFlow.emit(UiEvents.Next)
//        }

//        uiEventsLiveData.value = UiEvents.Next

        viewModelScope.launch {
            _uiEventsChannel.send(UiEvents.Next)
        }
    }

    fun canvas(){
        viewModelScope.launch {
            _uiEventsChannel.send(UiEvents.GoToCanvasFlow)
        }
    }

    override fun onCleared() {
        super.onCleared()
        log("${this::class.java.name} - onCleared is called")
    }
}