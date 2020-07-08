package com.jtauber.river.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.jtauber.river.model.Diagram;

public class DiagramTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

   public DiagramTreeEditPart(Object model) {
      super(model);
   }

   public void activate() {
      super.activate();
      ((Diagram) getModel()).addPropertyChangeListener(this);
   }

   public void deactivate() {
      ((Diagram) getModel()).removePropertyChangeListener(this);
      super.deactivate();
   }

   protected List getModelChildren() {
      return ((Diagram) getModel()).getNodes();
   }

   public void propertyChange(PropertyChangeEvent change) {
      refreshChildren();
   }
}