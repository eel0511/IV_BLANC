package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.response.ClothesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClothesApi {
    // TODO: 2022/01/26 access token 사용시 변경
    @GET("/api/clothes/all")

    suspend fun getAllClothes(@Query("page") page: Int): Response<ClothesResponse>
//    suspend fun getAllClothes(@Query("page") page: Int, @Query("userId")userId: Int): Response<ClothesResponse>
}