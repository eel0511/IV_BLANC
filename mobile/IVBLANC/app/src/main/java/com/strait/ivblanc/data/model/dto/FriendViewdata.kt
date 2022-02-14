package com.strait.ivblanc.data.model.dto

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendViewdata(
    val name:String,
    val email:String,
    val cloth1: Uri,
    val cloth2: Uri,
    val cloth3: Uri,
    val cloth4: Uri,
    var style1: Uri,
    var style2: Uri,
    var style3: Uri,
    var style4: Uri,
): Parcelable