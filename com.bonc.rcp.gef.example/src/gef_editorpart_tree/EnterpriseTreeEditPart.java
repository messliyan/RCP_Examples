package gef_editorpart_tree;
import java.beans.PropertyChangeEvent;
import java.util.List;

import gef_model.Node;
import gef_model.Enterprise;
//´ó¸ÙÊ÷ 
public class EnterpriseTreeEditPart extends AppAbstractTreeEditPart {

	protected List<Node> getModelChildren() {
		return ((Enterprise)getModel()).getChildrenArray();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Enterprise.PROPERTY_CAPITAL)) refreshVisuals();
	}

}