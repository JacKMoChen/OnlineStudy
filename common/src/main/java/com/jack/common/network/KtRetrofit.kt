package com.jack.common.network

import com.jack.common.network.config.HeaderInterceptor
import com.jack.common.network.config.HostInterceptor
import com.jack.common.network.config.HttpLogInterceptor
import com.jack.common.network.config.LocalCookieJar
import com.jack.common.network.support.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object KtRetrofit {
    private val mClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS) //完成请求超时时长，从发起请求到服务器返回数据，默认值0，不限定
        .connectTimeout(10, TimeUnit.SECONDS) //建立连接超时时间
        .readTimeout(10, TimeUnit.SECONDS) //读取服务器返回数据的时长
        .writeTimeout(10, TimeUnit.SECONDS) //向服务器写入数据的时长
        .retryOnConnectionFailure(true) //失败重连
        .followRedirects(false) //重定向
        .cache(Cache(File("sdcard/cache", "okHttp"), 1024))
        .cookieJar(LocalCookieJar()) //持久化cookieJar 或者设置成CookieJar.NO_COOKIES
        .addInterceptor(HostInterceptor())
        .addNetworkInterceptor(HeaderInterceptor())
        .addNetworkInterceptor(HttpLogInterceptor {
            logLevel(HttpLogInterceptor.LogLevel.BODY)
        })
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(mClient)

    private var retrofit: Retrofit? = null

    /**
     * @description:    初始化配置
     * @param:          baseUrl必须/结尾
     * @return:         retrofit实例
     * @author:         JacKMoChen
     * @time:           2021/1/25 17:07
     */
    fun initConfig(baseUrl: String, client: OkHttpClient = mClient): KtRetrofit {
        retrofit = retrofitBuilder.baseUrl(baseUrl).client(client).build()
        return this
    }

    /**
     * @description:    获取retrofit的service对象
     * @param:          service接口类的class
     * @return:         service对象
     * @author:         JacKMoChen
     * @time:           2021/1/25 17:10
     */
    fun <T> getService(serviceClass: Class<T>): T {
        if (retrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化，需要配置baseUrl")
        } else {
            return this.retrofit!!.create(serviceClass)
        }
    }
}