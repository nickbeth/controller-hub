package com.spacelynx.controllerhub.receiver

import android.os.Handler
import android.os.Looper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.view.InputDevice

import android.widget.Toast
import android.util.Log
import com.spacelynx.controllerhub.util.ControllerHelper

private const val TAG = "UsbBroadcastReceiver"
private const val AUTO_LAUNCH = false

class UsbBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    when (intent.action) {
      UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
        val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
        Toast.makeText(context, device?.productName + " connected.", Toast.LENGTH_SHORT).show()
        val deviceName = device?.let { getName(it) }

        val launchActivity = {
          ControllerHelper.getGameControllerIds().run {
            forEach { deviceId ->
              Log.d(TAG, deviceName + " ?= " + InputDevice.getDevice(deviceId).name)

              if (deviceName == InputDevice.getDevice(deviceId).name) {
                Log.v(TAG, "Controller plugged in, starting activity")
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
        Handler(Looper.getMainLooper()).postDelayed(launchActivity, 100)
      }

      UsbManager.ACTION_USB_DEVICE_DETACHED -> {

      }
    }
  }

  companion object {
    private fun getName(device: UsbDevice): String {
      return device.manufacturerName + " " + device.productName
    }
  }
}