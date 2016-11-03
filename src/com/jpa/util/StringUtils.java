package com.jpa.util;

public class StringUtils {

	public static boolean isNotEmpty(String str){
		if(null != str && str.trim().length() !=0){
			return true;
		}
		return false;
	}
}
