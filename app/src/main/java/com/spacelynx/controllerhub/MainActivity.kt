package com.spacelynx.controllerhub

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.spacelynx.controllerhub.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: MainActivityBinding
  private lateinit var mainContent: FrameLayout

  @SuppressLint("ClickableViewAccessibility")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mainContent = binding.mainContent
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