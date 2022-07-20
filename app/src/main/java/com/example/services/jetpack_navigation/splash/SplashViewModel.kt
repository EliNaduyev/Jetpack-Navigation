package com.example.services.jetpack_navigation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.jetpack_navigation.delayOperation
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    val uiEventsLiveData = MutableLiveData<UiEvents>()

    private val _uiEventsSharedFlow = MutableSharedFlow<UiEvents>()
    val uiEventsSharedFlow = _uiEventsSharedFlow.asSharedFlow()

    val uiEventsStateFlow = MutableStateFlow<UiEvents?>(null)


    init {
        log("${this::class.java.name} - init is called")
//        emitEventUsingSharedFlow()
        emitEventUsingStateFlow()
    }

    private fun emitEventUsingLiveData(){
        delayOperation(3000){
            log("SplashViewModel - LIVE_DATA event arrived")
            uiEventsLiveData.value = UiEvents.Next
        }
    }

    private fun emitEventUsingSharedFlow(){
        delayOperation(3000) {
            viewModelScope.launch {
                log("SplashViewModel - SHARED_FLOW event arrived")
                _uiEventsSharedFlow.emit(UiEvents.Next)
            }
        }
    }

    private fun emitEventUsingStateFlow(){
        delayOperation(3000) {
            viewModelScope.launch {
                log("SplashViewModel - STATE_FLOW event arrived")
                uiEventsStateFlow.value = UiEvents.Next
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        log("${this::class.java.name} - onCleared is called")
    }
}