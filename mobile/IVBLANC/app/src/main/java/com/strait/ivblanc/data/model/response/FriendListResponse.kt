package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.Friend

class FriendListResponse :BaseResponse(){
    @SerializedName("data")
    val dataSet: List<Friend>? = null
}