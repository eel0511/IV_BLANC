package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryPhoto(
    val createDate: String,
    val photoId: Int,
    val updateDate: String,
    val url: String,
    val historyId: Int
): Parcelable