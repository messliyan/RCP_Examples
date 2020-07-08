package com.bonc.rcp.dbtool.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.bonc.rcp.dbtool.Constants.eventConstants.MyEventConstants;

import org.eclipse.e4.core.di.annotations.CanExecute;

public class SaveAllHandler {
	@Execute
	public void execute(EPartService service, IEventBroker broker) {  
		
		service.saveAll(false);
		broker.post(MyEventConstants.TOPIC_MIGRATION_SAVE, "saved");
	}
	
	
	@CanExecute
	public boolean canExecute(@Optional EPartService partService) {
		//组件脏状态改变会激发此方法  

		if (partService != null) {
			return !partService.getDirtyParts().isEmpty();
		}
		return false;
	}
		
}