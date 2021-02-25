package com.jack.course.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.support.serverData
import com.jack.course.net.CourseCategoryRsp
import com.jack.course.net.CourseListRsp
import com.jack.course.net.CourseService
import com.jack.course.repo.data.CourseItemPagingSource
import com.jack.service.net.onBizFailure
import com.jack.service.net.onBizSuccess
import com.jack.service.net.onFailure
import com.jack.service.net.onSuccess
import kotlinx.coroutines.flow.Flow

class CourseRepo(private val service: CourseService) {

    val courseCategoryRsp = MutableLiveData<CourseCategoryRsp>()
    val courseListRsp = MutableLiveData<CourseListRsp>()

    /**
     * 课程分类
     */
    suspend fun getCourseCategory() {
        service.getCourseCategory().serverData().onSuccess {
            onBizSuccess<CourseCategoryRsp> { code, data, message ->
                courseCategoryRsp.value = data
                LogUtils.i("getCourseCategory onBizSuccess $code , $data")
            }
            onBizFailure { code, message ->
                LogUtils.w("getCourseCategory onBizFailure $code ,$message")
            }
        }.onFailure {
            LogUtils.e("getCourseCategory onFailure ${it.message}")
        }
    }

    /**
     * 课程列表
     */
    private val pageSize = 20
    suspend fun getCourseList(
        course_type: Int,
        code: String,
        difficulty: Int,
        is_free: Int,
        q: Int): Flow<PagingData<CourseListRsp.Data>> {
        val config =
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                initialLoadSize = 10,
                maxSize = pageSize * 3
            )
        return Pager(config = config, null) {
            CourseItemPagingSource(service,
                course_type,
                code,
                difficulty,
                is_free,
                q)
        }.flow
    }
}

