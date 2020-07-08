package com.bonc.rcp.dbtool.lifecyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.osgi.framework.AdminPermission;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bonc.rcp.common.CryptTest.CryptTest;
import com.bonc.rcp.common.HttpUtil.HttpUtil;
import com.bonc.rcp.common.TestReg.IVerifyTest;
import com.bonc.rcp.common.bundleresourceloader.IBundleResourceLoader;
import com.bonc.rcp.common.logback.ILogger;
import com.bonc.rcp.common.startup_parameter.Startup_parameter;
import com.bonc.rcp.dbtool.Constants.preferenceConstants.LoginPreferenceConstants;
import com.bonc.rcp.dbtool.addons.WindewCloseBehaviour;
import com.bonc.rcp.dbtool.comp.dialogs.loginwizards.LoginWizrds;
import com.bonc.rcp.dbtool.model.Cluster;
import com.bonc.rcp.dbtool.services.IClusterService;
import org.eclipse.core.runtime.IStatus;
/**
 * This is a stub implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references
 * this class.
 **/
@SuppressWarnings("restriction")
public class Manager {

	// We add the nodePath in case you move the lifecycle handler to
	// another plug-in later
	
	@Inject
	@Preference(nodePath = LoginPreferenceConstants.NODEPATH, 
				value = LoginPreferenceConstants.USER_PREF_KEY)
	private String userName;

	@Inject
	@Preference(nodePath = LoginPreferenceConstants.NODEPATH, 
				value = LoginPreferenceConstants.PASSWORD_PREF_KEY)
	private String password;
//	
	@Inject
	@Preference(nodePath = LoginPreferenceConstants.NODEPATH, 
				value = LoginPreferenceConstants.HOST_PREF_KEY)
	private String host;
//	
	@Inject
	@Preference(nodePath = LoginPreferenceConstants.NODEPATH, 
				value = LoginPreferenceConstants.PORT_PREF_KEY)
	private String port;
//	
//	
	private IClusterService clusterService;
	@Inject
	@Preference(nodePath = LoginPreferenceConstants.NODEPATH, 
				value = LoginPreferenceConstants.SAVEPASSWORD_PREF_KEY)
	private String savePassword;
	private IApplicationContext appContext;
	
	
	private IVerifyTest verifyTest;
	private Display display;
	private WindewOpenBehaviour windewOpenBehaviour;
	private  IEclipseContext context;
	private ILogger logger;
	private IEclipsePreferences prefs;
	private IBundleResourceLoader loader;
	/*	Is called after the Application’s IEclipseContext is created,
	 *  can be used to add objects, 
	 *  services, etc. to the context. 
	 *  This context is created for the MApplication class.
	 *  可以使用 IEclipseContext  没有appctaion
	 *  上下文加载了 但是界面还没有 shell 不可用 注入的服务可以用
		*/
	public static WithDilogOpen dilogopen;
	@PostContextCreate
	public void postContextCreate(
			IClusterService clusterService,
			@Preference (nodePath = LoginPreferenceConstants.NODEPATH)IEclipsePreferences prefs,
			ILogger logger,IApplicationContext appContext, Display display, IEclipseContext context,
			IVerifyTest verifyTest,IBundleResourceLoader loader) {
		//显示为最上层
		dilogopen=new WithDilogOpen();
		
		if ("true".equals(Startup_parameter.getArgValue("develop", appContext, true))) {
			setWindewOpenBehaviour(new WithOutOpenBehaviour());
		}else if ("true".equals(Startup_parameter.getArgValue("test", appContext, true))) {
			setWindewOpenBehaviour(new WithTestOpenBehaviour());
		}else {
			setWindewOpenBehaviour(dilogopen);
		}
	
		this.logger=logger;
		this.loader=loader;
		this.clusterService=clusterService;
		this.display=display;
		this.appContext=appContext;
		this.verifyTest=verifyTest;
		this.prefs=prefs;
		this.context=context;
		password=CryptTest.decodeData(password); 
		
		
	
		  
		
//		sub1.put("login", "wode");
//		 close the static splash screen
		appContext.applicationRunning();

	

		  logger.debug(" 生命周期中设置初始化样式");
		 String cssURI = "platform:/plugin/com.bonc.xcloud.dataMigration/css/dark-gradient.css";
//			
//	
//	     context.set(E4Workbench.CSS_URI_ARG, cssURI);
	     
	     //注释下面代码 回复原始
//	     PartRenderingEngine.initializeStyling(shell.getDisplay(), context);

		
		
		
		
//		 LoginWizrdsPage loginWizrdsPage= loginWizrds.getLoginWizrdsPage();
		 
		
//		 

		 
//		 if (userName!=null) {
//			 loginWizrdsPage.setUserName(userName);
//		}
//		 if (host!=null) {
//			 loginWizrdsPage.setHost(host);
//			 
//		}
//		 if (post!=null) {
//			 loginWizrdsPage.setPort(post);
//			 
//		}
//		 
//		 if (savePassword!=null&&savePassword.equals("true")) {
//			 
//			 loginWizrdsPage.setPassword(password);
//			 loginWizrdsPage.setSavePassword(savePassword);
//		}
			//重用删掉下面的语句
			windewOpenBehaviour.Canopem();
				
		

		
		 
	}
	private void setWindewOpenBehaviour(WindewOpenBehaviour windewOpenBehaviour) {
		this.windewOpenBehaviour=windewOpenBehaviour;
	}
	
/*	在保存应用程序模型之前调用。您可以在持久化模型之前修改它。
*/	
	@PreSave
	void preSave(IEclipseContext workbenchContext,EPartService partService) {
		
		//重用的时候解开下面这个注释 ，也可以写在其他实现处
//		setWindewOpenBehaviour(new WithOutOpen());
	}
/*
	Is called directly before the model is passed to the renderer, 
	can be used to add additional elements to the model.
	将模型传递给呈现程序之前直接调用，可以用来向模型添加额外的元素。
	*/
	@ProcessAdditions
	void processAdditions(IEclipseContext workbenchContext) {
		
	}

