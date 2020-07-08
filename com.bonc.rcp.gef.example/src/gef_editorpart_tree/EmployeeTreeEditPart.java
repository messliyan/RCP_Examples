package gef_editorpart_tree;
import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import gef_editpolicy.AppDeletePolicy;
import gef_model.Node;
import gef_model.Employee;
import gef_model.Enterprise;

//´ó¸ÙÊ÷ 
public class EmployeeTreeEditPart extends AppAbstractTreeEditPart {
	
	protected List<Node> getModelChildren() {
		return ((Employee)getModel()).getChildrenArray();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
	}
	
	public void refreshVisuals() {
		Employee model = (Employee)getModel();
		setWidgetText(model.getName() + " " + model.getPrenom());
		setWidgetImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_DEF_VIEW));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Employee.PROPERTY_FIRSTNAME)) refreshVisuals();
	}

}