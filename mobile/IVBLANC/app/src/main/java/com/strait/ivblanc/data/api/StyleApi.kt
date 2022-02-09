package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.response.StyleResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StyleApi {
    @Multipart
    @POST("/api/style/add")
    suspend fun addStyle(@Part image: MultipartBody.Part, @Part clothesList: MultipartBody.Part): Response<StyleResponse>
}