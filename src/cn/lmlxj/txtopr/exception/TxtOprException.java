package cn.lmlxj.txtopr.exception;

/**
 * AppException.    
 * @author maben586@163.com
 * @version AppException V1.0 2016-6-4 下午1:27:00
 */
public class TxtOprException extends RuntimeException {
	private static final long serialVersionUID = 1061086429699071818L;

	public TxtOprException(String errMsg) {
		super(errMsg);
	}
	
	public TxtOprException(Throwable ex) {
		super(ex);
	}
	
	public TxtOprException(String errMsg, Throwable ex) {
		super(errMsg, ex);
	}
}