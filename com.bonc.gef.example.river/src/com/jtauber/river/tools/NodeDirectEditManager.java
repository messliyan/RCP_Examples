package com.jtauber.river.tools;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Text;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

import com.jtauber.river.figures.NodeFigure;

public class NodeDirectEditManager extends DirectEditManager {

   Font scaledFont;
   protected VerifyListener verifyListener;
   protected NodeFigure nodeFigure;

   public NodeDirectEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator, NodeFigure nodeFigure) {
      super(source, editorType, locator);
      this.nodeFigure = nodeFigure;
   }


   //------------------------------------------------------------------------
   // Overridden methods from DirectEditManager

   protected void bringDown() {
      Font disposeFont = this.scaledFont;
      this.scaledFont = null;
      super.bringDown();
      if (disposeFont != null) {
         disposeFont.dispose();
      }
   }

   protected void unhookListeners() {
      super.unhookListeners();
      Text text = (Text) getCellEditor().getControl();
      text.removeVerifyListener(verifyListener);
      verifyListener = null;
   }


   //------------------------------------------------------------------------
   // Abstract methods from DirectEditManager

   protected void initCellEditor() {

      verifyListener = new VerifyListener() {
         public void verifyText(VerifyEvent event) {
            Text text = (Text) getCellEditor().getControl();
            String oldText = text.getText();
            String leftText = oldText.substring(0, event.start);
            String rightText = oldText.substring(event.end, oldText.length());
            GC gc = new GC(text);
            Point size = gc.textExtent(leftText + event.text + rightText);
            gc.dispose();
            if (size.x != 0) {
               size = text.computeSize(size.x, SWT.DEFAULT);
            }
            getCellEditor().getControl().setSize(size.x, size.y);
         }
      };

      Text text = (Text) getCellEditor().getControl();
      text.addVerifyListener(verifyListener);

      getCellEditor().setValue(this.nodeFigure.getText());
      IFigure figure = ((GraphicalEditPart) getEditPart()).getFigure();
      scaledFont = figure.getFont();
      FontData data = scaledFont.getFontData()[0];
      Dimension fontSize = new Dimension(0, data.getHeight());
      data.setHeight(fontSize.height);
      scaledFont = new Font(null, data);

      text.setFont(scaledFont);
      text.selectAll();
   }
}