	@ProcessRemovals
	void processRemovals(IEclipseContext workbenchContext) {
		
	}
	
	private void setLocation(Display display, Shell shell) {
		Monitor monitor = display.getPrimaryMonitor();
		Rectangle monitorRect = monitor.getBounds();
		Rectangle shellRect = shell.getBounds();
		int x = monitorRect.x + (monitorRect.width - shellRect.width) / 2;
		int y = monitorRect.y + (monitorRect.height - shellRect.height) / 2;
		shell.setLocation(x, y);
	}
	
	
	public class WithDilogOpen implements WindewOpenBehaviour{
		
		private Shell shell;
		private Map<String, Object> servrMap = null;
		private boolean stopFlag;// 停止标志
		@Override
		public void Canopem() {
			// TODO 自动生成的方法存根
			 shell = new Shell(SWT.SHELL_TRIM|SWT.ON_TOP);

			 LoginWizrds loginWizrds=new LoginWizrds(verifyTest,shell,prefs, host,  port,  userName,
					 password, savePassword);
			 logger.debug("生命周期最开始 创建登陆窗口");
			  
			   
			  ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(),shell);
			  
				 Image image = resourceManager. createImage(loader.getImageDescriptor(this.getClass(), "icons/tray.png"));  //$NON-NLS-1$
			WizardDialog dialog = new WizardDialog(shell, loginWizrds); 
			dialog.setDefaultImage(image);

			// position the shell
			 setLocation(display, shell);

				if (dialog.open() != Window.OK) {
					// close the application
					  logger.debug("登录取消");
					System.exit(-1);
				} 

		}
		public boolean addCluster() {
			 String url = "http://" //$NON-NLS-1$
						+ host + ":" //$NON-NLS-1$
						+ port + "/DBService"; //$NON-NLS-1$
				
			 servrMap = new HashMap<String, Object>();

					String resultString="";
					try {
						resultString = HttpUtil.postRequest(url, "admin/clusterManager/getClusters", servrMap);
					
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						MultiStatus status = createMultiStatus(e.getLocalizedMessage(), e);
						// show error dialog
						ErrorDialog.openError(shell, "错误", "发生了一个错误", status); //$NON-NLS-1$ //$NON-NLS-2$
				
						return false;
					} //$NON-NLS-1$
					
				
				clusterService.addCluster(JSONObject.parseArray(resultString, Cluster.class)); //$NON-NLS-1$
				
				return true;
			}
		public boolean verifylogin(Shell shell, String host, String port, String userName, String password) {


			if ("".equals(userName)) { //$NON-NLS-1$

				MessageDialog.openConfirm(shell, "格式错误", "用户名不可为空");
				return false;
			}
			if ("".equals(host) || !verifyTest.verifyIP(host)) { //$NON-NLS-1$

				MessageDialog.openConfirm(shell, "格式错误","主机地址格式错误");
				return false;
			}
			if ("".equals(port) || !verifyTest.verifyPort(port)) { //$NON-NLS-1$

				MessageDialog.openConfirm(shell, "格式错误","端口号格式错误");
				return false;
			}

			if ("".equals(password)) { //$NON-NLS-1$

				MessageDialog.openConfirm(shell, "格式错误", "密码不可为空");
				return false;
			}
			return true;
		} 
	
	
	//验证登陆是否成功
		public boolean verifyLoginTest(Shell shell, String host, String port, String userName, String password) {


			servrMap = null;
			stopFlag = false;// 每次都设初值为false


			new Thread() {// 把后台任务放到一个新开线程里执行
				public void run() {
					servrMap=go(  host,  port,  userName,  password);

				}
			}.start();

			showProgressDialog();// 打开一个进度框

			if(servrMap==null) {
				return false;
			}
			String status=servrMap.get("status").toString();
			String error= servrMap.get("error").toString(); //$NON-NLS-1$

			if ("0".equals(status)) {
				return true;
			}

			MessageDialog.openInformation(shell, "错误", error);
			return false;

		}

