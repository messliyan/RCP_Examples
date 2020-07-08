package gef_editpart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import gef_model.Employee;
import gef_model.Enterprise;
import gef_model.Service;


public class AppEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		// TODO Auto-generated method stub
		AbstractGraphicalEditPart part = null;
		
		if(model instanceof Enterprise) {
			part = new EnterprisePart();
		} else if(model instanceof Service) {
			part = new ServicePart();
		} else if(model instanceof Employee) {
			part = new EmployeePart();
		}
		
		part.setModel(model);
		return part;
	}

}