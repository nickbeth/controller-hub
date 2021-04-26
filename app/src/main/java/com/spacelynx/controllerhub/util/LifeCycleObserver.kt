package com.spacelynx.controllerhub.util

import android.app.ActivityManager
import android.content.Context

object LifeCycleObserver {
  fun appInForeground(context: Context): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = activityManager.runningAppProcesses ?: return false //false if no running processes
    return runningAppProcesses.any { it.processName == context.packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }//false if not in foreground or not running
  }
}