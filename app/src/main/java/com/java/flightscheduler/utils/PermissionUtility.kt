package com.java.flightscheduler.utils

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object PermissionUtility {
    fun hasPhoneDialPermission(context: Context) = EasyPermissions.hasPermissions(context,Manifest.permission.CALL_PHONE)
}