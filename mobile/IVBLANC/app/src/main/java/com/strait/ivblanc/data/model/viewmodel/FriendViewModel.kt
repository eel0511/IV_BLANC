package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.Clothes
import com.strait.ivblanc.data.model.dto.Friend
import com.strait.ivblanc.data.model.dto.FriendViewdata
import com.strait.ivblanc.data.model.dto.PhotoItem
import com.strait.ivblanc.data.model.response.ClothesResponse
import com.strait.ivblanc.data.model.response.FriendListResponse
import com.strait.ivblanc.data.model.response.FriendResponse
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.data.repository.FriendRepository
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FriendViewModel :ViewModel(){


    private val _friendName = MutableLiveData<String>()
    val friendName:LiveData<String> get()=_friendName

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
    fun setFriendName(name:String){
        _friendName.postValue(name)
    }
    // 옷관련 필드
    private val clothesRepository = ClothesRepository()
    var currentCategory = CategoryCode.TOTAL
    private var totalClothesList = mutableListOf<Clothes>()

    private val _clothesResponseStatus = MutableLiveData<Resource<*>>()
    val clothesResponseStatus: LiveData<Resource<*>>
        get() = _clothesResponseStatus

    // ExpandableRecyclerView에서 observe하는 리스트
    private val _clothesListLiveData = MutableLiveData<List<List<PhotoItem<Clothes>>>>()
    val clothesListLiveData : LiveData<List<List<PhotoItem<Clothes>>>>
        get() = _clothesListLiveData
    // 옷관련 필드 끝
    //친구 관련
    private val friendRepository = FriendRepository()

    private val _friendResponseStatus = MutableLiveData<Resource<*>>()
    val friendResponseStatus: LiveData<Resource<*>>
        get() = _friendResponseStatus
    private val totalFriendList = mutableListOf<Friend>()
    private val totalFriendViewdataList = mutableListOf<FriendViewdata>()
    private val _friendListLiveData =  MutableLiveData<List<FriendViewdata>>()
    val friendListLiveData:LiveData<List<FriendViewdata>>
        get() = _friendListLiveData
    //친구 관련 끝


    // friend
    fun getAllFriends(applicant: String)=viewModelScope.launch {
        //친구 리스트를 가져옴
        getFriends(applicant)
        Log.d("bbbb", "getAllFriends: "+totalFriendList)

        //가져온 친구리스트로 각각 옷을 뽑아옴
        totalFriendList.forEach {
            getAllFriendClothes(it.friendEmail,it.friendName)
        }

        //최종 친구목록 보여질 것
        _friendListLiveData.postValue(totalFriendViewdataList)
    }
    suspend fun getAllFriendClothes(email:String,name:String) = withContext(Dispatchers.Main) {
        setLoading()
        //default data
        var u =
            Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/00e3e841-0ec1-4261-909a-52ff448af69a.jpeg")
        val result: Resource<ClothesResponse> = clothesRepository.getAllFriendClothes(email)
        _friendResponseStatus.postValue(result)
        Log.d("aaaa", "getAllFriendClothes: "+result)

        if(result.status == Status.SUCCESS) {
            var list = arrayListOf<Uri>()
            for(i in result.data!!.dataSet!!.indices){
                if(i==4) break
                if(result.data!!.dataSet!![i].url!="NULL"&&result.data!!.dataSet!![i].url!=null){
                    list.add(Uri.parse(result.data!!.dataSet!![i].url))
                }
            }
             while(list.size<4){
                list.add(u)
            }
            var friendViewdata = FriendViewdata(name,list[0],list[1],list[2],list[3],u,u,u,u)
            if(!totalFriendViewdataList.contains(friendViewdata)){
                totalFriendViewdataList.addAll(listOf(friendViewdata))
            }
        }
        Log.d("aaaa", "getAllFriendClothes: "+totalFriendViewdataList)
    }
    suspend fun getFriends(applicant:String)=withContext(Dispatchers.IO) {
        val result: Resource<FriendListResponse> = friendRepository.getAllFriends(applicant)
        _friendResponseStatus.postValue(result)
        if(result.status==Status.SUCCESS){
            result.data!!.dataSet!!.forEach {
                if(!totalFriendList.contains(it)){
                    totalFriendList.add(it)
                }
            }
        }
    }
    //friend end

    private fun setLoading() = _friendResponseStatus.postValue(Resource.loading(null))

}