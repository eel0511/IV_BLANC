package com.strait.ivblanc.util

private const val TAG = "InputValidUtil_strait"
object InputValidUtil {
    val nameRegex = "^[가-힣a-zA-Z]{2,}+$".toRegex()
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$".toRegex()
    //영문, 숫자
    val passRegex1 = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,14}$".toRegex()
    //영문, 특문
    val passRegex2 = "^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,14}$".toRegex()
    //특문, 숫자
    val passRegex3 = "^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,14}$".toRegex()
    val passRegexes = listOf(passRegex1, passRegex2, passRegex3)

    val birthDayRegex = "([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))".toRegex()
    val phoneRegex = "^\\d{3}\\d{3,4}\\d{4}$".toRegex()

    fun isValidEmail(email: String): Boolean {
        val result = email.matches(emailRegex)
        return result
    }

    fun isValidName(name: String): Boolean {
        val result = name.matches(nameRegex)
        return result
    }

    fun isValidPassword(password: String): Boolean {
        for(i in passRegexes) {
            if(password.matches(i))
                return true
        }
        return false
    }

    fun isValidBirthDay(birthDay: String): Boolean {
        return birthDay.matches(birthDayRegex)
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(phoneRegex)
    }
}