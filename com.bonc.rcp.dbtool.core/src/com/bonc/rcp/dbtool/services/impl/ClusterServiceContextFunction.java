package com.bonc.rcp.dbtool.services.impl;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

import com.bonc.rcp.dbtool.services.IClusterService;

public class ClusterServiceContextFunction extends ContextFunction {
	//注入工厂注入ClusterService
	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		IClusterService clusterService = 
				ContextInjectionFactory.make(MyClusterServiceImpl.class, context);
		

//		MApplication app = context.get(MApplication.class);
//		IEclipseContext appCtx = app.getContext();
		context.set(IClusterService.class, clusterService);

		return clusterService;
	}
}
