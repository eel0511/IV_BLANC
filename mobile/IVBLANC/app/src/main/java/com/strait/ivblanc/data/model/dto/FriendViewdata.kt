package com.strait.ivblanc.data.model.dto

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FriendViewdata(
    val name:String,
    val cloth1: Uri,
    val cloth2: Uri,
    val cloth3: Uri,
    val cloth4: Uri,
    val style1: Uri,
    val style2: Uri,
    val style3: Uri,
    val style4: Uri,
): Parcelable