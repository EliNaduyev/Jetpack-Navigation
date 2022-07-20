package com.example.services.jetpack_navigation

/**
 * Notes Observer(Live Data, Shared Flow etc...):
 * 1. onCleared in viewModel called right before fragment onDestroy
 * 2. init in viewModel called after onCreateView and before onViewCreated
 * 3. vm.uiEvents.observe(viewLifecycleOwner) will not triggered in the background, but right after the screen
 * is turn on it will triggered and get the event that was assign/emitted
 * 4. Channels are the BEST option for events that i don't want miss while app in BACKGROUND
 *
 *
 * Notes For Jetpack Navigation:
 * 1. I think i can't navigate to inner screen in the nested graph only to the nested graph its self
 * and the fragment that will be displayed its the startDestination of the nested graph
 */