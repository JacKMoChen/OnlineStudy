package com.jack.study.net
import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**
 * 学习详情
 */
@Keep
data class StudyInfoRsp(
    val rank: Int,
    val today_study_time: String,
    val total_study_time: String
)

/**
 * 已经学习过的课程
 */
@Keep
@Parcelize
data class StudiedRsp(
    val datas: List<Data>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
):Parcelable {
    @Keep
    @Parcelize
    data class Data(
        val brief: String?,
        val comment_count: Int,
        val cost_price: Int,
        val course: Course?,
        val course_type: Int,
        val current_price: Int,
        val first_category: FirstCategory?,
        val get_method: Int,
        val id: Int,
        val img_url: String?,
        val is_distribution: Boolean,
        val is_free: Int,
        val is_live: Int,
        val is_pt: Boolean?,
        val left_expiry_days: Int,
        val lessons_count: Int,
        val lessons_played_time: Int,
        val name: String?,
        val now_price: Double,
        val number: Int,
        val original_price: Double,
        val progress: Double,
        val title: String?
    ):Parcelable  {
        @Keep
        @Parcelize
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        ):Parcelable

        @Keep
        @Parcelize
        data class FirstCategory(
            val code: String?,
            val id: Int,
            val title: String?
        ):Parcelable
    }
}

/**
 * 购买的课程
 */
@Keep
data class BoughtRsp(
    val datas: List<Data?>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
) {
    @Keep
    data class Data(
        val cancel_time: String?,
        val course: Course?,
        val created_time: String?,
        val get_method: Int,
        val id: Int,
        val is_expired: Boolean?,
        val left_expiry_days: Double,
        val order_time: String?,
        val product_id: Int,
        val product_type: Int
    ) {
        @Keep
        data class Course(
            val brief: Any?,
            val comment_count: Int,
            val cost_price: Int,
            val course: Course?,
            val first_category: FirstCategory?,
            val id: Int,
            val img_url: String?,
            val is_distribution: Boolean?,
            val is_free: Int,
            val is_live: Int,
            val is_pt: Boolean?,
            val lessons_played_time: Int,
            val name: String?,
            val now_price: Double,
            val number: Int,
            val progress: Int,
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

            @Keep
            data class FirstCategory(
                val code: String?,
                val id: Int,
                val title: String?
            )
        }
    }
}

@Keep
data class StudyAuthority(
    val cancel_time: String?,
    val days: Double,
    val get_method: Int,
    val is_true: Int,
    val type: String?
){
    companion object {
        const val HAS_COURSE_PERMISSION=1
        const val HAS_NOT_COURSE_PERMISSION=0
    }
}

/**
 * 课程章节信息
 */
class CourseChapter : ArrayList<CourseChapter.CourseChapterItem>(){
    @Keep
    data class CourseChapterItem(
        val bsort: Int,
        val class_id: Int,
        val id: Int,
        val lessons: List<Lesson>?,
        val title: String?
    ) {
        var viewType:Int=0
        companion object {
            const val ITEM_TYPE_TITLE =1
        }
        @Keep
        data class Lesson(
            val bsort: Int,
            val is_free: Int,
            val is_live: Int,
            val key: String?,
            val lesson_id: Int,
            val live_begin_time: String?,
            val live_end_time: String?,
            val live_plan_begin_time: String?,
            val live_plan_end_time: String?,
            val live_status: Int,
            val name: String?,
            val state: Int,
            val video_duration: String?,
            val video_info_duration: Int
        )
    }
}

/**
 * 视频播放信息
 */

@Keep
data class VideoPlayInfo(
    val is_live: Int,
    val last_play_info: LastPlayInfo?,
    val play_urls: PlayUrls?,
    val video_info_id: Int
) {
    @Keep
    data class LastPlayInfo(
        val key: String?,
        val position: Int,
        val title: String?
    )

    @Keep
    data class PlayUrls(
        val flv: Flv?,
        val hls: Hls?,
        val mp4: Mp4?,
        val origin: String?,
        val rtmp: Rtmp?
    ) {
        @Keep
        data class Flv(
            val is_encryption: Int?,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Hls(
            val is_encryption: Int?,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Mp4(
            val is_encryption: Int?,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Rtmp(
            val is_encryption: Int?,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }
    }
}

