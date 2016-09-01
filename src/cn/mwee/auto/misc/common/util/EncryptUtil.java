package cn.mwee.auto.misc.common.util;


import org.springframework.util.DigestUtils;

public class EncryptUtil {

	
	public static String md5(String input) {
		if(input != null){
			return DigestUtils.md5DigestAsHex(input.getBytes());
		}
		return null;
	}
	
}
