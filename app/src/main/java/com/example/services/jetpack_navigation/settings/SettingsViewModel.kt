package com.example.services.jetpack_navigation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.services.jetpack_navigation.log

class SettingsViewModel: ViewModel() {
    val uiEvents = MutableLiveData<UiEvents>()

    init {
        log("${this::class.java.name} - init is called")
    }

    fun next(){
        uiEvents.value = UiEvents.Next
    }

    fun back(){
        uiEvents.value = UiEvents.Back
    }

    override fun onCleared() {
        super.onCleared()
        log("${this::class.java.name} - onCleared is called")
    }
}