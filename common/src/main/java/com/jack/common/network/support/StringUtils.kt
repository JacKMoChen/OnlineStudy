package com.jack.common.network.support

import com.blankj.utilcode.util.EncryptUtils
import com.jack.common.network.config.NET_CONFIG_APPKEY

import java.util.regex.Pattern

object StringUtils {
    /**
     * 中文转 unicode
     *
     * @param string
     * @return
     */
    fun unicodeEncode(string: String): String {
        val utfBytes = string.toCharArray()
        var unicodeBytes = ""
        for (i in utfBytes.indices) {
            var hexB = Integer.toHexString(utfBytes[i].toInt())
            if (hexB.length <= 2) {
                hexB = "00$hexB"
            }
            unicodeBytes = "$unicodeBytes\\u$hexB"
        }
        return unicodeBytes
    }

    /**
     * unicode 转中文
     *
     * @param string
     * @return
     */
    fun unicodeDecode(string: String): String {
        var string = string
        val pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")
        val matcher = pattern.matcher(string)
        var ch: Char
        while (matcher.find()) {
            ch = matcher.group(2).toInt(16).toChar()
            //            Integer.valueOf("", 16);
            string = string.replace(matcher.group(1), ch.toString() + "")
        }
        return string
    }

    /**
     * 解析返回的data数据
     *
     * @param dataStr 未解密的响应数据
     * @return 解密后的数据String
     */
    fun decodeData(dataStr: String?): String? {
        //java代码，无自动null判断，需要自行处理
        return if (dataStr != null) {
            String(
                EncryptUtils.decryptBase64AES(
                    dataStr.toByteArray(), NET_CONFIG_APPKEY.toByteArray(),
                    "AES/CBC/PKCS7Padding",
                    "J#y9sJesv*5HmqLq".toByteArray()
                )
            )
        } else {
            null
        }
    }
}