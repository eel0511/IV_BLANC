package com.strait.ivblanc.data.repository

import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.HistoryApi
import com.strait.ivblanc.data.model.response.HistoryResponse
import com.strait.ivblanc.util.Resource
import okhttp3.MultipartBody
import retrofit2.http.Part
import java.lang.Exception

private const val TAG = "HistoryRepository_debug"
class HistoryRepository {

    val historyApi = ApplicationClass.sRetrofit.create(HistoryApi::class.java)

    suspend fun getAllHistory(): Resource<HistoryResponse> {
        return try {
            val response = historyApi.getAllHistory()
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1 ) {
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
    suspend fun getHistoryThisMonth(): Resource<HistoryResponse> {
        return try {
            val response = historyApi.getHistoryThisMonth()
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "히스토리 데이터가 없습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            val msg = e.message
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
    suspend fun getHistoryByMonth(year: Int, month: Int): Resource<HistoryResponse> {
        return try {
            val response = historyApi.getHistoryByMonth(year, month)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "히스토리 데이터가 없습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            val msg = e.message
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }

    suspend fun updateHistory(historyId: MultipartBody.Part, location: MultipartBody.Part, field: MultipartBody.Part, date: MultipartBody.Part, weather: MultipartBody.Part,
                              temperature_low: MultipartBody.Part, temperature_high: MultipartBody.Part, text: MultipartBody.Part, subject: MultipartBody.Part,
                              styleId: MultipartBody.Part): Resource<HistoryResponse> {
        return try{
            val response = historyApi.updateHistory(historyId, location, field, date, weather, temperature_low, temperature_high, text, subject, styleId)
            if(response.isSuccessful) {
                return if(response.code() == 200 && response.body()!!.output == 1 && response.body()!!.dataSet?.isNotEmpty() == true) {
                    Resource.success(response.body()!!)
                } else {
                    Resource.error(response.body(), "히스토리 업데이트에 실패했습니다.")
                }
            } else {
                Resource.error(null, "알 수 없는 오류입니다.")
            }
        } catch (e: Exception) {
            val msg = e.message
            Resource.error(null, "네트워크 상태를 확인해 주세요.")
        }
    }
}