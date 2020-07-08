package webhgef_8;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import gef_myeditor.MyEditorInput;
import gef_myeditor.MyGraphicalEditor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "WEBHGEF_8.perspective";

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}
	
	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
	 @Override
	   	public void postStartup() {
	   	// TODO 自动生成的方法存根
	   		try {
	   			IWorkbenchPage page = 
	   				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	   				page.openEditor(new MyEditorInput("WBHGEF"), MyGraphicalEditor.ID, false);
	   			} catch (Exception e) {
	   				e.printStackTrace();
	   			}
	   	}
}
