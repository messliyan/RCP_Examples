package gef_myeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	public String name = null;
	
	public MyEditorInput(String name) {
		this.name = name;
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof MyEditorInput))
			return false;
		return ((MyEditorInput)o).getName().equals(getName());
	}
	
	@Override
	public boolean exists() {
		// TODO 自动生成的方法存根
		return (this.name != null);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO 自动生成的方法存根
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		// TODO 自动生成的方法存根
		return this.name;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO 自动生成的方法存根
		return this.name;
	}

}
