package com.example.services.jetpack_navigation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.databinding.FragmentSettingsBinding
import com.example.services.jetpack_navigation.databinding.FragmentSplashBinding
import com.example.services.jetpack_navigation.log
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    val vm: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        initLifeCycle()
        initObservers()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        log("${this::class.java.name} - onDestroy: is called")
    }

    private fun initLifeCycle(){
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vm
    }

    private fun initObservers() {
        vm.uiEvents.observe(viewLifecycleOwner){
            when(it){
                UiEvents.Back -> findNavController().popBackStack()
                UiEvents.Next -> TODO()
            }
        }
    }
}