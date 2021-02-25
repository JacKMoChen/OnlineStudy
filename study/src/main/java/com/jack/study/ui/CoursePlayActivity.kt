package com.jack.study.ui

import android.content.Context
import android.content.Intent
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ToastUtils
import com.jack.common.base.BaseActivity
import com.jack.study.R
import com.jack.study.databinding.ActivityCoursePlayBinding
import com.jack.study.net.StudiedRsp
import com.jack.study.net.StudyAuthority.Companion.HAS_COURSE_PERMISSION
import com.jack.study.ui.adapter.CourseChapterAdapter
import com.jack.study.ui.adapter.CourseChapterAdapter.LessonSection
import kotlinx.android.synthetic.main.activity_course_play.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoursePlayActivity : BaseActivity<ActivityCoursePlayBinding>() {
    private val viewModel: CoursePlayViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_course_play

    private val courseChapterAdapter = CourseChapterAdapter { clickedItem ->
        lessonSectionList.forEach {
            it.isPlaying.set(it.key == clickedItem.key)
        }

        clickedItem.key?.let {
            viewModel.getPlayInfo(clickedItem.key)
        }
    }

    private val lessonSectionList = mutableListOf<LessonSection>()

    override fun initData() {
        super.initData()

        val courseInfo: StudiedRsp.Data =
            intent.getParcelableExtra<StudiedRsp.Data>(INTENT_KEY_COURSE_INFO)
                ?: return ToastUtils.showShort("Course Info Null")

        mBinding.vm = viewModel
        mBinding.info = courseInfo
        mBinding.adapter = courseChapterAdapter
        mBinding.jzVideoPlayer.reset()
        viewModel.apply {
            checkPermission(courseInfo.id)

            liveAuthority.observerKt { p ->
                if (p?.is_true == HAS_COURSE_PERMISSION) {
                    getChapters(courseInfo.id)
                } else {
                    ToastUtils.showShort("not have course permission")
                }
            }

            liveCourseChapter.observerKt { chapters ->
                lessonSectionList.clear()
                chapters?.forEach { chapter ->
                        lessonSectionList.add(LessonSection(LessonSection.ITEM_TYPE_TITLE,"第${chapter.bsort}章 ${chapter.title}"))
                        chapter.lessons?.forEach {lesson->
                            lessonSectionList.add(LessonSection(LessonSection.ITEM_TYPE_CONTENT,"${chapter.bsort}-${lesson.bsort} ${lesson.name}"
                            ,lesson.key,lesson.is_free==1))
                        }
                }
                courseChapterAdapter.updateList(lessonSectionList)
            }

            //播放信息
            liveVideoPlayInfo.observerKt {info->
                val vUrl="https:${info?.play_urls?.hls?.urls?.hd}"
                jz_video_player.setUp(vUrl,info?.last_play_info?.title)
                jz_video_player.startVideo()
            }
        }

    }

    companion object {
        const val INTENT_KEY_COURSE_INFO = "intent_key_course_info"

        fun openPlay(context: Context, course: StudiedRsp.Data) {
            context.startActivity(Intent(context, CoursePlayActivity::class.java).also {
                it.putExtra(INTENT_KEY_COURSE_INFO, course)
            })
        }
    }

    override fun onBackPressed() {
        if(Jzvd.backPress()){
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}