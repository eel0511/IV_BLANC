package com.strait.ivblanc.data.api

import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.ClothesForUpload
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ClothesApi {
    @GET("/api/clothes/all")
    suspend fun getAllClothes(): Response<ClothesResponse>

    @GET("/api/clothes/friendclothes")
    suspend fun getAllFriendClothes(@Query("email") email:String):Response<ClothesResponse>
    @DELETE("/api/clothes/deleteById")
    suspend fun deleteClothesById(@Query("clothesId") clothesId: Int): Response<ClothesDeleteResponse>

    // TODO: 2022/02/03 ClothesDeleteResponse 이름 바꾸기 
    @Multipart
        @POST("/api/clothes/add")
    suspend fun addClothes(@Part category: MultipartBody.Part
                           , @Part color: MultipartBody.Part
                           , @Part material: MultipartBody.Part
                           , @Part season: MultipartBody.Part
                           , @Part size: MultipartBody.Part
                           , @Part image: MultipartBody.Part): Response<ClothesDeleteResponse>

}