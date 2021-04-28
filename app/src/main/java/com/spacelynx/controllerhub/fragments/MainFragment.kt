package com.spacelynx.controllerhub.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.spacelynx.controllerhub.R

import com.spacelynx.controllerhub.databinding.FragmentMainBinding
import com.spacelynx.controllerhub.main.AppList
import com.spacelynx.controllerhub.main.AppListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding
  private lateinit var navController: NavController

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentMainBinding.bind(view)
    navController = Navigation.findNavController(view)

    binding.exitIcon.setOnClickListener {
      activity?.finish()
    }


    lifecycleScope.launch {
      var appListAdapter: AppListAdapter?
      withContext(Dispatchers.Default) {
        appListAdapter = context?.let { AppListAdapter(AppList.getInstalledApps(it)) }
      }
      binding.appList.adapter = appListAdapter
    }
  }
}