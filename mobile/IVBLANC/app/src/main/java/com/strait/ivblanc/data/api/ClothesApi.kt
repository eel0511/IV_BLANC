package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.response.ClothesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClothesApi {
    @GET("/api/clothes/all")
    suspend fun getAllClothes(@Query("page") page: Int): Response<ClothesResponse>
}