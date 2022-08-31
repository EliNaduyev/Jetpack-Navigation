package com.example.services.jetpack_navigation.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class UserRepo(private val userDao: UserDao) {
    private val TAG = "RoomRepo"
    private val allUsersLiveData = MutableLiveData<List<UserModel>>()

    init {
        startObserverAllUsers()
    }

    private fun startObserverAllUsers(){
        userDao.getAllUsersObservable().observeForever {
            allUsersLiveData.value = it.map { userTable ->
                convertToUserModel(userTable)
            }
        }
    }

    fun addUser(userModel: UserModel) {
        userDao.addUser(convertToUserTable(userModel))
    }

    fun getAllUsers(): LiveData<List<UserModel>> = allUsersLiveData

    private fun convertToUserTable(userModel: UserModel): UserTable {
        return UserTable(userModel.userId, userModel.name)
    }

    private fun convertToUserModel(userTable: UserTable): UserModel {
        return UserModel(userTable)
    }

    fun deleteUser(userModel: UserModel) {
        userDao.deleteUser(convertToUserTable(userModel))
    }
}