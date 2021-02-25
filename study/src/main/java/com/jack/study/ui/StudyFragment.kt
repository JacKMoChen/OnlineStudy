package com.jack.study.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.jack.common.base.BaseFragment
import com.jack.service.repo.DbHelper
import com.jack.study.R
import com.jack.study.databinding.FragmentStudyBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description:学习中心
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 11:16
 */
class StudyFragment : BaseFragment() {
    private val viewModel: StudyViewModel by viewModel()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentStudyBinding.bind(view).apply {
            vm = viewModel
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_study
    }

    override fun initData() {
        super.initData()
        DbHelper.getLiveUserInfo(requireContext()).observerKt {
            viewModel.obUserInfo.set(it)
        }

        viewModel.getStudyData()
        viewModel.apply {
            liveStudyList.observerKt {
               // StudiedAdapter adapter.submit(it?.datas?: emptyList())
            }
            lifecycleScope.launchWhenStarted {
                pagingData().observerKt { data ->
                    data?.let {
                        adapter.submitData(lifecycle, data)
                    }
                }
            }
        }
    }
}