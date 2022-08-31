package com.example.services.jetpack_navigation.db

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        val allUsers = vm.allUsers.collectAsState().value

        Text(text = "test")
        Column(
            Modifier.background(Color(0xFFD1D2D1)),
            /*verticalArrangement = Arrangement.SpaceBetween*/) {
            UsersList(allUsers)
            ButtonsSection()
        }
    }

    @Composable
    fun ButtonsSection() {
        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = {
                val userModel = UserModel(UUID.randomUUID().toString(), "eli")
                vm.addUser(userModel)
            }) {
                Text(text = "Add User")
            }

            Button(onClick = {
            }) {
                Text(text = "Delete All")
            }

            Button(onClick = {
            }) {
                Text(text = "Test")
            }
        }
    }

    @Composable
    fun UsersList(userList: List<UserModel>) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            userList.forEach {
                UserRow(it)
            }
        }
    }

    @Composable
    fun UserRow(user: UserModel) {
        Card(modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth()
            .clickable {
                vm.deleteUser(user)
            }) {
            Column(modifier = Modifier.padding(all = 10.dp)) {
                Text(user.name, fontSize = 22.sp, fontWeight = FontWeight.W700, modifier = Modifier.padding(5.dp))
                Text("userId - ${user.userId}", color = Color.Gray, modifier = Modifier.padding(5.dp))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("${this::class.java.name} - onDestroy: is called")
    }
}