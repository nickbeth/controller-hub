package com.spacelynx.controllerhub.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.input.InputManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.spacelynx.controllerhub.utils.ControllerHelper

class ContextBarViewModel : ViewModel(), InputManager.InputDeviceListener {
  val contextIcon: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
  }

  val action0text: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
  }
  val action0drawable: MutableLiveData<Drawable> by lazy {
    MutableLiveData<Drawable>()
  }

  val action1text: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
  }
  val action1drawable: MutableLiveData<Drawable> by lazy {
    MutableLiveData<Drawable>()
  }

  fun updateContextIcon() {
    contextIcon.postValue(ControllerHelper.getControllerIcons())
  }

  fun registerGamepadService(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    inputManager.registerInputDeviceListener(this, null)
    updateContextIcon()
  }

  fun unregisterGamepadService(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    inputManager.unregisterInputDeviceListener(this)
  }

  override fun onInputDeviceAdded(deviceId: Int) {
    Log.d("InputDeviceListener", "InputDevice added, updating context icon.")
    updateContextIcon()
  }

  override fun onInputDeviceRemoved(deviceId: Int) {
    Log.d("InputDeviceListener", "InputDevice removed, updating context icon.")
    updateContextIcon()
  }

  override fun onInputDeviceChanged(deviceId: Int) {
    Log.d("InputDeviceListener", "InputDevice changed, updating context icon.")
    updateContextIcon()
  }
}