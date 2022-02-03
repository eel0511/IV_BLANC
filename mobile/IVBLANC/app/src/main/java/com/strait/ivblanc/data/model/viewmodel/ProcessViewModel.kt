package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.model.dto.ClothesForUpload
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProcessViewModel: ViewModel() {
    private val clothesRepository = ClothesRepository()
    private val _clothesResponseStatus = MutableLiveData<Resource<ClothesDeleteResponse>>()
    val clothesResponseStatus: LiveData<Resource<ClothesDeleteResponse>> get() = _clothesResponseStatus
    var category: Int? = null
    var season: Int? = null
    var color: String? = null
    var material: Int? = null
    var size: Int? = null

    fun addClothes(clothes: ClothesForUpload, imgUri: Uri) = viewModelScope.launch {
        setLoading()
        withContext(Dispatchers.IO) {
            val result = clothesRepository.addClothes(clothes, makeMultiPart(imgUri))
            if(result.status == Status.SUCCESS) {
                _clothesResponseStatus.postValue(Resource.success(result.data!!))
            } else {
                _clothesResponseStatus.postValue(result)
            }
        }
    }

    private fun makeMultiPart(imgUri: Uri): MultipartBody.Part {
        val imgFile = File(imgUri.toString())
        val requestBody = imgFile.asRequestBody("image/*".toMediaType())
        return MultipartBody.Part.create(requestBody)
    }

    private fun setLoading() = _clothesResponseStatus.postValue(Resource.loading(null))
}