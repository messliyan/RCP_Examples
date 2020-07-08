/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.example.model.Diagram;
import com.example.model.Node;


/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreePartFactory implements EditPartFactory{

    public EditPart createEditPart(EditPart context, Object model) {
        if (model instanceof Diagram) {
            return new DiagramTreeEditPart(model);
         }
         else if (model instanceof Node) {
            return new NodeTreeEditPart(model);
         }
         else {
            return null;
         }
    }
}
