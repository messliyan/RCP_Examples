package com.bonc.rcp.dbtool.core;

import org.eclipse.swt.graphics.Point;

import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
//		configurer.setInitialSize(new Point(800, 600));	
		configurer.setShowCoolBar(false);
//		configurer.setShowPerspectiveBar(true);
		configurer.setShowProgressIndicator(true);
		configurer.getWindow().getShell().setFullScreen(true);
		configurer.setShowStatusLine(true); 
	
		configurer.setTitle("行云DBTool");
	}
}
