package com.strait.ivblanc.data.api

import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ClothesApi {
    @GET("/api/clothes/all")
    suspend fun getAllClothes(): Response<ClothesResponse>

    @DELETE("/api/clothes/deleteById")
    suspend fun deleteClothesById(@Query("clothesId") clothesId: Int): Response<ClothesDeleteResponse>

    @Multipart
        @POST("/api/clothes/add")
    suspend fun addClothes(@Query("category") category: Int
                           , @Query("color") color: String
                           , @Query("material") material: Int
                           , @Query("season") season: Int
                           , @Query("size") size: Int
                           , @Part("file") image: MultipartBody.Part): Response<BaseResponse>

}