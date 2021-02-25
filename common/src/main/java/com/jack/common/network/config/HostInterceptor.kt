package com.jack.common.network.config

import androidx.core.net.toUri
import com.jack.common.utils.getBaseHost
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @description:host拦截器
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/31 21:04
 */
class HostInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val oldHost = originRequest.url.host
        val oldUrlStr = originRequest.url.toString()

        val newHost = getBaseHost().toUri().host ?: oldHost
        val newHostStr = if (newHost == oldHost) {
            oldUrlStr
        } else oldUrlStr.replace(oldHost, newHost)

        val newBuilder = originRequest.newBuilder()
        newBuilder.url(newHostStr)
        return chain.proceed(newBuilder.build())
    }
}