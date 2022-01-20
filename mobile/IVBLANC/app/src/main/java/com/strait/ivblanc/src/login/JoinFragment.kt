package com.strait.ivblanc.src.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.strait.ivblanc.R
import com.strait.ivblanc.config.BaseFragment
import com.strait.ivblanc.data.model.dto.UserForJoin
import com.strait.ivblanc.data.model.viewmodel.LoginViewModel
import com.strait.ivblanc.databinding.FragmentJoinBinding
import com.strait.ivblanc.util.InputValidUtil
import com.strait.ivblanc.util.Resource
import com.strait.ivblanc.util.Status
import java.util.*

class JoinFragment : BaseFragment<FragmentJoinBinding>(FragmentJoinBinding::bind, R.layout.fragment_join) {
    val loginViewModel: LoginViewModel by activityViewModels()
    var isEmailChecked = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState)
        val constraintLayout = parent!!.findViewById<ConstraintLayout>(R.id.constaraintLayout_joinF)
        setLayoutWithoutStatusBarHeight(constraintLayout)
        return parent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.buttonJoinFJoin.setOnClickListener {
            if (checkInputForm() && isEmailChecked) {
                join()
            } else if(!isEmailChecked) {
                toast(resources.getText(R.string.emailNotCheckedErrorMessage) as String, Toast.LENGTH_LONG)
            }
       }

        binding.buttonJoinFCheckEmail.setOnClickListener {
            if(InputValidUtil.isValidEmail(binding.editTextJoinFEmail.text.toString())) {
                loginViewModel.emailCheck(binding.editTextJoinFEmail.text.toString())
            } else {
                toast(resources.getText(R.string.emailErrorMessage) as String, Toast.LENGTH_LONG)
            }
        }

        // todo: 리팩터링 해야함
        binding.editTextJoinFEmail.addTextChangedListener {
            isEmailChecked = false
            val input = it.toString()
            if (InputValidUtil.isValidEmail(input)) {
                binding.textInputLayoutJoinFEmail.error = null
            }
        }

        binding.editTextJoinFPassword.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidPassword(input)) {
                binding.textInputLayoutJoinFPassword.error = null
            }
        }

        binding.editTextJoinFPasswordcheck.addTextChangedListener {
            val input = it.toString()
            val originPassword = binding.editTextJoinFPassword.text.toString()
            if (InputValidUtil.isValidPassword(input) && input == originPassword) {
                binding.textInputLayoutJoinFPasswordcheck.error = null
            }
        }

        binding.editTextJoinFName.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidName(input)) {
                binding.textInputLayoutJoinFName.error = null
            }
        }

        binding.editTextJoinFPhone.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidPhoneNumber(input)) {
                binding.textInputLayoutJoinFPhone.error = null
            }
        }

        binding.editTextJoinFBirthday.addTextChangedListener {
            val input = it.toString()
            if (InputValidUtil.isValidBirthDay(input)) {
                binding.textInputLayoutJoinFBirthday.error = null
            }
        }
    }

    fun observeViewModel() {
        loginViewModel.joinRequestLiveData.observe(requireActivity()) {
            when(it.status) {
                Status.LOADING -> {
                    binding.progressBarJoinFLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBarJoinFLoading.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBarJoinFLoading.visibility = View.GONE
                    toast(it.message!!, Toast.LENGTH_SHORT)
                }
            }
        }

        loginViewModel.emailCheckRequestLiveData.observe(requireActivity()) {
            when(it.status) {
                Status.LOADING -> {
                    binding.progressBarJoinFLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    isEmailChecked = true
                    binding.progressBarJoinFLoading.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBarJoinFLoading.visibility = View.GONE
                    toast(it.message!!, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    private fun join() {
        val email = binding.editTextJoinFEmail.text.toString()
        val password = binding.editTextJoinFPassword.text.toString()
        val name = binding.editTextJoinFName.text.toString()
        val birthYear = binding.editTextJoinFBirthday.text.toString().substring(0, 2).toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val phoneNumber = binding.editTextJoinFPhone.text.toString()
        val gender:Int = when(binding.radioGroupJoinFGenders.checkedRadioButtonId) {
            (R.id.radioButton_joinF_male) -> 1
            (R.id.radioButton_joinF_female) -> 2
            else -> return
        }
        val age = when(currentYear - birthYear >= 0) {
            true -> currentYear - birthYear + 1
            else -> 100 - birthYear + currentYear + 1
        }

        loginViewModel.join(UserForJoin(email, password, name, gender, age, phoneNumber))
    }

    private fun checkInputForm(): Boolean {
        var result = 1
        val email = binding.editTextJoinFEmail.text.toString()
        val password = binding.editTextJoinFPassword.text.toString()
        val passwordCheck = binding.editTextJoinFPasswordcheck.text.toString()
        val name = binding.editTextJoinFName.text.toString()
        val birthDay = binding.editTextJoinFBirthday.text.toString()
        val phoneNumber = binding.editTextJoinFPhone.text.toString()
        val gendersCheckedId = binding.radioGroupJoinFGenders.checkedRadioButtonId // if not selected any button: -1

        if(!InputValidUtil.isValidEmail(email)) {
            result *= 0
            binding.textInputLayoutJoinFEmail.error = resources.getText(R.string.emailErrorMessage)
        }
        if(!InputValidUtil.isValidPassword(password) || !InputValidUtil.isValidPassword(passwordCheck)) {
            result *= 0
            binding.textInputLayoutJoinFPassword.error = resources.getText(R.string.passwordErrorMessage)
        }
        if(!InputValidUtil.isValidPassword(passwordCheck)) {
            result *= 0
            binding.textInputLayoutJoinFPasswordcheck.error = resources.getText(R.string.passwordErrorMessage)
        }
        if(password != passwordCheck) {
            result *= 0
            binding.textInputLayoutJoinFPasswordcheck.error = resources.getText(R.string.passwordCheckErrorMessage)
        }
        if(!InputValidUtil.isValidName(name)) {
            result *= 0
            binding.textInputLayoutJoinFName.error = resources.getText(R.string.nameErrorMessage)
        }
        if(!InputValidUtil.isValidBirthDay(birthDay)) {
            result *= 0
            binding.textInputLayoutJoinFBirthday.error = resources.getText(R.string.birthdayErrorMessage)
        }
        if(!InputValidUtil.isValidPhoneNumber(phoneNumber)) {
            result *= 0
            binding.textInputLayoutJoinFPhone.error = resources.getText(R.string.phoneNumberErrorMessage)
        }
        if(gendersCheckedId == -1) {
            result *= 0
            toast(resources.getString(R.string.genderNotCheckedErrorMessage), Toast.LENGTH_SHORT)
        }

        return when(result) {
            1 -> true
            else -> false
        }
    }

    fun setLayoutWithoutStatusBarHeight(layout: ConstraintLayout) {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        layout.minHeight = requireActivity().resources.displayMetrics.heightPixels - result
    }
}