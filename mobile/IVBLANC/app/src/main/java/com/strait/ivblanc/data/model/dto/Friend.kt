package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val friendEmail:String,
    val friendName:String
):Parcelable
