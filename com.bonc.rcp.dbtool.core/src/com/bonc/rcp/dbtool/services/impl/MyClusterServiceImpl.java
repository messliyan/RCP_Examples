package com.bonc.rcp.dbtool.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.bonc.rcp.dbtool.Constants.eventConstants.MyEventConstants;
import com.bonc.rcp.dbtool.model.Cluster;
import com.bonc.rcp.dbtool.services.IClusterService;

/**
 * 集群事件
 * */
public class MyClusterServiceImpl implements IClusterService {

	@Inject 
	IEventBroker broker;
	
	
	
	private static int current = 1;
	private List<Cluster> clusters;

	// use dependency injection in MyTodoServiceImpl


	public MyClusterServiceImpl() {
		clusters = createInitialModel();
	}

	// always return a new copy of the data
	@Override
	public List<Cluster> getClusters() {
		
		
		
		List<Cluster> list = new ArrayList<Cluster>();
		for (Cluster cluster : clusters) {
			list.add(cluster.copy());
		}
		return list;
	}

	
	@Override
	public Cluster getCluster(long id) {
		Cluster cluster = findById(id);

		if (cluster != null) {
			return cluster.copy();
		}
		return null;
	}

	// Example data, change if you like
	private List<Cluster> createInitialModel()         {
		List<Cluster> list = new ArrayList<Cluster>();
//		list.add(createCluster(1, "Flexible and extensible"));
//		list.add(createCluster(2, "@Inject as programming mode"));		
//		list.add(createCluster(3, "Services"));
//		list.add(createCluster(4, "Widgets"));
//		list.add(createCluster(5, "Especially Viewers!"));
//		list.add(createCluster(6, "Style your application"));
//		list.add(createCluster(7, "Selection, model, Part"));
//		list.add(createCluster(8, "Different UI toolkit"));
//		list.add(createCluster(9, "Run Eclipse 3.x"));
		return list;
	}

	
	@Override
	public void addCluster(List<Cluster> list) {
		if (list!=null) {
			for (Cluster cluster : list) {
				
				clusters.add(createCluster(cluster.getClusterId(), cluster.getClusterName()));
			
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		// in case the receiver wants to check the topic 
		
		
		map.put(MyEventConstants.TOPIC_MIGRATION_CLUSTER, true);
		
		// which todo has changed 
		
		broker.post(MyEventConstants.TOPIC_MIGRATION_CLUSTER,map);
		
		broker.post(MyEventConstants.TOPIC_ADDCLUSTER_ID,getClusters());
	}
	
	
	
//	//登录 操作
//	@Inject
//	@Optional
//	private void subscribeTopicLogin (
//			@EventTopic(MyEventConstants.TOPIC_MIGRATION_CLUSTER) Map data) {
//		
//		Map<String, Object> map=null;
//		try {
//			map =dataMigrationService.getCluster();
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	
//		List<Cluster> list=(List<Cluster>) map.get("clusters");
//		
//	
//		
//		for (Cluster cluster : list) {
//			
//			
//			clusters.add(createCluster(cluster.getClusterId(), cluster.getClusterName()));
//		
//		}
//		
	
//		
//		for (int i = 0; i < list.size(); i++) {
//		
//			Cluster cluster=list.get(i);
//			
//			list.add(createCluster(cluster.getClusterId(), cluster.getClusterName()));
//			
//		}
		
//		List<Cluster> list = new ArrayList<Cluster>();
//		list.add(createCluster(1, "Flexible and extensible"));
//		list.add(createCluster(2, "@Inject as programming mode"));		
//		list.add(createCluster(3, "Services"));
//		list.add(createCluster(4, "Widgets"));
//		list.add(createCluster(5, "Especially Viewers!"));
//		list.add(createCluster(6, "Style your application"));
//		list.add(createCluster(7, "Selection, model, Part"));
//		list.add(createCluster(8, "Different UI toolkit"));
//		list.add(createCluster(9, "Run Eclipse 3.x"));
	
//		clusters=newlist;
		
		
//	}
	private Cluster createCluster(Integer clusterId, String clusterName) {
		return new Cluster(current++, clusterId, clusterName);
	}

	private Cluster findById(long id) {
		for (Cluster cluster : clusters) {
			if (id == cluster.getId()) {
				return cluster;
			}
		}
		return null;
	}

}
