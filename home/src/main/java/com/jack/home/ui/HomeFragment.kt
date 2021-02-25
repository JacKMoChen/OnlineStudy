package com.jack.home.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.jack.common.base.BaseFragment
import com.jack.common.base.WebActivity
import com.jack.common.network.model.DataResult
import com.jack.home.R
import com.jack.home.databinding.FragmentHomeBinding
import com.jack.home.net.BannerListRsp
import com.jack.home.net.HomeClass
import com.jack.home.net.HomeCourse
import com.jack.home.net.HomeTeacher
import com.jack.home.ui.adapter.HomeAdapter
import com.jack.home.ui.adapter.HomeItem
import com.jack.home.ui.adapter.ImageAdapter
import com.jack.service.net.*
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @description:首页
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/27 11:13
 */
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModel()
    private val mBanners = mutableListOf<BannerListRsp.BannerListRspItem>()
    private val bannerAdapter: ImageAdapter by lazy { ImageAdapter(mBanners) }
    private val homeAdapter = HomeAdapter()
    private val homeList = mutableListOf<HomeItem>()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding {
        return FragmentHomeBinding.bind(view).apply {
            vm = viewModel
            adapter=homeAdapter
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initConfig() {
        super.initConfig()
        banner_ad.addBannerLifecycleObserver(viewLifecycleOwner)//添加生命周期观察者
            .setAdapter(bannerAdapter).indicator = CircleIndicator(requireContext())
        banner_ad.setOnBannerListener { data, position ->
            WebActivity.openUrl(requireContext(),mBanners[position].redirect_url)
        }
    }

    override fun initData() {
        super.initData()
        viewModel.apply {
            getBannerList()
            getModules()
            liveBanner.observerKt { banners ->
                banners ?: return@observerKt
                mBanners.clear()
                mBanners.addAll(banners)
            }
            val scope = CoroutineScope(SupervisorJob())
            liveModules.observerKt { modules ->
                lifecycleScope.launchWhenCreated {
                    modules?.map { module ->
                        module.id to scope.async { getItems(module.id) }
                    }?.asFlow()?.collect { pair ->
                        parseResult(pair.first, pair.second.await())
                    }
                    homeAdapter.updateList(homeList)
                }
            }
        }
    }

    private fun parseResult(typeId: Int, data: DataResult<BaseRsp>) {
        data.onSuccess {
            when (typeId) {
                TYPE_JOB_CLASS -> {
                    onBizSuccess<HomeClass> { code, data, message ->
                        //就业班
                        homeList.add(HomeItem(typeId,"就业班",data))
                    }
                }
                TYPE_NEW_CLASS -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //新上好课
                        homeList.add(HomeItem(typeId,"新上好课",data))
                    }
                }
                TYPE_LIMIT_FREE -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //限时免费
                        homeList.add(HomeItem(typeId,"限时免费",data))
                    }
                }
                TYPE_ANDROID -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //Android
                        homeList.add(HomeItem(typeId,"Android",data))
                    }
                }
                TYPE_AI -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //AI人工智能
                        homeList.add(HomeItem(typeId,"AI人工智能",data))
                    }
                }
                TYPE_BD -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //大数据精选
                        homeList.add(HomeItem(typeId,"大数据精选",data))
                    }
                }
                TYPE_10w -> {
                    onBizSuccess<HomeCourse> { code, data, message ->
                        //10w学员推荐
                        homeList.add(HomeItem(typeId,"10w学员推荐",data))
                    }
                }
                TYPE_TEACHER -> {
                    onBizSuccess<HomeTeacher> { code, data, message ->
                        //人气讲师
                        homeList.add(HomeItem(typeId,"人气讲师",data))
                    }
                }
                else ->{

                }
            }
            onBizFailure { code, message ->  }
        }.onFailure {

        }
    }

    companion object {
        internal const val TYPE_JOB_CLASS = 1
        internal const val TYPE_NEW_CLASS = 2
        internal const val TYPE_LIMIT_FREE = 3
        internal const val TYPE_ANDROID = 4
        internal const val TYPE_AI = 5
        internal const val TYPE_BD = 6
        internal const val TYPE_10w = 7
        internal const val TYPE_TEACHER = 8
    }
}