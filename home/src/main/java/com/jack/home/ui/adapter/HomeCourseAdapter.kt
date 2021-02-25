package com.jack.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.home.databinding.HomeRecycleItemCourseBinding
import com.jack.home.net.HomeCourse


class HomeCourseAdapter(private val mList:List<HomeCourse.CourseItem>) : RecyclerView.Adapter<HomeCourseVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeCourseVH.createVH(parent)

    override fun onBindViewHolder(holder: HomeCourseVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}
class HomeCourseVH(private val binding: HomeRecycleItemCourseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): HomeCourseVH {
            return HomeCourseVH(
                HomeRecycleItemCourseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(info: HomeCourse.CourseItem) {
        binding.info = info
        binding.executePendingBindings()
    }

}

