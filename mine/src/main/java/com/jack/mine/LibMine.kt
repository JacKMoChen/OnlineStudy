package com.jack.mine

import com.jack.common.network.KtRetrofit
import com.jack.common.utils.getBaseHost
import com.jack.mine.net.MineService
import com.jack.mine.repo.MineRepo
import com.jack.mine.ui.MineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/**
 * @description: Mine模块的koin的module配置
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 14:46
 */
val moduleMine: Module = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost())}.getService(MineService::class.java)
    }

    single {
        MineRepo(get())
    }


    viewModel { MineViewModel(get()) }
}