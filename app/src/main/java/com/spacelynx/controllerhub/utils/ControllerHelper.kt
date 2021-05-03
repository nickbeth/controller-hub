package com.spacelynx.controllerhub.utils

import android.view.InputDevice
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.spacelynx.controllerhub.MainApplication
import com.spacelynx.controllerhub.R

private const val MAX_ICON_COUNT = 4 - 1 // Used as index so subtract 1 to count

object ControllerHelper {
  data class ControllerObject(val vid: Int, val pid: Int)

  private var iconCache = HashMap<ControllerObject, String>()

  fun getGameControllerIds(): List<Int> {
    val gameControllerDeviceIds = mutableListOf<Int>()
    val deviceIds = InputDevice.getDeviceIds()
    deviceIds.forEach { deviceId ->
      InputDevice.getDevice(deviceId).apply {

        // Verify that the device has gamepad buttons, control sticks, or both.
        if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
          || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
        ) {
          // This device is a game controller. Store its device ID.
          gameControllerDeviceIds
            .takeIf { !it.contains(deviceId) }
            ?.add(deviceId)
        }
      }
    }
    return gameControllerDeviceIds
  }

  private fun getControllerIcon(deviceId: Int): String {
    val device = InputDevice.getDevice(deviceId)
    val vendorId = device.vendorId
    val productId = device.productId

    when (vendorId) {
      // Microsoft
      0x045e -> when (productId) {
        0x0202, 0x0285, 0x0289, 0x02e6 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
        0x028e, 0x028f, 0x0291, 0x02a1, 0x0719 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
        0x02d1, 0x02dd, 0x02e3, 0x02ea, 0x02fd, 0x02e0, 0x02ff,
        0x0b12 -> return MainApplication.resources.getString(R.string.cc_xbox_one)
      }
      // Logitech
      0x046d -> when (productId) {
        0xc21d, 0xc21e, 0xc21f, 0xc242 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Shenzen
      0x0079 -> when (productId) {
        0x18d4 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Thrustmaster
      0x044f, 0x24c6 -> when (productId) {
        0xb326, 0x5300, 0x5303, 0x530a, 0x531a, 0x5397, 0x5500, 0x550d, 0x5b02,
        0x5d04, 0xfafe -> return MainApplication.resources.getString(R.string.cc_xbox_360)
        0x541a, 0x542a, 0x543a, 0x551a, 0x561a -> return MainApplication.resources.getString(R.string.cc_xbox_one)
      }
      // Elecom
      0x056e -> when (productId) {
        0x2004 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Saitek
      0x06a3 -> when (productId) {
        0xf51a -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Mad Catz
      0x0738 -> when (productId) {
        0x4716, 0x4726, 0x4736, 0x4738, 0xb726, 0xbeef, 0xcb02, 0xcb03 -> return MainApplication.resources.getString(
            R.string.cc_xbox_360
        )
      }
      // Logic3
      0x0e6f -> when (productId) {
        0x0113, 0x011f, 0x0133, 0x0201, 0x0213, 0x021f, 0x0246, 0x0301, 0x0346, 0x0401,
        0x0413, 0x0501, 0xf900 -> return MainApplication.resources.getString(
            R.string.cc_xbox_360
        )
        0x0139, 0x013a, 0x0146, 0x0147, 0x0161, 0x0162, 0x0163, 0x0164,
        0x0165 -> return MainApplication.resources.getString(R.string.cc_xbox_one)
      }
      // Nacon
      0x11c9 -> when (productId) {
        0x55f0 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Razer
      0x1532, 0x1689 -> when (productId) {
        0x0037, 0xfd00, 0xfd01, 0xfe00 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
        0x0a03 -> return MainApplication.resources.getString(R.string.cc_xbox_one)
      }
      // Numark
      0x15e4 -> when (productId) {
        0x3f00, 0x3f0a, 0x3f10 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Hamonix Music Systems
      0x1bad -> when (productId) {
        0xf016, 0xf021, 0xf023, 0xf025, 0xf027, 0xf036, 0xf501, 0xf900, 0xf901,
        0xf903, 0xfa01, 0xfd00, 0xfd01 -> return MainApplication.resources.getString(R.string.cc_xbox_360)
      }
      // Gamesir
      0x05ac -> when (productId) {
        0x3b06 -> return MainApplication.resources.getString(R.string.cc_wii_u)
      }
      // Google
      0x18d1 -> when (productId) {
        0x9400 -> return MainApplication.resources.getString(R.string.cc_stadia)
      }
    }
    // Default (generic icon)
    return MainApplication.resources.getString(R.string.cc_generic)
  }

  fun getControllerIcons(): SpannableStringBuilder {
    val controllerIds = getGameControllerIds()
    val controllerIcons = SpannableStringBuilder()

    controllerIds.forEachIndexed { index, deviceId ->
      val device = InputDevice.getDevice(deviceId)
      val controllerObj = ControllerObject(device.vendorId, device.productId)
      var controllerIcon = iconCache[controllerObj]
      var color: ForegroundColorSpan? = ForegroundColorSpan(Color.rgb(14, 122, 13))

      if (controllerIcon == null) {
        //cache miss
        controllerIcon = getControllerIcon(deviceId)
        iconCache[controllerObj] = controllerIcon
        color = null
      }
      if (index < MAX_ICON_COUNT) controllerIcons.append(controllerIcon, color, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE).append(" ")
    }
    return controllerIcons
  }

  fun flushIconCache() {
    iconCache.clear()
  }
}

