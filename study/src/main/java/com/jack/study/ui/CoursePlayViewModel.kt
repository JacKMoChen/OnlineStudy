package com.jack.study.ui

import com.jack.common.base.BaseViewModel
import com.jack.study.repo.StudyRepo

class CoursePlayViewModel(private val repo: StudyRepo) : BaseViewModel() {
    val liveAuthority = repo.liveAuthority
    val liveCourseChapter = repo.liveCourseChapter
    val liveVideoPlayInfo = repo.liveVideoPlayInfo

    fun checkPermission(courseId: Int) {
        serverAwait {
            repo.hasPermission(courseId)
        }
    }

    /**
     * 获取对应课程的章节列表
     */
    fun getChapters(courseId: Int) {
        serverAwait {
            repo.getChapters(courseId)
        }
    }

    /**
     * 获取视频播放信息
     */
    fun getPlayInfo(key: String) {
        serverAwait {
            repo.getPlayInfo(key)
        }
    }
}