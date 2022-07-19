package com.example.services.jetpack_navigation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.R
import com.example.services.jetpack_navigation.databinding.FragmentSplashBinding
import com.example.services.jetpack_navigation.log
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

    fun initObservers() {
        vm.uiEvents.observe(viewLifecycleOwner){
            when(it){
                UiEvents.Next -> {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//                    val action = Home.toProfilesListFragment(it.isItPerformECGFlow)
//                    findNavController().navigate(action)
                }
            }
        }
    }
}