package com.jack.onlinestudy

import com.alibaba.android.arouter.launcher.ARouter
import com.jack.common.BaseApplication
import com.jack.common.ktx.application
import com.jack.course.moduleCourse
import com.jack.home.moduleHome
import com.jack.login.moduleLogin
import com.jack.mine.moduleMine
import com.jack.service.assistant.AssistantApp
import com.jack.service.moduleService
import com.jack.study.moduleStudy
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

class MyApplication : BaseApplication() {
    private val modules= arrayListOf<Module>(
        moduleService, moduleLogin, moduleMine, moduleStudy,moduleCourse,moduleHome
    )

    override fun initConfig() {
        super.initConfig()
        loadKoinModules(modules)
        AssistantApp.initConfig(application)
        ARouter.init(application)
    }
}