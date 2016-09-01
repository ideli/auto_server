
package cn.mwee.auto.common.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class AbstractExample
{
	protected String orderByClause;
	protected boolean distinct;
	protected List<AbstractCriteria> oredCriteria;
	protected int limitStart = -1;
	protected int limitEnd = -1;

	protected abstract <T extends AbstractCriteria> T createCriteriaInternal();	
	protected abstract Map<String, String> getOrderByOrderByClauseMap();

	public AbstractExample()
	{
		oredCriteria = new ArrayList<AbstractCriteria>();
	}

	public void setOrderByClause(String orderByClause)
	{
		Map<String, String> propMap = getOrderByOrderByClauseMap();
		
		if(propMap == null || propMap.isEmpty())
		{
			this.orderByClause = orderByClause;
			return;
		}
		
		StringBuilder sb	= new StringBuilder();
		StringTokenizer st	= new StringTokenizer(orderByClause, " \t\r\n,", true);
		
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			if(propMap.containsKey(token))
				sb.append(propMap.get(token));
			else
				sb.append(token);
		}
		
		this.orderByClause = sb.toString();
	}

	public String getOrderByClause()
	{
		return orderByClause;
	}

	public void setDistinct(boolean distinct)
	{
		this.distinct = distinct;
	}

	public boolean isDistinct()
	{
		return distinct;
	}

	public List<AbstractCriteria> getOredCriteria()
	{
		return oredCriteria;
	}

	public void or(AbstractCriteria criteria)
	{
		oredCriteria.add(criteria);
	}

	public AbstractCriteria or()
	{
		AbstractCriteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public <T extends AbstractCriteria> T createCriteria()
	{
		T criteria = createCriteriaInternal();
		
		if(oredCriteria.size() == 0)
		{
			oredCriteria.add(criteria);
		}
		
		return criteria;
	}

	public void clear()
	{
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	public void setLimitStart(int limitStart)
	{
		this.limitStart = limitStart;
	}

	public int getLimitStart()
	{
		return limitStart;
	}

	public void setLimitEnd(int limitEnd)
	{
		this.limitEnd = limitEnd;
	}

	public int getLimitEnd()
	{
		return limitEnd;
	}

	protected abstract static class AbstractCriteria
	{
		protected List<Criterion> criteria;

		protected AbstractCriteria()
		{
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid()
		{
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria()
		{
			return criteria;
		}

		public List<Criterion> getCriteria()
		{
			return criteria;
		}

		protected void addCriterion(String condition)
		{
			if(condition == null)
			{
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property)
		{
			if(value == null)
			{
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property)
		{
			if(value1 == null || value2 == null)
			{
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

	}

	public static class Criterion
	{
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition()
		{
			return condition;
		}

		public Object getValue()
		{
			return value;
		}

		public Object getSecondValue()
		{
			return secondValue;
		}

		public boolean isNoValue()
		{
			return noValue;
		}

		public boolean isSingleValue()
		{
			return singleValue;
		}

		public boolean isBetweenValue()
		{
			return betweenValue;
		}

		public boolean isListValue()
		{
			return listValue;
		}

		public String getTypeHandler()
		{
			return typeHandler;
		}

		protected Criterion(String condition)
		{
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler)
		{
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if(value instanceof List<?>)
			{
				this.listValue = true;
			}
			else
			{
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value)
		{
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
		{
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue)
		{
			this(condition, value, secondValue, null);
		}
	}
}
