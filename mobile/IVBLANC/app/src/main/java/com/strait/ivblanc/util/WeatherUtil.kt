package com.strait.ivblanc.util

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.strait.ivblanc.R
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val SERVICE_KEY = "zkRWa0Rhm9r4fYLj0DzsDezuSW%2FjBzuU3nAUHBSEtZizlGvabVXjN1ozTeDEBQDTZTJBMuXtKI%2FARKHcjw6T0Q%3D%3D"

val num_of_rows = 10
val page_no = 1
val data_type = "JSON"
val base_time = 1100
val base_data = 20200808
val nx = "55"
val ny = "127"

data class WEATHER (
    val response : RESPONSE
)
data class RESPONSE (
    val header : HEADER,
    val body : BODY
)
data class HEADER(
    val resultCode : Int,
    val resultMsg : String
)
data class BODY(
    val dataType : String,
    val items : ITEMS
)
data class ITEMS(
    val item : List<ITEM>
)
data class ITEM(
    val baseData : Int,
    val baseTime : Int,
    val category : String
)

interface WeatherInterface {
    @GET("getVilageFcst?serviceKey=${SERVICE_KEY}")
    fun getWeather(
        @Query("dataType") data_type : String,
        @Query("numOfRows") num_of_rows : Int,
        @Query("pageNo") page_no : Int,
        @Query("base_date") base_date : Int,
        @Query("base_time") base_time : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ): Call<WEATHER>
}


private val retrofit = Retrofit.Builder()
    .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/") // 마지막 / 반드시 들어가야 함
    .addConverterFactory(GsonConverterFactory.create()) // converter 지정
    .build() // retrofit 객체 생성

object ApiObject {
    val retrofitService: WeatherInterface by lazy {
        retrofit.create(WeatherInterface::class.java)
    }
}

class WeatherUtil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = ApiObject.retrofitService.getWeather(data_type, num_of_rows, page_no, base_data, base_time, nx, ny)
        call.enqueue(object : retrofit2.Callback<WEATHER>{
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful){
                    Log.d("api", response.body().toString())
                    Log.d("api", response.body()!!.response.body.items.item.toString())
                    Log.d("api", response.body()!!.response.body.items.item[0].category)
                }
            }
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                Log.d("api fail : ", t.message!!)
            }
        })
    }
}