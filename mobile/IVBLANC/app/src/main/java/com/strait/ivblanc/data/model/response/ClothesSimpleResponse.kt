package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse

class ClothesSimpleResponse: BaseResponse() {
    @SerializedName("data")
    val dataSet: String? = null
}