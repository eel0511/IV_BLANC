package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.R
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.model.dto.ClothesForUpload
import com.strait.ivblanc.data.model.response.ClothesDeleteResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.MaterialCode
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

    var imgUri: Uri? = null
    var absoluteImgPath: String? = null
    val categorySet = CategoryCode().codeSet
    val materialSet = MaterialCode().codeSet

    private val _largeCategory = MutableLiveData<Int>(CategoryCode.UNSELECTED) // CategoryCode 1 ~ 7까지
    private val _smallCategory = MutableLiveData<Int>(CategoryCode.UNSELECTED) // CategoryCode 대분류에서 가져오기
    private val _season = MutableLiveData<Int>(1) // 1 - 4
    private val _color = MutableLiveData<String>("#FFFFFF") // hex code
    private val _size = MutableLiveData<Int>(0) // 모든 숫자
    private val _material = MutableLiveData<Int>(MaterialCode.COTTON) // MaterialCode

    val largeCategory: LiveData<Int> get() = _largeCategory
    val smallCategory: LiveData<Int> get() = _smallCategory
    val season: LiveData<Int> get() = _season
    val color: LiveData<String> get() = _color
    val size: LiveData<Int> get() = _size
    val material: LiveData<Int> get() = _material

    fun setLargeCategory(largeCategory: Int) = _largeCategory.postValue(largeCategory)
    fun setSmallCategory(smallCategory: Int) = _smallCategory.postValue(smallCategory)
    fun setSeason(season: Int) = _season.postValue(season)
    fun setColor(color: String) = _color.postValue(color)
    fun setMaterial(material: Int) = _material.postValue(material)
    fun setSize(size: Int) = _size.postValue(size)

    // 서버에 옷 업로드
    fun addClothes() = viewModelScope.launch {
        val clothes = makeClothesForUpload()
        if (clothes != null && absoluteImgPath != null) {
            setLoading()
            withContext(Dispatchers.IO) {
                val result = clothesRepository.addClothes(clothes, makeMultiPart(this@ProcessViewModel.absoluteImgPath!!))
                if(result.status == Status.SUCCESS) {
                    _clothesResponseStatus.postValue(Resource.success(result.data!!))
                } else {
                    _clothesResponseStatus.postValue(result)
                }
            }
        } else {
            _clothesResponseStatus.postValue(Resource.error(null, "분류를 하지 않은 항목이 있습니다."))
        }
    }

    private fun makeClothesForUpload(): ClothesForUpload? {
        val category = getCategoryFromLiveData()
        val season = _season.value
        val material = _material.value
        val color = _color.value
        val size = _size.value
        category?.let {
            return ClothesForUpload(it, color!!, material!!, season!!, size!!)
        }
        return null
    }

    //
    private fun getCategoryFromLiveData(): Int? {
        val smallCategory = _smallCategory.value
        if(smallCategory == CategoryCode.UNSELECTED) return null
        return smallCategory
    }

    private fun makeMultiPart(path: String): MultipartBody.Part {
        val imgFile = File(path)
        return MultipartBody.Part.createFormData("photo", imgFile.name, imgFile.asRequestBody("image/*".toMediaType()))
    }

    private fun setLoading() = _clothesResponseStatus.postValue(Resource.loading(null))

    val largeCategorySet = getLargeCategories()

    // 전체를 제외한 대분류만 나타낸다. 전체 카테고리 셋을 순회하며 1부터 9사이의 값을 취한다.
    private fun getLargeCategories(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        categorySet.forEach { entry ->
            if(entry.key in 1..9) result.add(entry.toPair())
        }
        return result
    }

    // 대분류로부터 소분류 카테고리 분류, 대분류의 값과 소분류의 맨 앞자리가 같다.
    // 모든 소분류는 10 이상이며, -1은 전체를 나타낸다.
    fun getSmallCategoriesByLargeCategory(largeCategory: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()

        categorySet.forEach { entry ->
            if(entry.key.toString()[0] == largeCategory.toString()[0] && entry.key >= 10) {
                result.add(entry.toPair())
            }
        }

        return result
    }
}