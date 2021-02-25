package com.jack.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.home.databinding.HomeRecycleItemClassBinding
import com.jack.home.databinding.HomeRecycleItemTeacherBinding
import com.jack.home.net.HomeTeacher


class HomeTeacherAdapter(private val mList: List<HomeTeacher.TeacherItem>) :
    RecyclerView.Adapter<HomeTeacherVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeTeacherVH.createVH(parent)

    override fun onBindViewHolder(holder: HomeTeacherVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}

class HomeTeacherVH(private val binding: HomeRecycleItemTeacherBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): HomeTeacherVH {
            return HomeTeacherVH(
                HomeRecycleItemTeacherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(info: HomeTeacher.TeacherItem) {
        binding.teacher=info.logo_url
        binding.course= info.top5_courses?.get(0)?.img_url
        binding.executePendingBindings()
    }

}


