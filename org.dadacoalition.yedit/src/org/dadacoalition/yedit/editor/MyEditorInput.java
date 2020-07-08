package org.dadacoalition.yedit.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	public String name = null;
	public MyEditorInput() {
		name="org.dadacoalition.yedit";
	}
	public MyEditorInput(String name) {
		this.name = name;
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		
		return null;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof MyEditorInput))
			return false;
		return ((MyEditorInput)o).getName().equals(getName());
	}
	
	@Override
	public boolean exists() {
		
		return (this.name != null);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public IPersistableElement getPersistable() {
		
		return null;
	}

	@Override
	public String getToolTipText() {
		
		return this.name;
	}

}
