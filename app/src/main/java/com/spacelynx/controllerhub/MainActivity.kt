package com.spacelynx.controllerhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.spacelynx.controllerhub.databinding.MainActivityBinding
import com.spacelynx.controllerhub.ui.ContextBar

class MainActivity : AppCompatActivity() {

  private lateinit var binding: MainActivityBinding
  private lateinit var mainContent: FrameLayout
  private lateinit var contextBar: ContextBar

  private var shouldAllowBack = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mainContent = binding.mainContent

    binding.exitIcon.setOnClickListener { this.finish() }

    contextBar = ContextBar(binding.contextBar)
    contextBar.setContextIcon(R.string.cc_xbox_one)
    contextBar.setContextActions(
        R.string.OK,
        R.drawable.ic_button_a_18,
        R.string.options,
        R.drawable.ic_button_x_18
    )
  }

  override fun onResume() {
    super.onResume()
    hideSystemUI()
  }

  /**
   * Disable back button unless we explicitly allow it.
   * This prevents the app from closing when back button is pressed.
   */
  override fun onBackPressed() {
    if (shouldAllowBack)
      super.onBackPressed()
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
