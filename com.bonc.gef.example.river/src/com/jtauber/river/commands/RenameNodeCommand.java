package com.jtauber.river.commands;

import org.eclipse.gef.commands.Command;

import com.jtauber.river.model.Node;

public class RenameNodeCommand extends Command {

   private Node node;
   private String newName;
   private String oldName;


   // setters

   public void setName(String name) {
      this.newName = name;
   }

   public void setNode(Node node) {
      this.node = node;
   }


   //------------------------------------------------------------------------
   // Overridden from Command

   public String getLabel() {
      return "Rename Node";
   }

   public void execute() {
      oldName = this.node.getName();
      this.node.setName(newName);
   }

   public void undo() {
      this.node.setName(oldName);
   }

   public void redo() {
      this.node.setName(newName);
   }
}