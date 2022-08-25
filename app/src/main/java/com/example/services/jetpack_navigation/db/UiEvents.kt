package com.example.services.jetpack_navigation.db


sealed class UiEvents {
    object Next: UiEvents()
    object Back: UiEvents()
}
