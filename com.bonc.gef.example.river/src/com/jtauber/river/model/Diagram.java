package com.jtauber.river.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Diagram extends Element {

   // serialization version
   static final long serialVersionUID = 1;

   // properties
   public static String NODES = "nodes";

   // actual fields
   protected List nodes = new ArrayList();

   public void addNode(Node node) {
      nodes.add(node);
      fireStructureChange(NODES, nodes);
   }

   public void removeNode(Node node) {
      nodes.remove(node);
      fireStructureChange(NODES, nodes);
   }
   
   public List getNodes() {
      return this.nodes;
   }


   //------------------------------------------------------------------------
   // I/O
   
   public InputStream getAsStream() throws IOException {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(os);
      out.writeObject(this);
      out.close();
      InputStream istream = new ByteArrayInputStream(os.toByteArray());
      os.close();
      return istream;
   }
   
   public static Diagram makeFromStream(InputStream istream) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = new ObjectInputStream(istream);
      Diagram diagram = (Diagram) ois.readObject();
      ois.close();
      return diagram;
   }
}