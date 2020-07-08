package gef_editpart;
import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import gef_editpolicy.AppDeletePolicy;
import gef_editpolicy.AppEditLayoutPolicy;
import gef_editpolicy.AppRenamePolicy;
import gef_figure.ServiceFigure;
import gef_model.Enterprise;
import gef_model.Node;
import gef_model.Service;


public class ServicePart extends AppAbstractEditPart{

	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		IFigure figure = new ServiceFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AppRenamePolicy());
	}
	
	protected void refreshVisuals() {
		ServiceFigure figure = (ServiceFigure)getFigure();
		Service model = (Service)getModel();
		figure.setColor(model.getColor());
		figure.setName(model.getName());
		figure.setEtage(model.getEtage());
		figure.setLayout(model.getLayout());
	}
	
	public List<Node> getModelChildren() {
		return ((Service)getModel()).getChildrenArray();
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) refreshVisuals();
		if(evt.getPropertyName().equals(Node.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Service.PROPERTY_COLOR)) refreshVisuals();
		if(evt.getPropertyName().equals(Service.PROPERTY_FLOOR)) refreshVisuals();
	}
}