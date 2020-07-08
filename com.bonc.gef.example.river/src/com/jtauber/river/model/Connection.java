package com.jtauber.river.model;

public class Connection extends Element {

   private Node target;
   private Node source;

   public Connection(Node source, Node target) {
      this.source = source;
      this.target = target;

      source.addOutput(this);
      target.addInput(this);
   }

   public Node getTarget() {
      return this.target;
   }
}