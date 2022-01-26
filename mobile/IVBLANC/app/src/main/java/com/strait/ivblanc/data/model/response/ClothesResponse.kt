package com.strait.ivblanc.data.model.response

import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.Clothes

class ClothesResponse: BaseResponse() {
    override val data: List<Clothes>? = super.data as? List<Clothes>
}