package com.spacelynx.controllerhub.main

interface MainView {
  fun onAppListLoad(appList: List<AppList.AppListItem>)

  fun onContextIconUpdate()
}