 
package com.example.headler;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.example.ui.MyEditorInput;


public class EditorsHeadler {
	

	@Execute
	public void execute() {
		try {
   			IWorkbenchPage page = 
   				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
   				page.openEditor(new MyEditorInput("WBHGEF"), "com.example.ui.PracticeEditor", false);
   			} catch (Exception e) {
   				e.printStackTrace();
   			}
	}
		
	@CanExecute
	public boolean canexecute() {
		return true;
	}
}