package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.StyleApi
import com.strait.ivblanc.data.model.response.StyleResponse
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.StatusCode
import okhttp3.MultipartBody
import java.lang.Exception

class StyleRepository {
    val styleApi = ApplicationClass.sRetrofit.create(StyleApi::class.java)

    suspend fun addClothes(image: MultipartBody.Part, clothesList: MultipartBody.Part): Resource<StyleResponse> {
        return try {
            val response = styleApi.addStyle(image, clothesList)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "스타일 등록에 실패했습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 연결을 확인해 주세요")
        }
    }
}