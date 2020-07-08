package com.bonc.rcp.common.TestReg.internal;

import com.bonc.rcp.common.TestReg.IVerifyTest;

public class VerifyTestImpl implements IVerifyTest{

	@Override
	public boolean verifyHdfs(String teString) {
		// TODO 自动生成的方法存根
		/*
		 * 1.输入值等于空直接返回
		 */
		if (teString.length()<7) {
			return false;
		}
		String str = teString.substring(0,7);
		String temp = teString;
		String str2 = teString.substring(7,temp.length());
		if(str.startsWith("hdfs://")  &&verifyIpPort(str2)){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyAbsolutePath(String teString) {
		// TODO 自动生成的方法存根
		if (teString.length()<2) {
			return false;
		}
		String patt ="(^\\/.+$)" ;

		
		if (!teString.matches(patt)) {
		
			return false;
		}
		return true;
	}



	@Override
	public boolean verifyIpPort(String teString) {
		// TODO 自动生成的方法存根
	
		
		
		String[] ip = teString.split(":");

		if (ip.length!=2) {
			return false;
		}
		
		if(verifyIP(ip[0])&&verifyPort(ip[1])){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyPort(String teString) {
		// TODO 自动生成的方法存根
		
		String pattPort = "(102[4-9]|10[3-9]\\d|1[1-9]\\d\\d|[2-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
		if(teString.matches(pattPort)){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyPercent(String teString) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean verifyIP(String teString) {
		// TODO 自动生成的方法存根
		String patt = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";
		if(teString.matches(patt)){
			return true;
		}
		return false;
	}

	@Override
	public boolean verifyBoolean(String teString) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean verifyAbsoluteXmlPath(String teString) {
		// TODO 自动生成的方法存根
		if (teString.length()<6) {
			return false;
		}
		String patt ="(^\\/.+$)" ;

		
		if (!teString.matches(patt)||!teString.endsWith(".xml")) {
		
			return false;
		}
		return true;
	}

	@Override
	public boolean verifyIpPortall(String teString) {
		// TODO 自动生成的方法存根
		
		String[] IpPorts = teString.split(",");
		
		boolean flage=false;
		for (String IpPort : IpPorts) {
			flage=verifyIpPort(IpPort );
			if (flage==false) {
				return false;
			}
		}	
		return flage;
	}

	

}
