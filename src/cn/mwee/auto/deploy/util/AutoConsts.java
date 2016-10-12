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

		public static final Byte BuildGroup   = 88;
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

    /** 系统日志 **/
	public static class ChangeLog {
        public static final byte LOG_TYPE_TASK          = 0;
        public static final byte LOG_TYPE_TEMP          = 1;
        public static final byte LOG_TYPE_ZONE          = 2;
        public static final byte LOG_TYPE_TEMPTASK      = 3;
        public static final byte LOG_TYPE_TEMPZONE      = 4;

        public static final byte OPERATE_TYPE_ADD       = 1;
        public static final byte OPERATE_TYPE_DEL       = 2;
        public static final byte OPERATE_TYPE_UPDATE    = 3;


    }

	public static class TemplateType {
        public static final byte BUILD          = 1;    //构建
        public static final byte DEPLOY         = 2;    //部署
        public static final byte RESTART        = 3;    //重启服务
        public static final byte STOP           = 4;    //停止服务
        public static final byte ROLLBACK       = 5;    //回滚
    }

    public static class Env {
        public static final byte DEV            = 0;    //开发
        public static final byte TEST           = 1;    //测试
        public static final byte UAT            = 2;    //uat
        public static final byte PROD           = 3;    //生产
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
