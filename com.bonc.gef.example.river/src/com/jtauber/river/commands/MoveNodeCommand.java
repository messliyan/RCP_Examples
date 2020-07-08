package com.jtauber.river.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.jtauber.river.model.Node;

public class MoveNodeCommand extends Command {

   private Node node;
   private Point newPos;
   private Point oldPos;


   // setters

   public void setLocation(Point p) {
      this.newPos = p;
   }

   public void setNode(Node node) {
      this.node = node;
   }


   //------------------------------------------------------------------------
   // Overridden from Command

   public String getLabel() {
      return "Move Node";
   }

   public void execute() {
      oldPos = this.node.getLocation();
      this.node.setLocation(newPos);
   }

   public void undo() {
      this.node.setLocation(oldPos);
   }

   public void redo() {
      this.node.setLocation(newPos);
   }
}