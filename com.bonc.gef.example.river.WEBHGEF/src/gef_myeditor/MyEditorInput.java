package gef_myeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	public String name = null;

	public MyEditorInput() {
		name="MyEditorInput";
	}
	
	public MyEditorInput(String name) {
		this.name = name;
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO �Զ����ɵķ������
		return null;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof MyEditorInput))
			return false;
		return ((MyEditorInput)o).getName().equals(getName());
	}
	
	@Override
	public boolean exists() {
		// TODO �Զ����ɵķ������
		return (this.name != null);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO �Զ����ɵķ������
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		// TODO �Զ����ɵķ������
		return this.name;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO �Զ����ɵķ������
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO �Զ����ɵķ������
		return this.name;
	}

}
