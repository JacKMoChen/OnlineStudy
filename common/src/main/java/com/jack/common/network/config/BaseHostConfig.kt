package com.jack.common.utils

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.jack.common.BuildConfig

/**
 * @description:baseUrl管理
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/31 20:33
 */

fun getBaseHost(): String {
    return if (BuildConfig.DEBUG) {
       val url= SPStaticUtils.getString(SP_KEY_BASE_HOST,null) ?: HOST_PRODUCT
        LogUtils .d("getBaseHost $url")
        return url
    } else {
        HOST_PRODUCT
    }
}

fun setBaseHost(host: String) {
    SPStaticUtils.put(SP_KEY_BASE_HOST, host)
}

private const val SP_KEY_BASE_HOST = "sp_key_base_host"
const val HOST_DEV = "https://course.api.cniao5.com/" //开发环境
const val HOST_QA = "https://qa.course.api.cniao5.com/" //qa环境
const val HOST_PRODUCT = "https://release.course.api.cniao5.com/" //正式环境