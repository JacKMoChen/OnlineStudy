package com.jack.mine.net

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
data class UserInfoRsp(
    var company: String?,
    var desc: String?,
    val email: String?,
    val focus_it: String?,
    val follower_count: Int?,
    val following_count: Int?,
    val id: Long,
    var job: String?,
    val logo_url: String?,
    val mobi: String?,
    val real_name: String?,
    val username: String?,
    var work_years: String?
):Parcelable