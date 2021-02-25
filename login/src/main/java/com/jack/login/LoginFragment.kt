package com.jack.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SPStaticUtils
import com.jack.common.base.BaseFragment
import com.jack.common.network.config.SP_KEY_USER_TOKEN
import com.jack.login.databinding.FragmentLoginBinding
import com.jack.login.net.RegisterRsp
import com.jack.service.repo.DbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {

        return FragmentLoginBinding.bind(view).apply {
            vm = viewModel
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun initConfig() {
        super.initConfig()
        viewModel.apply {
            liveRegisterRsp.observerKt {
                if (it?.is_register == RegisterRsp.FLAG_IS_REGISTER) {
                    requestLogin()
                }
            }

            liveLoginRsp.observerKt {
                //ToastUtils.showShort("登录结果 ${it.toString()}")
                it?.let {
                    DbHelper.insertUserInfo(requireContext(), it)
                    SPStaticUtils.put(SP_KEY_USER_TOKEN, it.token)
                    activity?.finish()
                }
            }
        }
    }

    override fun getLayoutRes() = R.layout.fragment_login
}