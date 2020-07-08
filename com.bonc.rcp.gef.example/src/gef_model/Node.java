package gef_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Rectangle;
//λ�� ���� �� ��	����Դ
import org.eclipse.ui.views.properties.IPropertySource;

import gef_property.NodePropertySource;

//����Դ���������ַ�ʽ���壺
//-����ֱ����ģ�Ͷ����е�IPropertySource�ӿ�ʵ��
//����ģ�Ͷ����е�IAdaptable�ӿ�ʵ�֣�������һ����getAdapter()������ʵ��IPropertySource�Ķ��󣬴�ʱIPropertySource������Ϊ�����Ĳ������ݡ������������ʹģ�ͺ�����Դ����ø��õ��ŵ㣬���ҽ���Ҫ��Node����ʵ�ֵ�һ�Σ�����Դ�����Ķ��󣬼��̳�Node���ཫ����Щ���ԣ�����Enterprise��Service��Employee����
public class Node implements IAdaptable{

	private String name;
	private Rectangle layout;
	private List<Node> children;
	private Node parent;
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_LAYOUT = "NodeLayout";
	public static final String PROPERTY_ADD = "NodeAddChild";
	public static final String PROPERTY_REMOVE = "NodeRemoveChild";
	public static final String PROPERTY_RENAME = "NodeRename";
	private IPropertySource propertySource = null;
	
	public Node() {
		this.name = "Unknown";
		this.layout = new Rectangle(10,10,100,100);
		this.children = new ArrayList<Node>();
		this.parent = null;
		this.listeners = new PropertyChangeSupport(this);
	}
	
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		getListeners().firePropertyChange(PROPERTY_RENAME, oldName, this.name);
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		if(adapter == IPropertySource.class) {
			if(propertySource == null) {
				propertySource = new NodePropertySource(this);
			}
			return propertySource;
		}
		return null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setLayout(Rectangle newLayout) {//��ʼ��λ��
		Rectangle oldLayout = this.layout;
		this.layout = newLayout;
		getListeners().firePropertyChange(PROPERTY_LAYOUT, oldLayout, newLayout);
	
	}
	
	
	public Rectangle getLayout() {
		return this.layout;
	}
	
	public boolean addChild(Node child) {
//		child.setParent(this);
//		return this.children.add(child);
		boolean b = this.children.add(child);
		if(b) {
			child.setParent(this);
			getListeners().firePropertyChange(PROPERTY_ADD, null, child);
		}
		return b;
	}
	
	public boolean removeChild(Node child) {
//		return this.children.remove(child);
		boolean b = this.children.remove(child);
		if(b) {
			getListeners().firePropertyChange(PROPERTY_REMOVE, child, null);
		}
		return b;
	}
	
	public List<Node> getChildrenArray() {
		return this.children;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getParent() {
		return this.parent;
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}
	public boolean contains(Node child) {
		return children.contains(child);
	}
	
	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	
	
}