package com.example.services.jetpack_navigation.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.example.services.jetpack_navigation.MainActivity
import com.example.services.jetpack_navigation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val TAG = "servicelogs"
class MyService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand is called")
//        startForegroundServiceExample()
        workFromCoroutine()
        return START_STICKY
    }

    private fun startForegroundServiceExample(){
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        Log.d(TAG, "startForegroundService is called")
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText("body")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(1, notification)
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind is called")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy is called")
    }


    //todo check if i can simulate this without coroutine

    fun workFromCoroutine(){
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                Log.d(TAG, "work is called")
                delay(3000)
            }
        }
    }
}