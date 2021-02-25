package com.jack.login.net

import androidx.annotation.Keep
import com.jack.service.repo.UserInfo


/**
 * 是否注册响应
 */
@Keep
data class RegisterRsp(val is_register: Int = FLAG_UN_REGISTER) {
    companion object {
        const val FLAG_IS_REGISTER = 1
        const val FLAG_UN_REGISTER = 0
    }
}

typealias LoginRsp=UserInfo