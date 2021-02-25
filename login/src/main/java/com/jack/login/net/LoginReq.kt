package com.jack.login.net

import androidx.annotation.Keep

@Keep
data class LoginReq(val mobi:String, val password:String)