package com.jack.home.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jack.home.net.BannerListRsp.BannerListRspItem
import com.jack.home.ui.adapter.ImageAdapter.BannerViewHolder
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(mDatas: List<BannerListRspItem?>?) :
    BannerAdapter<BannerListRspItem?, BannerViewHolder>(mDatas) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: BannerListRspItem?,
        position: Int,
        size: Int
    ) {
        Glide.with(holder!!.imageView).load(data!!.img_url).into(holder.imageView)
    }

    class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(
        imageView
    )
}