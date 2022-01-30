package com.strait.ivblanc.data.repository

import android.content.res.Resources
import com.strait.ivblanc.R
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.ClothesApi
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.StatusCode
import java.lang.Exception

class ClothesRepository {
    val clothesApi = ApplicationClass.sRetrofit.create(ClothesApi::class.java)
    private val resource = Resources.getSystem()

    suspend fun getAllClothes(page: Int): Resource<ClothesResponse> {
        return try {
            val response = clothesApi.getAllClothes(page)
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
            val msg = e.message
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun getClothesByCategory(category: Int, page: Int): Resource<ClothesResponse> {
        return try {
            val response = clothesApi.getClothesByCategory(category, page)
            if(response.isSuccessful) {
                return if(response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, resource.getString(R.string.noClothesErrorMessage))
                }
            } else {
                Resource.error(null, resource.getString(R.string.unknownErrorMessage))
            }
        } catch (e: Exception) {
            Resource.error(null, resource.getString(R.string.networkErrorMessage))
        }
    }


}