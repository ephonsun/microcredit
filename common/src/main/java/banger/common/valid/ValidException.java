/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :数据服务端校验错误类
 * Author     :zhushengwei
 * Create Date:2013-3-28
 */
package banger.common.valid;

public class ValidException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ValidException(String message)
	{
		super("Data Validation Error"+message);
	}
	
	public ValidException(String message,Throwable cause)
	{
		super("Data Validation Error"+message,cause);
	}
}
