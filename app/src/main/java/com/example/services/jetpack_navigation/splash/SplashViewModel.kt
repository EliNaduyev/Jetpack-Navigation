package com.example.services.jetpack_navigation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.services.jetpack_navigation.delayOperation
import com.example.services.jetpack_navigation.log

class SplashViewModel: ViewModel() {
    val uiEvents = MutableLiveData<UiEvents>()

    init {
        log("${this::class.java.name} - init is called")
        delayOperation(2000){
            uiEvents.value = UiEvents.Next
        }
    }

    override fun onCleared() {
        super.onCleared()
        log("${this::class.java.name} - onCleared is called")
    }
}