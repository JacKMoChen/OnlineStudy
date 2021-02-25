package com.jack.service.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.daimajia.numberprogressbar.NumberProgressBar
import com.jack.service.R

/**
 * @description:项目适配BindAdapter
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/1/29 11:21
 */

/**
 * ImageView支持glide
 */
@BindingAdapter("app:srcCompat", requireAll = false)
fun imgSrc(iv: ImageView, src: Any?) {
    var imgSrc = src ?: R.drawable.ic_default_header
    if(imgSrc is String && (imgSrc as String).contains("img.cniao5.com",ignoreCase = true) && !(imgSrc as String).contains("http")){
        imgSrc="https:"+imgSrc.toString()
    }
    Glide.with(iv).load(imgSrc).into(iv)
}

/**
 * 图片颜色
 */
@BindingAdapter("app:tint")
fun imgColor2(iv: ImageView, color: Int) {
    if (color == 0) return
    runCatching {
        iv.setColorFilter(iv.resources.getColor(color))
    }.onFailure {
        iv.setColorFilter(color)
    }
}

/**
 * textview字体颜色
 */
@BindingAdapter("android:textColor")
fun tvColor2(tv: TextView, color: Int) {
    if (color == 0) return
    runCatching {
        tv.setTextColor(tv.resources.getColor(color))
    }.onFailure {
        tv.setTextColor(color)
    }
}

@BindingAdapter("app:progress_current")
fun setProgress(pb:NumberProgressBar, progress: Double) {
    pb.progress = ((progress ?: 0.0) * 100).toInt()
}