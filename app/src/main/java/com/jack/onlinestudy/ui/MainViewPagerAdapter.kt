package com.jack.onlinestudy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jack.onlinestudy.R

class MainViewPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val fragments: Map<Int, Fragment>
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments.get(position)
                ?: error(fragmentActivity.getString(R.string.error_get_fragment))
        }

    }