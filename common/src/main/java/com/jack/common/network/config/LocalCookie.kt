package com.jack.common.network.config

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @description:    持久化cookieJar实现
 * @param:
 * @return:
 * @author:         JacKMoChen
 * @time:           2021/1/24 11:22
 */
internal class LocalCookieJar : CookieJar {
    //cookie本地化存储
    private val cache: MutableList<Cookie> = mutableListOf<Cookie>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //过期cookie
        val invalidCookies: MutableList<Cookie> = mutableListOf<Cookie>()
        //有效cookie
        val validCookies: MutableList<Cookie> = mutableListOf<Cookie>()

        for (cookie in cache) {
            if (cookie.expiresAt < System.currentTimeMillis()) {
                //过期cookie
                invalidCookies.add(cookie)
            } else {
                //有效cookie
                validCookies.add(cookie)
            }
        }
        //从缓存中移除过期cookie
        cache.removeAll(invalidCookies)

        return validCookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        //保存cookie
        cache.addAll(cookies)
    }
}