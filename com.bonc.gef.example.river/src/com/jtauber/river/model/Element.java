package com.jtauber.river.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

// abstract base class of elements in the model

abstract public class Element implements Cloneable, Serializable {

   // serialization version
   static final long serialVersionUID = 1;

   // property change listeners
   transient protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      listeners.addPropertyChangeListener(listener);
   }

   protected void firePropertyChange(String prop, Object old, Object newValue) {
      listeners.firePropertyChange(prop, old, newValue);
   }

   protected void fireStructureChange(String prop, Object child) {
      listeners.firePropertyChange(prop, null, child);
   }

   // implemented in order to create listeners field
   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      listeners = new PropertyChangeSupport(this);
   }

   public void removePropertyChangeListener(PropertyChangeListener l) {
      listeners.removePropertyChangeListener(l);
   }

}