package com.jack.common.network

import com.jack.common.network.support.IHttpCallBack

/**
 * @description: 网络请求封装接口
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/23 17:27
 */
interface HttpApi {
    /**
     * @description:    get异步请求封装
     * @param:          
     * @return:         
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:07
     */
    fun get(params: Map<String, Any>,path:String,callBack: IHttpCallBack)

    /**
     * @description:    get同步
     * @param:          
     * @return:         
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:11
     */
    fun getSync(params: Map<String, Any>,path:String):Any{
        return Any()
    }
    
    /**
     * @description:    post异步请求封装
     * @param:
     * @return:         
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:10
     */
    fun post(body:Any,path:String,callBack: IHttpCallBack)
    
    /**
     * @description:    post同步请求封装
     * @param:
     * @return:         
     * @author:         JacKMoChen
     * @time:           2021/1/23 18:12
     */
    fun postSync(body: Any,path:String):Any{
        return Any()
    }

    /**
     * 根据 tag 取消请求
     */
    fun cancelRequest(tag: Any)

    /**
     * 取消所有请求
     */
    fun cancelAllRequest()
}