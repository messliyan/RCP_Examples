package gef_Palette;
import org.eclipse.gef.requests.CreationFactory;

import gef_model.Employee;
import gef_model.Service;


public class NodeCreationFactory implements CreationFactory {
	
	private Class<?> template;
	
	public NodeCreationFactory(Class<?> t) {
		this.template = t;
	}

	@Override
	public Object getNewObject() {
		// TODO Auto-generated method stub
		if(template == null) {
			return null;
		}
		if(template == Service.class) {
			Service srv = new Service();
			srv.setEtage(42);
			srv.setName("¿Í·¿");
			return srv;
		}else if (template == Employee.class) {
			Employee emp = new Employee();
			emp.setPrenom("×£");
			emp.setName("ÎÞË«");
			return emp;
		}
		return null;
	
	}

	@Override
	public Object getObjectType() {
		// TODO Auto-generated method stub
		return template;
	}

}