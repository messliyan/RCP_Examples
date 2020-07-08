package com.bonc.rcp.dbtool.handlers;

import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class showViewHandler {
	@Execute
	public void execute(
			MWindow window, 
			EPartService partService,
			EModelService modelService,
			@Named("com.bonc.rcp.dbtool.core.commandparameter.showview") String showviewName) {
				
		List<MPart> parts = (List<MPart>) partService.getParts();

		
//		 判断part是否已经打开
		for (MPart mPart : parts) {
			
			String currentId = mPart.getElementId();
			
			if (currentId!= null) {
				if (currentId.contains(showviewName)) {
					
					mPart.setVisible(true);
					partService.showPart(mPart, EPartService.PartState.VISIBLE);
					partService.showPart(mPart, EPartService.PartState.ACTIVATE);
				}
			}
			
			
		
//			if (currentId != null && currentId.equals(id)) {
//				ILoggerFactory.getDebugLogger().debug("集群编辑器已经打开 FIELD_ID为"+cluster.getClusterId());
//				partService.showPart(mPart, EPartService.PartState.ACTIVATE);
//				return;
//			}
		}
		
	}
		
}