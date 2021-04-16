package com.spacelynx.controllerhub

import android.bluetooth.BluetoothManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.spacelynx.controllerhub.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: MainActivityBinding
  private lateinit var mainContent: FrameLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mainContent = binding.mainContent

    binding.exitIcon.setOnClickListener {
      this.finish()
    }

    binding.contextBar.action0.text = "Start"
    binding.contextBar.action0.setOnClickListener {}
    binding.contextBar.action1.text = "Options"
    binding.contextBar.action1.setOnClickListener {}

    binding.contextBar.contextIconError.visibility = View.VISIBLE

  }

  override fun onResume() {
    super.onResume()
    hideSystemUI()

  }



  private fun hideSystemUI() {
    mainContent.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LOW_PROFILE or
          View.SYSTEM_UI_FLAG_FULLSCREEN or
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
          View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
          View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
          View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
  }
}
