package com.strait.ivblanc.src.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.UserForLogin
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.FragmentLoginBinding
import com.strait.ivblanc.src.main.MainActivity
import com.strait.ivblanc.util.InputValidUtil
import com.strait.ivblanc.util.LoginUtil
import com.strait.ivblanc.util.Status

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by activityViewModels()
    var loginActivity : LoginActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.buttonLoginFJoin.setOnClickListener {
            loginActivity!!.setFragment(2)
        }
        binding.buttonLoginFLogin.setOnClickListener {
            if (isValidForm()) {
                login()
            }
        }
        binding.editTextLoginFEmail.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidEmail(input)) {
                dismissErrorOnEmail()
            }
        }
        binding.editTextLoginFPassword.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidPassword(input)) {
                dismissErrorOnPassword()
            }
        }
        loginViewModel.loginRequestLiveData.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    LoginUtil.setAutoLogin(loginViewModel.isAutoLogin)
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                    dismissLoading()
                }
                Status.ERROR -> {
                    toast(it.message!!, Toast.LENGTH_SHORT)
                    dismissLoading()
                }
                Status.LOADING -> {
                    setLoading()
                }
            }
        }
        binding.checkboxLoginFAutoLogin.setOnCheckedChangeListener { _, isChecked -> loginViewModel.isAutoLogin = isChecked }
    }

    private fun login() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            val token = it.result
            loginViewModel.emailLogin(
                UserForLogin(
                    binding.editTextLoginFEmail.text.toString(),
                    binding.editTextLoginFPassword.text.toString(),
                    fcmToken = token
                )
            )
        }
    }

    private fun setLoading() {
        binding.progressBarLoginFLoading.visibility = View.VISIBLE
    }

    private fun dismissLoading() {
        binding.progressBarLoginFLoading.visibility = View.GONE
    }

    private fun isValidForm(): Boolean {
        val email = binding.editTextLoginFEmail.text.toString()
        val pw = binding.editTextLoginFPassword.text.toString()
        var flag = 1
        // 이메일 유효성 검사
        if (InputValidUtil.isValidEmail(email)) {
            dismissErrorOnEmail()
        } else {
            flag *= 0
            setErrorOnEmail()
        }
        // 비밀번호 유효성 검사
        if (InputValidUtil.isValidPassword(pw)) {
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
        binding.textInputLayoutLoginFPassword.error =
            resources.getString(R.string.passwordErrorMessage)
    }


}