package com.jtauber.river.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Node extends Element implements IPropertySource {

   // serialization version
   static final long serialVersionUID = 4;

   // properties
   public static final String LOCATION = "location";
   public static final String NAME = "name";
   public static final String INPUTS = "inputs";
   public static final String OUTPUTS = "outputs";

   // descriptors for property sheet
   protected static IPropertyDescriptor[] descriptors;
   static {
      descriptors = new IPropertyDescriptor[] {
         new TextPropertyDescriptor(NAME, "Name")};
   }

   // actual fields
   protected Point location = new Point(0, 0);
   protected String name = "Node";
   private List outputs = new ArrayList(5);
   private List inputs = new ArrayList(5);


   public void setLocation(Point p) {
      if (this.location.equals(p)) {
         return;
      } 
      this.location = p;
      firePropertyChange(LOCATION, null, p);
   }

   public Point getLocation() {
      return location;
   }
   
   public void setName(String name) {
      if (this.name.equals(name)) {
         return;
      }
      this.name = name;
      firePropertyChange(NAME, null, name);
   }
   
   public String getName() {
      return name;
   }

   public void addInput(Connection connection) {
      this.inputs.add(connection);
      fireStructureChange(INPUTS, connection);
   }

   public void addOutput(Connection connection) {
      this.outputs.add(connection);
      fireStructureChange(OUTPUTS, connection);
   }

   public List getIncomingConnections() {
      return this.inputs;
   }

   public List getOutgoingConnections() {
      return this.outputs;
   }

   public void removeInput(Connection connection) {
      this.inputs.remove(connection);
      fireStructureChange(INPUTS, connection);
   }

   public void removeOutput(Connection connection) {
      this.outputs.remove(connection);
      fireStructureChange(OUTPUTS, connection);
   }


   //------------------------------------------------------------------------
   // Abstract methods from IPropertySource

   public Object getEditableValue() {
      return this;
   }

   public IPropertyDescriptor[] getPropertyDescriptors() {
      return descriptors;
   }

   public Object getPropertyValue(Object id) {
      if (NAME.equals(id)) {
         return getName();
      }
      else {
         return null;
      }
   }

   public boolean isPropertySet(Object id) {
      return true;
   }

   public void resetPropertyValue(Object id) {
      // do nothing
   }

   public void setPropertyValue(Object id, Object value) {
      if (id == NAME) {
         setName((String)value);
      }
   }
}