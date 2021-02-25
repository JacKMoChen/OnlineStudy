package com.jack.login

import android.util.Log
import androidx.databinding.ObservableField
import com.blankj.utilcode.util.LogUtils
import com.jack.common.base.BaseViewModel
import com.jack.login.net.LoginReq
import com.jack.login.repo.ILoginResource
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * @description:登录viewModel
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/28 11:51
 */
class LoginViewModel(private val loginRepo: ILoginResource) : BaseViewModel() {
    val mobile = ObservableField<String>()
    val password = ObservableField<String>()

    val liveRegisterRsp=loginRepo.registerRsp
    val liveLoginRsp = loginRepo.loginRsp

    val catchEx = CoroutineExceptionHandler { coroutineContext, throwable ->
        LogUtils.e("异常 ${throwable.message}")
    }

    /**
     * @description:    登录
     * 1.判断是否注册，2.登录
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/28 13:39
     */
    fun goLogin() {
        Log.d("LoginViewModel", "登录")
        mobile.get() ?: return
        checkRegister(mobile.get().toString())
    }

    private fun checkRegister(account: String) {
        serverAwait {
            loginRepo.checkRegister(account)
        }
    }

    fun requestLogin() {
        val mobile = mobile.get() ?: return
        val pwd = password.get() ?: return
        serverAwait {
            loginRepo.requestLogin(LoginReq(mobile, pwd))
        }
    }

}