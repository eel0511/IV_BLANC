package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.EmailCheckResponse
import com.strait.ivblanc.data.model.response.JoinResponse
import com.strait.ivblanc.data.repository.UserRepository
import com.strait.ivblanc.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val userRepository = UserRepository()

    val joinRequestLiveData = MutableLiveData<Resource<JoinResponse?>>()
    val emailCheckRequestLiveData = MutableLiveData<Resource<EmailCheckResponse>>()

    fun join(user: UserForJoin) = CoroutineScope(Dispatchers.Main).launch {
        joinRequestLiveData.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            joinRequestLiveData.postValue(userRepository.join(user))
        }
    }

    fun emailCheck(email: String) = CoroutineScope(Dispatchers.Main).launch {
        emailCheckRequestLiveData.postValue(Resource.loading(null))
        CoroutineScope(Dispatchers.IO).launch {
            emailCheckRequestLiveData.postValue(userRepository.emailCheck(email))
        }
    }
}