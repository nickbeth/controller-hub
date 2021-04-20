package com.spacelynx.controllerhub.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.spacelynx.controllerhub.databinding.CardViewBinding

class AppListAdapter(private val appList: List<AppItem>) :
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
        binding.title.text = title
        binding.icon.setImageBitmap(icon)

        holder.itemView.setOnClickListener {
          Toast.makeText(
              holder.itemView.context, title,
              Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }
}