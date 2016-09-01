package cn.mwee.auto.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import cn.mwee.auto.misc.common.util.BeanHelper;
import cn.mwee.auto.misc.common.util.GeneralHelper;

public class SoaHelper
{
	public static final String DELIMITERR_CHAR	= " ,\t\r\n\f";
	
	public static final <S, T> T convertToObject(S src, Class<T> destClass)
	{
		Map<String, Object> properties = BeanHelper.getProperties(src);
		return BeanHelper.createBean(destClass, properties);
	}

	public static final String longList2Str(List<Long> list)
	{
		return list2Str(list);
	}
	
	public static final <T> String list2Str(List<T> list)
	{
		String str = null;
		
		if(list != null)
		{
			int size = list.size();
			
			if(size > 0)
			{
				StringBuilder sb = new StringBuilder();
				
				for(int i = 0; i < size; i++)
				{
					sb.append(list.get(i));
					if(i < size - 1)
						sb.append(',');
				}
				
				str = sb.toString();
			}
		}
		
		return str;
	}
	
	public static final List<Long> str2LongList(String str)
	{
		List<Long> list = null;
		
		if(str != null)
		{
			StringTokenizer st = new StringTokenizer(str, DELIMITERR_CHAR);
			list = new ArrayList<Long>(st.countTokens());
			
			while(st.hasMoreTokens())
				list.add(Long.parseLong(st.nextToken()));
		}

		return list;
	}
	
	public static final boolean isNullOrEmpty(Collection<?> c)
	{
		return c == null || c.isEmpty();
	}
	
	public static final boolean isNotNullOrEmpty(Collection<?> c)
	{
		return !isNullOrEmpty(c);
	}
	
	public static final boolean isNullOrEmpty(Map<?, ?> m)
	{
		return m == null || m.isEmpty();
	}
	
	public static final boolean isNotNullOrEmpty(Map<?, ?> m)
	{
		return !isNullOrEmpty(m);
	}
	
	public static final int compare(Number n, byte v)
	{
		if(n == null)
			return -1;
		
		return n.byteValue() - v;
	}
	
	public static final boolean equals(Number n, byte v)
	{
		return compare(n, v) == 0;
	}
	
	public static final int compare(Number n, short v)
	{
		if(n == null)
			return -1;
		
		return n.shortValue() - v;
	}
	
	public static final boolean equals(Number n, short v)
	{
		return compare(n, v) == 0;
	}
	
	public static final int compare(Number n, int v)
	{
		if(n == null)
			return -1;
		
		return n.intValue() - v;
	}
	
	public static final boolean equals(Number n, int v)
	{
		return compare(n, v) == 0;
	}
	
	public static final long compare(Number n, long v)
	{
		if(n == null)
			return -1;
		
		return n.longValue() - v;
	}
	
	public static final boolean equals(Number n, long v)
	{
		return compare(n, v) == 0;
	}
	
	public static final String mask(String str, int size, int offset)
	{
		if(GeneralHelper.isStrEmpty(str))
			return "";
		
		int len		= str.length();
		int end		= len - offset;
		int begin	= len - size - offset;
		
		if(end <= 0)
			return str;
		if(begin < 0)
			begin = 0;
		
		StringBuilder sb = new StringBuilder(len);
		
		for(int i = 0; i < len; i++)
		{
			if(i >= begin && i < end)
				sb.append('*');
			else
				sb.append(str.charAt(i));
		}
		
		return sb.toString();
	}

}
