package com.bonc.rcp.common.logback;
import org.slf4j.ILoggerFactory;

public   interface ILogger   {
	public void debug (Object obj);
	public void error (Object obj);
	public void info (Object obj);
	public void trace (Object obj);
	public void warn (Object obj);
}
