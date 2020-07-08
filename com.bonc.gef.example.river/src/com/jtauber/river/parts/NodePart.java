package com.jtauber.river.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.jtauber.river.figures.NodeFigure;
import com.jtauber.river.model.Node;
import com.jtauber.river.policies.NodeDirectEditPolicy;
import com.jtauber.river.policies.NodeEditPolicy;
import com.jtauber.river.policies.NodeGraphicalNodeEditPolicy;
import com.jtauber.river.tools.NodeCellEditorLocator;
import com.jtauber.river.tools.NodeDirectEditManager;

public class NodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {

   protected DirectEditManager manager;


   //------------------------------------------------------------------------
   // Overridden from AbstractGraphicalEditPart

   // override activate to register with the model for property changes
   public void activate() {
      if (isActive()) {
         return;
      }
      super.activate();
      ((Node) getModel()).addPropertyChangeListener(this);
   }

   // override deactivate to deregister with the model
   public void deactivate() {
      if (!isActive()) {
         return;
      }
      super.deactivate();
      ((Node) getModel()).removePropertyChangeListener(this);
   }

   protected List getModelSourceConnections() {
      return ((Node) this.getModel()).getOutgoingConnections();
   }

   protected List getModelTargetConnections() {
      return ((Node) this.getModel()).getIncomingConnections();
   }


   //------------------------------------------------------------------------
   // Overridden from AbstractEditPart

   protected void refreshVisuals() {
      Node node = (Node) this.getModel();
      Point loc = node.getLocation();
      Dimension size = new Dimension(150, 40);
      Rectangle rectangle = new Rectangle(loc, size);

      ((NodeFigure) this.getFigure()).setName(((Node) this.getModel()).getName());

      // tells the parent part (in this case DiagramPart) that this part
      // and its figure are to be constrained to the given rectangle
       ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
   }

   public void performRequest(Request request) {
      if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
         if (manager == null) {
            NodeFigure nodeFigure = (NodeFigure) getFigure();
            manager = new NodeDirectEditManager(this, TextCellEditor.class, new NodeCellEditorLocator(nodeFigure), nodeFigure);
         }
         manager.show();
      }
   }


   //------------------------------------------------------------------------
   // Abstract methods from AbstractGraphicalEditPart

   protected IFigure createFigure() {
      return new NodeFigure();
   }


   //------------------------------------------------------------------------
   // Abstract methods from AbstractEditPart

   protected void createEditPolicies() {
      installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeEditPolicy());
      installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeGraphicalNodeEditPolicy());
      installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new NodeDirectEditPolicy());
   }


   //------------------------------------------------------------------------
   // Abstract methods from PropertyChangeListener

   public void propertyChange(PropertyChangeEvent changeEvent) {
      if (changeEvent.getPropertyName().equals(Node.LOCATION)) {
         refreshVisuals();
      }
      else if (changeEvent.getPropertyName().equals(Node.NAME)) {
         refreshVisuals();
      }
      else if (changeEvent.getPropertyName().equals(Node.INPUTS)) {
         refreshTargetConnections();
      }
      else if (changeEvent.getPropertyName().equals(Node.OUTPUTS)) {
         refreshSourceConnections();
      }
   }


   //------------------------------------------------------------------------
   // Abstract methods from NodeEditPart

   public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
      return new NodeConnectionAnchor(getFigure());
   }

   public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
      return new NodeConnectionAnchor(getFigure());
   }

   public ConnectionAnchor getSourceConnectionAnchor(Request request) {
      return new NodeConnectionAnchor(getFigure());
   }

   public ConnectionAnchor getTargetConnectionAnchor(Request request) {
      return new NodeConnectionAnchor(getFigure());
   }


   //------------------------------------------------------------------------
   // Inner Classes

   class NodeConnectionAnchor extends ChopboxAnchor {

      public NodeConnectionAnchor(IFigure figure) {
         super(figure);
      }
   }
}