package com.jack.course.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.jack.common.base.BaseFragment
import com.jack.course.R
import com.jack.course.databinding.FragmentCourseBinding
import com.jack.course.databinding.PopupCourseTypeBinding
import com.jack.course.ui.adapter.CourseLoadAdapter
import com.jack.course.ui.adapter.CoursePagerAdapter
import kotlinx.android.synthetic.main.fragment_course.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.ext.getOrCreateScope

/**
 * @description: 课程
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 10:37
 */
class CourseFragment : BaseFragment() {

    private val viewModel: CourseViewModel by viewModel()
    val adapter = CoursePagerAdapter()
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentCourseBinding.bind(view).apply {
            vm = viewModel
            tlCourse
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_course

    override fun initData() {
        super.initData()
        viewModel.getCourseCategory()
        viewModel.liveCourseCategory.observerKt {

        }
        viewModel.apply {
            liveCourseCategory.observerKt { types ->
                tl_course.removeAllTabs()
                tl_course.addTab(
                    tl_course.newTab().also { tab ->
                        tab.text = getString(R.string.course_all)
                    }
                )
                types?.forEach { item ->
                    tl_course.addTab(
                        tl_course.newTab().also { tab ->
                            tab.text = item.title
                        }
                    )
                }
            }

            tl_course.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val types = liveCourseCategory.value ?: return
                    tl_course.tabCount
                    val index = tab?.position ?: 0
                    val selCode = if (index > 0) {
                        types?.get(index).code ?: "all"
                    } else "all"
                    lifecycleScope.launchWhenCreated {
                        getCourseList(code = selCode).collectLatest {
                            adapter.submitData(it)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            rv_course.adapter = adapter.withLoadStateFooter(CourseLoadAdapter(adapter))

            adapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    //显示加载loading
                } else {
                    //隐藏加载loadingProgressBar
                    val error = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    error?.let {
                        ToastUtils.showShort(it.error.message)
                    }

                }

            }
        }
        popupFilter()
    }

    private lateinit var popWindow: PopupWindow
    private fun popupFilter() {
        val popBinding =
            PopupCourseTypeBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        popWindow = PopupWindow(
            popBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val obCheckPos = ObservableInt(0)
        popBinding.apply {
            pos = obCheckPos
            tvAllType.setOnClickListener {
                obCheckPos.set(0)
                tv_course_type.text = tvAllType.text
                popWindow.dismiss()
                adapter.refresh()
            }

            tvBizType.setOnClickListener {
                obCheckPos.set(1)
                tv_course_type.text = tvBizType.text
                popWindow.dismiss()
                adapter.refresh()
            }

            tvSpecialType.setOnClickListener {
                obCheckPos.set(2)
                tv_course_type.text = tvSpecialType.text
                popWindow.dismiss()
                adapter.refresh()
            }
        }

        tv_course_type.setOnClickListener { v ->
            popWindow.showAsDropDown(v)
        }

    }
}