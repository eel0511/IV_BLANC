package com.strait.ivblanc.util

import org.junit.Assert.*

import org.junit.Test

class InputValidUtilTest {

    @Test
    fun `올바른 이메일`() {
        val email = "ms@gmail.com"
        val result = InputValidUtil.isValidEmail(email)
        assertEquals(true, result)
    }

    @Test
    fun `빈칸이 있는 이메일`() {
        val email = "ms 1@naver.com"
        assertEquals(false, InputValidUtil.isValidEmail(email))
    }

    @Test
    fun `올바른 이름`() {
        val name = "김김김"
        assertEquals(true, InputValidUtil.isValidName(name))
    }

    @Test
    fun `빈칸이 있는 이름`() {
        val name = "김 나 박"
        val result = InputValidUtil.isValidName(name)
        assertEquals(false, result)
    }

    @Test
    fun `짧은 이름`() {
        val name = "김"
        val result = InputValidUtil.isValidName(name)
        assertEquals(false, result)
    }

    @Test
    fun `올바른 pwd`() {
        //val pwd = "012345!a"
        val pwd = "0123454!"
        val result = InputValidUtil.isValidPassword(pwd)
        assertEquals(true, result)
    }

    @Test
    fun`올바른 생년월일`() {
        val birth = "931118"
        val result = InputValidUtil.isValidBirthDay(birth)
        assertEquals(true, result)
    }

    @Test
    fun`짧은 생년월일`() {
        val birth = "93111"
        val result = InputValidUtil.isValidBirthDay(birth)
        assertEquals(false, result)
    }

    @Test
    fun`긴 생년월일`() {
        val birth = "9311181"
        val result = InputValidUtil.isValidBirthDay(birth)
        assertEquals(false, result)
    }

    @Test
    fun`13월 생년월일`() {
        val birth = "931318"
        val result = InputValidUtil.isValidBirthDay(birth)
        assertEquals(false, result)
    }

    @Test
    fun `없는 날짜`() {
        val birth = "931131"
        val result = InputValidUtil.isValidBirthDay(birth)
        assertEquals(false, result)
    }

    @Test
    fun`올바른 휴대전화번호`() {
        val phoneNumber = "01044441111"
        val result = InputValidUtil.isValidPhoneNumber(phoneNumber)
        assertEquals(true, result)
    }

    @Test
    fun`짧은 휴대전화번호`() {
        val phoneNumber = "010444111"
        val result = InputValidUtil.isValidPhoneNumber(phoneNumber)
        assertEquals(false, result)
    }

    @Test
    fun `하이픈 휴대전화번호`() {
        val phoneNumber = "010-444-1111"
        val result = InputValidUtil.isValidPhoneNumber(phoneNumber)
        assertEquals(false, result)
    }
}