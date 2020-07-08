package gef_editorpart_tree;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import gef_model.Node;

//����� ���ݰ�
public abstract class AppAbstractTreeEditPart extends AbstractTreeEditPart
		implements PropertyChangeListener {
	@Override
	public void activate() {
		super.activate();
		((Node)getModel()).addPropertyChangeListener(this);
	}
	@Override	
	public void deactivate() {
		((Node)getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
	
//	@Override
//	public void propertyChange(PropertyChangeEvent evt) {
//		// TODO Auto-generated method stub
//
//	}

}