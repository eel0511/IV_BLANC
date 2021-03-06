package com.strait.ivblanc.src.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseActivity
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.ActivityLoginBinding
import com.strait.ivblanc.src.main.MainActivity
import com.strait.ivblanc.util.LoginUtil

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    val loginViewModel: LoginViewModel by viewModels()
    lateinit var loginFragment: Fragment
    lateinit var joinFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(LoginUtil.isAutoLogin() && LoginUtil.isTokenExisted()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginFragment = LoginFragment()
        joinFragment = JoinFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout_login_container, loginFragment).commit()
        //setFragment(1)

    }

    fun setFragment(num: Int) {
        when (num) {
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_login_container, loginFragment).commit()
            }
            else -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout_login_container, joinFragment).commit()
            }
        }

    }

}