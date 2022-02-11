package com.strait.ivblanc.data.api

import com.strait.ivblanc.data.model.response.StyleAllResponse
import com.strait.ivblanc.data.model.response.StyleResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface StyleApi {
    @Multipart
    @POST("/api/style/add")
    suspend fun addStyle(@Part image: MultipartBody.Part, @Part clothesList: MultipartBody.Part): Response<StyleResponse>

    @GET("/api/style/finduserstyle")
    suspend fun getAllStyles(): Response<StyleAllResponse>

    @DELETE("/api/style/delete")
    suspend fun deleteStyleById(@Query("styleId") styleId: Int): Response<StyleResponse>

    @GET("/api/style/findfriendstyle")
    suspend fun getAllFriendStyles(@Query("FriendEmail")FriendEmail:String): Response<StyleAllResponse>

    @Multipart
    @POST("/api/style/change")
    suspend fun updateStyle(@Part image: MultipartBody.Part, @Part clothesList: MultipartBody.Part, @Part styleId: MultipartBody.Part): Response<StyleResponse>
}