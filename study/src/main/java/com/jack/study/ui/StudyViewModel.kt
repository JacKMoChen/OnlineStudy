package com.jack.study.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.asLiveData
import com.jack.common.base.BaseViewModel
import com.jack.service.repo.UserInfo
import com.jack.study.repo.StudyRepo
import com.jack.study.ui.adapter.StudyPagerAdapter

class StudyViewModel(private val repo: StudyRepo): BaseViewModel() {
    val liveStudyInfo= repo.liveStudyInfo
    val liveStudyList= repo.liveStudyList
    val liveBoughtList= repo.liveBoughtList

    val obUserInfo= ObservableField<UserInfo>()

    val adapter= StudyPagerAdapter()

    fun  getStudyData()=serverAwait {
        repo.getStudyInfo()
        repo.getStudyList()
        repo.getBoughtCourse()
    }

    suspend fun pagingData()=repo.pagingData().asLiveData()
}