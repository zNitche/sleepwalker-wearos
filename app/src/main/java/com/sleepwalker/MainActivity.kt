package com.sleepwalker

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


const val PERMISSIONS_REQUEST_CODE = 100
val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.BODY_SENSORS)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasPermissions()) {
            requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }

        setContent {
            val router = RouterProvider((application as MainApplication))
            router.getRouter()
        }
    }

    private fun hasPermissions(): Boolean {
        var state = true

        for (permission in REQUIRED_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                state = false
                break
            }
        }

        return state
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    finishAndRemoveTask()
                }
                return
            }

            else -> {}
        }
    }
}
