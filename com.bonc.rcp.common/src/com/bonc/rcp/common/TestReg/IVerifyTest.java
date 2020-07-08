package com.bonc.rcp.common.TestReg;



public   interface IVerifyTest {

/**
 * 验证hdfs
 */
	public boolean verifyHdfs (String teString);
	/**
	 * 验证绝对路径
	 */
	public boolean verifyAbsolutePath (String teString);
	/**
	 * 验证xml文件的绝对路径
	 */
	public boolean verifyAbsoluteXmlPath (String teString);
	/**
	 * 验证ip:port
	 * @returns
	 */
	public boolean verifyIpPort (String teString);
	/**
	 * 验证ip:port,ip:port
	 * @returns
	 */
	public boolean verifyIpPortall (String teString);

	/**
	 * 验证Port
	 */
	public boolean verifyPort (String teString);
	/**
	 * 验证百分比
	 * @returns
	 */
	public boolean verifyPercent (String teString);

	/**
	 * 验证IP
	 * @returns
	 */
	public boolean verifyIP (String teString);
	/**
	 * 验证Boolean值
	 * @returns
	 */
	public boolean verifyBoolean (String teString);
	
}
