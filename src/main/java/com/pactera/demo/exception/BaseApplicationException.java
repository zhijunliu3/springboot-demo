package com.pactera.demo.exception;


public abstract class BaseApplicationException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码,0为无错误码
     */
    protected int errorCode = 0;
    
    /**
     * 默认错误信息
     */
    protected String defaultMessage = "";
    
    /**
     * 输入的参数
     */
    protected Object inputArgs[];
    
    /**
     * 异常类别
     * @return
     */
    public abstract String getErrorCategory() ;
    
    /**
     *  构造函数
     * @param errorCode 错误码
     * @param args  可变参数 ，格式同 String.format()
     */
    public  BaseApplicationException(ErrorCodeEnum errorCode, Object... args) {
    	 super(errorCode.getMsg());
         initException(errorCode.getErrorCode(),errorCode.getMsg(),args);
    }
    /**
     * 构造函数
     * @param cause 当前异常栈
     * @param errorCode  错误码
     * @param args  可变参数 ，格式同 String.format()
     */
   public  BaseApplicationException(Throwable cause,ErrorCodeEnum errorCode, Object... args) {
	   super(errorCode.getMsg(),cause);
       initException(errorCode.getErrorCode(),errorCode.getMsg(),args);
    }
   
    
    /**
     * 设置异常内部变量
     * @param errorCode
     * @param defalutMsg
     * @param args
     */
    protected void initException(int errorCode, String defalutMsg, Object... args) {
    	 this.errorCode = errorCode;
         this.defaultMessage = defalutMsg;
         inputArgs = new Object[args.length];
         for (int i = 0; i < args.length; i++)
         {
             inputArgs[i] = args[i];
         }
    }
    
    /**
     * 返回錯誤信息
     * 
     * @return
     */
    public int getErrorcode()
    {
        return this.errorCode;
    }
    
    /**
     * 获得错误信息
     */
    @Override
    public String getMessage()
    {
        String message = null;
        String pMsg = null;
        message = formatMsg(defaultMessage);
        return message;
    }
    
    /**
     * 根据错误码与语言获得国际化的错误信息 后续得进行修改，建议国际化信息统一写入Redis，再从redis中获取。
     * 
     * @param lang
     * @return
     */
    protected String getFormatMsgByErrorCode(String lang, int errorCode)
    {
    	//TODO 需要从资源文件获得错误消息
        return defaultMessage;
    }
    
    /**
     * 获得本地语言错误信息
     */
    public String getLocateMessage(String lang)
    {
        String message = null;
        String pMsg = null;
        String locateMsg = getFormatMsgByErrorCode(lang, this.errorCode);
        message = formatMsg(locateMsg);
        return message;
    }
    
    /**
     * 格式化错误信息
     * 
     * @return
     */
    protected String formatMsg(String locateMsg)
    {
        String message;
        try
        {
  
                message = String.format(locateMsg, inputArgs);
        }
        catch (Exception e)
        {
            message = locateMsg;
            String addMsg = "[";
            for (int i = 0; i < inputArgs.length; i++)
            {
                if (i == 0)
                    addMsg = addMsg + inputArgs[i];
                else
                    addMsg = addMsg + " , " + inputArgs[i];
            }
            addMsg = addMsg + "]";
            message = message + " , " + addMsg;
        }
        return message;
    }
    
}