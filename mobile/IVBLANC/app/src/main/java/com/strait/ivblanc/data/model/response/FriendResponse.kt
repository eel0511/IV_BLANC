package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse

class FriendResponse :BaseResponse(){
    @SerializedName("data")
    val dataSet : HashMap<String, Int>? = null

}