package com.bonc.rcp.common.thread;

public class ThreadFactory {
	  /**
     * 获取调用此函数的代码的位置 和设定输出模式
     * @return 包名.类名.方法名(行数)
     */
    public static String getCodeLocation( int Thread_number){
        try{
            /*** 获取输出信息的代码的位置 ***/
              String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[Thread_number].getClassName() + "." + stacks[Thread_number].getMethodName()
            		  + "(" + stacks[Thread_number].getFileName()+":"+stacks[Thread_number].getLineNumber() + ")";
            return location;
        }catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
            return "";
        }
    }

}
