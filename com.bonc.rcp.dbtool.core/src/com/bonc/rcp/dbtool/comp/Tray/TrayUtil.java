package com.bonc.rcp.dbtool.comp.Tray;


import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.bonc.rcp.dbtool.i18n.Messages;


public class TrayUtil implements SelectionListener,Listener{
	@Inject
	 Shell shell;
	private Menu menu;
	private Tray tray;

	
	
	@Inject
	 IWorkbench workbench;
	private MenuItem[] menuItems = new MenuItem[0];
	private RestoreWindowListener restoreWindowListener;
	
	
	@Inject
	public TrayUtil(Shell shell
			){
	this.shell=shell;
		this.restoreWindowListener = new RestoreWindowListener();
	}
	//关闭窗口
	private void closeApplication(){
		workbench.close();
	}
	//还原窗口
	private void restoreWindow(){

		shell.open();
		shell.setMinimized(false);
		shell.forceActive();
		shell.forceFocus();
	}
	
	
	//最小化
	public void  minimizeWindow(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		shell.setMinimized(true);
		shell.setVisible(false);
	}
	//显示右键菜单
	private void showMenu(){
		clearItems();
		MenuItem openItem;
		MenuItem closeItem;
		openItem = new MenuItem(this.menu, SWT.PUSH);
		openItem.setText("打开工作台");
//		openItem.setText(message.TrayUtil_0);
		openItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				restoreWindow();
			}
		});
		closeItem = new MenuItem(this.menu, SWT.NONE);
		closeItem.setText("退出");
//		closeItem.setText(message.TrayUtil_1);
		closeItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				Display.getCurrent().close();
//				System.exit(0);
				closeApplication();
			}
		});
		this.menuItems = new MenuItem[]{openItem,closeItem};
		this.menu.setVisible(true);
	}
	private void clearItems(){
		for(int i =0; i<this.menuItems.length;i++){
			MenuItem item = this.menuItems[i];
			item.removeSelectionListener(this.restoreWindowListener);
			this.menuItems[i].dispose();
		}
	}
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		showMenu();
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		restoreWindow();
	}
	
	private final class RestoreWindowListener extends SelectionAdapter{
		@Override
		public void widgetSelected(SelectionEvent e) {
			restoreWindow();
		}
	}

	public void createSystemTray(Image image){
		//取得托盘对象
		tray = Display.getDefault().getSystemTray();
		//创建托盘对象
		TrayItem item = new TrayItem(tray, SWT.NONE);
		item.setText("DataMigration"); //$NON-NLS-1$
		item.setToolTipText("DataMigration");//托盘提示文字 //$NON-NLS-1$
		item.setImage(image);
		
		item.addSelectionListener(this);
		item.addListener(SWT.MenuDetect, this);	 
		menu = new Menu(shell,SWT.POP_UP);
	}
}