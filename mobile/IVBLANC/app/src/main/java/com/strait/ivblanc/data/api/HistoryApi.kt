package com.strait.ivblanc.data.api

import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.ClothesForUpload
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.data.model.response.HistoryResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface HistoryApi {
    @GET("/api/history/find/all")
    suspend fun getAllHistory(): Response<HistoryResponse>
    @GET("/api/history/find/thisMonth")
    suspend fun getHistoryThisMonth(): Response<HistoryResponse>
    @GET("/api/history/find/month")
    suspend fun getHistoryByMonth(@Query("year") year:Int, @Query("month") month:Int): Response<HistoryResponse>
    @GET("/api/history/find/date")
    suspend fun getHistoryByDate(@Query("date") date:String): Response<HistoryResponse>
    @GET("/api/history/find/weather")
    suspend fun getHistoryByWeather(@Query("weather") weather:String): Response<HistoryResponse>
    @GET("/api/history/find/temperature")
    suspend fun getHistoryByTemperature(@Query("최저기온") temperature_low: Int, @Query("최고기온") temperature_high: Int): Response<HistoryResponse>
    @GET("/api/history/find/subject")
    suspend fun getHistoryBySubject(@Query("subject") subject:String): Response<HistoryResponse>

    @DELETE("/api/history/delete")
    suspend fun deleteHistory(@Query("historyId") historyId: Int): Response<HistoryResponse>

    @Multipart
    @PUT("/api/history/update")
    suspend fun updateHistory(@Query("historyId") historyId: Int
                              , @Part latitude: MultipartBody.Part
                              , @Part longitude: MultipartBody.Part
                              , @Part date: MultipartBody.Part
                              , @Part weather: MultipartBody.Part
                              , @Part temperature_low: MultipartBody.Part
                              , @Part temperature_high: MultipartBody.Part
                              , @Part text: MultipartBody.Part
                              , @Part subject: MultipartBody.Part
                              , @Part styleId: MultipartBody.Part)

    @Multipart
        @POST("/api/history/add")
    suspend fun addHistory(@Part latitude: MultipartBody.Part
                           , @Part longitude: MultipartBody.Part
                           , @Part date: MultipartBody.Part
                           , @Part weather: MultipartBody.Part
                           , @Part temperature_low: MultipartBody.Part
                           , @Part temperature_high: MultipartBody.Part
                           , @Part text: MultipartBody.Part
                           , @Part subject: MultipartBody.Part
                           , @Part styleId: MultipartBody.Part
                           , @Part photoList: MultipartBody.Part): Response<HistoryResponse>

    @Multipart
    @POST("/api/history/photo/add")
    suspend fun addHistoryPhotos(@Part historyId: MultipartBody.Part
                                , @Part photoList: MultipartBody.Part): Response<HistoryResponse>

    @DELETE("/api/history/photo/delete")
    suspend fun deleteHistoryPhoto(@Query("photoId") photoId: Int): Response<HistoryResponse>

    @Multipart
    @PUT("/api/history/photo/update")
    suspend fun updateHistoryPhotos(@Part photoId: MultipartBody.Part
                                 , @Part newPhoto: MultipartBody.Part): Response<HistoryResponse>
}