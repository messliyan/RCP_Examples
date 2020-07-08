package gef_editpart;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import gef_editpolicy.AppEditLayoutPolicy;
import gef_figure.EnterpriseFigure;
import gef_model.Enterprise;
import gef_model.Node;



public class EnterprisePart extends AppAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		IFigure figure = new EnterpriseFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new AppEditLayoutPolicy());
	
	}
	
	protected void refreshVisuals() {
		EnterpriseFigure figure = (EnterpriseFigure)getFigure();
		Enterprise model = (Enterprise)getModel();
		
		figure.setName(model.getName());
		figure.setAddress(model.getAddress());
		figure.setCapital(model.getCapital());
	}
	
	public List<Node> getModelChildren() {
		return ((Enterprise)getModel()).getChildrenArray();
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) refreshVisuals();
		if(evt.getPropertyName().equals(Node.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_REMOVE)) refreshChildren();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Enterprise.PROPERTY_CAPITAL)) refreshVisuals();
	}
}