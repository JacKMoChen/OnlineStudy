package com.jack.study.utils

object StudyUtils{
    @JvmStatic
    fun rankStr(rank :Int):String{
        return if (rank>0) "第${rank}名" else "千里之外"
    }
}