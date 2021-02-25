package com.jack.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.SPStaticUtils
import com.jack.common.base.BaseFragment
import com.jack.common.network.config.SP_KEY_USER_TOKEN
import com.jack.mine.R
import com.jack.mine.databinding.FragmentMineBinding
import com.jack.service.repo.DbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @description:我的
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 11:16
 */
class MineFragment : BaseFragment() {
    private val viewModel: MineViewModel by viewModel()
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentMineBinding.bind(view).apply {
            vm = viewModel
            ivUserHeader.setOnClickListener {
                val info = viewModel.liveInfo.value
                if (info == null) {
                    ARouter.getInstance().build("/login/login").navigation()
                } else {
                    info?.company = "自由职业"
                    val action = MineFragmentDirections.actionMineFragmentToUserInfoFragment(info)
                    findNavController().navigate(action)
                }
            }

            btnLogout.setOnClickListener {
                DbHelper.deleteUser(requireContext())
                SPStaticUtils.put(SP_KEY_USER_TOKEN, "")
                ARouter.getInstance().build("/login/login").navigation()
            }
        }
    }

    override fun getLayoutRes() = R.layout.fragment_mine

    override fun initData() {
        super.initData()
        DbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.getUserInfo()
        }
    }

}