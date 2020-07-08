package com.jtauber.river.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.jtauber.river.commands.RenameNodeCommand;
import com.jtauber.river.figures.NodeFigure;
import com.jtauber.river.model.Node;

public class NodeDirectEditPolicy extends DirectEditPolicy {


   //------------------------------------------------------------------------
   // Abstract methods from DirectEditPolicy

   protected Command getDirectEditCommand(DirectEditRequest request) {
      RenameNodeCommand cmd = new RenameNodeCommand();
      cmd.setNode((Node) getHost().getModel());
      cmd.setName((String) request.getCellEditor().getValue());
      return cmd;
   }

   protected void showCurrentEditValue(DirectEditRequest request) {
      String value = (String) request.getCellEditor().getValue();
      ((NodeFigure) getHostFigure()).setName(value);
   }
}