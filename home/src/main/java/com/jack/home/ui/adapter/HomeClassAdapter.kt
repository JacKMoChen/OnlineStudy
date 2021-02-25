package com.jack.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.home.databinding.HomeRecycleItemClassBinding
import com.jack.home.net.HomeClass


class HomeClassAdapter(private val mList: List<HomeClass.ClassItem>) :
    RecyclerView.Adapter<HomeClassVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeClassVH.createVH(parent)

    override fun onBindViewHolder(holder: HomeClassVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}

class HomeClassVH(private val binding: HomeRecycleItemClassBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): HomeClassVH {
            return HomeClassVH(
                HomeRecycleItemClassBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(info: HomeClass.ClassItem) {
        binding.imgUrl = info.course?.img_url
        binding.executePendingBindings()
    }

}


