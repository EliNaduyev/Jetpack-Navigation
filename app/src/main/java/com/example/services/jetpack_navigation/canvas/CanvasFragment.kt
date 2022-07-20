package com.example.services.jetpack_navigation.canvas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.R
import com.example.services.jetpack_navigation.canvas2.CanvasViewModel2
import com.example.services.jetpack_navigation.canvas2.UiEvents
import com.example.services.jetpack_navigation.databinding.FragmentCanvasBinding
import com.example.services.jetpack_navigation.log
import com.example.services.jetpack_navigation.splash.collectLatestLifecycleFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CanvasFragment : Fragment() {
    private lateinit var binding: FragmentCanvasBinding
    val vm: CanvasViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentCanvasBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        initLifeCycle()
        initObservers()
        return binding.root
    }

    fun initLifeCycle(){
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vm
    }

    private fun initObservers() {
        lifecycleScope.launch {
            collectLatestLifecycleFlow(vm.uiEventsChannel){
                when(it){
                    UiEvents.Next -> {
                        findNavController().navigate(R.id.action_canvasFragment_to_canvasFragment2)
                    }
                    UiEvents.Back -> findNavController().popBackStack()
                }
            }
        }
    }
}