package com.example.services.jetpack_navigation.guide_line

sealed class UiEvents {
    object Next: UiEvents()
    object Back: UiEvents()
}
