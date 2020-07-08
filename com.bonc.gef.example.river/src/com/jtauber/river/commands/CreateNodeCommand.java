package com.jtauber.river.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.jtauber.river.model.Diagram;
import com.jtauber.river.model.Node;

public class CreateNodeCommand extends Command {

   private Diagram diagram;
   private Node node;
   private Point location;


   // setters
   
   public void setDiagram(Diagram diagram) {
      this.diagram = diagram;
   }

   public void setNode(Node node) {
      this.node = node;
   }

   public void setLocation(Point location) {
      this.location = location;
   }


   //------------------------------------------------------------------------
   // Overridden from Command

   public String getLabel() {
      return "Create Node";
   }

   public void execute() {
      if (this.location != null) {
         this.node.setLocation(this.location);
      }
      this.diagram.addNode(this.node);
   }

   public void undo() {
      this.diagram.removeNode(this.node);
   }

   public void redo() {
      this.execute();
   }
}