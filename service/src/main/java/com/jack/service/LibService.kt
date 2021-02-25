package com.jack.service

import com.jack.common.network.KtRetrofit
import org.koin.core.module.Module
import org.koin.dsl.module
/**
 * @description: service模块的koin的module配置
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 14:46
 */
val moduleService:Module= module {
    single <KtRetrofit>{(host:String)->KtRetrofit.initConfig(host)}
}