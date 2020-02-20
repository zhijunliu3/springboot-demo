package com.pactera.demo.common.exception;

import com.pactera.demo.exception.BaseApplicationException;

public class FASException extends BaseApplicationException {
	
	public static final String ERROR_CATEGORY="COMMON";
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2452561795917364890L;

	public FASException(FASErrorCodeEnum error, Object...  args) {
		super(error, args);
	}
	
    public FASException(Throwable cause, FASErrorCodeEnum error, Object... args) {
    	super(cause,error, args);
    }

	@Override
	public String getErrorCategory() {
		
		return ERROR_CATEGORY;
	}

}
