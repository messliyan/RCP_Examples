package com.bonc.rcp.dbtool.comp.dialogs.loginwizards;


import java.net.URL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.bonc.rcp.common.TestReg.IVerifyTest;




public class LoginWizrdsPage extends WizardPage {
	
	private IVerifyTest verifyTest;
	private Group group;

	private String hostString;
	private String portString;
	private String userNameString;
	private String passwordString;
	private String savePasswordString;
	
	private Text host;
	private Text port;
	private Text userName;
	private Text password;

	private Button savePassword;
	
	
		// private Text connectRetry;
	// private Text socketTimeOut;

	// public Text procedurePort;
	public Label testConnect;
	

	public LoginWizrdsPage(String pageName) {
		
		
		super(pageName);
		// TODO Auto-generated constructor stub
	}



	protected LoginWizrdsPage(String pageName, String hostString, String portString, String userNameString,
			String passwordString,String savePasswordString, IVerifyTest verifyTest) {
		super(pageName);
		
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		URL url = FileLocator.find(bundle, 
		  new Path("icons/DIalog/loginWizrdImageDescriptor.png"), null); //$NON-NLS-1$
		setImageDescriptor( ImageDescriptor.createFromURL(url));
		setDescription("登录信息");
		this.hostString = hostString;
		this.portString = portString;
		this.userNameString = userNameString;
		this.passwordString = passwordString;
		this.savePasswordString=savePasswordString;
		this.verifyTest=verifyTest;
	}



	@Override
	public void createControl(Composite parent) {

		// TODO Auto-generated method stub
//		setTitle(Messages.LoginWizrds_0);

		
//		
		
		final Composite comp = new Composite(parent, SWT.NONE);
		
		comp.setLayout(new GridLayout(4, false));

		 group = new Group(comp, SWT.NONE);
		group.setLayout(new GridLayout(4, false));
		GridData gd_group_conn = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		// gd_group.heightHint=240;
		group.setLayoutData(gd_group_conn);
		group.setText("登录信息");

		// localName.setText(dBConfiguration.getLocalName());
		// localName.setEnabled(false);

	
		Label label = new Label(group, SWT.NONE);
		label.setText("主机地址:");

		host = new Text(group, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
//		gd_text_1.widthHint = 190;
		host.setLayoutData(gd_text_1);
	
		ControlDecoration deco = new ControlDecoration(host, SWT.TOP | SWT.LEFT);
		deco.setShowOnlyOnFocus(false);
		host.addModifyListener(e -> {
			if (verifyTest.verifyIP(host.getText())) {
				deco.setDescriptionText("格式正确！");
				Image image = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION).getImage();
				deco.setImage(image);

			} else {
				deco.setDescriptionText("请输入正确的IP！");
				Image image = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
				deco.setImage(image);

			}
		});
//		host.setText("123456IP");

		Label label_1 = new Label(group, SWT.NONE);
		label_1.setText("端口：");

		port = new Text(group, SWT.BORDER);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
//		gd_text_2.widthHint = 125;
		port.setLayoutData(gd_text_2);
//		port.setText("456port");
		ControlDecoration deco2 = new ControlDecoration(port, SWT.TOP | SWT.LEFT);
		deco2.setShowOnlyOnFocus(false);
		port.addModifyListener(e -> {
			if (verifyTest.verifyPort(port.getText())) {
				deco2.setDescriptionText("格式正确！");
				Image image = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION).getImage();
				deco2.setImage(image);

			} else {
				deco2.setDescriptionText("请输入正确的端口号！");
				Image image = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();
				deco2.setImage(image);

			}
		});
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setText("用户名:");
		

		userName = new Text(group, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
//		gd_text_3.widthHint = 190;
		userName.setLayoutData(gd_text_3);
//		userName.setText("用户名");
	
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setText("密码:");

		password = new Text(group, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_4 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
//		gd_text_4.widthHint = 190;
		password.setLayoutData(gd_text_4);
//		password.setText("123456");
		
		Label label_5 = new Label(group, SWT.NONE);
		label_5.setText("保存密码");
		savePassword = new Button(group, SWT.NONE | SWT.CHECK);

		GridData gd_text_5 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
	
		savePassword.setLayoutData(gd_text_5);

		
		
//		if (getXmlNode(a, "password").equals("")) {
//			savePassword.setSelection(false);
//		} else {
//			savePassword.setSelection(true);
//			/*String str = AESAndC02.deCiphering(getXmlNode(a, "password"));*/
//			String str = new String(AES2XCloud.decryptAES(AES2XCloud.parseHexStr2Byte(getXmlNode(a, "password")))).trim();
//			password.setText(str);
//			correctPass = str;
//		}

		


		testConnect = new Label(comp, SWT.NONE);
		GridData gd_testConnect = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_testConnect.widthHint = 150;

		testConnect.setLayoutData(gd_testConnect);
		
		 if (userNameString!=null) {
			 setUserName(userNameString);
		}
		 if (hostString!=null) {
			setHost(hostString);
			 
		}
		 if (portString!=null) {
			 setPort(portString);
			 
		}
		 
		 if (savePasswordString!=null&&savePasswordString.equals("true")) { //$NON-NLS-1$
			 
			 setPassword(passwordString);
			 
			 setSavePassword(savePasswordString);
		}
		
		host.setFocus();
		// dbType.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// if(dbType.getText().equals("XCLOUD")){
		// GridData gd_group2=new GridData(SWT.FILL, SWT.CENTER, false, false,
		// 5, 1);
		// gd_group2.heightHint=130;
		// group.setLayoutData(gd_group2);
		// comp.setSize(523, 245);
		//
		// label_7.setVisible(true);
		//
		// connectDirect.setVisible(true);
		//
		// }else {
		// GridData gd_group1=new GridData(SWT.FILL, SWT.CENTER, false, false,
		// 5, 1);
		// gd_group1.heightHint=100;
		// group.setLayoutData(gd_group1);
		// comp.setSize(523, 216);
		//
		// label_7.setVisible(false);
		//
		// connectDirect.setVisible(false);
		// }
		// }
		// });
		setControl(comp);
	}




	public String getHost() {
		return host.getText();
	}



	public void setHost(String host) {
		this.host.setText(host);
	}



	public String getPort() {
		return port.getText();
	}



	public void setPort(String port) {
		this.port.setText(port);
	}



	public String getUserName() {
		
		return userName.getText();
	}



	public void setUserName(String userName) {
		this.userName.setText(userName); 
	}



	public String getPassword() {
		return password.getText();
	}



	public void setPassword(String password) {
		this.password.setText(password); 
	}



	public String getSavePassword() {
		
		if (savePassword.getSelection()) {
			return "true"; //$NON-NLS-1$
		} else {
			return "false"; //$NON-NLS-1$
		}
	
	}



	public void setSavePassword(String savePassword) {
		
	
		if (savePassword.equals("true")) { //$NON-NLS-1$
		
			this.savePassword.setSelection(true);
		}else {
			this.savePassword.setSelection(false);
		}
		
		
	}
	
	

}
