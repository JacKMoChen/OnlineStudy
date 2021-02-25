package com.jack.home.net
import androidx.annotation.Keep

class HomeModule : ArrayList<HomeModule.HomeModuleItem>(){
    @Keep
    data class HomeModuleItem(
        val created_time: String?,
        val data_url: String?,
        val id: Int,
        val is_show_more: Int?,
        val layout: Int?,
        val more_redirect_url: Any?,
        val scroll: Int?,
        val sub_title: Any?,
        val title: String?,
        val type: Int?
    )
}

class HomeClass: ArrayList<HomeClass.ClassItem>(){
    @Keep
    data class ClassItem(
        val apply_deadline_time: String?,
        val balance_payment_time: Any?,
        val button_desc: Any?,
        val course: Course?,
        val created_time: String?,
        val current_price: Int?,
        val graduate_time: String?,
        val id: Int?,
        val is_apply_stop: Int?,
        val learning_mode: Int?,
        val lessons_count: Any?,
        val number: Int?,
        val original_price: Int?,
        val start_class_time: String?,
        val status: Int?,
        val stop_use_down_payment_time: Any?,
        val student_count: Int?,
        val student_limit: Int?,
        val study_expiry_day: Int?,
        val teacher_ids: String?,
        val title: String?
    ) {
        @Keep
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        )
    }
}

class HomeCourse: ArrayList<HomeCourse.CourseItem>(){
    @Keep
data class CourseItem(
    val brief: Any?,
    val comment_count: Int,
    val cost_price: Double,
    val first_category: FirstCategory?,
    val id: Int,
    val img_url: String?,
    val is_distribution: Boolean,
    val is_pt: Boolean,
    val lessons_played_time: Int,
    val name: String?,
    val now_price: Double
) {
    @Keep
    data class FirstCategory(
        val code: String?,
        val id: Int,
        val title: String?
    )
}
}

class HomeTeacher: ArrayList<HomeTeacher.TeacherItem>(){
   @Keep
data class TeacherItem(
    val brief: String?,
    val company: String?,
    val id: Int?,
    val job_title: String?,
    val logo_url: String?,
    val teacher_name: String?,
    val top5_courses: List<Top5Course?>?
) {
    @Keep
    data class Top5Course(
        val id: Int?,
        val img_url: String?,
        val name: String?,
        val now_price: Int?,
        val score: Int?
    )
}
}


class BannerListRsp : ArrayList<BannerListRsp.BannerListRspItem>(){
    @Keep
    data class BannerListRspItem(
        val client_url: String?,
        val created_time: String?,
        val id: Int,
        val img_url: String?,
        val name: Any?,
        val order_num: Int,
        val page_show: Int,
        val redirect_url: String?,
        val state: Int,
        val type: String?
    )
}

