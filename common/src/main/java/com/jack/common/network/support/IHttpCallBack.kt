package com.jack.common.network.support
/**
 * @description: 网络回调接口
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/23 17:26
 */
interface IHttpCallBack {

    /**
     * @description:    请求成功回调
     * @param:
     * @return:
     * @author:         jack
     * @time:           2021/1/23 17:26
     */
    fun onSuccess(data:Any?)

    /**
     * @description:    请求失败回调
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:08
     */
    fun onFailed(error:Any?)
}