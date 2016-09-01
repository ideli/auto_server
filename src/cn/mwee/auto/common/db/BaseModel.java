package cn.mwee.auto.common.db;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import cn.mwee.auto.misc.common.exception.ServiceException;
import cn.mwee.auto.misc.common.util.GeneralHelper;

public class BaseModel
{
	public static final String DELIMITERR_CHAR = " ,\t\r\n\f";
	private static final Set<String> ORDER_BY_DELIMITER_SET;

	static
	{
		ORDER_BY_DELIMITER_SET = new HashSet<String>();
		ORDER_BY_DELIMITER_SET.add("ASC");
		ORDER_BY_DELIMITER_SET.add("DESC");

		for(int i = 0; i < DELIMITERR_CHAR.length(); i++)
			ORDER_BY_DELIMITER_SET.add(Character.toString(DELIMITERR_CHAR.charAt(i)));
	}

	public static <T> BaseQueryResult<T> selectByPage(Object mapper, Object example, String method, PageInfo page)
	{
		return selectByPage(mapper, example, page, method, false);
	}

	public static <T> BaseQueryResult<T> selectByPage(Object mapper, Object example
			, PageInfo page)
	{
		return selectByPage(mapper, example, page, false);
	}

	public static <T> BaseQueryResult<T> selectByPage(Object mapper, Object example
			, PageInfo page, boolean defaultFirstPage)
	{
		return selectByPage(mapper, example, page, "selectByExample", defaultFirstPage);
	}

	@SuppressWarnings("unchecked")
	public static <T> BaseQueryResult<T> selectByPage(Object mapper, Object example
			, PageInfo page, String queryMethod, boolean defaultFirstPage)
	{
		BaseQueryResult<T> queryRs = new BaseQueryResult<T>();

		if(defaultFirstPage && page == null)
			page = new PageInfo();

		try
		{
			Method selectMethod	= mapper.getClass().getMethod(queryMethod, example.getClass());

			if(page != null)
			{
				Method method = mapper.getClass().getMethod("countByExample", example.getClass());
				PageInfo resultPage = new PageInfo();
				queryRs.setPage(resultPage);

				if(page.getPageSize() != 0)
					resultPage.setPageSize(page.getPageSize());
				if(page.getCurrentPage() != 0)
					resultPage.setCurrentPage(page.getCurrentPage());
				if(page.getOrderBy() != null)
					resultPage.setOrderBy(page.getOrderBy());

				int count = (Integer)method.invoke(mapper, example);

				resultPage.setTotalCount(count);
				resultPage.calculatePageCount();

				if(resultPage.isOutofBounds())
				{
					queryRs.setList(new ArrayList<T>());
					return queryRs;
				}

				if(resultPage.getOrderBy() != null)
				{
					Method orderMethod = example.getClass().getMethod("setOrderByClause", String.class);
					orderMethod.invoke(example, parseOrderByClause(resultPage.getOrderBy()));
				}

				Method startMethod = example.getClass().getMethod("setLimitStart", int.class);
				Method endMethod = example.getClass().getMethod("setLimitEnd", int.class);

				startMethod.invoke(example, resultPage.getLimitStart());
				endMethod.invoke(example, resultPage.getPageSize());
			}

			List<T> lstResult = (List<T>)selectMethod.invoke(mapper, example);
			queryRs.setList(lstResult);
		}
		catch(Exception e)
		{
			throw new ServiceException("分页查询失败", e);
		}

		return queryRs;
	}

	public static final String parseOrderByClause(String clause)
	{
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(clause, DELIMITERR_CHAR, true);

		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			if(ORDER_BY_DELIMITER_SET.contains(token.toUpperCase()))
				sb.append(token);
			else
				sb.append(propertyToColumn(token));
		}

		return sb.toString();
	}

	public static final String propertyToColumn(String prop)
	{
		if(GeneralHelper.isStrEmpty(prop))
			return prop;

		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < prop.length(); i++)
		{
			char c = prop.charAt(i);

			if(c >= 'A' && c <= 'Z')
				sb.append('_').append((char)(c + 32));
			else
				sb.append(c);
		}

		return sb.toString();
	}
}
