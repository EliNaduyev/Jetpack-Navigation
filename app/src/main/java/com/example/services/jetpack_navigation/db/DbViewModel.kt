package com.example.services.jetpack_navigation.db

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DbViewModel(private val userRepo: UserRepo): ViewModel() {
    val uiEvents = MutableLiveData<UiEvents>()
    private val TAG = "DbViewModel"

    init {
        log("${this::class.java.name} - init is called")
        getAllUsers()
    }

    fun addUser(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.addUser(userModel)
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            userRepo.getAllUsers().observeForever {
                Log.d(TAG, "getAllUsers: is updated new list = $it")
            }
        }
    }

}