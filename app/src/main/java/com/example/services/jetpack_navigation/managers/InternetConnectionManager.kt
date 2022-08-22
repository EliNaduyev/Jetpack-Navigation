package com.example.services.jetpack_navigation.managers

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.LiveData
import java.lang.reflect.Method


class InternetConnectionManager(val context: Context) : LiveData<Boolean>() {
    private val TAG = "InternetConnectionLiveData"

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Log.d(TAG, "onAvailable: $network")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            if (hasInternetCapability == true)
                validNetworks.add(network)

            checkValidNetworks()
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "onLost: $network")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    private fun checkValidNetworks() {
        if(validNetworks.size > 0){
            Log.d(TAG, "App Have Internet")
            postValue(true) // have internet
        }
        else // checking which services is on and off
            postValue(false)
    }

//    var isUserAskedTurnOffWfi = false
//    fun onInternetConnectionLost(): Boolean {
//        val isCellularInternetTurnedOn = isCellularInternetTurnedOn()
//        val isWifiIsTurnedOn = isWifiIsTurnedOn()
//
//        if(isWifiIsTurnedOn && isCellularInternetTurnedOn) { // 10
//            Timber.e("Wifi and Cellular services is ON but no Internet")
//            isUserAskedTurnOffWfi = true
//            return DialogType.CommunicationErrorUseCellular
//        }
//        else if(isWifiIsTurnedOn && !isCellularInternetTurnedOn){ // 10
//            Timber.e("Wifi is On and Cellular Off try to use Cellular")
//            return DialogType.CommunicationErrorUseCellular
//        }
//        else if(!isUserAskedTurnOffWfi && !isWifiIsTurnedOn && isCellularInternetTurnedOn){ // 20
//            Timber.e("Wifi is Off and Cellular On try to use Wifi")
//            return DialogType.CommunicationErrorUseWifi
//        }
//        else {
//            Timber.e("Wifi and Cellular services is Off")
//            isUserAskedTurnOffWfi = false
//            return DialogType.CommunicationErrorPleaseTryAgainWhenRestored // 40
//        }
//    }

    private fun isCellularInternetTurnedOn(): Boolean {
        return try {
            val cmClass = Class.forName(cm.javaClass.name)
            val method: Method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true
            val mobileDataEnabled = method.invoke(cm) as Boolean
            Log.d(TAG, "isWifiIsTurnedOn - $mobileDataEnabled")
            mobileDataEnabled
        } catch (e: Exception) {
            Log.d(TAG, "isWifiIsTurnedOn - Error")
            false
        }
    }

    private fun isWifiIsTurnedOn(): Boolean {
        val wifi: WifiManager? = context.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        return wifi?.isWifiEnabled ?: false
    }
}