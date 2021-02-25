package com.jack.home.net

import com.jack.service.net.BaseRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    /**
     * 首页配置，获取模块组件列表
     */
    @GET("/allocation/component/list")
    fun getModuleItems(@Query("module_id")moduleId: Int): Call<BaseRsp>

    /**
     * 根据页面id获取页面模块
     */
    @GET("/allocation/module/list")
    fun getPageModuleList(@Query("page_id")pageId:Int=1):Call<BaseRsp>

    /**
     * 获取banner图
     */
    @GET("/ad/new/banner/list")
    fun getBannerList():Call<BaseRsp>

}