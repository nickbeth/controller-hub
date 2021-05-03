package com.spacelynx.controllerhub.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.spacelynx.controllerhub.R

import com.spacelynx.controllerhub.main.AppList
import com.spacelynx.controllerhub.main.AppListAdapter
import androidx.fragment.app.activityViewModels
import com.spacelynx.controllerhub.viewmodels.ContextBarViewModel
import com.spacelynx.controllerhub.databinding.FragmentMainBinding

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding
  private lateinit var navController: NavController
  private val contextBarModel: ContextBarViewModel by activityViewModels()

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    navController = Navigation.findNavController(view)

    binding.exitIcon.setOnClickListener {
      activity?.finish()
    }

    contextBarModel.setContextActions(
        this.requireContext(),
        R.string.start,
        R.drawable.ic_button_a_18,
        R.string.options,
        R.drawable.ic_button_b_18
    )

    lifecycleScope.launch {
      var appListAdapter: AppListAdapter?
      withContext(Dispatchers.Default) {
        appListAdapter = context?.let { AppListAdapter(AppList.getInstalledApps(it)) }
      }
      binding.appList.adapter = appListAdapter
    }
  }
}