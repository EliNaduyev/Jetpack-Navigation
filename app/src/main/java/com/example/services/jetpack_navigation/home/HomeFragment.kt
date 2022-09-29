package com.example.services.jetpack_navigation.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.services.jetpack_navigation.*
import com.example.services.jetpack_navigation.databinding.FragmentHomeBinding
import com.example.services.jetpack_navigation.managers.PermissionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val vm: HomeViewModel by viewModel()
    lateinit var permissionManager: PermissionManager

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
        initToolbarObservers()
        darkLightModeListener()
//        permissionManager = PermissionManager(requireActivity() as MainActivity)

        return binding.root
    }

    private fun darkLightModeListener(){
        // set app theme based on PHONE settings of dark mode.
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
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
         * the liveData is register again and triggers the last event the was received even with distinctUntilChanged
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

            UiEvents.GoToCanvasFlow -> {
//                findNavController().navigate(R.id.action_fragment_to_canvas_graph) // simple navigation

                val deeplinkY = Uri.parse("any-app://guid_line_frag_screen") // navigation to inner screen in nested graph via deep link
                findNavController().navigate(deeplinkY)


//                findNavController().navigate(R.id.global_action_to_guid_line_screen) // todo this method is not work lead to CRASH
            }
            UiEvents.CheckPermissions -> {
                permissionManager.askPermissions()
            }
        }
    }

    private fun initToolbarObservers(){
        binding.topAppBar.setNavigationOnClickListener {
            showMsg(binding.root, "menu")
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            val v = binding.root
            when (menuItem.itemId) {
                R.id.edit -> {
                    showMsg(v, "edit")
                    true
                }
                R.id.settings -> {
                    showMsg(v, "settings")
                    true
                }
                R.id.send-> {
                    showMsg(v, "send")
                    true
                }

                else -> false
            }
        }
    }

}

fun showMsg(view: View, msg: String){
    Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("My Action") {

    }.show()
}