package com.pactera.demo.config;

import com.pactera.demo.common.exception.FASErrorCodeEnum;
import com.pactera.demo.common.exception.FASException;
import com.pactera.demo.context.Result;
import com.pactera.demo.exception.BaseApplicationException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.annotations.ApiIgnore;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class ControllerAdviceConfig  implements ResponseBodyAdvice<Object>{

	@Override
	public boolean supports(MethodParameter returnType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return AnnotationUtils.findAnnotation(returnType.getContainingClass(), ApiIgnore.class) == null;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		if(null == body) {
			Result<Object> rs = new Result<>();
			return rs;
		}
		if(!Result.class.isAssignableFrom(body.getClass())) {
			Result<Object> rs = new Result<>();
			rs.setData(body);
			return rs;
		}
		return body;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception{
	    CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    CustomDateEditor timeStampEditor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true);
	    //表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换  
	    binder.registerCustomEditor(Date.class, dateEditor);
	    binder.registerCustomEditor(Timestamp.class, timeStampEditor);
	}

	/**
	 * 捕获到BaseApplicationException的处理
	 * @param handlerMethod
	 * @param e
	 * @return
	 * @return：Object
	 */
	@ExceptionHandler(BaseApplicationException.class)
	@ResponseBody
	public Object applicationExceptionProcess(HandlerMethod handlerMethod, BaseApplicationException e){
		Result<Object> rs = new Result<>();
		rs.setCode(getErrorCode(e));
		rs.setMessage(e.getMessage());
		rs.setStack(getStack(e));
		rs.setData(null);
		rs.setCause(getExceptionCause(e));
		return rs;
	}

	/**
	 * 捕获到NoHandlerFoundException异常的处理
	 * @param e
	 * @return
	 * @return：Object
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public Object noHandlerFoundExceptionProcess(NoHandlerFoundException e) {
		FASException warpException=new FASException(e,FASErrorCodeEnum.ERROR_1000);
		Result<Object> rs = new Result<>();
		rs.setCode(getErrorCode(warpException));
		rs.setMessage(warpException.getMessage());
		rs.setStack(getStack(e));
		rs.setData(null);
		rs.setCause(getExceptionCause(warpException));
		return rs;
	}

	/**
	 * 
	 * 将错误栈打印出
	 * @param throwable
	 * @return
	 * @return：String
	 */
	protected String getStack(Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            return sw.toString();
        }catch(Exception e) {
	        return "";
	    }
	}

	protected String getErrorCode(BaseApplicationException e) {
		return String.format("%s_%d", e.getErrorCategory(),e.getErrorcode());
	}

	/**
        *  捕获到Exception的处理
     * @param handlerMethod
     * @param e
     * @return
     * @return：Object
     */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exceptionProcess(HandlerMethod handlerMethod, Exception e){
		FASException warpException=new FASException(e,FASErrorCodeEnum.ERROR_1001);
		Result<Object> rs = new Result<>();
		rs.setCode(getErrorCode(warpException));
		rs.setMessage(warpException.getMessage());
		rs.setStack(getStack(e));
		rs.setData(null);
		rs.setCause(getExceptionCause(warpException));
		return rs;
	}
	
	/**
	 * 获得异常Cause信息
	 * @param e
	 * @return
	 */
	protected String getExceptionCause(Exception e) {
		Throwable myException = e;
		StringBuffer causeMsg = new StringBuffer();
		boolean first=true;
		if (e == null) {
			return null;
		}
		try {
			while (myException.getCause() != null) {
				myException = myException.getCause();
				String currMsg = null;
				if (myException instanceof BaseApplicationException) {
					BaseApplicationException ae = (BaseApplicationException) myException;
					currMsg = String.format("%s_%d : %s",ae.getErrorCategory(), ae.getErrorcode(), ae.getMessage());
				} else {
					currMsg = myException.getMessage();
				}
				if (first) {
				    causeMsg.append(currMsg);
				    first=false;
				}else {
				    causeMsg.append("\\r\n"); 
				    causeMsg.append(currMsg);
				}
			}
		} catch (Exception ex) {
			;
		}
		return causeMsg.toString();
	}
}
