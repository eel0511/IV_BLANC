package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.R
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.response.ClothesResponse
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
import java.text.SimpleDateFormat
import java.util.*

class ClothesViewModel: ViewModel() {

    // 옷관련 필드
    private val clothesRepository = ClothesRepository()
    var currentCategory = CategoryCode.TOTAL
    private val totalClothesList = mutableListOf<Clothes>()

    private val _clothesResponseStatus = MutableLiveData<Resource<*>>()
    val clothesResponseStatus: LiveData<Resource<*>>
        get() = _clothesResponseStatus

    // TODO: 2022/02/08 List<PhotoItem<Clothes>>로 변환하는 함수는 따로 추출
    // ExpandableRecyclerView에서 observe하는 리스트
    private val _clothesListLiveData = MutableLiveData<List<PhotoItem<Clothes>>>()
    val clothesListLiveData : LiveData<List<PhotoItem<Clothes>>>
        get() = _clothesListLiveData

    private val _clothesList = MutableLiveData<List<Clothes>>()
    val clothesList: LiveData<List<Clothes>> get() = _clothesList
    // 옷관련 필드 끝

    suspend fun getAllClothes() = withContext(Dispatchers.IO) {
        setLoading()
        val result: Resource<ClothesResponse> = clothesRepository.getAllClothes()
        _clothesResponseStatus.postValue(result)
        if(result.status == Status.SUCCESS) {
            totalClothesList.addAll(result.data!!.dataSet!!)
        }
    }

    fun getAllClothesWithCategory(category: Int) = viewModelScope.launch {
        getAllClothes()
        updateClothesByCategory(category)
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
            if(result.status == Status.SUCCESS) {
                totalClothesList.remove(totalClothesList.find { clothes -> clothes.clothesId == clothesId })
                updateClothesByCategory(currentCategory)
                _clothesResponseStatus.postValue(Resource.success(result.data!!))
            } else {
                _clothesResponseStatus.postValue(result)
            }
        }
    }

    // 대분류 카테고리로 전체 옷 필터링
    // 0이면 전체 옷 반환
    private fun getClothesListWithLargeCategory(category: Int): MutableList<Clothes> {
        if(category == 0) return totalClothesList
        val largeCategory = category.toString()[0].digitToInt()
        return totalClothesList.filter { clothes -> clothes.category.toString()[0].digitToInt() == largeCategory }.toMutableList()
    }

    // 소분류 카테고리로 옷 필터링
    // -1은 대분류 전체를 반환해야함
    private fun getClothesListWithSmallCategory(category: Int): MutableList<Clothes> {
        if(category == -1) {
            _largeCategory.value?.let {
                return getClothesListWithLargeCategory(it)
            }
        }
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
        if(recentlyCreatedClothesList.size != 0) {
            photoItemList.add(PhotoItem<Clothes>(ExpandableRecyclerViewAdapter.HEADER, "최근에 추가한 옷").apply{
                initInvisibleItems()
            })
            photoItemList.addAll(getPhotoItemList(recentlyCreatedClothesList))
        }
        // 주석의 두번째 조건
        if(favoriteClothesList.size != 0) {
            photoItemList.add(PhotoItem<Clothes>(ExpandableRecyclerViewAdapter.HEADER, "즐겨찾기한 옷").apply {
                initInvisibleItems()
            })
            photoItemList.addAll(getPhotoItemList(favoriteClothesList))
        }
        // 주석의 마지막 조건
        if(favoriteClothesList.size != 0 || recentlyCreatedClothesList.size != 0) {
            photoItemList.add(PhotoItem(ExpandableRecyclerViewAdapter.HEADER))
        }
        photoItemList.addAll(getPhotoItemList(filteredList))
        return photoItemList
    }

    // List<Clothes>를 List<PhotoItem<Clothes>>로 변환
    private fun <T> getPhotoItemList(list: MutableList<T>):List<PhotoItem<T>> {
        val result = mutableListOf<PhotoItem<T>>()
        list.forEach { clothes -> result.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = clothes)) }
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
        return list.filter { clothes -> dateStringToMillis(clothes.createDate) > (today - oneWeekMillis)}.toMutableList()
    }

    // string to millis
    private fun dateStringToMillis(dateString: String): Long {
        val stringFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss", Locale.KOREA)
        val date = stringFormat.parse(dateString)
        return date.time
    }

    private fun setLoading() = _clothesResponseStatus.postValue(Resource.loading(null))


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
                _clothesList.postValue(totalClothesList.toMutableList())
            }
            category in 1..9 -> {
                _clothesListLiveData.postValue(makePhotoItemList(getClothesListWithLargeCategory(category)))
                _clothesList.postValue(getClothesListWithLargeCategory(category))
            }
            category == CategoryCode.TOTAL_SMALL -> {
                _clothesListLiveData.postValue(makePhotoItemList(getClothesListWithLargeCategory(largeCategory.value!!)))
                _clothesList.postValue(getClothesListWithLargeCategory(largeCategory.value!!))
            }
            else -> {
                _clothesListLiveData.postValue(makePhotoItemList(getClothesListWithSmallCategory(category)))
                _clothesList.postValue(getClothesListWithSmallCategory(category))
            }
        }
    }

    val categorySet = CategoryCode().codeSet

    private val _largeCategory = MutableLiveData<Int>(CategoryCode.TOTAL) // CategoryCode 1 ~ 7까지
    private val _smallCategory = MutableLiveData<Int>(CategoryCode.TOTAL_SMALL) // CategoryCode 대분류에서 가져오기
    val largeCategory: LiveData<Int> get() = _largeCategory
    val smallCategory: LiveData<Int> get() = _smallCategory

    fun setLargeCategory(largeCategory: Int) = _largeCategory.postValue(largeCategory)
    fun setSmallCategory(smallCategory: Int) = _smallCategory.postValue(smallCategory)

    val largeCategorySet = getLargeCategories()

    // 대분류만 나타낸다. 전체 카테고리 셋을 순회하며 0부터 9사이의 값을 취한다.
    // CategoryCode.TOTAL = 0, 전체도 포함한다.
    private fun getLargeCategories(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        categorySet.forEach { entry ->
            if(entry.key in 0..9) result.add(entry.toPair())
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