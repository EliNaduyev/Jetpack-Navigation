package com.example.services.jetpack_navigation.files

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.services.jetpack_navigation.managers.FilesManager
import com.example.services.jetpack_navigation.managers.DateManager
import com.example.services.jetpack_navigation.databinding.FragmentFilesBinding

class FilesFragment : Fragment() {
    private val TAG = "FilesFragment"
    lateinit var binding: FragmentFilesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesBinding.inflate(layoutInflater)

        val filesManager = FilesManager(requireContext(), false, true)

        binding.addLogBtn.setOnClickListener {
            val time = DateManager.getCurrentDateWithTime()
            filesManager.makeLog("test", "data - $time", FilesManager.Companion.LogLevel.Low)
        }

        binding.readFileBtn.setOnClickListener {
            val fileTxt = filesManager.readFileFromStorage()
            binding.fileContentTv.text = fileTxt
        }

        binding.clearFileBtn.setOnClickListener {
            filesManager.deleteFile()
        }

        binding.shareFileBtn.setOnClickListener {
            filesManager.x()
        }

        return binding.root
    }




}