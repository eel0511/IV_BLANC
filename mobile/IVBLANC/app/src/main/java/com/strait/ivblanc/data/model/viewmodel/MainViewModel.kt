package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 네트워크에서 받은 모든 정보는 id를 키로 HashMap에 저장?
 * 문제점: 변경이 있을 때만 네트워크에 요청, 어떻게 변경을 감지?
 */
class MainViewModel: ViewModel() {
    private val clothesRepository = ClothesRepository()
    var currentCategory = CategoryCode.TOTAL
    private val totalClothesList = mutableListOf<Clothes>()

    private val _clothesResponseStatus = MutableLiveData<Resource<ClothesResponse>>()
    val clothesResponseStatus: LiveData<Resource<ClothesResponse>>
        get() = _clothesResponseStatus

    private val _clothesListLiveData = MutableLiveData<List<PhotoItem<Clothes>>>()
    val clothesListLiveData : LiveData<List<PhotoItem<Clothes>>>
        get() = _clothesListLiveData

    // TODO: 2022/01/31 page 삭제
    fun getAllClothes(page: Int) = viewModelScope.launch {
        setLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val result: Resource<ClothesResponse> = clothesRepository.getAllClothes(page)
            _clothesResponseStatus.postValue(result)
            updateResult(result)
        }
    }

    /**
     * @param result
     * clothesRepository에서 받은 response
     */
    // TODO: 2022/01/31 paging이 사라져서 필요 없어짐
    private fun updateResult(result: Resource<ClothesResponse>) {
        //
        if(result.status == Status.SUCCESS) {
            result.data?.dataSet?.let {
                // 서버에서 받은 옷 목록이 비어있지 않을 때, _clothesListLiveData 내부의 list에 옷 목록 추가하여 post
                if(it.isNotEmpty()) {
                    val mutableList = getPhotoItemListFromLiveData()
                    for(clothes in it) {
                        mutableList.add(PhotoItem(ExpandableRecyclerViewAdapter.CHILD, content = clothes))
                    }
                    _clothesListLiveData.postValue(mutableList)
                }
            }
        }
    }

    // 대분류 카테고리로 전체 옷 필터링
    private fun getClothesListWithLargeCategory(category: Int): MutableList<Clothes> {
        val largeCategory = category.toString()[0].digitToInt()
        return totalClothesList.filter { clothes -> clothes.category.toString()[0].digitToInt() == largeCategory }.toMutableList()
    }

    // 소분류 카테고리로 옷 필터링
    private fun getClothesListWithSmallCategory(category: Int): MutableList<Clothes> {
        val filteredList = getClothesListWithLargeCategory(category)
        return filteredList.filter { clothes -> clothes.category == category }.toMutableList()
    }

    // 라이브 데이터가 비어있으면 빈 리스트 반환
    private fun getPhotoItemListFromLiveData():MutableList<PhotoItem<Clothes>> {
        _clothesListLiveData.value?.let { it ->
            return it.toMutableList()
        }
        return mutableListOf()
    }

    /**
     * 대분류로 분류 후, 소분류로 분류
     * _clothesListLiveData 업데이트
     */
    // View에서 호출하는 카테고리 별 조회
    fun getClothesByCategory(category: Int) {

    }

    private fun setLoading() = _clothesResponseStatus.postValue(Resource.loading(null))
}