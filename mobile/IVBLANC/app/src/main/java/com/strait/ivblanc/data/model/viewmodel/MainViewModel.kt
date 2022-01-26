package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.config.ApplicationClass
import com.strait.ivblanc.data.api.ClothesApi
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val clothesRepository = ClothesRepository()
    private val _clothesResponseStatus = MutableLiveData<Resource<ClothesResponse>>()
    val clothesResponseStatus: LiveData<Resource<ClothesResponse>>
        get() = _clothesResponseStatus

    private val _clothesListLiveData = MutableLiveData<List<PhotoItem<Clothes>>>()
    val clothesListLiveData : LiveData<List<PhotoItem<Clothes>>>
        get() = _clothesListLiveData

    fun getAllClothes(page: Int) = viewModelScope.launch {
        _clothesResponseStatus.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            val result: Resource<ClothesResponse> = clothesRepository.getAllClothes(page)
            _clothesResponseStatus.postValue(result)
            updateResult(result)
        }
    }

    /**
     * @param result
     * clothesRepository에서 받은 response
     * response의 status에 따라 _clothesResponseStatus post
     */
    private fun updateResult(result: Resource<ClothesResponse>) {
        //
        if(result.status == Status.SUCCESS) {
            result.data?.data?.let {
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

    private fun getPhotoItemListFromLiveData():MutableList<PhotoItem<Clothes>> {
        _clothesListLiveData.value?.let { it ->
            return it.toMutableList()
        }
        return mutableListOf()
    }
}