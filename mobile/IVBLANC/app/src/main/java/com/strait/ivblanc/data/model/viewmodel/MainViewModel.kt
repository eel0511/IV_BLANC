package com.strait.ivblanc.data.model.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.response.ClothesFavoriteResponse
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {
    // 툴바 관련 필드
    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> get() = _toolbarTitle
    private val _leadingIconDrawable = MutableLiveData<Int>()
    val leadingIconDrawable: LiveData<Int> get() = _leadingIconDrawable

    private val _trailingIconDrawable = MutableLiveData<Int>()
    val trailingIconDrawable: LiveData<Int> get() = _trailingIconDrawable
    // 툴바 관련 필드 끝

    // 툴바 관련 메서드
    fun setToolbarTitle(title: String) {
        _toolbarTitle.postValue(title)
    }

    fun setLeadingIcon(resId: Int) {
        _leadingIconDrawable.postValue(resId)
    }

    fun setTrailingIcon(resId: Int) {
        _trailingIconDrawable.postValue(resId)
    }
    // 툴바 관련 메서드 끝

    // 옷관련 필드
    private val clothesRepository = ClothesRepository()
    var currentCategory = CategoryCode.TOTAL
    private val totalClothesList = mutableListOf<Clothes>()

    private val _clothesResponseStatus = MutableLiveData<Resource<*>>()
    val clothesResponseStatus: LiveData<Resource<*>>
        get() = _clothesResponseStatus

    // ExpandableRecyclerView에서 observe하는 리스트
    private val _clothesListLiveData = MutableLiveData<List<PhotoItem<Clothes>>>()
    val clothesListLiveData: LiveData<List<PhotoItem<Clothes>>>
        get() = _clothesListLiveData
    // 옷관련 필드 끝

    //add

    private var _resFavorite = MutableLiveData<String>()
    val resFavorite:LiveData<String>
        get() = _resFavorite

    //add end
    suspend fun getAllClothes() = withContext(Dispatchers.IO) {
        setLoading()
        val result: Resource<ClothesResponse> = clothesRepository.getAllClothes()
        _clothesResponseStatus.postValue(result)
        if (result.status == Status.SUCCESS) {
            totalClothesList.addAll(result.data!!.dataSet!!)
        }
    }

    suspend fun getAllFriendClothes(email: String) = withContext(Dispatchers.IO) {
        setLoading()
        val result: Resource<ClothesResponse> = clothesRepository.getAllFriendClothes(email)
        _clothesResponseStatus.postValue(result)
        if (result.status == Status.SUCCESS) {
            totalClothesList.addAll(result.data!!.dataSet!!)
        }
    }

    fun getAllFriendClothesWithCategory(email: String, category: Int) = viewModelScope.launch {
        getAllFriendClothes(email)
        updateClothesByCategory(category)
    }

    fun getAllClothesWithCategory(category: Int) = viewModelScope.launch {
        getAllClothes()
        updateClothesByCategory(category)
    }

    fun addFavorite(clothesId: Int) = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            val result: Resource<ClothesFavoriteResponse> = clothesRepository.addfavorite(clothesId)
            _clothesResponseStatus.postValue(result)
            if (result.status == Status.SUCCESS) {
                _resFavorite.postValue(result.data!!.clothesId ?: "")
            }
        }
    }

     fun deleteFavorite(clothesId: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val result: Resource<ClothesFavoriteResponse> =
                clothesRepository.deletefavorite(clothesId)
            _clothesResponseStatus.postValue(result)
            if (result.status == Status.SUCCESS) {
                _resFavorite.postValue(result.data!!.clothesId ?: "")
            }
        }
    }

    /**
     * 삭제 성공 시,
     *  전체 TotalClothesList에서 해당 옷 삭제,
     *  현재 카테고리에 따라 _clothesListLiveDate 업데이트,
     */
    fun deleteClothesById(clothesId: Int) = viewModelScope.launch {
        setLoading()
        withContext(Dispatchers.IO) {
            val result = clothesRepository.deleteClothesById(clothesId)
            if (result.status == Status.SUCCESS) {
                totalClothesList.remove(totalClothesList.find { clothes -> clothes.clothesId == clothesId })
                updateClothesByCategory(currentCategory)
                _clothesResponseStatus.postValue(Resource.success(result.data!!))
            } else {
                _clothesResponseStatus.postValue(result)
            }
        }
    }

    // 대분류 카테고리로 전체 옷 필터링
    private fun getClothesListWithLargeCategory(category: Int): MutableList<Clothes> {
        val largeCategory = category.toString()[0].digitToInt()
        return totalClothesList.filter { clothes -> clothes.category.toString()[0].digitToInt() == largeCategory }
            .toMutableList()
    }

    // 소분류 카테고리로 옷 필터링
    private fun getClothesListWithSmallCategory(category: Int): MutableList<Clothes> {
        val filteredList = getClothesListWithLargeCategory(category)
        return filteredList.filter { clothes -> clothes.category == category }.toMutableList()
    }

    /**
     * 최근에 추가한 옷이 있으면
     * 최근에 추가한 옷 헤더 추가
     * 최근에 추가한 옷 child 추가
     *
     * 즐겨찾기한 옷이 있으면
     * 즐겨찾기한 옷 헤더 추가
     * 즐겨찾기한 옷 child 추가
     *
     * 최근에 추가한 옷, 즐겨찾기한 옷 둘중에 하나라도 있으면
     * -> 카테고리 별 옷 헤더 추가
     *
     * 카테고리 별 옷 child 추가
     */
    // TODO: 2022/01/31 ExpandableRecyclerViewAdapter 뷰타입 Divider 추가
    private fun makePhotoItemList(filteredList: MutableList<Clothes>): List<PhotoItem<Clothes>> {
        val photoItemList = mutableListOf<PhotoItem<Clothes>>()
        val recentlyCreatedClothesList = getCreatedRecentlyClothesList(filteredList)
        val favoriteClothesList = getFavoriteClothesList(filteredList)
        // 주석의 첫번째 조건
        if (recentlyCreatedClothesList.size != 0) {
            photoItemList.add(
                PhotoItem<Clothes>(
                    ExpandableRecyclerViewAdapter.HEADER,
                    "최근에 추가한 옷"
                ).apply {
                    initInvisibleItems()
                })
            photoItemList.addAll(getPhotoItemList(recentlyCreatedClothesList))
        }
        // 주석의 두번째 조건
        if (favoriteClothesList.size != 0) {
            photoItemList.add(
                PhotoItem<Clothes>(
                    ExpandableRecyclerViewAdapter.HEADER,
                    "즐겨찾기한 옷"
                ).apply {
                    initInvisibleItems()
                })
            photoItemList.addAll(getPhotoItemList(favoriteClothesList))
        }
        // 주석의 마지막 조건
        if (favoriteClothesList.size != 0 || recentlyCreatedClothesList.size != 0) {
            photoItemList.add(PhotoItem(ExpandableRecyclerViewAdapter.HEADER))
        }
        photoItemList.addAll(getPhotoItemList(filteredList))
        return photoItemList
    }

    // List<Clothes>를 List<PhotoItem<Clothes>>로 변환
    private fun <T> getPhotoItemList(list: MutableList<T>): List<PhotoItem<T>> {
        val result = mutableListOf<PhotoItem<T>>()
        list.forEach { clothes ->
            result.add(
                PhotoItem(
                    ExpandableRecyclerViewAdapter.CHILD,
                    content = clothes
                )
            )
        }
        return result
    }

    // 즐겨찾기 한 옷
    private fun getFavoriteClothesList(list: MutableList<Clothes>): MutableList<Clothes> {
        return list.filter { clothes -> clothes.favorite == 1 }.toMutableList()
    }

    // 최근 일주일 간 생성된 옷
    private fun getCreatedRecentlyClothesList(list: MutableList<Clothes>): MutableList<Clothes> {
        val today = System.currentTimeMillis()
        val oneWeekMillis = 1000 * 7 * 24 * 60 * 60
        return list.filter { clothes -> dateStringToMillis(clothes.createDate) > (today - oneWeekMillis) }
            .toMutableList()
    }

    // string to millis
    private fun dateStringToMillis(dateString: String): Long {
        val stringFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
        val date = stringFormat.parse(dateString)
        return date.time
    }


    /**
     * 대분류로 분류 후, 소분류로 분류
     * (대분류 전체인 경우 code는 10보다 작음)
     * List<PhotoItem<Clothes>> 생성 후
     * _clothesListLiveData 업데이트
     */
    // View에서 호출하는 카테고리 별 조회
    fun updateClothesByCategory(category: Int) {
        currentCategory = category
        when {
            category == CategoryCode.TOTAL -> {
                _clothesListLiveData.postValue(makePhotoItemList(totalClothesList))
            }
            category < 10 -> {
                _clothesListLiveData.postValue(
                    makePhotoItemList(
                        getClothesListWithLargeCategory(
                            category
                        )
                    )
                )
            }
            else -> {
                _clothesListLiveData.postValue(
                    makePhotoItemList(
                        getClothesListWithSmallCategory(
                            category
                        )
                    )
                )
            }
        }
    }

    private fun setLoading() = _clothesResponseStatus.postValue(Resource.loading(null))
}
