package com.jtauber.river.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.jtauber.river.model.Connection;
import com.jtauber.river.model.Diagram;
import com.jtauber.river.model.Node;

// an EditPartFactory knows how to make an EditPart from a model object
// in the context of a parent EditPart

public class PartFactory implements EditPartFactory {

   //------------------------------------------------------------------------
   // Abstract methods from EditPartFactory

   public EditPart createEditPart(EditPart context, Object model) {
      EditPart part = null;

      if (model instanceof Diagram) {
         part = new DiagramPart();
      }
      else if (model instanceof Node) {
         part = new NodePart();
      }
      else if (model instanceof Connection) {
         part = new ConnectionPart();
      }
      else {
         return null;
      }
      // tell the newly created part about the model object         
      part.setModel(model);
      
      return part;      
   }
}