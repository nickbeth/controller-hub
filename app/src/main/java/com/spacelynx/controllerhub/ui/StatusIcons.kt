package com.spacelynx.controllerhub.ui

import android.widget.ImageView
import android.widget.TextClock
import com.spacelynx.controllerhub.databinding.StatusIconsBinding

class StatusIcons(statusIconsBinding: StatusIconsBinding) {
  val clock: TextClock = statusIconsBinding.time
  val network: ImageView = statusIconsBinding.network
  val battery: ImageView = statusIconsBinding.battery

  init {
    // subscribe to intent callbacks for battery and network icons
  }
}