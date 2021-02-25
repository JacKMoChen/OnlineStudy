package com.jack.course.repo.data

import androidx.paging.PagingSource
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.support.serverData
import com.jack.course.net.CourseListRsp
import com.jack.course.net.CourseService
import com.jack.service.net.onBizFailure
import com.jack.service.net.onBizSuccess
import com.jack.service.net.onFailure
import com.jack.service.net.onSuccess


class CourseItemPagingSource(
    private val service: CourseService,
    private val course_type: Int = -1,
    private val code: String = "all",
    private val difficulty: Int = -1,
    private val is_free: Int = -1,
    private val q: Int = -1
) :
    PagingSource<Int, CourseListRsp.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CourseListRsp.Data> {
        var result: LoadResult<Int, CourseListRsp.Data> =
            LoadResult.Error<Int, CourseListRsp.Data>(Exception("加载中..."))

        val pageNum = params.key ?: 1
        service.getCourseList(course_type,code,difficulty,is_free,q,pageNum,params.loadSize)
            .serverData().onSuccess {
                onBizSuccess<CourseListRsp> { code, data, message ->
                    LogUtils.i("getCourseList onBizSuccess $code , $data")
                    val totalPage = data?.total_page ?: 0
                    result = LoadResult.Page<Int, CourseListRsp.Data>(
                        data = data?.datas ?: emptyList(),
                        prevKey = if (pageNum == 1) null else pageNum - 1,
                        nextKey = if (pageNum < totalPage) pageNum + 1 else null
                    )
                }
                onBizFailure { code, message ->
                    LogUtils.w("getCourseList onBizFailure $code ,$message")
                    result = LoadResult.Error<Int, CourseListRsp.Data>(Exception(message))
                }
            }.onFailure {
                LogUtils.e("getCourseList Exception ${it.message}")
                result = LoadResult.Error<Int, CourseListRsp.Data>(Exception(it.message))
            }

        return result
    }

}