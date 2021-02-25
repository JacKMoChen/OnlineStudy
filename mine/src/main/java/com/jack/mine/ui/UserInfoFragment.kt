package com.jack.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jack.common.base.BaseFragment
import com.jack.mine.R
import com.jack.mine.databinding.FragmentUserInfoBinding

class UserInfoFragment : BaseFragment() {
    private val args by navArgs<UserInfoFragmentArgs>()
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentUserInfoBinding.bind(view).apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            //修改返回键颜色
            // toolbar.navigationIcon?.setTint(Color.RED)
            btnSave.setOnClickListener {
                findNavController().navigateUp()
            }
            info=args.info
        }
    }

    override fun getLayoutRes() = R.layout.fragment_user_info
}