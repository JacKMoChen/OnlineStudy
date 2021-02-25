package com.jack.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.jack.common.base.BaseFragment
import com.jack.mine.R
import com.jack.mine.databinding.FragmentContainerMineBinding

class MineContainerFragment: BaseFragment() {
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding =
        FragmentContainerMineBinding.bind(view).apply {}

    override fun getLayoutRes()= R.layout.fragment_container_mine
}