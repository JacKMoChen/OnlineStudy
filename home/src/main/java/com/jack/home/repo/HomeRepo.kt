package com.jack.home.repo

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.model.DataResult
import com.jack.common.network.support.serverData
import com.jack.home.net.BannerListRsp
import com.jack.home.net.HomeModule
import com.jack.home.net.HomeService
import com.jack.service.net.*

class HomeRepo(private val service: HomeService) {
    val liveModules = MutableLiveData<HomeModule>()
    val liveBanner = MutableLiveData<BannerListRsp>()

    suspend fun getPageModuleList() {
        service.getPageModuleList().serverData().onSuccess {
            onBizSuccess<HomeModule> { code, data, message ->
                liveModules.value = data
                LogUtils.i("getPageModuleList onBizSuccess $code , $data")
            }
            onBizFailure { code, message ->
                LogUtils.w("getPageModuleList onBizFailure $code ,$message")
            }
        }.onFailure {
            LogUtils.e("getPageModuleList onFailure ${it.message}")
        }
    }

    suspend fun getModuleItems(id: Int): DataResult<BaseRsp> {
        return service.getModuleItems(id).serverData()
    }

    suspend fun getBannerList() {
        service.getBannerList().serverData().onSuccess {
            onBizSuccess<BannerListRsp> { code, data, message ->
                liveBanner.value = data
                LogUtils.i("getBannerList onBizSuccess $code , $data")
            }
            onBizFailure { code, message ->
                LogUtils.w("getBannerList onBizFailure $code ,$message")
            }
        }.onFailure {
            LogUtils.e("getBannerList onFailure ${it.message}")
        }
    }
}