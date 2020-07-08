package com.bonc.rcp.dbtool.Constants.eventConstants;

/**
 *
 * @noimplement This interface is not intended to be implemented by clients.
 *
 * Only used for constant definition
 * 最多一个  字段不能有空格
 */

public interface MyEventConstants {
	//当前活动clusterID
		String TOPIC_ACTIVE_CLUSTER_ID = "TOPIC_CORE_CLUSTERID";
	
		//获取集群
		String TOPIC_MIGRATION_CLUSTER = "TOPIC_CORE_MIGRATIONS/CLUSTER";
		//保存全部
		String TOPIC_MIGRATION_SAVE = "TOPIC_CORE_MIGRATIONS/TOPIC_MIGRATION_SAVE";
		//添加集群
		String TOPIC_ADDCLUSTER_ID = "TOPIC_CORE_ADDCLUSTER";
	
}
