package com.jack.mine.net

import com.jack.service.net.BaseRsp
import retrofit2.Call
import retrofit2.http.GET

interface MineService {
    @GET("/member/userinfo")
    fun getUserInfo(): Call<BaseRsp>
}