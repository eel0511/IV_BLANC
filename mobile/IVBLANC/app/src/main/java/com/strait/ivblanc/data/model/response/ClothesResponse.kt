package com.strait.ivblanc.data.model.response

import com.google.gson.annotations.SerializedName
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.Clothes

class ClothesResponse: BaseResponse() {
    @SerializedName("data")
    override val dataSet: List<Clothes>? = super.dataSet as? List<Clothes>
}