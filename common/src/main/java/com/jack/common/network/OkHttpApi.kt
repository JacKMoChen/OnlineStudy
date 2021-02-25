package com.jack.common.network

import androidx.collection.SimpleArrayMap
import com.google.gson.Gson
import com.jack.common.network.config.HeaderInterceptor
import com.jack.common.network.config.HttpLogInterceptor
import com.jack.common.network.config.LocalCookieJar
import com.jack.common.network.config.RetryInterceptor
import com.jack.common.network.support.IHttpCallBack

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class OkHttpApi private constructor() : HttpApi {

    private var baseUrl = "http://api.qingyunke.com/"
    private val maxRetry = 0

    // 存储请求，用于取消
    private val callMap = SimpleArrayMap<Any, Call>()

    private val defaultClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS) //完成请求超时时长，从发起请求到服务器返回数据，默认值0，不限定
        .connectTimeout(10, TimeUnit.SECONDS) //建立连接超时时间
        .readTimeout(10, TimeUnit.SECONDS) //读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS) //向服务器写入数据的时长
        .retryOnConnectionFailure(true) //失败重连
        .followRedirects(false) //重定向
        .cache(Cache(File("sdcard/cache", "okHttp"), 1024))
        .cookieJar(LocalCookieJar()) //持久化cookieJar 或者设置成CookieJar.NO_COOKIES
        .addNetworkInterceptor(HeaderInterceptor())
        .addNetworkInterceptor(RetryInterceptor(maxRetry))
        .addNetworkInterceptor(HttpLogInterceptor {
            logLevel(HttpLogInterceptor.LogLevel.BODY)
        })
        .build()
    private var mClient = defaultClient

    fun getClient() = mClient

    fun initConfig(client: OkHttpClient) {
        this.mClient = client
    }

    companion object {
        private const val TAG = "OkHttpApi"

        @Volatile
        private var api: OkHttpApi? = null

        @Synchronized
        fun getInstance(): OkHttpApi {
            return api ?: OkHttpApi().also { api = it }
        }
    }

    /**
     * @description:    get异步请求
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:46
     */
    override fun get(params: Map<String, Any>, path: String, callBack: IHttpCallBack) {
        val url = "$baseUrl$path"
        val urlBuilder = url.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }
        val request: Request = Request.Builder()
            .get()
            .url(urlBuilder.build())
            .build()

        val newCall = mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        //发起请求
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callBack.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callBack.onSuccess(response)
            }

        })
    }

    //使用协程get请求，runBlocking或者suspend
    fun get(params: Map<String, Any>, path: String) = runBlocking {
        val url = "$baseUrl$path"
        val urlBuilder = url.toHttpUrl().newBuilder()
        params.forEach { entry ->
            urlBuilder.addEncodedQueryParameter(entry.key, entry.value.toString())
        }
        val request: Request = Request.Builder()
            .get()
            .url(urlBuilder.build())
            .build()

        val newCall = mClient.newCall(request)
        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        //发起请求
        newCall.call()
    }

    private suspend fun Call.call(async: Boolean = true): Response {
        return suspendCancellableCoroutine { continuation ->
            if (async) {
                enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        if (continuation.isCancelled) {
                            return continuation.resumeWithException(e)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        continuation.resume(response)
                    }
                })
            } else {
                continuation.resume(execute())
            }
            continuation.invokeOnCancellation {
                try {
                    cancel()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * @description:    post异步请求
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:46
     */
    override fun post(body: Any, path: String, callBack: IHttpCallBack) {
        val url = "$baseUrl$path"
        val request: Request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .tag(body)
            .url(url)
            .build()
        val newCall = mClient.newCall(request)

        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        //发起请求
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callBack.onFailed(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                callBack.onSuccess(response)
            }

        })
    }

    //使用协程post请求，runBlocking或者suspend
    suspend fun post(body: Any, path: String) {
        val url = "$baseUrl$path"
        val request: Request = Request.Builder()
            .post(Gson().toJson(body).toRequestBody())
            .tag(body)
            .url(url)
            .build()
        val newCall = mClient.newCall(request)

        //存储请求，用于取消
        callMap.put(request.tag(), newCall)
        //发起请求
        newCall.call()
    }

    /**
     * @description:    取消指定请求
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/24 12:05
     */
    override fun cancelRequest(tag: Any) {
        callMap[tag]?.cancel()
    }

    /**
     * @description:    取消全部请求
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/24 12:32
     */
    override fun cancelAllRequest() {
        for (i in 0 until callMap.size()) {
            callMap.get(callMap.keyAt(i))?.cancel()
        }
    }
}