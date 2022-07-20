package com.example.services.jetpack_navigation

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun log(msg: String){
    Log.d("test", msg)
}

fun Fragment.delayOperation(delay: Long, cb:() -> Unit){
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        cb.invoke()
    }, delay)
}

fun ViewModel.delayOperation(delay: Long, cb:() -> Unit){
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        cb.invoke()
    }, delay)
}

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}