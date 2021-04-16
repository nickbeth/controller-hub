package com.spacelynx.controllerhub.utils

import android.view.InputDevice

class ControllerId {
  private fun getGameControllerIds(): List<Int> {
    val gameControllerDeviceIds = mutableListOf<Int>()
    val deviceIds = InputDevice.getDeviceIds()
    deviceIds.forEach { deviceId ->
      InputDevice.getDevice(deviceId).apply {

        // Verify that the device has gamepad buttons, control sticks, or both.
        if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
          || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
          // This device is a game controller. Store its device ID.
          gameControllerDeviceIds
            .takeIf { !it.contains(deviceId) }
            ?.add(deviceId)
        }
      }
    }


    return gameControllerDeviceIds
  }

  private fun checkIds(){
    val controllerIds = getGameControllerIds()
    val vendorIds = listOf<Int>()
    val productIds = listOf<Int>()

    controllerIds.forEach {deviceId ->
      vendorIds.forEach {vendorId ->
        if(InputDevice.getDevice(deviceId).vendorId==vendorId){
          productIds.forEach {productId ->
            if(InputDevice.getDevice(deviceId).productId==productId){

            }
          }
        }
      }
    }
  }
}