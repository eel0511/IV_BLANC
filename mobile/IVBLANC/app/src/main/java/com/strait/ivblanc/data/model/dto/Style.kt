package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Style(
    val createDate: String,
    val favorite: Int,
    val madeby: String,
    val photoName: String?,
    val styleDetails: List<StyleDetail>,
    val styleId: Int,
    val updateDate: String,
    val url: String,
    val userId: Int
): Parcelable