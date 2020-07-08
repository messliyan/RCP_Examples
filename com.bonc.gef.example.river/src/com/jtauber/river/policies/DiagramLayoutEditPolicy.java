package com.jtauber.river.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.jtauber.river.commands.CreateNodeCommand;
import com.jtauber.river.commands.MoveNodeCommand;
import com.jtauber.river.model.Diagram;
import com.jtauber.river.model.Node;

// XYLayoutEditPolicy is a policy that constrains figures by a location and size
// This extends it with the capability to change the location

public class DiagramLayoutEditPolicy extends XYLayoutEditPolicy {

   //------------------------------------------------------------------------
   // Overridden from ConstrainedLayoutEditPolicy
   
   protected EditPolicy createChildEditPolicy(EditPart child) {
      // we need to do this because the default is resizable
      return new NonResizableEditPolicy();
   }


   //------------------------------------------------------------------------
   // Abstract methods from ConstrainedLayoutEditPolicy

   protected Command createAddCommand(EditPart child, Object constraint) {
      return null; // no support for adding
   }

   protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
      // return a command to move the part to the location given by the constraint
      MoveNodeCommand locationCommand = new MoveNodeCommand();
      locationCommand.setNode((Node) child.getModel());
      locationCommand.setLocation(((Rectangle) constraint).getLocation());
      return locationCommand;
   }


   //------------------------------------------------------------------------
   // Abstract methods from LayoutEditPolicy

   protected Command getCreateCommand(CreateRequest request) {
      CreateNodeCommand command = new CreateNodeCommand();
      command.setDiagram((Diagram) getHost().getModel());
      command.setNode((Node) request.getNewObject());
      Rectangle constraint = (Rectangle)getConstraintFor(request);
      command.setLocation(constraint.getLocation());
      return command;
   }

   protected Command getDeleteDependantCommand(Request request) {
      return null; // no support for deleting a dependant
   }
}