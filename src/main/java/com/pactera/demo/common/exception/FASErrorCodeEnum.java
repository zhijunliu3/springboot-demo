package com.pactera.demo.common.exception;

import com.pactera.demo.exception.ErrorCodeEnum;

public enum FASErrorCodeEnum implements ErrorCodeEnum {


    /*****************系统错误码四位 按照以下格式1xxx *********************/
    /**
     * 1000 :请求的URL无效
     */
    ERROR_1000(1000, "请求的URL无效"),
    /**
     * 1001 :未知的异常
     */
    ERROR_1001(1001, "未知的异常"),
    /*****************系统异常end*********************/

    /**
     * 业务逻辑错误码五位 第一位是固定的 第二位是具体的业务
     * 如 指标库 11xxx
     */

    /*****************考核基础配置 以1开头 格式1xxxx *********************/


    /*****************考核基础配置end*********************/


    /*****************考核流程管理 以2开头 格式2xxxx *********************/


    /*****************考核流程管理end*********************/

    /*****************用户管理 以9开头 格式9xxxx *********************/
    ERROR_90001(90001, "用户不存在"),
    ERROR_90002(90002, "该用户没有访问权限");


    /*****************用户管理end*********************/



    /**
      * 默认错误信息
     */
    private String msg;
    private int errorCode;

    /**
     * 私有构造,防止被外部调用
     *
     */
    private FASErrorCodeEnum(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * 
     * @return
     */
    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
}
