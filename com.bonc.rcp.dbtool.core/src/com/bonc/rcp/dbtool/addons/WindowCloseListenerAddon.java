package com.bonc.rcp.dbtool.addons;


import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ElementMatcher;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.bonc.rcp.common.bundleresourceloader.IBundleResourceLoader;
import com.bonc.rcp.common.e3part.Consolepart;
import com.bonc.rcp.common.logback.ILogger;
import com.bonc.rcp.dbtool.Constants.preferenceConstants.CloseWindowPreferenceConstants;
import com.bonc.rcp.dbtool.comp.Tray.TrayUtil;
import com.bonc.rcp.dbtool.comp.dialogs.CloseDialog;
// @PostConstruct does not work as the workbench gets 
// instantiated after the processing of the add-ons
// hence this approach uses method injection
//窗口关闭事件
@SuppressWarnings("restriction")
public class WindowCloseListenerAddon {
	
	@Inject
	@Preference(nodePath = CloseWindowPreferenceConstants.NODEPATH, 
				value = CloseWindowPreferenceConstants.CLOSETYPE_PREF_KEY)
	private String close;
	
	@Inject 
	private ILogger logger;

	private WindewCloseBehaviour closebehaviour;
	
	private CloseDialog dialog ;
	
	@Inject
	UISynchronize sync;
	
	private IWorkbench workbench;

	private TrayUtil trayUtil ;
	private EPartService partService;
	private EModelService modelService;
	@Inject
	private  MApplication app;

	@Inject
	@Optional
	@Named(IServiceConstants.ACTIVE_SHELL)Shell shell;
	@Inject
	@Optional
	private void subscribeApplicationCompleted(MApplication app, EPartService partService, EModelService modelService,
			IBundleResourceLoader loader,
			final IWorkbench workbench,
			//主窗口加载完成之后的事件
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application,
			@Preference (nodePath = CloseWindowPreferenceConstants.NODEPATH)
			IEclipsePreferences prefs) {
		// only if a close of the main window is requested
		MWindow mainWindow = findMainWindow(application);
		app.getPersistedState().put("CanClose", "true");
		this.workbench=workbench;
		ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
		 Image image = resourceManager. createImage(loader.getImageDescriptor(this.getClass(), "icons/tray.png"));  //$NON-NLS-1$
		 	this.app=app;
			this.partService=partService;
			this.modelService=modelService;
//		TrayUtil taTrayUtil =new TrayUtil(shell,workbench);
		 
////		 //去掉控制台的action
		 IWorkbenchPage iWorkbenchPage=PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();


				final IViewPart findView = iWorkbenchPage
						.findView(IConsoleConstants.ID_CONSOLE_VIEW);
				if (findView != null) {
					final IActionBars actionBars = findView.getViewSite().getActionBars();
					final IToolBarManager toolBarManager = actionBars.getToolBarManager();
					final IContributionItem[] items = toolBarManager.getItems();
					for (final IContributionItem iContributionItem : items) {
						if (iContributionItem.getId()==null) {					
								toolBarManager.remove(iContributionItem);
		
//							System.out.println(iContributionItem.getId());
						}
						
						
						
					}
					actionBars.updateActionBars();
				}
	
				
	
			
		
		 
//		Consolepart.syso("wenti", "123123",SWT.COLOR_RED );
		
		 trayUtil = ContextInjectionFactory.
				make(TrayUtil.class, mainWindow.getContext());
		
		trayUtil.createSystemTray(image);
		 dialog = ContextInjectionFactory.
				make(CloseDialog.class, mainWindow.getContext());
		 
		 
		 setCloseBehaviour(new PreferenceClose());
		 
		mainWindow.getContext().set(IWindowCloseHandler.class, new IWindowCloseHandler() {
			@Override
			public boolean close(MWindow window) {
				
				String canclose=app.getPersistedState().get("CanClose");
			if (!canclose.equals("true")) {
				MessageDialog.openConfirm(shell, "关闭错误", canclose+"  请不要关闭");
				return false;
			}
				
				
				//测试所用 -data @user.home 放在了 用户 下的 、mete。。/插件/runtime 下面
				
//				prefs.put(CloseWindowPreferenceConstants.CLOSEINFO_PREF_KEY, "Null");
				// CloseDialog buttonPressed 没有 走这里
				if (closebehaviour.Canclose()) {
					 workbench.close();
				}
				
				
											
//				Collection<EPartService> allPartServices = getAllPartServices(application);
//
//				if (containsDirtyParts(allPartServices)) {
//					Shell shell = (Shell) window.getWidget();
//					MessageDialog msgDialog = new MessageDialog(shell, "gun", null,
//							"Do you want to save the changes before you close the entire application?",
//							MessageDialog.QUESTION_WITH_CANCEL, new String[] { IDialogConstants.YES_LABEL,
//									IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL },
//							0);
//					switch (msgDialog.open()) {
//					case 0: // YES: save all and close
//						saveAll(allPartServices);
//						return workbench.close();
//
//					case 1: // NO: save nothing and close
//						return workbench.close();
//
//					case 2: // CANCEL: prevent close
//					default:
//						return false;
//					}
//				} else {
//					Shell shell = (Shell) window.getWidget();
//					if (MessageDialog.openConfirm(shell, "Close Application",
//							"Do you really want to close the entire application?")) {
//						return workbench.close();
//					}
//				}

				return false;
			}
		});
	}

