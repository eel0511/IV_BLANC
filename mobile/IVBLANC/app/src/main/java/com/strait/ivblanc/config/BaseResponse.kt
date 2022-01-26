package com.strait.ivblanc.config

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("output")
    val output: Int = 0
    @SerializedName("msg")
    val message: String? = null
    @SerializedName("data")
    open val dataSet: Any? = null
}