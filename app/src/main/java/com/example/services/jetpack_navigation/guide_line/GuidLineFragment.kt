package com.example.services.jetpack_navigation.guide_line

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.collectLatestLifecycleFlow
import com.example.services.jetpack_navigation.databinding.FragmentGuidLineBinding
import com.example.services.jetpack_navigation.log
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GuidLineFragment : Fragment() {
    private lateinit var binding: FragmentGuidLineBinding
    val vm: GuidLineViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentGuidLineBinding.inflate(layoutInflater)
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
        lifecycleScope.launch {
            collectLatestLifecycleFlow(vm.uiEventsChannel){
                when(it){
                    UiEvents.Next -> {}
                    UiEvents.Back -> findNavController().popBackStack()
                }
            }
        }
    }
}