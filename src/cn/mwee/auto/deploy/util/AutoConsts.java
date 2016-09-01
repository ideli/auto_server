package cn.mwee.auto.deploy.util;

/** 自动化服务相关常量定义 */
public class AutoConsts {

	/** 代码 */
	public final short CODE;
	/** 描述 */
	public final String DESC;
	
	public AutoConsts(Number code)
	{
		this(code, "");
	}

	public AutoConsts(Number code, String desc)
	{
		CODE	= code.shortValue();
		DESC	= desc;
	}

	/** 代码 */
	public final int INTVAL()
	{
		return CODE;
	}

	/** 代码 */
	public final byte BYTEVAL()
	{
		return (byte)CODE;
	}
	
	/* *********************************************************************************************************************************** */
	/* ****************************************************** Begin: Template Task Constant ****************************************************** */
	/* *********************************************************************************************************************************** */

    public static final String DEFAULT_EXEC_ZONE = "target";

	/** 组类型 */
	public static class GroupType
	{
		public static final Byte PrepareGroup   = -1;
		public static final Byte PreGroup		= 0;
		public static final Byte PostGroup 		= Byte.MAX_VALUE;

		public static final Byte RollbackGroup   = -2;

	}

    public static class FlowReviewType {
        public static final Byte Ignore         = 0;    //无需审核
        public static final Byte Unreviewed     = 1;    //未审核
        public static final Byte Approved       = 2;    //审核通过
        public static final Byte Unapproved     = 3;    //审核不通过
    }

	/** 使用中 */
	public static class InUseType
	{
		public static final Byte IN_USE			= 1;
		public static final Byte NOT_USE 		= 0;
	}
    /** 监控类型 **/
	public static class MonitorType {
        public static final byte MONITOR_URL          = 1;
        public static final byte MONITOR_PORT         = 2;
        public static final byte MONITOR_PROCESS      = 3;

    }

	/** 监控类型 **/
	public static class PermConst {
		public static final byte TYPE_MENU              = 1;
		public static final byte TYPE_BTN               = -1;
	}

	public enum TaskState
	{
		INIT,ING,MANUAL,ERROR,TIMER,SUCCESS
	}

	public enum TaskType
	{
		AUTO,MANUAL,TIMER
	}

	public enum ZoneState{
        RUNNING,UNKNOWN,WARNING,ERROR
    }
	/* *********************************************************************************************************************************** */
	/* ******************************************************* End: Template Task Constant ****************************************************** */
	/* *********************************************************************************************************************************** */

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof AutoConsts)
		{
			AutoConsts other = (AutoConsts)obj;
			return CODE == other.CODE;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("{%d, '%s'}", CODE, DESC);
	}
}
