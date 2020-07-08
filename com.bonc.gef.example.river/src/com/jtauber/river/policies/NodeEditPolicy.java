package com.jtauber.river.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;

import org.eclipse.gef.requests.GroupRequest;

import com.jtauber.river.commands.DeleteNodeCommand;
import com.jtauber.river.model.Diagram;
import com.jtauber.river.model.Node;

public class NodeEditPolicy extends ComponentEditPolicy {

   //------------------------------------------------------------------------
   // Overridden from ComponentEditPolicy

   protected Command createDeleteCommand(GroupRequest request) {
      Object parent = getHost().getParent().getModel();
      DeleteNodeCommand deleteCommand = new DeleteNodeCommand();
      deleteCommand.setDiagram((Diagram) parent);
      deleteCommand.setNode((Node) getHost().getModel());
      return deleteCommand;
   }
}