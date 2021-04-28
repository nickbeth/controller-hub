package com.spacelynx.controllerhub.receivers

import android.os.Handler
import android.os.Looper
import android.content.Context
import android.content.Intent
import android.content.BroadcastReceiver
import android.util.Log

private const val TAG = "GamepadStatusReceiver"
private const val DETECTION_DELAY: Long = 150

class GamepadStatusReceiver(private val listener: GamepadStatusListener) : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    Log.d(TAG, "Gamepad status event: " + intent.action + ", updating context icon.")
    Looper.myLooper()?.let { Handler(it).postDelayed(listener::onGamepadStatusEvent, DETECTION_DELAY) }
  }
}