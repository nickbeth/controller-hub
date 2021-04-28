package com.spacelynx.controllerhub

import android.app.Application
import android.content.Context
import android.content.res.Resources

class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  companion object {
    private lateinit var instance: MainApplication

    val resources: Resources
      get() = instance.resources

    val context: Context
      get() = instance.applicationContext
  }
}