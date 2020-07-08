/*
 * Created on 2005-1-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

import com.example.tools.ElementFactory;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DiagramTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

    /**
     * @param viewer
     */
    public DiagramTemplateTransferDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    protected CreationFactory getFactory(Object template) {
        return new ElementFactory(template);
    }
}