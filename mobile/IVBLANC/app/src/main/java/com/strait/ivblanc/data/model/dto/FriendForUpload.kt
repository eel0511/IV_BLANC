package com.strait.ivblanc.data.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendForUpload(
    var applicant: String,
    var friendName: String
) : Parcelable