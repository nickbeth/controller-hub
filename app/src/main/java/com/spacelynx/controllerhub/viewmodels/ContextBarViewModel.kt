package com.spacelynx.controllerhub.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.input.InputManager
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.InputDevice
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.spacelynx.controllerhub.utils.ControllerHelper

class ContextBarViewModel : ViewModel(), InputManager.InputDeviceListener {
  val contextIcon: MutableLiveData<SpannableStringBuilder> by lazy {
    MutableLiveData<SpannableStringBuilder>()
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

  fun setContextActions(
      context: Context,
      @StringRes action0: Int,
      @DrawableRes action0icon: Int,
      @StringRes action1: Int,
      @DrawableRes action1icon: Int
  ) {
    viewModelScope.launch(Dispatchers.Default) {
      action0text.postValue(context.getString(action0))
      action0drawable.postValue(ResourcesCompat.getDrawable(context.resources, action0icon, context.theme))
      action1text.postValue(context.getString(action1))
      action1drawable.postValue(ResourcesCompat.getDrawable(context.resources, action1icon, context.theme))
    }
  }

  fun updateContextIcon() {
    viewModelScope.launch(Dispatchers.Default) {
      contextIcon.postValue(ControllerHelper.getControllerIcons())
    }
  }

  fun registerGamepadService(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    inputManager.registerInputDeviceListener(this, null)
  }

  fun unregisterGamepadService(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    inputManager.unregisterInputDeviceListener(this)
  }

  override fun onInputDeviceAdded(deviceId: Int) {
    Log.d("InputDeviceListener", "InputDevice added, updating context icon: " + InputDevice.getDevice(deviceId)?.name)
    updateContextIcon()
  }

  override fun onInputDeviceRemoved(deviceId: Int) {
    Log.d("InputDeviceListener", "InputDevice removed, updating context icon.")
    updateContextIcon()
  }

  override fun onInputDeviceChanged(deviceId: Int) {}
}