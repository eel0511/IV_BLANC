package com.ivblanc.core.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 비밀번호 형식 체크하는건 프론트에서 해줄거같긴 한데 혹시 몰라서 만듦..
public class PasswordValidate {

	public boolean checkPwForm(String pw) {
		// password는 영문,숫자,특수문자 2가지이상 포함 8자리이상14자리이하
		Pattern check1 = Pattern.compile("/^(?=.*[a-zA-Z])(?=.*[0-9]).{10,12}$/");   //영문,숫자
		Pattern check2 = Pattern.compile("/^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{10,12}$");  //영문,특수문자
		Pattern check3 = Pattern.compile("/^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{10,12}$");  //특수문자, 숫자

		Matcher passMatcher1 = check1.matcher(pw);
		Matcher passMatcher2 = check2.matcher(pw);
		Matcher passMatcher3 = check3.matcher(pw);

		if(!(passMatcher1.find() || passMatcher2.find() || passMatcher3.find())){
			return false;
		}

		return true;
	}

	public boolean checkPwMatch(String pw1, String pw2){
		if(pw1.equals(pw2)) return true;
		return false;
	}
}
