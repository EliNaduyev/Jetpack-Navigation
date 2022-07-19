package com.example.services.jetpack_navigation.settings

sealed class UiEvents {
    object Next: UiEvents()
    object Back: UiEvents()
}
