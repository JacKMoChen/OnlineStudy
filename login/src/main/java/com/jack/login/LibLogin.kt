package com.jack.login

import com.jack.common.network.KtRetrofit
import com.jack.common.utils.getBaseHost
import com.jack.login.net.LoginService
import com.jack.login.repo.ILoginResource
import com.jack.login.repo.LoginRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * @description: Login模块的koin的module配置
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 14:46
 */
val moduleLogin: Module = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost())}.getService(LoginService::class.java)
    }

    single {
        LoginRepo(get())
    } bind ILoginResource::class


    viewModel { LoginViewModel(get()) }

}