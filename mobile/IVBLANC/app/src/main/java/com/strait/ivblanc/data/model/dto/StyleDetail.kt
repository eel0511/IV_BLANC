package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StyleDetail(
    val clothes: Clothes,
    val sdId: Int
): Parcelable