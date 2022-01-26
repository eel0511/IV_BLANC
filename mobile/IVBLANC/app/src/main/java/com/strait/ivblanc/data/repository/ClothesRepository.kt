package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.ClothesApi
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.util.Resource
import java.lang.Exception

class ClothesRepository {
    val clothesApi = ApplicationClass.sRetrofit.create(ClothesApi::class.java)

    // TODO: 2022/01/26 access token 사용시 변경
//    suspend fun getAllClothes(page: Int): Resource<ClothesResponse> {
    suspend fun getAllClothes(page: Int, userId: Int): Resource<ClothesResponse> {
        return try {
            val response = clothesApi.getAllClothes(page, userId)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "옷 데이터가 없습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
}