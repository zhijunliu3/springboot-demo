package com.pactera.demo.context;

import lombok.Data;

@Data
public class Result<T> {
	/**
	 * 状态码
	 */
	private String code = "0";
	/**
	 * 状态消息
	 */
	private String message;
	/**
	 * 堆栈信息
	 */
	private String stack;
	
	
	/**
	 * 错误原因
	 * 展示更详细的错误信息
	 */
	private String cause;
	
	/**
	 * 返回数据
	 */
	private T data;
}
