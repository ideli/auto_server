package cn.mwee.auto.common.exception;

/**
 *  知道明确的错误原因的异常
 */
public class MwException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public int errorCode = 400;
	
	public static final Integer NOT_EXIST = 404;
	public static final Integer ACCOUNT_NOT_EQUAL = 405;
	public static final Integer SUCCESSFUL = 200;
	public static final Integer UNKNOWN_ERROR = 400;
	
	public static final Integer SEND_MSG_ERROR = 500;
	
	public static final Integer APPKEY_IS_NULL = 600;
	public static final Integer METHOD_IS_NULL = 601;
	public static final Integer SOURCE_TARGET_IS_NULL = 602;
	public static final Integer SOURCE_TARGET_IS_NOT_NULL = 603;
	public static final Integer QUANTITY_IS_ILLEGAL = 604;
	public static final Integer COMPANY_IS_NULL = 606;
	public static final Integer START_IS_ILLEGAL = 607;
	public static final Integer SIZE_IS_ILLEGAL = 609;
	public static final Integer BEGINTIME_IS_ILLEGAL = 610;
	public static final Integer ENDTIME_IS_ILLEGAL = 611;
	public static final Integer SOURCE_IS_NULL = 615;
	public static final Integer TARGET_IS_NULL = 616;
	public static final Integer VALIDATE_ERROR = 617;
	public static final Integer PARAM_IS_ILLEGAL = 700;
	
	public static final Integer REFUND_SUBMIT_ERROR = 800;
	
	/** 微信错误定义  1000-2000 */
	public static final Integer WECHAT_SIGN_VALIDATE_FAIL = 1001;
	
	public static final Integer WECHAT_H5_PAY_WITHOUT_OPENID = 1002;
	
	public static final Integer NOT_SUPPORT_PAY_NOTIFY = 1500;


	public MwException(String message) {
		super(message);
	}

	public MwException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MwException(String message, int errcode) {
		super(message);
		this.errorCode=errcode;
	}

	public MwException(String message, Throwable cause, int errcode) {
		super(message, cause);
	    this.errorCode=errcode;
	}
}
