package com.spacelynx.controllerhub.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.spacelynx.controllerhub.databinding.CardViewBinding

class AppListAdapter(private val appList: List<AppList.AppListItem>) :
  RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {

  inner class AppListViewHolder(val binding: CardViewBinding) :
    RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
    val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AppListViewHolder(binding)
  }

  override fun getItemCount() = appList.size

  override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
    with(holder) {
      with(appList[position]) {
        binding.icon.setImageDrawable(icon)

        holder.itemView.setOnClickListener {
          startIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
              Intent.FLAG_ACTIVITY_NEW_TASK or
              Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
          it.context.startActivity(startIntent)
        }
      }
    }
  }
}