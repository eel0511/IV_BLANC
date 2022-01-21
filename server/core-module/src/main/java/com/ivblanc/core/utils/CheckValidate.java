package com.ivblanc.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckValidate {


	public static boolean isOnlyNum(String phone){
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(phone);

		if(matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean checkPhoneForm(String phone){
		Matcher matcher = Pattern.compile("^01{1}[016789]{1}[0-9]{7,8}$").matcher(phone);

		if(matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean checkEmailForm(String email) {
		Matcher matcher = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$").matcher(email);

		if(matcher.find()) {
			return true;
		}
		return false;
	}

}
