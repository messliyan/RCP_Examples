package com.jtauber.river.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.jtauber.river.model.Diagram;
import com.jtauber.river.policies.DiagramLayoutEditPolicy;

public class DiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

   //------------------------------------------------------------------------
   // Overridden methods from AbstractEditPart

   protected List getModelChildren() {
      return ((Diagram) this.getModel()).getNodes();
   }


   //------------------------------------------------------------------------
   // Overridden from AbstractGraphicalEditPart

   // override activate to register with the model for property changes
   public void activate() {
      if (isActive()) {
         return;
      }
      super.activate();
      ((Diagram) getModel()).addPropertyChangeListener(this);
   }

   // override deactivate to deregister with the model
   public void deactivate() {
      if (!isActive()) {
         return;
      }
      super.deactivate();
      ((Diagram) getModel()).removePropertyChangeListener(this);
   }


   //------------------------------------------------------------------------
   // Abstract methods from AbstractGraphicalEditPart

   // create the figure corresponding to this part
   protected IFigure createFigure() {
      Figure figure = new FreeformLayer();
      figure.setLayoutManager(new FreeformLayout());
      return figure;
   }


   //------------------------------------------------------------------------
   // Abstract methods from AbstractEditPart

   protected void createEditPolicies() {
      // install a custom layout policy that handles dragging nodes around
      installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutEditPolicy());
   }


   //------------------------------------------------------------------------
   // Abstract methods from PropertyChangeListener

   // catch property changes
   public void propertyChange(PropertyChangeEvent event) {
      String prop = event.getPropertyName();
      if (Diagram.NODES.equals(prop)) {
         refreshChildren();
      }
   }
}