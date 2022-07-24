package com.example.services.jetpack_navigation.canvas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.jetpack_navigation.guide_line.UiEvents
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CanvasViewModel: ViewModel() {
    private val _uiEventsChannel = Channel<UiEvents>()
    val uiEventsChannel = _uiEventsChannel.receiveAsFlow()

    init {
        log("${this::class.java.name} - init is called")
    }

    fun next(){
        viewModelScope.launch {
            _uiEventsChannel.send(UiEvents.Next)
        }
    }

    fun back(){
        viewModelScope.launch {
            _uiEventsChannel.send(UiEvents.Back)
        }
    }
}