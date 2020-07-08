package com.jtauber.river.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

import com.jtauber.river.model.Node;
import com.jtauber.river.policies.NodeEditPolicy;

public class NodeTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {

   public NodeTreeEditPart(Object model) {
      super(model);
   }

   public void activate() {
      super.activate();
      ((Node) getModel()).addPropertyChangeListener(this);
   }

   public void deactivate() {
      ((Node) getModel()).removePropertyChangeListener(this);
      super.deactivate();
   }

   protected List getModelChildren() {
      return Collections.EMPTY_LIST;
   }

   public void propertyChange(PropertyChangeEvent change) {
      refreshVisuals();
   }

   protected void createEditPolicies() {
      installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeEditPolicy());
   }

   protected void refreshVisuals() {
      setWidgetText(((Node) getModel()).getName());
   }
}