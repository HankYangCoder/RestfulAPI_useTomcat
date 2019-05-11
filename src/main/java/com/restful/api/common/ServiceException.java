package com.restful.api.common;

/**
 * 自訂的 exception 目前還沒完善
 * @author hankyang
 *
 */
public class ServiceException extends Exception {
	
	public ServiceException()
	{
		super();
	}
	
	public ServiceException(String message)
	{
		super(message);
	}
	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}    
	public ServiceException(Throwable cause)
	{
		super(cause);
	}  
}
