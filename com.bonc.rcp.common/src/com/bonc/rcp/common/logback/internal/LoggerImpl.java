package com.bonc.rcp.common.logback.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bonc.rcp.common.logback.ILogger;
import com.bonc.rcp.common.thread.ThreadFactory;
/**
 * @Description 日志记录类
 * @author panteng
 * @version V0.0.1
 * @date 2016-09-08
 */
public class LoggerImpl  implements ILogger {
    public static Logger log = LoggerFactory.getLogger(LogType.fileName);
    
    /**
     * 打印警告
     * 
     * @param obj
     */
    public  void warn(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
              String location = getCodeLocation();
          
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                log.warn(location + str);
            } else {
                log.warn(location + obj.toString());
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    /**
     * 打印信息
     * 
     * @param obj
     */
    public  void info(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
              String location = getCodeLocation();
         
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                log.info(location + str);
            } else {
                log.info(location + obj.toString());
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    /**
     * 打印错误
     * 
     * @param obj
     */
    public  void error(Object obj) {
        try{
              String location = getCodeLocation();
      
              
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                log.error(location + str);
            } else {
                log.error(location + obj.toString());
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    
    /**
     * 获取调用此函数的代码的位置 和设定输出模式
     * @return 包名.类名.方法名(行数)
     */
    public  String getCodeLocation(){
        try{
        	return ThreadFactory.getCodeLocation(4);
        }catch (Exception e) {
            // TODO: handle exception
         error(e); 
            return "";
        }
    }

    
	@Override
	public void debug(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
            String location = getCodeLocation();
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                log.debug(location + str);
            } else {
            	 log.debug(location + obj.toString());
   
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
	@Override
	public void trace(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
        	   String location = getCodeLocation();
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                log.trace(location + str);
            } else {
                log.trace(location + obj.toString());
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}