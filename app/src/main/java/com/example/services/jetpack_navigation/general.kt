package com.example.services.jetpack_navigation

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


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