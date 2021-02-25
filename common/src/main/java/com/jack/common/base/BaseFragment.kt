package com.jack.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @description:Fragment基类
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/26 17:38
 */
abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layout: Int) : super(layout)

    private var mBinding: ViewDataBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(this.javaClass.simpleName, "onCreateView()")
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = bindView(view, savedInstanceState)
        mBinding?.lifecycleOwner = viewLifecycleOwner
        initConfig()
        initData()
    }

    open fun initConfig() {}

    open fun initData() {}

    abstract fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "onDestroy()")
        mBinding?.unbind()
    }

    /**
     * @description:    扩展函数
     * @param:
     * @return:
     * @author:         JacKMoChen
     * @time:           2021/1/27 10:45
     */
    protected fun <T : Any> LiveData<T>.observerKt(block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { data ->
                block(data)
        })
    }
}