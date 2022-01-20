package com.strait.ivblanc.data.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.response.JoinResponse
import com.strait.ivblanc.data.repository.UserRepository
import com.strait.ivblanc.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val userRepository = UserRepository()

    val joinRequestLiveData = MutableLiveData<Resource<JoinResponse?>>()

    fun join(user: UserForJoin) = CoroutineScope(Dispatchers.Main).launch {
        CoroutineScope(Dispatchers.IO).launch {
            joinRequestLiveData.postValue(userRepository.join(user))
        }
    }
}