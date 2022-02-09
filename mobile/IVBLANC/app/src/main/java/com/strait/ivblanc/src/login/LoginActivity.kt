package com.strait.ivblanc.src.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout_login_container, loginFragment).commit()

    }

}