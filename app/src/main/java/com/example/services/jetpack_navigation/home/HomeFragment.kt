package com.example.services.jetpack_navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.R
import com.example.services.jetpack_navigation.collectLatestLifecycleFlow
import com.example.services.jetpack_navigation.databinding.FragmentHomeBinding
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val vm: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        initLifeCycle()
        initObservers()
        return binding.root
    }

    private fun initLifeCycle(){
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vm
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            vm.uiEventsSharedFlow.collectLatest {
                onEvent(it)
            }
        }

        /**
         * emit NEXT event, the event is stored in the liveData viewModel when we back to this screen
         * the liveData is register again and emit AGAIN the same event even with distinctUntilChanged
         * and probably will distinct only next same events
         */
        lifecycleScope.launch {
            vm.uiEventsLiveData.distinctUntilChanged().observe(viewLifecycleOwner) {
                onEvent(it)
            }
        }

        lifecycleScope.launch {
            collectLatestLifecycleFlow(vm.uiEventsChannel){
                onEvent(it)
            }
        }
    }

    private fun onEvent(uiEvent: UiEvents) {
        when(uiEvent){
            UiEvents.Next -> {
                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            UiEvents.GoToCanvasFlow -> findNavController().navigate(R.id.action_fragment_to_canvas_graph)
        }
    }

}