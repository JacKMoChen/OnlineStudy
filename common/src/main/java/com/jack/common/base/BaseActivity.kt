package com.jack.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.jack.common.ktx.bindView
import com.jack.common.ktx.viewLifeCycleOwner

/**
 * @description:Activity基类
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/26 17:33
 */
abstract class BaseActivity<ActBinding : ViewDataBinding> : AppCompatActivity {
    constructor() : super()

    constructor(@LayoutRes layout: Int) : super(layout)

    protected lateinit var mBinding: ActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<ActBinding>(getLayoutRes())
        initConfig()
        initView()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun initView() {}

    open fun initConfig() {}

    open fun initData() {}



    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    protected fun <T : Any> LiveData<T>.observerKt(block: (T?) -> Unit) {
        this.observe(viewLifeCycleOwner, Observer { data ->
            block(data)
        })
    }
}