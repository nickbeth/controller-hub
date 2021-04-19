package com.spacelynx.controllerhub.ui

import android.widget.TextView
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.spacelynx.controllerhub.databinding.ContextBarBinding

class ContextBar(contextBarBinding: ContextBarBinding) {
  private val contextIcon: TextView = contextBarBinding.contextIcon
  private val action0: TextView = contextBarBinding.action0
  private val action1: TextView = contextBarBinding.action1

  fun setContextIcon(@StringRes stringResId: Int) {
    contextIcon.setText(stringResId)
  }

  fun setContextIcon(content: String?) {
    contextIcon.text = content
  }

  /**
   * Set context bar actions. For empty actions use 0. At least one context action should always
   * be supplied.
   * @note actions are numbered right-to-left, action0 being the rightmost one.
   */
  fun setContextActions(
      @StringRes action0Text: Int,
      @DrawableRes action0DrawableRes: Int,
      @StringRes action1Text: Int,
      @DrawableRes action1DrawableRes: Int
  ) {
    action0.setText(action0Text)
    action0.setCompoundDrawablesWithIntrinsicBounds(action0DrawableRes, 0, 0, 0)
    if (action1Text == 0) action1.text = null else action1.setText(action1Text)
    action1.setCompoundDrawablesWithIntrinsicBounds(action1DrawableRes, 0, 0, 0)
  }

  /**
   * Set context bar actions. For empty actions use null. At least one context action should always
   * be supplied.
   * @note actions are numbered right-to-left, action0 being the rightmost one.
   */
  fun setContextActions(
      action0Text: String,
      action0Drawable: Drawable,
      action1Text: String?,
      action1Drawable: Drawable?
  ) {
    action0.text = action0Text
    action0.setCompoundDrawablesWithIntrinsicBounds(action0Drawable, null, null, null)
    action1.text = action1Text
    action1.setCompoundDrawablesWithIntrinsicBounds(action1Drawable, null, null, null)
  }
}