package com.jack.common.network.config

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RetryInterceptor(private val maxRetry: Int = 0) : Interceptor {
    private var retriedNum: Int = 0 //已重试次数

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request =chain.request()
        var response: Response=chain.proceed(request)
        while (!response.isSuccessful && retriedNum< maxRetry){
            retriedNum++
            response=chain.proceed(request)
        }
        return response
    }
}