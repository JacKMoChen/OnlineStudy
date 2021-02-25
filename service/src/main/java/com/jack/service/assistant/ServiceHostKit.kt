package com.jack.service.assistant

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ToastUtils
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.jack.common.utils.*
import com.jack.service.R

/**
 * @description:切换域名工具
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 15:02
 */
class ServiceHostKit : AbstractKit() {
    override val icon: Int
        get() = R.drawable.ic_host
    override val name: Int
        get() = R.string.server_host_dokit

    override fun onAppInit(context: Context?) {

    }

    private val hostMap = mapOf<String, String>(
        "开发环境" to HOST_DEV,
        "QA环境" to HOST_QA,
        "正式环境" to HOST_PRODUCT
    )

    private val hosts = hostMap.values.toTypedArray()
    private val names = hostMap.keys.toTypedArray()

    override fun onClick(context: Context?) {
        val pos = hostMap.values.indexOf(getBaseHost()) % hostMap.size
        context ?: return ToastUtils.showShort("context null")
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.service_change_host))
            .setSingleChoiceItems(
                names, pos
            ) { dialog, which ->
                setBaseHost(hosts[which])
                dialog.dismiss()
            }.show()

    }
}