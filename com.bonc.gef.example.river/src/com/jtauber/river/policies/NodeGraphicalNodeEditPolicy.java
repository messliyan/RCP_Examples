package com.jtauber.river.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.jtauber.river.commands.ConnectionCreateCommand;
import com.jtauber.river.model.Node;

public class NodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

   //------------------------------------------------------------------------
   // Abstract methods from GraphicalNodeEditPolicy

   protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
      ConnectionCreateCommand command = (ConnectionCreateCommand) request.getStartCommand();
      command.setTarget((Node) getHost().getModel());
      return command;
   }

   protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
      ConnectionCreateCommand command = new ConnectionCreateCommand();
      command.setSource((Node) getHost().getModel());
      request.setStartCommand(command);
      return command;
   }

   protected Command getReconnectTargetCommand(ReconnectRequest request) {
      return null;
   }

   protected Command getReconnectSourceCommand(ReconnectRequest request) {
      return null;
   }
}