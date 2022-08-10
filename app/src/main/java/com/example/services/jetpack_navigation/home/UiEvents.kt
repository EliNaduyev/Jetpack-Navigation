package com.example.services.jetpack_navigation.home

sealed class UiEvents {
    object Next: UiEvents()
    object GoToCanvasFlow: UiEvents()
    object CheckPermissions : UiEvents()
}
