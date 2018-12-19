package banger.common.exception;

/**
 * 自定义异常
 * @author ThinkPad
 *
 */
public class BangerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4027591690327440085L;
	
	public BangerException(){
		super();
	}
	
	public BangerException(String msg){
		super(msg);
	}
}
