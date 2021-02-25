package com.jack.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * @description: Activity扩展函数
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/26 16:28
 */
//region 扩展函数
/**
 * @description:
 * @param:          layout布局文件
 * @return:         Binding实例
 * @author:         JacKMoChen
 * @time:           2021/1/26 16:28
 */
fun <T : ViewDataBinding> Activity.bindView(layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

/**
 * @description:
 * @param:          
 * @return:         
 * @author:         JacKMoChen
 * @time:           2021/1/26 17:05
 */
fun <T : ViewDataBinding> Activity.bindView(view: View): T? {
    return DataBindingUtil.bind<T>(view)
}

/**
 * @description:    沉浸式状态栏
 * @param:
 * @return:
 * @author:         JacKMoChen
 * @time:           2021/1/26 17:11
 */
fun Activity.immediateStatusBar(){
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * @description:    关闭软键盘
 * @param:          
 * @return:         
 * @author:         JacKMoChen
 * @time:           2021/1/26 17:13
 */
fun Activity.dismissKeyBoard(view:View){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}


//endregion

//region 扩展属性
/**
 * 扩展lifeCycleOwner属性，便于和Fragment之间使用lifeCycleOwner 一致性
 */
val ComponentActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this

/**
 * Activity的扩展字段，便于和Fragment中使用liveData之类的时候，参数一致性
 */
val Activity.context: Context
    get() = this

//endregion

