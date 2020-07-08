package gef_command;
import org.eclipse.draw2d.geometry.Rectangle;

import gef_model.Employee;

//Œª÷√“∆∂Ø ≥∑œ˙
public class EmployeeChangeLayoutCommand extends AbstractLayoutCommand {
	
	private Employee model;
	private Rectangle layout;
	private Rectangle oldLayout;
	
	@Override
	public void execute() {
		model.setLayout(layout);
	}

	@Override
	public void setConstraint(Rectangle rect) {
		// TODO Auto-generated method stub
		this.layout = rect;
	}

	@Override
	public void setModel(Object model) {
		// TODO Auto-generated method stub
		this.model = (Employee)model;
		this.oldLayout = ((Employee)model).getLayout();
	}
	
	@Override
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
	

}