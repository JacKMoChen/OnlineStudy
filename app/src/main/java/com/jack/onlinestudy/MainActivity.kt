package com.jack.onlinestudy

import androidx.fragment.app.Fragment
import com.jack.common.base.BaseActivity
import com.jack.common.widget.BnvVp2Mediator
import com.jack.course.ui.CourseFragment
import com.jack.home.ui.HomeFragment
import com.jack.mine.ui.MineContainerFragment
import com.jack.onlinestudy.databinding.ActivityMainBinding
import com.jack.onlinestudy.ui.MainViewPagerAdapter
import com.jack.study.ui.StudyFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutRes() = R.layout.activity_main

    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineContainerFragment()
    )

    override fun initView() {
        super.initView()
        //由于存在fragment重复创建，并且不能左右滑动的问题,使用ViewPager2代替
        //使用FragmentContainerView
        /*val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //setupActionBarWithNavController(navHostFragment.navController)
        mBinding.navView.setupWithNavController(navHostFragment.navController)*/

        //使用fragment
        /* val navController = findNavController(R.id.nav_host_fragment)
         setupActionBarWithNavController(navController)
         mBinding.navView.setupWithNavController(navController)*/

        mBinding.apply {
            viewPager.adapter = MainViewPagerAdapter(this@MainActivity, fragments)
            BnvVp2Mediator(navView, viewPager){bnv, vp2 ->
            //viewPager是否可滑动
            //vp2.isUserInputEnabled=false
            }.attach()
        }
    }

    companion object {
        const val INDEX_HOME = 0
        const val INDEX_COURSE = 1
        const val INDEX_STUDY = 2
        const val INDEX_MINE = 3
    }
}