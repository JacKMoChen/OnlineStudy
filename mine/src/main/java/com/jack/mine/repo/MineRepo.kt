package com.jack.mine.repo

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.support.serverData
import com.jack.mine.net.MineService
import com.jack.mine.net.UserInfoRsp
import com.jack.service.net.onBizFailure
import com.jack.service.net.onBizSuccess
import com.jack.service.net.onFailure
import com.jack.service.net.onSuccess

class MineRepo(private val service: MineService) {
    val userInfoRsp = MutableLiveData<UserInfoRsp>()

    suspend fun getUserInfo() {
        service.getUserInfo().serverData().onSuccess {
            onBizSuccess<UserInfoRsp> { code, data, message ->
                userInfoRsp.value = data
                LogUtils.i("获取用户信息 onBizSuccess $code , $data")
            }
            onBizFailure { code, message ->
                LogUtils.w("获取用户信息 onBizFailure $code ,$message")
            }
        }.onFailure {
            LogUtils.e("获取用户信息接口异常 ${it.message}")
        }
    }

}