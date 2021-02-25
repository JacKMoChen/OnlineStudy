package com.jack.common.model

import androidx.lifecycle.LiveData
/**
 * @description:
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/26 17:55
 */
class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {

    init {
        postValue(null)
    }

    companion object {
        fun <T : Any?> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}