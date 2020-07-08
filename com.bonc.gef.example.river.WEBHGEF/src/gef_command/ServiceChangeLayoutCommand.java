package gef_command;
import org.eclipse.draw2d.geometry.Rectangle;

import gef_model.Service;
//Œª÷√“∆∂Ø
public class ServiceChangeLayoutCommand extends AbstractLayoutCommand {
	
	private Service model;
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
		this.model = (Service)model;
		this.oldLayout = ((Service)model).getLayout();
	}
	
	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}