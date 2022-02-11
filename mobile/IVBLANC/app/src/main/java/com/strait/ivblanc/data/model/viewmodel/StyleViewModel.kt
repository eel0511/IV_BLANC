package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.dto.Style
import com.strait.ivblanc.data.repository.StyleRepository
import com.strait.ivblanc.util.MultiPartUtil
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StyleViewModel: ViewModel() {
    private val styleRepository = StyleRepository()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    // focus
    private val _focusedImage = MutableLiveData<ImageView>()
    var focusedImage : LiveData<ImageView> = _focusedImage

    // 네트워크 요청 상태, 로딩, 성공, 에러
    private val _styleResponseStatus = MutableLiveData<Resource<*>>()
    val styleResponseStatus: LiveData<Resource<*>> get() = _styleResponseStatus

    // 삭제 요청 상태, 로딩, 성공, 에러
    private val _styleDeleteResponseStatus = MutableLiveData<Resource<*>>()
    val styleDeleteResponseStatus: LiveData<Resource<*>> get() = _styleDeleteResponseStatus

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
    fun changefocus(imageView: ImageView){
        _focusedImage.postValue(imageView)
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
    fun getAllFriendStyles(FriendEmail:String)=viewModelScope.launch {
        setLoading()
        ioScope.launch {
            val response = styleRepository.getAllFriendStyles(FriendEmail)
            _styleResponseStatus.postValue(response)
            if(response.status == Status.SUCCESS) {
                _styleListLiveData.postValue(response.data!!.dataSet)
            }
        }
    }
    // TODO: 2022/02/10 PhotoItemList로 변환하는 로직 위치, MainViewModel, StyleViewModel
    // 함수 추출 범위 시작 -----------------------------------------

    fun makePhotoItemList(filteredList: MutableList<Style>): List<PhotoItem<Style>> {
        val photoItemList = mutableListOf<PhotoItem<Style>>()
        val recentlyCreatedItemList = getCreatedRecentlyItemList(filteredList)
        val favoriteItemList = getFavoriteItemList(filteredList)
        // 주석의 첫번째 조건
        if (recentlyCreatedItemList.size != 0) {
            photoItemList.add(
                PhotoItem<Style>(
                    ExpandableRecyclerViewAdapter.HEADER,
                    "최근에 추가한 스타일"
                ).apply {
                    initInvisibleItems()
                })
            photoItemList.addAll(getPhotoItemList(recentlyCreatedItemList))
        }
        // 주석의 두번째 조건
        if (favoriteItemList.size != 0) {
            photoItemList.add(
                PhotoItem<Style>(
                    ExpandableRecyclerViewAdapter.HEADER,
                    "즐겨찾기한 스타일"
                ).apply {
                    initInvisibleItems()
                })
            photoItemList.addAll(getPhotoItemList(favoriteItemList))
        }
        // 주석의 마지막 조건
        if (favoriteItemList.size != 0 || recentlyCreatedItemList.size != 0) {
            photoItemList.add(PhotoItem(ExpandableRecyclerViewAdapter.HEADER))
        }
        photoItemList.addAll(getPhotoItemList(filteredList))
        return photoItemList
    }

    // List<Style>을 List<PhotoItem<Style>>로 변환
    private fun getPhotoItemList(list: MutableList<Style>): List<PhotoItem<Style>> {
        val result = mutableListOf<PhotoItem<Style>>()
        list.forEach { item ->
            result.add(
                PhotoItem(
                    ExpandableRecyclerViewAdapter.CHILD,
                    content = item
                )
            )
        }
        return result
    }

    // 즐겨찾기 한 스타일
    private fun getFavoriteItemList(list: MutableList<Style>): MutableList<Style> {
        return list.filter { style -> style.favorite == 1 }.toMutableList()
    }

    // 최근 일주일 간 생성된 스타
    private fun getCreatedRecentlyItemList(list: MutableList<Style>): MutableList<Style> {
        val today = System.currentTimeMillis()
        val oneWeekMillis = 1000 * 7 * 24 * 60 * 60
        return list.filter { style -> dateStringToMillis(style.createDate) > (today - oneWeekMillis) }
            .toMutableList()
    }

    // string to millis
    private fun dateStringToMillis(dateString: String): Long {
        val stringFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
        val date = stringFormat.parse(dateString)
        return date.time
    }
    // 함수 추출 범위 끝 -----------------------------------------

    // 스타일 조회 요청 관련 끝 -----------------------

    // 스타일 삭제 요청 관련 시작 ---------------------
    fun deleteStyleById(styleId: Int) = viewModelScope.launch {
        setLoading()
        ioScope.launch {
            val response = styleRepository.deleteStyleById(styleId)
            _styleDeleteResponseStatus.postValue(response)
        }
    }

    // 스타일 삭제 요청 관련 끝 ------------------------


    private fun setLoading() {
        _styleResponseStatus.postValue(Resource.loading(null))
    }

}