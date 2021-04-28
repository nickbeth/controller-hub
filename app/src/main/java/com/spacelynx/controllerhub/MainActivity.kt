package com.spacelynx.controllerhub

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

import com.spacelynx.controllerhub.ui.ContextBar
import com.spacelynx.controllerhub.main.ContextBarListener

import com.spacelynx.controllerhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ContextBarListener {

  private lateinit var binding: ActivityMainBinding
  private lateinit var mainContent: ConstraintLayout
  private lateinit var contextBar: ContextBar

  private var shouldAllowBack = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (!isTaskRoot) {
      // Android launched another instance of the root activity into an existing task
      // so just quietly finish and go away, dropping the user back into the activity
      // at the top of the stack (ie: the last state of this task)
      finish()
      return
    }

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mainContent = binding.mainContent
    contextBar = ContextBar(binding.contextBar)

    onContextIconUpdate()
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

  override fun onContextIconUpdate() {
    contextBar.updateContextIcon(this)
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
