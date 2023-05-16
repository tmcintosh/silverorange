package com.silverorange.videoplayer.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.silverorange.videoplayer.R

object MockUtilsVersioning {
    fun getCurrentAppVersion(context: Context): String? {
        var appVersion: String? = null
        try {
            val pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("deprecation")
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
            appVersion = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return appVersion
    }

    fun getCurrentAppVersionFullString(context: Context): String {
        var currentAppVersionString = getCurrentAppVersion(context)
        currentAppVersionString = currentAppVersionString ?: ""
        return context.getString(R.string.version) + " " + currentAppVersionString
    }

    fun compareCurrentVersionToNeededVersion(neededVersion: String, currentAppVersion: String): Boolean {
        if (neededVersion.isEmpty() || currentAppVersion.isEmpty()) {
            return false
        }

        val neededVersionSplits = neededVersion.split("\\.".toRegex()).toMutableList()
        val currentAppVersionSplits = currentAppVersion.split("\\.".toRegex()).toList()

        //pad out versions with zeros if missing remotely
        val neededVersionAmounts = 4
        val requiredPaddingSize = neededVersionAmounts - neededVersionSplits.size
        repeat(requiredPaddingSize) {
            neededVersionSplits.add("0")
        }

        var upgradeNeeded = false
        val neededMajorVersion = neededVersionSplits[0].toInt()
        val neededMinorVersion = neededVersionSplits[1].toInt()
        val neededSubVersion = neededVersionSplits[2].toInt()
        val neededBuildVersion = neededVersionSplits[3].toInt()
        val currentAppMajorVersion = currentAppVersionSplits[0].toInt()
        val currentAppMinorVersion = currentAppVersionSplits[1].toInt()
        val currentAppSubVersion = currentAppVersionSplits[2].toInt()
        val currentAppBuildVersion = currentAppVersionSplits[3].toInt()
        if (neededMajorVersion > currentAppMajorVersion) {
            upgradeNeeded = true
        } else if (currentAppMajorVersion == neededMajorVersion) { //equal major version:
            if (neededMinorVersion > currentAppMinorVersion) {
                upgradeNeeded = true
            } else if (currentAppMinorVersion == neededMinorVersion) { //equal minor version:
                if (neededSubVersion > currentAppSubVersion) {
                    upgradeNeeded = true
                } else if (currentAppSubVersion == neededSubVersion) { //equal sub version:
                    if (neededBuildVersion > currentAppBuildVersion) {
                        upgradeNeeded = true
                    }
                }
            }
        }
        return upgradeNeeded
    }
}
