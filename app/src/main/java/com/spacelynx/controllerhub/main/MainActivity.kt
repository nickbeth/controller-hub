package com.spacelynx.controllerhub.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

import com.spacelynx.controllerhub.R
import com.spacelynx.controllerhub.databinding.MainActivityBinding
import com.spacelynx.controllerhub.ui.ContextBar
import com.spacelynx.controllerhub.ui.StatusIcons

class MainActivity : AppCompatActivity() {

  private lateinit var binding: MainActivityBinding
  private lateinit var mainContent: FrameLayout
  private lateinit var contextBar: ContextBar
  private lateinit var statusIcons: StatusIcons

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

    binding = MainActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mainContent = binding.mainContent

    binding.exitIcon.setOnClickListener { finish() }

    statusIcons = StatusIcons(binding.statusIcons)

    contextBar = ContextBar(binding.contextBar)
    contextBar.updateContextIcon(this)
    contextBar.contextIcon = ""
    contextBar.setContextActions(
        R.string.OK,
        R.drawable.ic_button_a_18,
        R.string.options,
        R.drawable.ic_button_x_18
    )

    val applist = listOf(
        AppItem("Netflix", null),
        AppItem("The Legend of Zelda: Breath of the Wild", null),
        AppItem("Super Mario Galaxy", null),
        AppItem("Super Mario Odyssey", null),
        AppItem("Skyline", null),
        AppItem("Dolphin", null),
    )

    binding.appList.adapter = AppListAdapter(applist)
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
