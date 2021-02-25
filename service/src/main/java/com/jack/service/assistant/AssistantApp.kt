package com.jack.service.assistant

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit

object AssistantApp {
    fun initConfig(application: Application) {
        DoraemonKit.install(
            application, mutableListOf(
                ServiceHostKit()
            )
        )
    }
}