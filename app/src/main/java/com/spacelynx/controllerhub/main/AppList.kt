package com.spacelynx.controllerhub.main

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

import android.util.Log

private const val TAG = "AppListBuilder"

object AppList {
  private fun getInstalledApps(context: Context): List<AppListItem> {
    val packageManager: PackageManager = context.packageManager
    val filteredPackages: MutableList<AppListItem> = mutableListOf()
    val defaultActivityIcon = packageManager.defaultActivityIcon

    packageManager.getInstalledPackages(PackageManager.GET_META_DATA).forEach {
      if (context.packageName == it.packageName) {
        return@forEach // Skip own app
      }
      try {
        // Add only apps with application icon that are not system apps
        val intentOfStartActivity = packageManager.getLaunchIntentForPackage(it.packageName)
          ?: return@forEach
        val applicationIcon = packageManager.getActivityIcon(intentOfStartActivity)
        val applicationLabel = packageManager.getApplicationLabel(it.applicationInfo) as String
        if (defaultActivityIcon != applicationIcon ||
          (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0)
        ) {
          filteredPackages.add(AppListItem(applicationLabel, applicationIcon, intentOfStartActivity))
        }
      } catch (e: PackageManager.NameNotFoundException) {
        Log.d(TAG, "Unknown package name " + it.packageName)
      }
    }
    return filteredPackages
  }

  fun loadAppList(context: Context, listener: MainView) {
    // Make this run on a new thread if necessary
    listener.onAppListLoad(getInstalledApps(context))
  }

  data class AppListItem(val title: String, val icon: Drawable, val startIntent: Intent)
}