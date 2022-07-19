package com.example.services.jetpack_navigation

/**
 * Notes:
 * 1. onCleared in viewModel called right before fragment onDestroy
 * 2. init in viewModel called after onCreateView and before onViewCreated
 * 3. vm.uiEvents.observe(viewLifecycleOwner) will not triggered in the background, but right after the screen
 * is turn on it will triggered and get the event that was assign/emitted
 */