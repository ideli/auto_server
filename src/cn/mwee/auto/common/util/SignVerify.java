
package cn.mwee.auto.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.mwee.auto.misc.common.util.GeneralHelper;

public class SignVerify
{

	public static boolean verify(Map<String, Object> map, String secret)
	{
		boolean ret = false;
		String sign = "";
		if(map != null)
		{
			List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(map.entrySet());
			Collections.sort(list, new Comparator<Entry<String, Object>>() {
				@Override
				public int compare(Entry<String, Object> o1, Entry<String, Object> o2)
				{
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			StringBuilder sb = new StringBuilder(1024);
			for(Entry<String, Object> entry : list)
			{
				if(entry.getKey().equals("sign"))
				{
					sign = GeneralHelper.safeString(entry.getValue());
					continue;
				}
				if(entry.getValue() != null)
				{
					sb = appendParam(sb, entry.getKey(), GeneralHelper.safeString(entry.getValue()));
				}
			}

			sb = appendParam(sb, "secret", secret);
			String signMsg = encodePassword(sb.toString(), "md5").toUpperCase();
			ret = signMsg.equals(sign);
		}
		return ret;
	}

	public static StringBuilder appendParam(StringBuilder returnStr, String paramId, String paramValue)
	{
		if(returnStr.length() > 0)
		{
			if(paramValue != null && !paramValue.equals(""))
			{
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		}
		else
		{
			if(paramValue != null && !paramValue.equals(""))
			{
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}

	public static String encodePassword(String password, String algorithm)
	{
		byte[] unencodedPassword;
		try
		{
			unencodedPassword = password.getBytes("UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			return password;
		}

		MessageDigest md = null;

		try
		{
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		}
		catch(Exception e)
		{
			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for(int i = 0; i < encodedPassword.length; i++)
		{
			if((encodedPassword[i] & 0xff) < 0x10)
			{
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

}
