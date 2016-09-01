package cn.mwee.auto.misc.common.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 此类用来做工具类集合
 *
 */
public class Utilities
{
	private static final ThreadPoolExecutor executer =new ThreadPoolExecutor(3, 5, 10
			, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100000));
	
	public static ThreadPoolExecutor getExecutor() {
		return executer;
	}
    
    /**
     * 产生数字和字母混合的指定位数的随机密码
     * @param count 位数
     * @return 随机密码
     */
    public static String getRandomPwd(int count)
    {
       	String randomPwd = RandomStringUtils.random(count, true, false);
       	return randomPwd;
    }
    
    public static String getRandomNumber(int count){
    	return RandomStringUtils.random(count, false, true);
    }
    
    public static Integer parseInt(String num){
    	Integer n = null;
    	try{
    		n = (num == null) ? null : Integer.parseInt(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
    public static Byte parseByte(String num){
    	Byte n = null;
    	try{
    		n = (num == null) ? null : Byte.parseByte(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
    public static Short parseShort(String num){
    	Short n = null;
    	try{
    		n = (num == null) ? null : Short.parseShort(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }     
    
    public static Long parseLong(String num){
    	Long n = null;
    	try{
    		n = (num == null) ? null : Long.parseLong(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }    
    
    public static Boolean parseBoolean(String num){
    	Boolean n = null;
    	try{
    		n = (num == null) ? null : Boolean.parseBoolean(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }  

	public static StringBuilder appendParam(StringBuilder returnStr,String paramId,String paramValue){
		if(returnStr.length() > 0){
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		}else{
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}

	private static Pattern VALID_IPV4_PATTERN = null;
	private static Pattern VALID_IPV6_PATTERN = null;
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

	static {
		try {
			VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
			VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException e) {
			//logger.severe("Unable to compile pattern", e);
		}
	}

	/**
	 * Determine if the given string is a valid IPv4 or IPv6 address.  This method
	 * uses pattern matching to see if the given string could be a valid IP address.
	 *
	 * @param ipAddress A string that is to be examined to verify whether or not
	 *  it could be a valid IP address.
	 * @return <code>true</code> if the string is a value that is a valid IP address,
	 *  <code>false</code> otherwise.
	 */
	public static boolean isIpAddress(String ipAddress) {

		Matcher m1 = Utilities.VALID_IPV4_PATTERN.matcher(ipAddress);
		if (m1.matches()) {
			return true;
		}
		Matcher m2 = Utilities.VALID_IPV6_PATTERN.matcher(ipAddress);
		return m2.matches();
	}

}
