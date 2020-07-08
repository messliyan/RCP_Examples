package com.jtauber.river.actions;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IWorkbenchActionConstants;

public class DiagramActionBarContributor extends ActionBarContributor {


   //------------------------------------------------------------------------
   // Abstract methods from GEF ActionBarContributor

   protected void buildActions() {
      addRetargetAction(new UndoRetargetAction());
      addRetargetAction(new RedoRetargetAction());
      addRetargetAction(new DeleteRetargetAction());
   }

   protected void declareGlobalActionKeys() {
      // do nothing
   }

   
   //------------------------------------------------------------------------
   // Abstract methods from Eclipse EditorActionBarContributor

   public void contributeToToolBar(IToolBarManager toolBarManager) {
      toolBarManager.add(getAction(IWorkbenchActionConstants.UNDO));
      toolBarManager.add(getAction(IWorkbenchActionConstants.REDO));
   }
}