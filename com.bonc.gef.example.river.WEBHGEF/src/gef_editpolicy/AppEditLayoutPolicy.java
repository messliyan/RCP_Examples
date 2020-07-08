package gef_editpolicy;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import gef_Palette.EmployeeCreateCommand;
import gef_Palette.ServiceCreateCommand;
import gef_command.AbstractLayoutCommand;
import gef_command.EmployeeChangeLayoutCommand;
import gef_command.ServiceChangeLayoutCommand;
import gef_editpart.EmployeePart;
import gef_editpart.EnterprisePart;
import gef_editpart.ServicePart;
import gef_figure.EmployeeFigure;
import gef_figure.ServiceFigure;

//移动 调色板
public class AppEditLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		// TODO Auto-generated method stub
		
		AbstractLayoutCommand command = null;
		
		if(child instanceof EmployeePart) {
			command = new EmployeeChangeLayoutCommand();
		} else if(child instanceof ServicePart) {
			command = new ServiceChangeLayoutCommand();
		}
		
		command.setModel(child.getModel());
		command.setConstraint((Rectangle)constraint);
		return command;
	}

	@Override	//调色板
	protected Command getCreateCommand(CreateRequest request) {
		// TODO Auto-generated method stub
		if(request.getType() == REQ_CREATE && getHost() instanceof EnterprisePart) {
			ServiceCreateCommand cmd = new ServiceCreateCommand();
			cmd.setEnterprise(getHost().getModel());
			cmd.setService(request.getNewObject());
			
			Rectangle constraint = (Rectangle)getConstraintFor(request);
			constraint.x = (constraint.x < 0) ? 0 : constraint.x;
			constraint.y = (constraint.y < 0) ? 0 : constraint.y;
			constraint.width = (constraint.width <= 0) ? 
					ServiceFigure.SERVICE_FIGURE_DEFWIDTH : constraint.width;
			constraint.height = (constraint.height <= 0) ? 
					ServiceFigure.SERVICE_FIGURE_DEFHEIGHT : constraint.height;
			cmd.setLayout(constraint);
			
			return cmd;
		}else if (request.getType() == REQ_CREATE && getHost() instanceof ServicePart) {
			EmployeeCreateCommand cmd = new EmployeeCreateCommand();
			
			cmd.setService(getHost().getModel());
			cmd.setEmployee(request.getNewObject());
			
			Rectangle constraint = (Rectangle)getConstraintFor(request);
			constraint.x = (constraint.x < 0) ? 0 : constraint.x;
			constraint.y = (constraint.y < 0) ? 0 : constraint.y;
			constraint.width = (constraint.width <=0 ) ? 
					EmployeeFigure.EMPLOYE_FIGURE_DEFWIDTH : constraint.width;
			constraint.height = (constraint.height <=0 ) ? 
					EmployeeFigure.EMPLOYE_FIGURE_DEFHEIGHT : constraint.height;
			cmd.setLayout(constraint);
			return cmd;
		}
		return null;
	}

}