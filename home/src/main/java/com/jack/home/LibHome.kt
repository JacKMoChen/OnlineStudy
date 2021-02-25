package com.jack.home

import com.jack.common.network.KtRetrofit
import com.jack.common.utils.getBaseHost
import com.jack.home.net.HomeService
import com.jack.home.repo.HomeRepo
import com.jack.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val moduleHome: Module = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(HomeService::class.java)
    }

    single {
        HomeRepo(get())
    }


    viewModel { HomeViewModel(get()) }
}
