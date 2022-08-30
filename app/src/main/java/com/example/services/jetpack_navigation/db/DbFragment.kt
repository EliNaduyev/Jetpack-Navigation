package com.example.services.jetpack_navigation.db

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.services.jetpack_navigation.databinding.FragmentDbBinding
import com.example.services.jetpack_navigation.log
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DbFragment : Fragment() {
    private lateinit var binding: FragmentDbBinding
    private val vm: DbViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("${this::class.java.name} - onCreate: is called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentDbBinding.inflate(layoutInflater)
        log("${this::class.java.name} - onCreateView: is called")
        composeInit(vm)
        return binding.root
    }

    private fun composeInit(vm: DbViewModel) {
        binding.composeContent.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ScreenContent(vm)
            }
        }
    }
    
    
    @Composable
    private fun ScreenContent(vm: DbViewModel) {
        Text(text = "test")

        Column(
            Modifier.background(Color(0xFFD1D2D1)),
            /*verticalArrangement = Arrangement.Center*/) {
            Button(onClick = {
                val userModel = UserModel(UUID.randomUUID().toString(), "eli")
                vm.addUser(userModel)
            }) {
                Text(text = "Add User")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("${this::class.java.name} - onDestroy: is called")
    }
}