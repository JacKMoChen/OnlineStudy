package com.jack.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jack.home.databinding.HomeRecycleItemBinding
import com.jack.home.net.HomeClass
import com.jack.home.net.HomeCourse
import com.jack.home.net.HomeTeacher
import com.jack.home.ui.HomeFragment

class HomeAdapter : RecyclerView.Adapter<HomeVH>() {

    private val mList = mutableListOf<HomeItem>()

    fun updateList(list: MutableList<HomeItem>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeVH.createVH(parent)

    override fun onBindViewHolder(holder: HomeVH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}

class HomeVH(private val binding: HomeRecycleItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun createVH(parent: ViewGroup): HomeVH {
            return HomeVH(
                HomeRecycleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(item: HomeItem) {
        binding.title = item.title
        setAdapter(item)
        binding.executePendingBindings()
    }

    private fun setAdapter(item: HomeItem) {
        binding.rvHomeItem.adapter = when (item.type) {
            HomeFragment.TYPE_JOB_CLASS -> {
                //就业班
                binding.rvHomeItem.layoutManager = GridLayoutManager(itemView.context, 2)
                HomeClassAdapter(item.obj as HomeClass)
            }
            HomeFragment.TYPE_NEW_CLASS -> {
                //新上好课
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_LIMIT_FREE -> {
                //限时免费
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_ANDROID -> {
                //Android
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_AI -> {
                //AI人工智能
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_BD -> {
                //大数据精选
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_10w -> {
                //10w学员推荐
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                HomeCourseAdapter(item.obj as HomeCourse)
            }
            HomeFragment.TYPE_TEACHER -> {
                //人气讲师
                binding.rvHomeItem.layoutManager =
                    LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                HomeTeacherAdapter(item.obj as HomeTeacher)
            }
            else -> {
              return
            }
        }
    }

}

data class HomeItem(val type: Int, val title: String?, val obj: Any?)
