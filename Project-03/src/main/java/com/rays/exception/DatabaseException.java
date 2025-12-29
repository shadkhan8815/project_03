package com.rays.exception;

/**
 * @author Shad Khan
 */
public class DatabaseException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg){
		super(msg);
	}
}
