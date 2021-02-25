package com.jack.study.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.blankj.utilcode.util.LogUtils
import com.jack.common.network.support.serverData
import com.jack.service.net.onBizFailure
import com.jack.service.net.onBizSuccess
import com.jack.service.net.onFailure
import com.jack.service.net.onSuccess
import com.jack.study.net.*
import kotlinx.coroutines.flow.Flow

class StudyRepo(private val service: StudyService) {
    val liveStudyInfo = MutableLiveData<StudyInfoRsp>()
    val liveStudyList = MutableLiveData<StudiedRsp>()
    val liveBoughtList = MutableLiveData<BoughtRsp>()
    val liveAuthority = MutableLiveData<StudyAuthority>()
    val liveCourseChapter = MutableLiveData<CourseChapter>()
    val liveVideoPlayInfo = MutableLiveData<VideoPlayInfo>()

    suspend fun getStudyInfo() {
        service.getStudyInfo()
            .serverData()
            .onSuccess {
                onBizSuccess<StudyInfoRsp> { code, data, message ->
                    liveStudyInfo.value = data
                    LogUtils.i("getStudyInfo onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("getStudyInfo onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("getStudyInfo Exception ${it.message}")
            }
    }

    suspend fun getStudyList() {
        service.getStudyList()
            .serverData()
            .onSuccess {
                onBizSuccess<StudiedRsp> { code, data, message ->
                    liveStudyList.value = data
                    LogUtils.i("getStudyList onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("getStudyList onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("getStudyList Exception ${it.message}")
            }
    }

    suspend fun getBoughtCourse() {
        service.getBoughtCourse()
            .serverData()
            .onSuccess {
                onBizSuccess<BoughtRsp> { code, data, message ->
                    liveBoughtList.value = data
                    LogUtils.i("getBoughtCourse onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("getBoughtCourse onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("getBoughtCourse Exception ${it.message}")
            }
    }

    private val pageSize = 10
    fun pagingData(): Flow<PagingData<StudiedRsp.Data>> {
        val config =
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                initialLoadSize = 10,
                maxSize = pageSize * 3
            )
        return Pager(config = config, null) {
            StudyItemPagingSource(service)
        }.flow
    }

    /**
     * 是否有课程的学习权限
     */
    suspend fun hasPermission(courseId: Int) {
        service.hasPermission(courseId)
            .serverData()
            .onSuccess {
                onBizSuccess<StudyAuthority> { code, data, message ->
                    liveAuthority.value = data
                    LogUtils.i("hasPermission onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("hasPermission onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("hasPermission Exception ${it.message}")
            }
    }

    /**
     * 获取对应课程的章节列表
     */
    suspend fun getChapters(courseId: Int) {
        service.getChapters(courseId)
            .serverData()
            .onSuccess {
                onBizSuccess<CourseChapter> { code, data, message ->
                    liveCourseChapter.value = data
                    LogUtils.i("getChapters onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("getChapters onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("getChapters Exception ${it.message}")
            }
    }

    /**
     * 获取视频播放信息
     */
    suspend fun getPlayInfo(key: String) {
        service.getPlayInfo(key)
            .serverData()
            .onSuccess {
                onBizSuccess<VideoPlayInfo> { code, data, message ->
                    liveVideoPlayInfo.value = data
                    LogUtils.i("getPlayInfo onBizSuccess $code , $data")
                }
                onBizFailure { code, message ->
                    LogUtils.w("getPlayInfo onBizFailure $code ,$message")
                }
            }
            .onFailure {
                LogUtils.e("getPlayInfo Exception ${it.message}")
            }
    }
}

/**
 * 已学习的课程分页数据源
 */
class StudyItemPagingSource(private val service: StudyService) :
    PagingSource<Int, StudiedRsp.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StudiedRsp.Data> {
        var result: LoadResult<Int, StudiedRsp.Data> =
            LoadResult.Error<Int, StudiedRsp.Data>(Exception("加载中..."))

        val firstPage = params.key ?: 1
        var nextPageNum = firstPage + 1
        service.getStudyList(page = firstPage, size = params.loadSize)
            .serverData().onSuccess {
                onBizSuccess<StudiedRsp> { code, data, message ->
                    LogUtils.i("getStudyList onBizSuccess $code , $data")
                    val totalPage = data?.total_page ?: 0
                    if (nextPageNum < totalPage) {
                        nextPageNum++
                    }
                    result = LoadResult.Page<Int, StudiedRsp.Data>(
                        data = data?.datas ?: emptyList(),
                        prevKey = null,
                        nextKey = nextPageNum
                    )

                }
                onBizFailure { code, message ->
                    LogUtils.w("getStudyList onBizFailure $code ,$message")
                    result = LoadResult.Error<Int, StudiedRsp.Data>(Exception(message))
                }
            }.onFailure {
                LogUtils.e("getStudyList Exception ${it.message}")
                result = LoadResult.Error<Int, StudiedRsp.Data>(Exception(it.message))
            }

        return result
    }

}
