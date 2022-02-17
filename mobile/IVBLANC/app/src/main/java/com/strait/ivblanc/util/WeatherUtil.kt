package com.strait.ivblanc.util

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private const val TAG = "WeatherUtil"
class WeatherUtil {
    private var nx = "60" //위도
    private var ny = "125" //경도
    private var baseDate = "20220214" //조회하고싶은 날짜
    private val baseTime = "0200" //조회하고싶은 시간
    private val type = "json" //조회하고 싶은 type(json, xml 중 고름)

    private var temp_high = "0"
    private var temp_low = "0"

    @Throws(IOException::class, JSONException::class)
    fun lookUpWeather(nx: String, ny: String, baseDate: String) : String{
        val apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
        val serviceKey = "zkRWa0Rhm9r4fYLj0DzsDezuSW%2FjBzuU3nAUHBSEtZizlGvabVXjN1ozTeDEBQDTZTJBMuXtKI%2FARKHcjw6T0Q%3D%3D"
        val urlBuilder = StringBuilder(apiUrl)
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey)
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + "1")
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + "1000")
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "="
                + URLEncoder.encode(nx, "UTF-8")) //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "="
                + URLEncoder.encode(ny, "UTF-8")) //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
                + URLEncoder.encode(baseDate, "UTF-8")) /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
                + URLEncoder.encode(baseTime, "UTF-8")) /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
                + URLEncoder.encode(type, "UTF-8")) /* 타입 */

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        val url = URL(urlBuilder.toString())
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-type", "application/json")
        println("Response code: " + conn.responseCode)
        val rd: BufferedReader = if (conn.responseCode in 200..300) {
            BufferedReader(InputStreamReader(conn.inputStream))
        } else {
            BufferedReader(InputStreamReader(conn.errorStream))
        }

        val sb = StringBuilder()
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            sb.append(line)
        }
        rd.close()
        conn.disconnect()


        //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//

        // response 키를 가지고 데이터를 파싱
        try {
            val result = sb.toString()
            val response = JSONObject(result).getString("response")
            // response 로 부터 body 찾기
            val body = JSONObject(response).getString("body")
            Log.d(TAG, "lookUpWeather: body = $body")

            // body 로 부터 items 찾기
            val items = JSONObject(body).getString("items")

            // items로 부터 itemlist 를 받기
            var jsonObj = JSONObject(items)
            val jsonArray = jsonObj.getJSONArray("item")
            Log.d("WEATHER_LENGTH", "${jsonArray.length()}")
            for (i in 0 until jsonArray.length()) {
                jsonObj = jsonArray.getJSONObject(i)

                val fcstValue = jsonObj.getString("fcstValue")
                val category = jsonObj.getString("category")

                val baseD = jsonObj.getString("baseDate")
                val fcstD = jsonObj.getString("fcstDate")
                if (!baseD.equals(fcstD)) continue

                if (category == "TMN") {
                    temp_low = fcstValue
                }
                if (category == "TMX") {
                    temp_high = fcstValue
                }
            }
        } catch(e: Exception) {
            Log.d(TAG, "lookUpWeather: ${e.message}")
            return "Error"
        }

        Log.i("WEATHER_TAG", "temp_low = $temp_low, temp_high = $temp_high")

        return "$temp_low/$temp_high"
    } // end of lookUpWeather
}
