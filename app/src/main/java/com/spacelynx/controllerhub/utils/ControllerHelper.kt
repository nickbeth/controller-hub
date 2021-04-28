package com.spacelynx.controllerhub.utils

import kotlin.math.min
import android.content.Context
import android.view.InputDevice
import com.spacelynx.controllerhub.R

private const val MAX_ICON_COUNT = 4

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

  private fun getControllerIcon(context: Context, deviceId: Int): String {
    val device = InputDevice.getDevice(deviceId)
    val vendorId = device.vendorId
    val productId = device.productId

    if (vendorId == 0x045E) {//microsoft
      if (productId == 0x0202 || productId == 0x0285 || productId == 0x0289 || productId == 0x02E6) {
        //xbox
        return context.getString(R.string.cc_xbox_360)
      } else if (productId == 0x028E || productId == 0x028F || productId == 0x0291 ||
        productId == 0x02a1 || productId == 0x0719
      ) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      } else if (productId == 0x02D1 || productId == 0x02DD || productId == 0x02E3 ||
        productId == 0x02EA || productId == 0x02FD || productId == 0x02e0 || productId == 0x02ff ||
        productId == 0x0B12 /*maybe xboxone X controller*/) {
        //xbox one
        return context.getString(R.string.cc_xbox_one)
      }
    } else if (vendorId == 0x046d) {//logitech
      if (productId == 0xc21d || productId == 0xc21e || productId == 0xc21f || productId == 0xc242) {
        //xbox360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x0079) {//Shenzhen
      if (productId == 0x18d4) {
        //xbox360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x044f) {//trustmaster
      if (productId == 0xb326) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      } else if (productId == 0xb326) {
        //xbox one
        return context.getString(R.string.cc_xbox_one)
      }
    } else if (vendorId == 0x056e) {//elecom
      if (productId == 0x2004) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x06a3) {//saitek
      if (productId == 0xf51a) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x0738) {//mad catz
      if (productId == 0x4716 || productId == 0x4726 || productId == 0x4736 || productId == 0x4738 ||
        productId == 0xb726 || productId == 0xbeef || productId == 0xcb02 || productId == 0xcb03
      ) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x0e6f) {//Logic3
      if (productId == 0x0113 || productId == 0x011f || productId == 0x0133 || productId == 0x0201 ||
        productId == 0x0213 || productId == 0x021f || productId == 0x0246 || productId == 0x0301 ||
        productId == 0x0346 || productId == 0x0346 || productId == 0x0401 || productId == 0x0413 ||
        productId == 0x0501 || productId == 0xf900
      ) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      } else if (productId == 0x0139 || productId == 0x013a || productId == 0x0146 || productId == 0x0147 ||
        productId == 0x0161 || productId == 0x0162 || productId == 0x0163 || productId == 0x0164 ||
        productId == 0x0165 || productId == 0x0246 || productId == 0x0346
      ) {
        //xbox one
        return context.getString(R.string.cc_xbox_one)
      }
    } else if (vendorId == 0x11c9) {//nacon
      if (productId == 0x55f0) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x1532) {//razer
      if (productId == 0x0037) {
        //xbox 360
        context.getString(R.string.cc_xbox_360)
      } else if (productId == 0x0a03) {
        ///xbox one
        return context.getString(R.string.cc_xbox_one)
      }
    } else if (vendorId == 0x15e4) {//Numark
      if (deviceId == 0x3f00 || deviceId == 0x3f0a || deviceId == 0x3f10) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x1689) {//Razer again?
      if (productId == 0xfd00 || productId == 0xfd01 || productId == 0xfe00) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x1bad) {//Harmonix Music Systems
      if (productId == 0xf016 || productId == 0xf021 || productId == 0xf023 || productId == 0xf025 ||
        productId == 0xf027 || productId == 0xf036 || productId == 0xf501 || productId == 0xf900 ||
        productId == 0xf901 || productId == 0xf903 || productId == 0xfa01 || productId == 0xfd00 ||
        productId == 0xfd01
      ) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      }
    } else if (vendorId == 0x24c6) {//thrustmaster
      if (productId == 0x5300 || productId == 0x5303 || productId == 0x530a || productId == 0x531a ||
        productId == 0x5397 || productId == 0x5500 || productId == 0x550d || productId == 0x5b02 ||
        productId == 0x5d04 || productId == 0xfafe
      ) {
        //xbox 360
        return context.getString(R.string.cc_xbox_360)
      } else if (productId == 0x541a || productId == 0x542a || productId == 0x543a || productId == 0x551a ||
        productId == 0x561a
      ) {
        //xbox one
        return context.getString(R.string.cc_xbox_one)
      }
    } else if (vendorId == 0x05ac) {//Gamesir
      if (productId == 0x3b06) {
        //Gamesir X2 Type-C
        return context.getString(R.string.cc_wii_u)
      }
    }
    // default icon
    return context.getString(R.string.cc_xbox_360)
  }

  fun getControllerIcons(context: Context): String {
    val controllerIds = getGameControllerIds()
    val controllerIcons = StringBuilder()

    controllerIds.takeIf { controllerIds.isNotEmpty() }
      ?.subList(0, min(controllerIds.lastIndex, MAX_ICON_COUNT - 1))
      ?.forEach { deviceId ->
        val device = InputDevice.getDevice(deviceId)
        val controllerObj = ControllerObject(device.vendorId, device.productId)
        var controllerIcon = iconCache[controllerObj]
        if (controllerIcon == null) {
          //cache miss
          controllerIcon = getControllerIcon(context, deviceId)
          iconCache[controllerObj] = controllerIcon
        }
        controllerIcons.append(controllerIcon).append(" ")
      }
    return controllerIcons.toString()
  }

  fun flushIconCache() {
    iconCache.clear()
  }
}

