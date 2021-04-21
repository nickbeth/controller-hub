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

class UsbBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED) {
      val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
      Toast.makeText(context, device?.productName + " connected.", Toast.LENGTH_SHORT).show()
      val deviceName = device?.let { getName(it) }

      val launchActivity = {
        ControllerHelper.getGameControllerIds().forEach { deviceId ->
          Log.d(TAG, deviceName + " ?= " + InputDevice.getDevice(deviceId).name)

          if (deviceName == InputDevice.getDevice(deviceId).name) {
            Log.v(TAG, "Controller plugged in, starting activity")
            val startIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)

            startIntent?.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            context.startActivity(startIntent)
            return@forEach
          }
        }
      }
      Handler(Looper.getMainLooper()).postDelayed(launchActivity, 100)
    }
  }

  companion object {
    private fun getName(device: UsbDevice): String {
      return device.manufacturerName + " " + device.productName
    }
  }
}