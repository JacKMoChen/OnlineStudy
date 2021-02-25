package com.jack.login.repo

import androidx.lifecycle.LiveData
import com.jack.login.net.LoginReq
import com.jack.login.net.LoginRsp
import com.jack.login.net.RegisterRsp

/**
 * @description:登录相关操作抽象
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/28 15:43
 */
interface ILoginResource {
   open val registerRsp: LiveData<RegisterRsp>

    open val loginRsp: LiveData<LoginRsp>

    /**
     * 校验手机号
     */
    suspend fun checkRegister(mobile: String)

    /**
     * 手机号已注册，调用登录接口
     */
    suspend fun requestLogin(loginReq: LoginReq)
}