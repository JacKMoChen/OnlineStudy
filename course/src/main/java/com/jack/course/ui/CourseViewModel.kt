package com.jack.course.ui

import com.jack.common.base.BaseViewModel
import com.jack.course.repo.CourseRepo

class CourseViewModel(private val repo: CourseRepo) : BaseViewModel() {
    val liveCourseCategory = repo.courseCategoryRsp



    fun getCourseCategory() = serverAwait {
        repo.getCourseCategory()
    }

    suspend fun getCourseList(
        course_type: Int = -1,
        code: String = "all",
        difficulty: Int = -1,
        is_free: Int = -1,
        q: Int = -1
    ) =
        repo.getCourseList(course_type, code, difficulty, is_free, q)


}