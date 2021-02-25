package com.jack.login

import com.jack.common.BaseApplication
import com.jack.common.ktx.application
import com.jack.service.assistant.AssistantApp
import com.jack.service.moduleService
import org.koin.core.context.loadKoinModules

class MyApplication : BaseApplication() {
    override fun initConfig() {
        super.initConfig()
      loadKoinModules(moduleService)
        loadKoinModules(moduleLogin)
    }
}