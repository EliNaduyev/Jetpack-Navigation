package com.example.services.jetpack_navigation.canvas2

sealed class UiEvents {
    object Next: UiEvents()
    object Back: UiEvents()
}