	private static MWindow findMainWindow(MApplication application) {
		// instead of using the index you could also use a tag on the MWindow to
		// mark the main window
		return application.getChildren().get(0);
	}
	public void setCloseBehaviour(WindewCloseBehaviour closeBehaviour) {
		this.closebehaviour=closeBehaviour;
	}

//	@Inject
//	@Optional
//	private void subscribeApplicationCompleted(
//		
//			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application
//			
//			) {
//		
//		System.out.println(2);
//	}
//	private static Collection<EPartService> getAllPartServices(MApplication application) {
//		List<EPartService> partServices = new ArrayList<EPartService>();
//
//		EModelService modelService = application.getContext().get(EModelService.class);
//		List<MWindow> elements = modelService.findElements(application, MWindow.class, EModelService.IN_ACTIVE_PERSPECTIVE,
//				new ElementMatcher(null, MWindow.class, (List<String>) null));
//		for (MWindow w : elements) {
//			if (w.isVisible() && w.isToBeRendered()) {
//				EPartService partService = w.getContext().get(EPartService.class);
//				if (partService != null) {
//					partServices.add(partService);
//						}
//		}
//		}
//		return partServices;
//	}
//
//	private static boolean containsDirtyParts(Collection<EPartService> partServices) {
//		for (EPartService partService : partServices) {
//			if (!partService.getDirtyParts().isEmpty())
//				return true;
//		}
//
//		return false;
//	}
//
//	private static void saveAll(Collection<EPartService> partServices) {
//		for (EPartService partService : partServices) {
//			partService.saveAll(false); // false: save without prompt
//		}
//	}
	private class PreferenceClose implements WindewCloseBehaviour{

	@Override
	public boolean Canclose() {
		// TODO 自动生成的方法存根
		if (close==null||close.equals("Null")) { //$NON-NLS-1$
			
			
			if (dialog.open() == Window.OK) {
				 if (dialog.isClose()) {
					return true;
				} 
				 else {
					 //最小化托盘操作
					 trayUtil.minimizeWindow(shell);
					 logger.debug("最小化托盘"); //$NON-NLS-1$	
					
				 }
			}
		}
		else 
		if (close.equals("Minimize")) { //$NON-NLS-1$
			trayUtil.minimizeWindow(shell);
			logger.debug("最小化托盘"); //$NON-NLS-1$
		} 
		else 
			if (close.equals("Close")) { //$NON-NLS-1$
				return true;
			} 
		return false;
	}
		
	}

}