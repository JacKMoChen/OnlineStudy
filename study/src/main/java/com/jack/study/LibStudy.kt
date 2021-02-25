package com.jack.study

import com.jack.common.network.KtRetrofit
import com.jack.common.utils.getBaseHost
import com.jack.study.net.StudyService
import com.jack.study.repo.StudyRepo
import com.jack.study.ui.CoursePlayViewModel
import com.jack.study.ui.StudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

/**
 * @description:Study模块的koin的module配置
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/2/1 11:31
 */
val moduleStudy: Module = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(StudyService::class.java)
    }

    single {
        StudyRepo(get())
    }


    viewModel {
        StudyViewModel(get())
    }

    viewModel {
        CoursePlayViewModel(get())
    }
}