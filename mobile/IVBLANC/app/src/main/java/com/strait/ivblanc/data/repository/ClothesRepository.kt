package com.strait.ivblanc.data.repository

import android.content.res.Resources
import android.util.Log
import com.strait.ivblanc.R
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.ClothesApi
import com.strait.ivblanc.data.model.dto.ClothesForUpload
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.model.response.ClothesFavoriteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.StatusCode
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.lang.Exception

private const val TAG = "ClothesRepository_debuk"

class ClothesRepository {
    val clothesApi = ApplicationClass.sRetrofit.create(ClothesApi::class.java)

    suspend fun getAllFriendClothes(email: String): Resource<ClothesResponse> {
        return try {
            val response = clothesApi.getAllFriendClothes(email)
            if (response.isSuccessful) {
                return if (response.code() == 200 && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "error")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            val msg = e.message
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun getAllClothes(): Resource<ClothesResponse> {
        return try {
            val response = clothesApi.getAllClothes()
            if (response.isSuccessful) {
                return if (response.code() == 200 && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
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

    suspend fun deleteClothesById(clothesId: Int): Resource<ClothesDeleteResponse> {
        return try {
            val response = clothesApi.deleteClothesById(clothesId)
            if (response.isSuccessful) {
                return if (response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey(
                        "clothes_id"
                    )
                ) {
                    Resource.success(response.body()!!)
                } else if (response.code() == StatusCode.UNDOCUMENT) {
                    Resource.error(null, "존재하지 않는 옷입니다.")
                } else {
                    Resource.error(null, "알 수 없는 오류입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    // TODO: 2022/02/03 ClothesDeleteResponse를 재활용 가능한 이름으로 변경하기
    suspend fun addClothes(
        clothes: ClothesForUpload,
        imageFile: MultipartBody.Part
    ): Resource<ClothesDeleteResponse> {
        return try {
            //Multipart/form-data로 변
            val category = getBody("category", clothes.category)
            val season = getBody("season", clothes.season)
            val size = getBody("size", clothes.size)
            val material = getBody("material", clothes.material)
            val color = getBody("color", clothes.color)
            val response = clothesApi.addClothes(
                category = category,
                season = season,
                size = size,
                material = material,
                color = color,
                image = imageFile
            )

            if (response.isSuccessful) {
                return if (response.code() == StatusCode.OK && response.body()!!.output == 1 && response.body()!!.dataSet!!.containsKey(
                        "clothes_id"
                    )
                ) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(null, "알 수 없는 에러입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 에러입니다.")
            }
        } catch (e: Exception) {
            Log.d(TAG, "addClothes: ${e.message}")
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun addfavorite(clothesId: Int): Resource<ClothesFavoriteResponse> {
        return try {
            val response = clothesApi.addfavorite(clothesId)
            if (response.isSuccessful) {
                return if (response.code() == StatusCode.OK && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else if (response.code() == StatusCode.UNDOCUMENT) {
                    Resource.error(null, "존재하지 않는 옷입니다.")
                } else {
                    Resource.error(null, "알 수 없는 오류입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun deletefavorite(clothesId: Int): Resource<ClothesFavoriteResponse> {
        return try {
            val response = clothesApi.deletefavorite(clothesId)
            if (response.isSuccessful) {
                return if (response.code() == StatusCode.OK && response.body()!!.output == 1) {
                    Resource.success(response.body()!!)
                } else if (response.code() == StatusCode.UNDOCUMENT) {
                    Resource.error(null, "존재하지 않는 옷입니다.")
                } else {
                    Resource.error(null, "알 수 없는 오류입니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
}

private fun getBody(name: String, value: Any): MultipartBody.Part {
    return MultipartBody.Part.createFormData(name, value.toString())
}
