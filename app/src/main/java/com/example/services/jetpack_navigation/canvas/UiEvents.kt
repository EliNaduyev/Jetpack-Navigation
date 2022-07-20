package com.example.services.jetpack_navigation.canvas

sealed class UiEvents {
    object Next: UiEvents()
    object Back: UiEvents()
}
