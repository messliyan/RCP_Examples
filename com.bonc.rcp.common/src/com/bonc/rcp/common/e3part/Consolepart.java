package com.bonc.rcp.common.e3part;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.bonc.rcp.common.swt.SWTResourceManager;

public class Consolepart {
	//向控制台输出一些奇怪的东西 标题？颜色？内容
	public static void syso(String console_tile,String  message,int systemColorID) {
		 IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() ;
		 IConsoleView consoleView = null;
		try {
			consoleView = (IConsoleView)page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 MessageConsole myConsole = new MessageConsole(console_tile, null);
		 ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { myConsole });
		 consoleView.display(myConsole);
		 MessageConsoleStream stream = myConsole.newMessageStream();
		 stream.setColor(SWTResourceManager.getColor(systemColorID));
		 stream.println(message);
		 
		 
	}
	public static void showConsolepart() {
		 IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() ;
		 IConsoleView consoleView = null;
		try {
			consoleView = (IConsoleView)page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
