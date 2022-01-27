package com.strait.ivblanc.src.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.FragmentLoginBinding
import com.strait.ivblanc.util.InputValidUtil

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.buttonLoginFLogin.setOnClickListener {
            if(isValidForm()) {
                // request login
            }
        }
        binding.editTextLoginFEmail.addTextChangedListener {
            val input = it.toString()
            if(InputValidUtil.isValidEmail(input)) {
                dismissErrorOnEmail()
            }
        }
        binding.editTextLoginFPassword.addTextChangedListener {
            val input = it.toString()
            if(InputValidUtil.isValidPassword(input)) {
                dismissErrorOnPassword()
            }
        }
    }

    private fun isValidForm(): Boolean {
        val email = binding.editTextLoginFEmail.text.toString()
        val pw = binding.editTextLoginFPassword.text.toString()
        var flag = 1
        // 이메일 유효성 검사
        if(InputValidUtil.isValidEmail(email)) {
            dismissErrorOnEmail()
        } else {
            flag *= 0
            setErrorOnEmail()
        }
        // 비밀번호 유효성 검사
        if(InputValidUtil.isValidPassword(pw)) {
            dismissErrorOnPassword()
        } else {
            flag *= 0
            setErrorOnPassword()
        }
        // 전부 통과해야 flag == 1
        return flag == 1
    }

    private fun setErrorOnEmail() {
        binding.textInputLayoutLoginFEmail.error = resources.getString(R.string.emailErrorMessage)
    }
    private fun dismissErrorOnEmail() {
        binding.textInputLayoutLoginFEmail.error = null
    }
    private fun dismissErrorOnPassword() {
        binding.textInputLayoutLoginFPassword.error = null
    }
    private fun setErrorOnPassword() {
        binding.textInputLayoutLoginFPassword.error = resources.getString(R.string.passwordErrorMessage)
    }


}