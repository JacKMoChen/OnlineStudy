package com.jack.course.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jack.course.databinding.ItemFooterCourseBinding

class CourseLoadAdapter(private val adapter: CoursePagerAdapter) : LoadStateAdapter<CourseLoadAdapter.FooterVH>() {
    override fun onBindViewHolder(holder: CourseLoadAdapter.FooterVH, loadState: LoadState) {
        holder.bindState(loadState,adapter)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CourseLoadAdapter.FooterVH {
        val binding = ItemFooterCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FooterVH(binding)
    }

    class FooterVH(private val binding: ItemFooterCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindState(loadState: LoadState, adapter: CoursePagerAdapter) {
            when (loadState) {
                is LoadState.Error -> {
                    binding.clpFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Load Failed,Tap Retry"
                    binding.tvFooterCourse.setOnClickListener {
                        adapter.retry()
                    }
                }
                is LoadState.Loading -> {
                    binding.clpFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Loading"

                }
                is LoadState.NotLoading -> {
                    binding.clpFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.GONE
                }
            }
        }
    }
}