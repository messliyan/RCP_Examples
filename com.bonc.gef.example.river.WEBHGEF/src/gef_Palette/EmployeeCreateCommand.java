package gef_Palette;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import gef_model.Employee;
import gef_model.Service;


public class EmployeeCreateCommand extends Command {
	
	private Service srv;
	private Employee emp;
	
	public EmployeeCreateCommand() {
		super();
		srv = null;
		emp = null;
	}
	
	public void setService(Object s) {
		if(s instanceof Service) {
			this.srv = (Service)s;
		}
	}
	
	public void setEmployee(Object e) {
		if(e instanceof Employee) {
			this.emp = (Employee)e;
		}
	}
	
	public void setLayout(Rectangle r) {
		if(emp == null) return;
		emp.setLayout(r);
	}
	
	@Override
	public boolean canExecute() {
		if(srv == null || emp == null)
			return false;
		return true;
	}
	
	@Override
	public void execute() {
		srv.addChild(emp);
	}
	
	@Override
	public boolean canUndo() {
		if(srv == null || emp == null)
			return false;
		return srv.contains(emp);
	}
	
	@Override
	public void undo() {
		srv.removeChild(emp);
	}

}