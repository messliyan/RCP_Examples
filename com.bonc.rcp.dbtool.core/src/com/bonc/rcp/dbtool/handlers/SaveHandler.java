package com.bonc.rcp.dbtool.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
public class SaveHandler {
	
	@CanExecute
	public boolean canExecute( EPartService partService) {
		MPart part=partService.getActivePart();
		if (part != null) {
			return part.isDirty();
		} 
		return false;
	}

	@Execute
	public void execute( EPartService partService) {
		MPart part=partService.getActivePart();
		if (part != null) {
			partService.savePart(part, false);
		
		}
		
	}
}