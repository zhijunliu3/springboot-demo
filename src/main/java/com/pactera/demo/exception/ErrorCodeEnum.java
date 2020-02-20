package com.pactera.demo.exception;

public interface ErrorCodeEnum {
	/**
	 * 获得默认错误信息
	 * @return
	 */
	String getMsg();
    /**
     * 获得错误码
     * @return
     */
    int getErrorCode() ;
}
