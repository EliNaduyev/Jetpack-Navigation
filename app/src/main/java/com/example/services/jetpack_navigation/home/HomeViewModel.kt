package com.example.services.jetpack_navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        log("${this::class.java.name} - init is called")
    }

    fun next(){
        viewModelScope.launch {
            _uiEvents.emit(UiEvents.Next)
        }
    }

    override fun onCleared() {
        super.onCleared()
        log("${this::class.java.name} - onCleared is called")
    }
}