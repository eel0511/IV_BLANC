package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.repository.StyleRepository
import com.strait.ivblanc.util.MultiPartUtil
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StyleViewModel: ViewModel() {
    private val styleRepository = StyleRepository()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    // 네트워크 요청 상태, 로딩, 성공, 에러
    private val _styleResponseStatus = MutableLiveData<Resource<*>>()
    val styleResponseStatus: LiveData<Resource<*>> get() = _styleResponseStatus

    // 스타일 목록 라이브 데이터
    private val _styleListLiveData = MutableLiveData<List<Style>>()
    val styleListLiveData: LiveData<List<Style>> get() = _styleListLiveData

    // 스타일 등록 요청 관련 시작 ------------------------
    fun addStyle(clothesList: List<Clothes>, absolutePath: String) = viewModelScope.launch {
        setLoading()
        val imageRequestBody = MultiPartUtil.makeMultiPartBodyFile("photo", absolutePath, "image/*")
        val clothesRequestBody = MultiPartUtil.makeMultiPartBody("clothesList", makeClothesIdString(clothesList))
        ioScope.launch {
            val response = styleRepository.addClothes(imageRequestBody, clothesRequestBody)
            _styleResponseStatus.postValue(response)
        }
    }

    private fun makeClothesIdString(clothesList: List<Clothes>): String {
        val value = StringBuilder().apply {
            clothesList.forEach {
                this.append(it.clothesId).append(",")
            }
            this.deleteCharAt(this.length - 1)
        }.toString()
        return value
    }
    // 스타일 등록 요청 관련 끝  ------------------------

    // 스타일 조회 요청 관련 시작 -----------------------
    fun getAllStyles() = viewModelScope.launch {
        setLoading()
        ioScope.launch {
            val response = styleRepository.getAllStyles()
            _styleResponseStatus.postValue(response)
            if(response.status == Status.SUCCESS) {
                _styleListLiveData.postValue(response.data!!.dataSet)
            }
        }
    }

    // 스타일 조회 요청 관련 끝 -----------------------


    private fun setLoading() {
        _styleResponseStatus.postValue(Resource.loading(null))
    }

}