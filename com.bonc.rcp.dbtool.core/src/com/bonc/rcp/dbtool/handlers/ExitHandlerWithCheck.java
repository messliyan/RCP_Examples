package com.bonc.rcp.dbtool.handlers;



import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.widgets.Shell;

import com.bonc.rcp.dbtool.addons.WindewCloseBehaviour;


public class ExitHandlerWithCheck {


	
	@Execute
	public void execute(IEclipseContext context,EPartService partService, IWorkbench workbench,Shell shell,MApplication application) {
		
		String canclose=application.getPersistedState().get("CanClose");
		if (!canclose.equals("true")) {
			MessageDialog.openConfirm(shell, "关闭错误", canclose);
			return ;
		}
			
	
		
		boolean result = MessageDialog.openConfirm(shell, "关闭", "可能会丢失数据"+",  "+ "确定关闭？");  //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$
		if (result) { 
			workbench.close(); 
					}
		}
	
	
	
}
