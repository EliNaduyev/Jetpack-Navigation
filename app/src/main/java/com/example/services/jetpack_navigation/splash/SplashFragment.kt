package com.example.services.jetpack_navigation.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.R
import com.example.services.jetpack_navigation.collectLatestLifecycleFlow
import com.example.services.jetpack_navigation.databinding.FragmentSplashBinding
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    val vm: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        initLifeCycle()
        initObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("${this::class.java.name} - onViewCreated: is called")
    }

    override fun onStop() {
        super.onStop()
        log("${this::class.java.name} - onStop: is called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("${this::class.java.name} - onDestroyView: is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("${this::class.java.name} - onDestroy: is called")
    }

    fun initLifeCycle(){
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * if uiEventsLiveData was arrived in the background he will not lost and the observe callback will be
     * triggered right after the screen is turned on
     */
    fun initObservers() {
//        observeUiEventLiveData()
//        observeUiEventSharedFlow()
//        observeUiEventSharedFlowAccordingLifeCycle()
//        observeUiEventStateFlow()
        observeUiEventChannel()
    }

    /**
     * if the fragment after onStop the liveData observer will wait until the app is back to
     * FOREGROUND and ready to navigate the user to the next screen and the actual
     * navigation will work properly
     */
    private fun observeUiEventLiveData(){
        vm.uiEventsLiveData.observe(viewLifecycleOwner){
            log("observeUiEventLiveData - LiveData event arrived")
            onEvent(it)
        }
    }

    /**
     * in case app launched and user press lockscreen immediately the emit from viewModel will
     * happened after onStop and will collected here, but because the fragment is after onStop
     * the actual navigation will not happened, in the previous liveData example this will not happened
     */
    private fun observeUiEventSharedFlow(){
        lifecycleScope.launch {
            vm.uiEventsSharedFlow.collectLatest {
                log("observeUiEventSharedFlow - SharedFlow event arrived")
                onEvent(it)
            }
        }
    }

    /**
     * Lifecycle.State.STARTED - will ignore events that emitted after onStop
     * Lifecycle.State.CREATED - will act like observeUiEventSharedFlow() example
     */
    private fun observeUiEventSharedFlowAccordingLifeCycle(){
        collectLatestLifecycleFlow(vm.uiEventsSharedFlow){
            log("observeUiEventSharedFlowAccordingLifeCycle - SharedFlow event arrived")
            onEvent(it)
        }
    }

    /**
     * Collect the value when starting observe including the DEFAULT the we MUST to set, LiveData
     * not obligates us to set default value but if we do set it will act the same
     */
    private fun observeUiEventStateFlowAccordingLifeCycle(){
        collectLatestLifecycleFlow(vm.uiEventsStateFlow){
            log("observeUiEventStateFlowAccordingLifeCycle - StateFlow event arrived")
            if(it != null)
                onEvent(it)
            else
                log("observeUiEventStateFlowAccordingLifeCycle - state flow is NULL - default value")
        }
    }

    private fun observeUiEventChannel(){
        collectLatestLifecycleFlow(vm.uiEventsChannel){
            log("observeUiEventChannel - Channel event arrived")
            onEvent(it)
        }
    }

    private fun onEvent(uiEvent: UiEvents) {
        when(uiEvent){
            UiEvents.Next -> {
                log("${this::class.java.name} - Redirecting user to home screen")
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }
    }
}