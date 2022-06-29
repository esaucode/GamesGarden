package com.esaudev.gamesgarden.util

import android.content.Context
import android.content.pm.PackageManager




object Utilities {

    fun isAppInstalled(packageName: String?, context: Context): Boolean {
        val pm: PackageManager = context.packageManager
        val installed: Boolean = try {
            pm.getPackageInfo(packageName!!, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return installed
    }
}