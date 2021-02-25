package com.jack.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.jack.common.model.SingleLiveData
import com.jack.common.network.support.serverData
import com.jack.login.net.LoginReq
import com.jack.login.net.LoginRsp
import com.jack.login.net.LoginService
import com.jack.login.net.RegisterRsp
import com.jack.service.net.onBizFailure
import com.jack.service.net.onBizSuccess
import com.jack.service.net.onFailure
import com.jack.service.net.onSuccess

/**
 * @description: 登录数据管理类
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/28 15:28
 */
class LoginRepo(private val service: LoginService) : ILoginResource {
    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()

    override val registerRsp: LiveData<RegisterRsp>
        get() = _registerRsp
    override val loginRsp: LiveData<LoginRsp>
        get() = _loginRsp

    /**
     * 校验手机号
     */
    override suspend fun checkRegister(mobile: String) {
        service.isRegister(mobile)
            .serverData()
            .onSuccess {
                onBizFailure { code, message ->
                    LogUtils.w("是否注册 BizFailure $code, $message")
                }
                onBizSuccess<RegisterRsp> { code, data, message ->
                    _registerRsp.setValue(data!!)
                    LogUtils.d("是否注册 onBizSuccess $data")
                    return@onBizSuccess
                }
            }.onFailure {
                LogUtils.w("是否注册 接口onFailure ${it.message}")
            }
    }

    /**
     * 登录请求
     */
    override suspend fun requestLogin(body: LoginReq) {
        service.login(body).serverData()
            .onSuccess {
                onBizFailure { code, message ->
                    LogUtils.w("登录接口 BizFailure $code, $message")
                }
                onBizSuccess<LoginRsp> { code, data, message ->
                    _loginRsp.setValue(data!!)
                    //保存用户信息到数据库
                    LogUtils.d("登录接口 onBizSuccess $data")
                    return@onBizSuccess
                }
            }.onFailure {
                LogUtils.w("登录接口 接口onFailure ${it.message}")
            }
    }
}


