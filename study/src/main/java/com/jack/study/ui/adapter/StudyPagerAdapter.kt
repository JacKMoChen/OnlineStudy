package com.jack.study.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jack.study.databinding.StudyRecycleItemCourseBinding
import com.jack.study.net.StudiedRsp
import com.jack.study.ui.CoursePlayActivity

/**
 * @description:paging学习课程数据适配器
 * @author:  JacKMoChen
 * @email:   chenjifangvip@163.com
 * @time :   2021/2/1 16:49
 */
class StudyPagerAdapter : PagingDataAdapter<StudiedRsp.Data, StudiedVH>(differCallback) {
    override fun onBindViewHolder(holder: StudiedVH, position: Int) {
        holder.itemView.setOnClickListener {
            getItem(position)?.let { data ->
                CoursePlayActivity.openPlay(
                    holder.itemView.context,
                    data
                )
            }
        }
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudiedVH =
        StudiedVH.createVH(parent)

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<StudiedRsp.Data>() {
            override fun areItemsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class StudiedVH(private val binding: StudyRecycleItemCourseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): StudiedVH {
            return StudiedVH(
                StudyRecycleItemCourseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(info: StudiedRsp.Data) {
        binding.info = info
        binding.executePendingBindings()
    }

}