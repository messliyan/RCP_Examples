package com.bonc.rcp.dbtool.comp.dialogs.loginwizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

import com.bonc.rcp.common.CryptTest.CryptTest;
import com.bonc.rcp.common.TestReg.IVerifyTest;
import com.bonc.rcp.dbtool.Constants.preferenceConstants.LoginPreferenceConstants;
import com.bonc.rcp.dbtool.lifecyle.Manager;

public class LoginWizrds extends Wizard {

	boolean finish = true;
	private Shell shell;
	private IEclipsePreferences prefs;
	private LoginWizrdsPage loginWizrdsPage;
	private IVerifyTest verifyTest;

	public LoginWizrdsPage getLoginWizrdsPage() {

		return loginWizrdsPage;
	}

	public LoginWizrds(  IVerifyTest verifyTest, Shell shell, IEclipsePreferences prefs, String hostString,
			String portString, String userNameString, String passwordString, String savePasswordString) {
		this.verifyTest = verifyTest;
	
		this.shell = shell;

		setWindowTitle("登录");
		loginWizrdsPage = new LoginWizrdsPage("登录", hostString, portString, userNameString, passwordString,
				savePasswordString,verifyTest);
		this.prefs = prefs;
	}


	@Override
	public void addPages() {

		addPage(loginWizrdsPage);
	}

	@Override
	public void dispose() {
		super.dispose();

	}

	@Override
	public boolean canFinish() {
		return finish;
	}

	// 登陆向导框的完成按钮
	@Override
	public boolean performFinish() {

		String userValue = loginWizrdsPage.getUserName();
		//		 store the user values in the preferences
		prefs.put(LoginPreferenceConstants.USER_PREF_KEY, userValue);

		String hostValue = loginWizrdsPage.getHost();
		//		 store the user values in the preferences
		prefs.put(LoginPreferenceConstants.HOST_PREF_KEY, hostValue);

		String port = loginWizrdsPage.getPort();

		//		 store the user values in the preferences
		prefs.put(LoginPreferenceConstants.PORT_PREF_KEY, port);

		String password = loginWizrdsPage.getPassword();
		String savepassword = loginWizrdsPage.getSavePassword();

		if (savepassword.equals("true")) { //$NON-NLS-1$

			String newpassword=CryptTest.encodeData(password);

			prefs.put(LoginPreferenceConstants.PASSWORD_PREF_KEY, newpassword);

		} else {
			prefs.put(LoginPreferenceConstants.PASSWORD_PREF_KEY, ""); //$NON-NLS-1$

		}
		prefs.put(LoginPreferenceConstants.SAVEPASSWORD_PREF_KEY, savepassword);

		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

		if (Manager.dilogopen.verifylogin(shell, hostValue,
				port,  userValue,  password)) {
			if (Manager.dilogopen.verifyLoginTest(shell, hostValue,
					port,  userValue,  password)) {
//				MessageDialog.openConfirm(shell, "验证成功", "LoginWizrds 登录验证！");
				return Manager.dilogopen.addCluster();
			}
			
		}
		return false;
		

	}

}
