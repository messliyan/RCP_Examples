package com.bonc.rcp.dbtool.services;


import java.util.List;

import com.bonc.rcp.dbtool.model.Cluster;


public interface IClusterService {
	
	List<Cluster> getClusters();


	Cluster getCluster(long id);


	void addCluster(List<Cluster> list);


}
