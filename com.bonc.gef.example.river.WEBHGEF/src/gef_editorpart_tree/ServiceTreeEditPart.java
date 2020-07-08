package gef_editorpart_tree;
import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import gef_editpolicy.AppDeletePolicy;
import gef_editpolicy.AppRenamePolicy;
import gef_model.Enterprise;
import gef_model.Node;
import gef_model.Service;

public class ServiceTreeEditPart extends AppAbstractTreeEditPart {

	protected List<Node> getModelChildren() {
		return ((Service)getModel()).getChildrenArray();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AppRenamePolicy());
	}
	
	public void refreshVisuals() {
		Service model = (Service)getModel();
		setWidgetText(model.getName());
		setWidgetImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Service.PROPERTY_COLOR)) refreshVisuals();
		if(evt.getPropertyName().equals(Service.PROPERTY_FLOOR)) refreshVisuals();
	}

}