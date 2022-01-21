package com.strait.ivblanc.src.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}