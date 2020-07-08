package gef_Palette;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import gef_model.Enterprise;
import gef_model.Service;


public class ServiceCreateCommand extends Command {
	private Enterprise en;
	private Service srv;
	
	public ServiceCreateCommand() {
		super();
		en = null;
		srv = null;
	}
	
	public void setService(Object s) {
		if(s instanceof Service) {
			this.srv = (Service)s;
		}
	}
	
	public void setEnterprise(Object e ) {
		if(e instanceof Enterprise) {
			this.en = (Enterprise)e;
		}
	}
	
	public void setLayout(Rectangle r) {
		if(srv == null) {
			return ;
		}
		srv.setLayout(r);
	}
	
	@Override
	public boolean canExecute() {
		if(srv == null || en == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public void execute() {
		en.addChild(srv);
	}
	
	@Override
	public boolean canUndo() {
		if(en == null || srv == null) {
			return false;
		}
		return en.contains(srv);
	}
	
	@Override
	public void undo() {
		en.removeChild(srv);
	}

}