package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.UserInfo

class LoginResponse: BaseResponse() {
    @SerializedName("data")
    val dataSet: UserInfo? = null
}