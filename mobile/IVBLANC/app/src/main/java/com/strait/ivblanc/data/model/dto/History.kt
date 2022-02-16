package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class History(
    val createDate: String,
    var date: String,
    var location: Double,
    var field: Double,
    val historyId: Int,
    val photos: List<HistoryPhoto>,
    var styleUrl: String,
    var subject: String,
    var temperature_high: Int,
    var temperature_low: Int,
    var text: String,
    var updateDate: String,
    var userId: Int,
    var weather: String
): Parcelable