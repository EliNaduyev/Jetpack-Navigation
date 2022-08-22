package com.example.services.jetpack_navigation.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.services.jetpack_navigation.databinding.FragmentServiceBinding


class ServiceFragment : Fragment() {
    lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(layoutInflater)

        binding.startServiceBtn.setOnClickListener {
            startService()
        }

        binding.stopServiceBtn.setOnClickListener {
            stopService()
        }

        return binding.root
    }

    private fun startForegroundService() {
        Log.d(TAG, "ServiceFragment startForegroundService")
        ContextCompat.startForegroundService(requireContext(), Intent(requireContext(), MyService::class.java))
    }

    private fun startService() {
        Log.d(TAG, "ServiceFragment startService")
        requireActivity().startService(Intent(requireContext(), MyService::class.java))
    }

    private fun stopService(){
        requireActivity().stopService(Intent(requireContext(), MyService::class.java))
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ServiceFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ServiceFragment onDestroy")
    }
}