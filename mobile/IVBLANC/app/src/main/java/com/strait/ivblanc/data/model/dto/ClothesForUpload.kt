package com.strait.ivblanc.data.model.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class ClothesForUpload(
    @SerializedName("category")
    var category: Int,
    @SerializedName("color")
    var color: String,
    @SerializedName("material")
    var material: Int,
    @SerializedName("season")
    var season: Int,
    @SerializedName("size")
    var size: Int
)
