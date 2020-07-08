package com.bonc.rcp.dbtool.comp.dialogs;



import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

import com.bonc.rcp.dbtool.Constants.preferenceConstants.CloseWindowPreferenceConstants;



public class CloseDialog extends Dialog {
	
	//必须使用注入
	@Inject
	@Preference (nodePath = CloseWindowPreferenceConstants.NODEPATH)
	IEclipsePreferences prefs;
	
	@Inject
	public CloseDialog(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		super(shell);
		
	}
	
	
	private Button b1,b2,checkBox;
	
	private boolean radioFlag;
	private boolean checkFlag;
	public boolean isClose(){
		return radioFlag;
	}
	public boolean isCheck(){
		return checkFlag;
	}
	@Override
	protected Control createDialogArea(Composite parent) {
	
		
		Composite comp = new Composite(parent,SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.minimumWidth=350;
		comp.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight=20;
		gridLayout.marginWidth=20;
		comp.setLayout(gridLayout);
		
		Group group = new Group(comp, SWT.NONE);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginWidth = 40;
		fillLayout.marginHeight = 10;
		fillLayout.spacing = 5;
		group.setLayout(fillLayout);
		group.setLayoutData(groupData);
		//group.setText("您点击了关闭按钮，您希望：");//Exit XCloud?
		group.setText("您点击了关闭按钮，您希望：");
		
		b1 = new Button(group, SWT.RADIO);
		b2 = new Button(group, SWT.RADIO);
		//b1.setText("最小化到托盘区，不退出");
		//b2.setText("退出程序");//Exit Application
		b1.setSelection(true);	
		b1.setText("最小化到托盘区，不退出");
		b2.setText("退出程序");
		
//		Composite checkComp = new Composite(comp, SWT.NONE);
//		checkComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
//		FillLayout fillLayout2 = new FillLayout();
//		fillLayout2.marginHeight = 20;
//		checkComp.setLayout(fillLayout2);
		
		checkBox = new Button(group, SWT.CHECK);
		checkBox.setText("不再显示提示框，按照本次设置执行");//Always exit without prompt
//		checkBox.setText(Messages.com_bonc_xcloud_client_util_TrayUtil_trayPromptCheck);
		
		this.getShell().setText("关闭窗口");//Confirm Exit
		
        return comp;
	
	}
	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId == IDialogConstants.OK_ID){
			
			
			if(b1.getSelection()){
				radioFlag = false;
			}
			else {
				radioFlag = true;
			}
			if(checkBox.getSelection()){
//				IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
				if(b1.getSelection()) {
					
					prefs.put(CloseWindowPreferenceConstants.CLOSETYPE_PREF_KEY, "Minimize"); //$NON-NLS-1$
				}
				else {
					prefs.put(CloseWindowPreferenceConstants.CLOSETYPE_PREF_KEY, "Close"); //$NON-NLS-1$
				}
				
				try {
					prefs.flush();
					//没有的话 不会持久化保存
				} catch (BackingStoreException e) {
					e.printStackTrace();
				}
				
				//关闭托盘
				
//					SystemConfigUtil.updateProperties("closeType", "Minimize");
//					ps.setValue("closeType", "Minimize");
//				else
					
//					SystemConfigUtil.updateProperties("closeType", "Close");
//					ps.setValue("closeType", "Close");			
			}	
		}
		super.buttonPressed(buttonId);
	}
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		return null;
	}
	@Override
	protected void initializeBounds() {
		Composite comp = (Composite) getButtonBar();
		super.createButton(comp, IDialogConstants.OK_ID, "确认", false);
		super.createButton(comp, IDialogConstants.CANCEL_ID, "取消", false);
		super.initializeBounds();
	}
//@Override
//protected Point getInitialSize() {
//	// TODO 自动生成的方法存根
//	return new Point(300, 150);
//}
}
