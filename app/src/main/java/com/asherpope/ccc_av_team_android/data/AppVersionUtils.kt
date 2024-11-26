package com.asherpope.ccc_av_team_android.data

import android.content.Context
import android.content.pm.PackageManager

class AppVersionUtils {
    companion object {
        fun getVersionInfo(context: Context): String {
            try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                val versionName = packageInfo.versionName
                val versionCode = packageInfo.longVersionCode
                return "Version $versionName (Build $versionCode)"
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the exception if package info can't be retrieved
                return "Unknown Version"
            }
        }
    }
}