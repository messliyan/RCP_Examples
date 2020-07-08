 
package com.bonc.rcp.dbtool.parts;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import javax.annotation.PreDestroy;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import com.bonc.rcp.common.bundleresourceloader.IBundleResourceLoader;
import com.bonc.rcp.common.logback.ILogger;
import com.bonc.rcp.dbtool.Constants.eventConstants.MyEventConstants;
import com.bonc.rcp.dbtool.i18n.Messages;
import com.bonc.rcp.dbtool.model.Cluster;
import com.bonc.rcp.dbtool.services.IClusterService;

import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;

public class NavigatorView {
//	final Logger uiLogge = LoggerFactory.getLogger (LogType.fileName);
	
	@Inject
	UISynchronize sync;
	@Inject  
	Shell shell;
	@Inject 
	private IEventBroker broker;
	
	private Label label;
	private ListViewer viewer;
	@Inject 
	private ILogger logger;
	
	@Inject
	IClusterService clusterservice;
	
	@Inject
	ESelectionService service;
	@Inject
	EModelService modelService;
	@Inject
	MApplication application;
	@Inject
	MPart Mpart;
	
	Composite composite;
	private WritableList writableList;//数据绑定使用
	protected String searchString = "";
	
	private Text search;
	@PostConstruct
	public void postConstruct(Composite parent,EMenuService menuService,//IBundleResourceLoader loader,
			IWorkbench workbench,IBundleResourceLoader loader,@Translation Messages message) {

		Mpart.setCloseable(false);
		logger.debug("生成导航视图");
		composite=parent;
		parent.setLayout(new GridLayout(1, false));
		
		search = new Text(parent, SWT.SEARCH | SWT.CANCEL | SWT.ICON_SEARCH);

		// Assuming that GridLayout is used
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		search.addModifyListener(event->{
			Text source = (Text) event.getSource();
				searchString = source.getText();
				// Trigger update in the viewer
				viewer.refresh();
				});
		search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text text = (Text) e.getSource();
					text.setText("");
					//
				}
			}
		});
		
//		Map<String, Object> map = new HashMap<String, Object>(); 
		// in case the receiver wants to check the topic 
		
		
//		map.put(MyEventConstants.TOPIC_MIGRATION_CLUSTER, true);
		
	 
		// which todo has changed 
		
//		broker.post(MyEventConstants.TOPIC_MIGRATION_CLUSTER,map);
		

		viewer = new ListViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		List list=viewer.getList();

		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	
		list.setSelection(0);
		//通过集群名字搜索集群 只显示搜索出的list
		viewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				Cluster cluster = (Cluster) element;
				//忽略大小写
				return  cluster.getClusterName().toLowerCase().contains(searchString.toLowerCase());
						
			}
		}); 
		//当前选择服务
		
		viewer.addSelectionChangedListener(event->{
			IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
			if (selection.getFirstElement()!=null) {
				logger.debug("当前选择为"+selection.getFirstElement().toString());
			service.setSelection(selection.getFirstElement());
//			broker.post(MyEventConstants.TOPIC_MIGRATION_CLUSTERSELECT, selection.getFirstElement());
			} 
		
		});
		//注册右键菜单
		menuService.registerContextMenu(viewer.getControl(), "com.bonc.xcloud.popupmenu.Listview");
	
		//数据绑定
		writableList = new WritableList(clusterservice.getClusters(), Cluster.class);
		logger.debug("视图view数据绑定ClusterServices");
		ViewerSupport.bind(viewer, writableList,
				BeanProperties.values(new String[] { Cluster.FIELD_NAME}));
		
		
		
//		 label = new Label(parent, SWT.NONE);
//		 ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(), label);
//		 Image image = resourceManager. createImage(loader.getImageDescriptor(this.getClass(), "images/vogella.png")); 
//		 label.setImage(image);
//		List<Cluster> clusters=clusterservice.getClusters();
//		for (Cluster object : clusters) {
//			System.err.println(object.getClusterName());
//		}
		

		translateTable(message);

	}
	//忽略大小写

	
	@PreDestroy
	public void preDestroy() {
		
	}
	
//	@Inject 
	public void translateTable(@Translation Messages message){ 
		if (viewer !=null && !viewer.getList().isDisposed()) { 
			search.setMessage("搜索");
		}
	}
	
	@Focus
	public void onFocus() {
		viewer.getControl().setFocus();
	
	}
	
	//暂且没用
	@Inject 
	@Optional 
	private void activePart(@UIEventTopic(MyEventConstants.TOPIC_ACTIVE_CLUSTER_ID)  Object data) {
		
		if (data!=null
				) {
	
			// code if you use databinding for your viewer 
			Cluster cluster=clusterservice.getCluster(Long.parseLong((String) data));
			
			viewer.setSelection(new StructuredSelection(cluster), true);
			// if you do not use databinding, use the following snippet:
			// viewer.setInput(todoService.getTodos()); }
			}
	}
	

	//暂且没用 TOPIC_MIGRATION_CLUSTER
	@Inject 
	@Optional 
	private void upList(@UIEventTopic(MyEventConstants.TOPIC_MIGRATION_CLUSTER)  Object data) {
		logger.debug("get clue"+data);
		if (viewer != null) {
							writableList.clear();
							writableList.addAll(clusterservice.getClusters());
				
							logger.debug("get clue"+clusterservice.getClusters());
				
		composite.layout();
		}
		
	
			// code if you use databinding for your viewer 
			
			// if you do not use databinding, use the following snippet:
			// viewer.setInput(todoService.getTodos()); }
			
	}
	
	private  MWindow findMainWindow(MApplication application) {
		// instead of using the index you could also use a tag on the MWindow to
		// mark the main window
		return application.getChildren().get(0);
	}
	
	
	@Persist
	public void save() {
		
	}
	
}