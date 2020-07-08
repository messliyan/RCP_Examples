package gef_command;
import org.eclipse.gef.commands.Command;

import gef_model.Node;

//É¾³ý ³·µô
public class DeleteCommand extends Command {
	private Node model;
	private Node parentModel;
	@Override
	public void execute() {
		this.parentModel.removeChild(model);
	}
	
	public void setModel(Object model) {
		this.model = (Node)model;
	}
	
	public void setParentModel(Object model) {
		parentModel = (Node)model;
	}
	@Override
	public void undo() {
		this.parentModel.addChild(model);
	}

}