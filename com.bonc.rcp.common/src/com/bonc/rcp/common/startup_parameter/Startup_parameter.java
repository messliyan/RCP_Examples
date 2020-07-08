package com.bonc.rcp.common.startup_parameter;

import org.eclipse.equinox.app.IApplicationContext;

/**
 * @author PCPC
 *
 */
public class Startup_parameter {
	
	
	/**
	 * @param argName
	 * @param applicationContext
	 * @param singledCmdArgValue
	 * @return
	 */
	public  static String  getArgValue(String argName, 
			IApplicationContext applicationContext, boolean singledCmdArgValue) {
				
		if (argName == null || argName.length() == 0) 
			return null;
		if (singledCmdArgValue) {
			
			String[] args = (String[]) applicationContext.getArguments(). get(IApplicationContext.APPLICATION_ARGS);
			
			for (String arg : args) { 
			if (
					("-" + argName).equals(arg))
			return "true"; 
			}
		}// not a singleCmdArgValue for (int i = 0; i < args.length; i++) { if (("-" + argName).equals(args[i]) && i + 1 < args.length) return args[i + 1]; }
		return "false"; 
	}
}
