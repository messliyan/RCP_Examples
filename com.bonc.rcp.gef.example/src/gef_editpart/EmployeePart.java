package gef_editpart;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import gef_editpolicy.AppDeletePolicy;
import gef_figure.EmployeeFigure;
import gef_model.Employee;
import gef_model.Enterprise;
import gef_model.Node;

public class EmployeePart extends AppAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		IFigure figure = new EmployeeFigure();
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AppDeletePolicy());
	}
	protected void refreshVisuals(){
		EmployeeFigure figure = (EmployeeFigure)getFigure();
		Employee model = (Employee)getModel();
		
		figure.setName(model.getName());
		figure.setFirstName(model.getPrenom());
		figure.setLayout(model.getLayout());
	}
	
	public List<Node> getModelChildren() {
		return new ArrayList<Node>();
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getPropertyName().equals(Node.PROPERTY_LAYOUT)) refreshVisuals();
		if(evt.getPropertyName().equals(Node.PROPERTY_RENAME)) refreshVisuals(); 
		if(evt.getPropertyName().equals(Employee.PROPERTY_FIRSTNAME)) refreshVisuals();
		}

}