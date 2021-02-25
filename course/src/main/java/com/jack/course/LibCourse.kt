package com.jack.course

import com.jack.common.network.KtRetrofit
import com.jack.common.utils.getBaseHost
import com.jack.course.net.CourseService
import com.jack.course.repo.CourseRepo
import com.jack.course.ui.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val moduleCourse: Module = module {
    single {
        get<KtRetrofit> { parametersOf(getBaseHost()) }.getService(CourseService::class.java)
    }

    single {
        CourseRepo(get())
    }


    viewModel { CourseViewModel(get()) }
}
