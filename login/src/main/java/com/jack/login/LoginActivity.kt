package com.jack.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.jack.common.base.BaseActivity
import com.jack.login.databinding.ActivityLoginBinding

@Route(path = "/login/login")
class LoginActivity:BaseActivity<ActivityLoginBinding>() {
    override fun getLayoutRes()=R.layout.activity_login

}