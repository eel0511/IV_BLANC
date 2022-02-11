package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strait.ivblanc.config.BaseResponse
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.dto.UserForLogin
import com.strait.ivblanc.data.model.response.EmailCheckResponse
import com.strait.ivblanc.data.model.response.JoinResponse
import com.strait.ivblanc.data.model.response.LoginResponse
import com.strait.ivblanc.data.repository.UserRepository
import com.strait.ivblanc.util.LoginUtil
import com.strait.ivblanc.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    val userRepository = UserRepository()
    var isAutoLogin = LoginUtil.isAutoLogin()

    val joinRequestLiveData = MutableLiveData<Resource<JoinResponse?>>()
    val emailCheckRequestLiveData = MutableLiveData<Resource<EmailCheckResponse>>()

    private val _loginRequestLiveData = MutableLiveData<Resource<LoginResponse>>()
    val loginRequestLiveData: LiveData<Resource<LoginResponse>>
        get() = _loginRequestLiveData

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

    fun emailLogin(user: UserForLogin) = viewModelScope.launch {
        _loginRequestLiveData.postValue(Resource.loading(null))
        withContext(Dispatchers.IO) {
            _loginRequestLiveData.postValue(userRepository.emailLogin(user))
        }
    }
}