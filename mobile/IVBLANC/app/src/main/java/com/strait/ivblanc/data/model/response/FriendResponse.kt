package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.Friend

class FriendResponse : BaseResponse(){
    @SerializedName("data")
    val data: Friend? = null
}