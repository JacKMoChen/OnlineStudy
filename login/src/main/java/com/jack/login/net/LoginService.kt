package com.jack.login.net

import com.jack.service.net.BaseRsp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("accounts/phone/is/register")
    fun isRegister(@Query("mobi") moblie: String): Call<BaseRsp>

    @POST("/accounts/login")
     fun login(@Body req: LoginReq): Call<BaseRsp>
}