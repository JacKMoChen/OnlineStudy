package com.jack.mine.ui

import com.jack.common.base.BaseViewModel
import com.jack.mine.repo.MineRepo

class MineViewModel(private val repo: MineRepo) : BaseViewModel() {
    val liveInfo= repo.userInfoRsp

    fun getUserInfo(){
        serverAwait {
            repo.getUserInfo()
        }
    }
}