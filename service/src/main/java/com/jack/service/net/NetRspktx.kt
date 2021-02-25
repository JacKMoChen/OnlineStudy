package com.jack.service.net

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.model.DataResult
import com.jack.common.network.model.succeeded
import com.jack.common.network.support.StringUtils
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * @description:网络请求响应数据封装
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/28 19:31
 */
@Keep
data class BaseRsp(
    val code: Int,
    val data: String?,
    val message: String?
) {
    companion object {
        const val SERVER_CODE_FAILED = 0
        const val SERVER_CODE_SUCCESS = 1
    }
}

/**
 *
 * 将网络请求响应的的data转化为实体对象
 */
inline fun <reified T> BaseRsp.toEntity(): T? {
    if (data == null) {
        LogUtils.e("Server Response Josn Ok ,But data=null,$code,$message")
        return null
    }
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return StringUtils.decodeData(this.data) as T
    }

    return kotlin.runCatching {
        GsonUtils.fromJson(StringUtils.decodeData(this.data), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()

}

/**
 * 接口成功但是业务code不是1
 */
@OptIn(ExperimentalContracts::class)
inline fun BaseRsp.onBizFailure(crossinline block: (code: Int, message: String) -> Unit): BaseRsp {
    contract {
        //契约类
        //下面代码最多执行一次，如果不成立会执行其它分支
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    if (code != BaseRsp.SERVER_CODE_SUCCESS) {
        block.invoke(code, message ?: "Error Message Null")
    }
    return this
}

/**
 * 接口成功并且业务code是1
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T> BaseRsp.onBizSuccess(crossinline action: (code: Int, data: T?, message: String?) -> Unit): BaseRsp {
    contract {
        //契约类
        //下面代码最多执行一次，如果不成立会执行其它分支
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }
    if (code == BaseRsp.SERVER_CODE_SUCCESS) {
        action.invoke(code,this.toEntity<T>(),message)
    }
    return this
}

/**
 * @description:
 *  * @param null :
 * @return:
 * @author:         JacKMoChen
 * @time:           2021/1/28 19:48
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onSuccess(action: R.() -> Unit): DataResult<R> {
    contract {
        //契约类
        //下面代码最多执行一次，如果不成立会执行其它分支
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (succeeded) action.invoke((this as DataResult.Success).data)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(action: (exception: Throwable) -> Unit): DataResult<R> {
    contract {
        //契约类
        //下面代码最多执行一次，如果不成立会执行其它分支
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is DataResult.Error) action.invoke(exception)
    return this
}