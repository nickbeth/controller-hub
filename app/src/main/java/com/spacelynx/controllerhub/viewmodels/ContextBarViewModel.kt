package com.spacelynx.controllerhub.viewmodels

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spacelynx.controllerhub.receivers.GamepadStatusListener
import com.spacelynx.controllerhub.utils.ControllerHelper

class ContextBarViewModel : ViewModel(), GamepadStatusListener {
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

  override fun onGamepadStatusEvent() {
    contextIcon.value = ControllerHelper.getControllerIcons()
  }
}