package com.example.weatherapp.service.helper

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.view.SearchActivity

class PermissionHelper() {
    companion object {
        fun isPermissionEnabled(context: Context, permission: String): Boolean {
            val array = arrayOf(permission)
            return if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchActivity(), array, 100)
                false
            }else{
                true
            }

        }





    }
}