		private  MultiStatus createMultiStatus(String msg, Throwable t) {

			List<Status> childStatuses = new ArrayList<Status>();
			StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

			for (StackTraceElement stackTrace: stackTraces) {
				Status status = new Status(IStatus.ERROR,
						"com.bonc.xcloud.datamigration", stackTrace.toString()); //$NON-NLS-1$
				childStatuses.add(status);
			}

			MultiStatus ms = new MultiStatus("com.bonc.xcloud.datamigration", //$NON-NLS-1$
					IStatus.ERROR, childStatuses.toArray(new Status[] {}),
					t.toString(), t);
			return ms;
		}
		private void showProgressDialog() {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("正在连接中......", 30);
					int i = 0;
					while (true) {
						if (monitor.isCanceled() || stopFlag) {
							stopFlag = true; // 单击了“取消”按钮要设标志位为停止，好通知后台任务中断执行
							break;// 中断处理
						}
						// i到30后清零。并将进度条重新来过
						if ((++i) == 30) {
							i = 0;
							monitor.beginTask("正在连接中......", 30);
						}
						// 进度条每前进一步体息一会，不用太长或太短，时间可任意设。
						try {
							Thread.sleep(99);
						} catch (Throwable t) {}
						monitor.worked(1);// 进度条前进一步
					}
					monitor.done();// 进度条前进到完成
				}
			};
			try {
				new ProgressMonitorDialog(shell).run(true, true, runnable);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private Map<String, Object>  go(String host, String port, String userName, String password ) {

			
			Map<String, Object> result = new HashMap<String, Object>();
			
			 String url = "http://" //$NON-NLS-1$
						+ host + ":" //$NON-NLS-1$
						+ port + "/DBService"; //$NON-NLS-1$
			 Map<String, Object> params = new HashMap<String, Object>();
				params.put("username", userName);
				params.put("password", password);
			
			try {
//				Thread.sleep(1000);
				String resultString = HttpUtil.postRequest(url, "/xcloud/MigrationData/loginCS", params); //$NON-NLS-1$

				if (resultString.contains(" timed out")) { //$NON-NLS-1$
		
					throw new Exception("代理连接超时"); //$NON-NLS-1$
				} else {
					// 接受的信息转化为类
					Map maps = (Map) JSON.parse(resultString);

					String status= maps.get("status").toString(); //$NON-NLS-1$

					String error= maps.get("error").toString(); //$NON-NLS-1$
					
					result.put("status", status); //$NON-NLS-1$
					result.put("error", error); //$NON-NLS-1$

				}
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();

				MultiStatus status = createMultiStatus(e.getLocalizedMessage(), e);
				// show error dialog
				ErrorDialog.openError(shell, "错误", "发生了一个错误", status); //$NON-NLS-1$ //$NON-NLS-2$
			} 
			stopFlag = true;// 执行完毕后把标志位设为停止，好通知给进度框
			return result;

		}
	}
	class WithTestOpenBehaviour implements WindewOpenBehaviour{

		@Override
		public void Canopem() {
			// TODO 自动生成的方法存根

			
			 String url = "http://" //$NON-NLS-1$
					+ "172.16.12.47" + ":" //$NON-NLS-1$
					+ "9372" + "/DBService"; //$NON-NLS-1$
			
			 Map<String, Object> params = new HashMap<String, Object>();

				String resultString="";
				try {
					resultString = HttpUtil.postRequest(url, "admin/clusterManager/getClusters", params);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} //$NON-NLS-1$
				 logger.debug("得到消息为"+resultString);
			clusterService.addCluster(JSONObject.parseArray(resultString, Cluster.class)); //$NON-NLS-1$
			
//			sub1.put("login", "wode");
//			 close the static splash screen
			appContext.applicationRunning();


		}
		
	}
}
