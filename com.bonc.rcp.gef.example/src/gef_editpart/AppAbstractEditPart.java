package gef_editpart;
//import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
//import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import gef_model.Node;

//数据绑定  属性页
public abstract class AppAbstractEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener {

//	@Override
//	protected IFigure createFigure() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected void createEditPolicies() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void propertyChange(PropertyChangeEvent evt) {
//		// TODO Auto-generated method stub
//
//	}
	
	public void activate() {
		super.activate();
		((Node)getModel()).addPropertyChangeListener(this);
	}
	
	public void deactivate() {
		super.deactivate();
		((Node)getModel()).removePropertyChangeListener(this);
	}
	//属性页 处理图形元素的双击
	public void performRequest(Request req) {
		if(req.getType().equals(RequestConstants.REQ_OPEN)) {
			try {
				IWorkbenchPage page = 
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.showView(IPageLayout.ID_PROP_SHEET);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
}