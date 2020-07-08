package gef_myeditor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

//´ó¸ÙÊ÷ ÓÒ¼ü²Ëµ¥
public class AppContextMenuProvider extends ContextMenuProvider {
	
	private ActionRegistry actionRegestry;

	public AppContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		// TODO Auto-generated constructor stub
		setActionRegistry(registry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		// TODO Auto-generated method stub
		IAction action;
		
		GEFActionConstants.addStandardActionGroups(menu);
		
		action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = getActionRegistry().getAction(ActionFactory.RENAME.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

	}
	
	private ActionRegistry getActionRegistry() {
		return actionRegestry;
	}
	
	private void setActionRegistry(ActionRegistry registry) {
		actionRegestry = registry;
	}

}