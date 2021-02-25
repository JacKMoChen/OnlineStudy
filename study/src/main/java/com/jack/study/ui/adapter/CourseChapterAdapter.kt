package com.jack.study.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.jack.study.databinding.StudyRecycleItemCourseChapterBinding
import com.jack.study.databinding.StudyRecycleItemCourseTitleBinding


class CourseChapterAdapter(private val callback: (LessonSection) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mList = mutableListOf<LessonSection>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LessonSection.ITEM_TYPE_TITLE) TitleVH.create(parent) else CourseVH.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val info = mList[position]
        when {
            holder is CourseVH -> {
                holder.bind(info)
                holder.itemView.setOnClickListener {
                    callback.invoke(info)
                }
            }
            holder is TitleVH -> {
                holder.bind(info.title ?: "Null")
            }
            else -> {
                error("error holder type")
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].viewType
    }

    fun updateList(list: List<LessonSection>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    class CourseVH(private val binding: StudyRecycleItemCourseChapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): CourseVH {
                return CourseVH(
                    StudyRecycleItemCourseChapterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(info: LessonSection) {
            binding.info = info
            binding.executePendingBindings()
        }

    }

    class TitleVH(private val binding: StudyRecycleItemCourseTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): TitleVH {
                return TitleVH(
                    StudyRecycleItemCourseTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }

    }

    data class LessonSection(
        val viewType: Int,
        val title: String?,
        val key: String? = null,
        val tryIt: Boolean = false
    ) {
        val isPlaying = ObservableBoolean(false)

        companion object {
            const val ITEM_TYPE_TITLE = 0
            const val ITEM_TYPE_CONTENT = 1
        }
    }
}