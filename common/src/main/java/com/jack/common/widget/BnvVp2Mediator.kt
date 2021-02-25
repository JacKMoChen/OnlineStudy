package com.jack.common.widget

import androidx.core.view.forEachIndexed
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class BnvVp2Mediator(
    private val bnv: BottomNavigationView,
    private val vp2: ViewPager2,
    private val config: ((bnv: BottomNavigationView,vp2: ViewPager2) -> Unit)?=null
) {
    private val map = mutableMapOf<Int, Int>()

    init {
        bnv.menu.forEachIndexed { index, item ->
            map[item.itemId] = index
        }
    }

    fun attach() {
        config?.invoke(bnv,vp2)
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu.getItem(position).itemId
            }
        })

        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item.itemId] ?: error("viewPager2 error")
            true
        }
    }
}