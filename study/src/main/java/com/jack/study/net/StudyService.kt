package com.jack.study.net

import com.jack.service.net.BaseRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StudyService {

    /**
     * 用户学习详情
     */
    @GET("/member/study/info")
    fun getStudyInfo(): Call<BaseRsp>

    /**
     * 获取用户学习过的课程列表
     */
    @GET("/member/courses/studied")
    fun getStudyList(@Query("page") page: Int = 1, @Query("size") size: Int = 10): Call<BaseRsp>

    /**
     * 用户购买的课程
     */
    @GET("/member/courses/bought")
    fun getBoughtCourse(@Query("page") page: Int = 1, @Query("size") size: Int = 10): Call<BaseRsp>

    /**
     * 课程权限
     */
    @GET("/course/authority")
    fun hasPermission(@Query("course_id") courseId: Int): Call<BaseRsp>

    /**
     * 获取章节信息
     */
    @GET("/course/chapter")
    fun getChapters(@Query("course_id") courseId: Int): Call<BaseRsp>

    /**
     * 获取视频播放信息
     */
    @GET("/lesson/play/v2")
    fun getPlayInfo(@Query("key")key:String):Call<BaseRsp>


}