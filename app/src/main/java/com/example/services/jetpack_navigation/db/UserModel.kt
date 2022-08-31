package com.example.services.jetpack_navigation.db

class UserModel {
    val userId: String
    val name: String

    constructor(userTable: UserTable){
        userId = userTable.userId
        name = userTable.name ?: "Default Name"
    }

    constructor(userId: String, name: String){
        this.userId = userId
        this.name = name
    }

    override fun toString(): String {
        return "userId - $userId, name - $name"
    }
}