package com.spacelynx.controllerhub

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    binding.slot0.setText("Start")
    binding.slot0.setOnClickListener {}
    binding.slot1.setText("Options")
    binding.slot1.setOnClickListener {}

    val slot0Icon: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_baseline_wifi_24)
    slot0Icon?.let { binding.slot0.setCompoundDrawablesWithIntrinsicBounds(slot0Icon, null, null, null) }
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