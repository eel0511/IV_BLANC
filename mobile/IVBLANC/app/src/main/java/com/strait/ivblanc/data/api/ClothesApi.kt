package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ClothesApi {
    // TODO: 2022/01/31 page 삭제
    @GET("/api/clothes/all")
    suspend fun getAllClothes(@Query("page") page: Int): Response<ClothesResponse>

    @DELETE("/api/clothes/deleteById")
    suspend fun deleteClothesById(@Query("clothesId") clothesId: Int): Response<ClothesDeleteResponse>
}