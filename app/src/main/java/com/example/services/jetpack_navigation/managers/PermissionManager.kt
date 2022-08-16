package com.example.services.jetpack_navigation.managers

import android.Manifest
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

/**
 * Created by Eli Naduyev 15.7.22
 */
class PermissionManager(private val activity: AppCompatActivity) {
    private val TAG = "PermissionManager"

    fun askPermissions() {
        permissionsResultLauncher.launch(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS
            )
        )
    }


    /**
     * shouldShowRequestPermissionRationale:
     * True Cases:
     * android 12 and higher - user denied permission only once
     * android 11 and lower - every time user denied permission
     *
     * In this case we explain to the user why we need this permission
     *
     * False Cases:
     * 2. android 12 and higher - denied permission twice or more
     * 1. android 11 and lower - user checked (Don't ask again)
     *
     * Don't ask again check box in android 11 and lower and denied TWICE in android 12 and higher is the same case,
     * need to open the SETTINGS for the user
     */
    private val permissionsResultLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
    { permissions ->
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value

            val shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(activity, permissionName)

            when {
                isGranted -> Log.d(TAG, "$permissionName permission is granted")
                shouldShowRequestPermissionRationale(activity, permissionName) -> Log.d(TAG, "shouldShowRequestPermissionRationale - $shouldShowRequestPermissionRationale")
                else -> {
                    if(isAndroid12Api31()){ // its like user press the check box in android 11 and lower
                        Log.e(TAG, "$permissionName denied TWICE or more times!!")
                    }
                    else
                        Log.e(TAG, "$permissionName user press Don't as again check box")
                }
            }
        }
    }

    private fun isAndroid12Api31(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S
    }
}