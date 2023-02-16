package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

//  mediaType = only want image
//  title for TalkBack
@Parcelize
data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
) : Parcelable