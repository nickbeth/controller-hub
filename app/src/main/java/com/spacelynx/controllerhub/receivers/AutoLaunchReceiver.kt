package com.spacelynx.controllerhub.receivers

import android.os.Handler
import android.os.Looper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.view.InputDevice
import android.util.Log

import com.spacelynx.controllerhub.utils.ControllerHelper

private const val AUTO_LAUNCH = false
private const val DETECTION_DELAY: Long = 150 // milliseconds

class AutoLaunchReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (AUTO_LAUNCH && intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
      val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
      Log.d(javaClass.simpleName, "${device?.productName} connected.")
      val deviceName = device?.let { getName(it) }

      val launchActivity = {
        ControllerHelper.getGameControllerIds().run {
          //check if the attached usb device is a controller
          forEach { deviceId ->
            Log.d(javaClass.simpleName, "$deviceName ?= ${InputDevice.getDevice(deviceId).name}")

            if (deviceName == InputDevice.getDevice(deviceId).name) {
              //if it is a controller start activity
              Log.v(javaClass.simpleName, "Controller plugged in, starting activity")
              val startIntent =
                context.packageManager.getLaunchIntentForPackage(context.packageName)

              startIntent?.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                  Intent.FLAG_ACTIVITY_NEW_TASK or
                  Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
              context.startActivity(startIntent)
              return@run
            }
          }
        }
      }
      // InputDevices take time to appear when a usb devices is attached
      Handler(Looper.getMainLooper()).postDelayed(launchActivity, DETECTION_DELAY)
    }
  }

  companion object {
    private fun getName(device: UsbDevice): String {
      return device.manufacturerName + " " + device.productName
    }
  }
}