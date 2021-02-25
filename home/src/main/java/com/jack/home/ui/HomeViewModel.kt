package com.jack.home.ui

import com.jack.common.base.BaseViewModel
import com.jack.home.repo.HomeRepo

class HomeViewModel(private val repo:HomeRepo):BaseViewModel() {
    val liveBanner = repo.liveBanner
    val liveModules = repo.liveModules
    fun getBannerList() = serverAwait {
        repo.getBannerList()
    }

    fun getModules() = serverAwait {
        repo.getPageModuleList()
    }

    suspend fun getItems(moduleId:Int) = repo.getModuleItems(moduleId)

}