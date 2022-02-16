package com.strait.ivblanc.data.model.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.adapter.ExpandableRecyclerViewAdapter
import com.strait.ivblanc.data.model.dto.*
import com.strait.ivblanc.data.model.response.*
import com.strait.ivblanc.data.repository.ClothesRepository
import com.strait.ivblanc.data.repository.FriendRepository
import com.strait.ivblanc.data.repository.StyleRepository
import com.strait.ivblanc.util.CategoryCode
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FriendViewModel : ViewModel() {


    private val _friendName = MutableLiveData<String>()
    val friendName: LiveData<String> get() = _friendName

    private val _friendEmail = MutableLiveData<String>()
    val firendEmail: LiveData<String> get() = _friendEmail

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
    fun setFriendName(name: String) {
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
    val clothesListLiveData: LiveData<List<List<PhotoItem<Clothes>>>>
        get() = _clothesListLiveData

    // 옷관련 필드 끝

    private val styleRepository = StyleRepository()
    private lateinit var friendViewdata: FriendViewdata

    //친구 관련
    private val friendRepository = FriendRepository()

    private val _acceptfriendName = MutableLiveData<String>()
    val acceptfriendName: LiveData<String> get() = _acceptfriendName

    private val _friendResponseStatus = MutableLiveData<Resource<*>>()
    val friendResponseStatus: LiveData<Resource<*>>
        get() = _friendResponseStatus
    private val totalFriendList = mutableListOf<Friend>()
    private val totalFriendViewdataList = mutableListOf<FriendViewdata>()
    private val _friendListLiveData = MutableLiveData<List<FriendViewdata>>()
    val friendListLiveData: LiveData<List<FriendViewdata>>
        get() = _friendListLiveData

    private val totalRequestFriendList = mutableListOf<Friend>()
    private val _friendRequestListLiveData = MutableLiveData<List<Friend>>()
    val friendRequestListLiveData: LiveData<List<Friend>>
        get() = _friendRequestListLiveData

    private val totalWaitFriendList = mutableListOf<Friend>()
    private val _friendWaitListLiveData = MutableLiveData<List<Friend>>()
    val friendWaitListLiveData: LiveData<List<Friend>>
        get() = _friendWaitListLiveData
    //친구 관련 끝


    // friend
    fun setFriendEmail(email: String) = viewModelScope.launch {
        _friendEmail.postValue(email)
    }

    fun getAllFriends(applicant: String) = viewModelScope.launch {
        //친구 리스트를 가져옴
        getFriends(applicant)
        Log.d("bbbb", "getAllFriends: " + totalFriendList)

        //가져온 친구리스트로 각각 옷을 뽑아옴
        totalFriendList.forEach {
            getAllFriendClothes(it.friendEmail, it.friendName)
            getAllFriendStyle(it.friendEmail, it.friendName)
        }

        //최종 친구목록 보여질 것
        _friendListLiveData.postValue(totalFriendViewdataList)
    }

    suspend fun getAllFriendClothes(email: String, name: String) = withContext(Dispatchers.Main) {
        setLoading()
        //default data
        var u =
            Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/2d4f5277-a52a-4c38-a27c-16d1ca7f5a6f.png")
        var u2 =
            Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/2d4f5277-a52a-4c38-a27c-16d1ca7f5a6f.png")
        val result: Resource<ClothesResponse> = clothesRepository.getAllFriendClothes(email)
        _friendResponseStatus.postValue(result)
        Log.d("aaaa", "getAllFriendClothes: " + result)

        friendViewdata =
            FriendViewdata(name, email, u, u, u, u, u, u, u, u)
        //clothes
        if (result.status == Status.SUCCESS) {
            var list = arrayListOf<Uri>()
            for (i in result.data!!.dataSet!!.indices) {
                if (i == 4) break
                if (result.data!!.dataSet!![i].url != "NULL" && result.data!!.dataSet!![i].url != null) {
                    list.add(Uri.parse(result.data!!.dataSet!![i].url))
                }
            }
            while (list.size < 4) {
                list.add(u)
            }

            // style
            val result2: Resource<StyleAllResponse> = styleRepository.getAllFriendStyles(email)
            _friendResponseStatus.postValue(result2)

            friendViewdata =
                FriendViewdata(name, email, list[0], list[1], list[2], list[3], u, u, u, u)
        }
        Log.d("aaaa", "getAllFriendClothes: " + totalFriendViewdataList)
    }

    suspend fun getAllFriendStyle(email: String, name: String) = withContext(Dispatchers.Main) {
        setLoading()
        //default data
        var u =
            Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/2d4f5277-a52a-4c38-a27c-16d1ca7f5a6f.png")
        var u2 =
            Uri.parse("https://storage.googleapis.com/iv-blanc.appspot.com/2d4f5277-a52a-4c38-a27c-16d1ca7f5a6f.png")

        // style
        val result2: Resource<StyleAllResponse> = styleRepository.getAllFriendStyles(email)
        _friendResponseStatus.postValue(result2)

        if (result2.status == Status.SUCCESS) {
            var stylelist = arrayListOf<Uri>()
            for (i in result2.data!!.dataSet!!.indices) {
                if (i == 4) break
                if (result2.data!!.dataSet!![i].url != "NULL" && result2.data!!.dataSet!![i].url != null) {
                    stylelist.add(Uri.parse(result2.data!!.dataSet!![i].url))
                }
            }
            while (stylelist.size < 4) {
                stylelist.add(u)
            }
            friendViewdata.style1 = stylelist[0]
            friendViewdata.style2 = stylelist[1]
            friendViewdata.style3 = stylelist[2]
            friendViewdata.style4 = stylelist[3]

        }
        if (!totalFriendViewdataList.contains(friendViewdata)) {
            totalFriendViewdataList.addAll(listOf(friendViewdata))
        }
    }

    suspend fun getFriends(applicant: String) = withContext(Dispatchers.IO) {
        val result: Resource<FriendListResponse> = friendRepository.getAllFriends(applicant)
        _friendResponseStatus.postValue(result)
        if (result.status == Status.SUCCESS) {
            result.data!!.dataSet!!.forEach {
                if (!totalFriendList.contains(it)) {
                    totalFriendList.add(it)
                }
            }
        }
    }

    suspend fun getmyrequest(applicant: String) = withContext(Dispatchers.IO) {
        val result: Resource<FriendListResponse> = friendRepository.getAllFriendRequest(applicant)
        _friendResponseStatus.postValue(result)
        totalRequestFriendList.clear()
        if (result.status == Status.SUCCESS) {
            result.data!!.dataSet!!.forEach {
                if (!totalRequestFriendList.contains(it)) {
                    totalRequestFriendList.add(it)
                }
            }
        }
    }

    fun getmyrequestFriend(applicant: String) = viewModelScope.launch {
        getmyrequest(applicant)
        _friendRequestListLiveData.postValue(totalRequestFriendList)
    }

    suspend fun acceptFriend(applicant: String, FriendEmail: String) = withContext(Dispatchers.IO) {
        val result: Resource<FriendResponse> =
            friendRepository.acceptFriend(FriendForUpload(FriendEmail, applicant))
        _friendResponseStatus.postValue(result)
        if (result.status == Status.SUCCESS) {
            _acceptfriendName.postValue(result.data!!.data!!.friendName)
        }
    }

    fun myacceptFriend(applicant: String, FriendEmail: String) = viewModelScope.launch {
        acceptFriend(applicant, FriendEmail)
        getmyrequestFriend(FriendEmail)
    }


    suspend fun cancelWaitFriend(applicant: String, FriendEmail: String) =
        withContext(Dispatchers.IO) {
            val result: Resource<FriendResponse> =
                friendRepository.cancelFriend(FriendForUpload(applicant, FriendEmail))
            Log.d("ssss", "cancelWaitFriend: " + result)
            _friendResponseStatus.postValue(result)
            if (result.status == Status.SUCCESS) {
                _acceptfriendName.postValue(result.data!!.data!!.friendName)
            }
        }

    fun cancelFriend(applicant: String, FriendEmail: String) = viewModelScope.launch {
        Log.d("ssss1111", "cancelFriend: " + totalWaitFriendList)
        cancelWaitFriend(applicant, FriendEmail)
        getWaitFriend(applicant)
        _friendWaitListLiveData.postValue(totalWaitFriendList)
        Log.d("ssss2222", "cancelFriend: " + totalWaitFriendList)
    }

    suspend fun getWaitFriend(applicant: String) = withContext(Dispatchers.IO) {
        val result: Resource<FriendListResponse> = friendRepository.getAllNotAcceptFriend(applicant)
        _friendResponseStatus.postValue(result)
        totalWaitFriendList.clear()
        if (result.status == Status.SUCCESS) {
            result.data!!.dataSet!!.forEach {
                if (!totalWaitFriendList.contains(it)) {
                    totalWaitFriendList.add(it)
                }
            }
        }
    }

    fun getmyWaitFriend(applicant: String) = viewModelScope.launch {
        getWaitFriend(applicant)
        _friendWaitListLiveData.postValue(totalWaitFriendList)
    }

    fun requestFriend(applicant: String, FriendEmail: String) = viewModelScope.launch {
        reqFriend(applicant, FriendEmail)
    }

    suspend fun reqFriend(applicant: String, FriendEmail: String) =
        withContext(Dispatchers.IO) {
            Log.d("ssss", "reqFriend: " + applicant + " " + FriendEmail)
            val result: Resource<FriendResponse> =
                friendRepository.requestFriend(FriendForUpload(applicant, FriendEmail))
            _friendResponseStatus.postValue(result)
            if (result.status == Status.SUCCESS) {
                _friendName.postValue(result.data!!.data!!.friendName)
            } else {
                Log.d("ssss", "reqFriend: " + result.message)
                if (result.message == "이미 요청보낸 친구입니다.") {
                    _friendName.postValue("error2")
                } else {
                    _friendName.postValue("error1")
                }
            }
        }

//friend end

    private fun setLoading() = _friendResponseStatus.postValue(Resource.loading(null))

}