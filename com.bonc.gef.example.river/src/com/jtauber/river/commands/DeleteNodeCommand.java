package com.jtauber.river.commands;

import org.eclipse.gef.commands.Command;

import com.jtauber.river.model.Diagram;
import com.jtauber.river.model.Node;

public class DeleteNodeCommand extends Command {

   private Diagram diagram;
   private Node node;
   private int index;


   // setters
   
   public void setDiagram(Diagram diagram) {
      this.diagram = diagram;
   }

   public void setNode(Node node) {
      this.node = node;
   }


   //------------------------------------------------------------------------
   // Overridden from Command

   public String getLabel() {
      return "Delete Node";
   }

   public void execute() {
      this.index = this.diagram.getNodes().indexOf(this.node);
      this.diagram.removeNode(node);
   }

   public void undo() {
      this.diagram.addNode(node);
   }

   public void redo() {
      this.execute();
   }
}