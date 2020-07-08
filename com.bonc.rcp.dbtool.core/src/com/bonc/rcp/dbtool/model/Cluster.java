package com.bonc.rcp.dbtool.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Cluster {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	public static final String FIELD_ID = "Id";
	public static final String FIELD_CLUSTERID = "clusterId";
	
	public static final String FIELD_NAME = "clusterName";
	public final long id;
	public  Integer clusterId=0;
	private String clusterName ="" ;
	
	public Cluster(long i) {
		id = i;
	}

	public Cluster(long id, Integer clusterId, String clusterName) {
		super();
		this.id = id;
		this.clusterId = clusterId;
		this.clusterName = clusterName;
	}

	public Integer getClusterId() {
		return clusterId;
	}

	public void setClusterId(Integer clusterId) {
		changes.firePropertyChange(FIELD_CLUSTERID, this.clusterId,
				this.clusterId = clusterId);
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		changes.firePropertyChange(FIELD_NAME, this.clusterName,
				this.clusterName = clusterName);
	}

	public long getId() {
		return id;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluster other = (Cluster) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cluster [id=" + id + ", clusterId=" + clusterId + ", clusterName=" + clusterName + "]";
	}

	public Cluster copy() {
		return new Cluster(id, clusterId, clusterName);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
}