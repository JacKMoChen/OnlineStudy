package com.jack.course.net
import androidx.annotation.Keep


class CourseCategoryRsp : ArrayList<CourseCategoryRsp.CourseCategoryRspItem>(){
    @Keep
    data class CourseCategoryRspItem(
        val code: String?,
        val id: Int?,
        val title: String?
    )
}

@Keep
data class CourseListRsp(
    val datas: List<Data>?,
    val page: Int?,
    val size: Int?,
    val total: Int?,
    val total_page: Int?
) {
    @Keep
    data class Data(
        val brief: String?,
        val comment_count: Int,
        val cost_price: Double,
        val expiry_day: Int,
        val finished_lessons_count: Int,
        val first_category: FirstCategory?,
        val id: Int,
        val img_url: String?,
        val is_free: Int,
        val is_live: Int,
        val is_pt: Boolean,
        val is_share_card: Boolean,
        val lessons_count: Int,
        val lessons_played_time: Double,
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