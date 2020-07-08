/*
 * Created on 2005-1-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.example.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.example.dnd.DiagramTemplateTransferDropTargetListener;
import com.example.model.Diagram;
import com.example.parts.PartFactory;
import com.example.parts.TreePartFactory;
import com.example.tools.PaletteFactory;

/**
 * @author zhanghao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PracticeEditor extends GraphicalEditorWithPalette {

    private Diagram diagram = new Diagram();

    private PaletteRoot paletteRoot;

    public Diagram getDiagram() {
        return this.diagram;
    }

    public PracticeEditor() {
        setEditDomain(new DefaultEditDomain(this));
    }

    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        getGraphicalViewer().setRootEditPart(new ScalableFreeformRootEditPart());
        getGraphicalViewer().setEditPartFactory(new PartFactory());
    }

    protected void initializeGraphicalViewer() {
        getGraphicalViewer().setContents(this.diagram);
        getGraphicalViewer().addDropTargetListener(new DiagramTemplateTransferDropTargetListener(getGraphicalViewer()));
    }

    public void doSave(IProgressMonitor monitor) {
    }

    public void doSaveAs() {
    }

    public boolean isDirty() {
        return getCommandStack().isDirty();
    }

    public boolean isSaveAsAllowed() {
        return true;
    }

    protected void setInput(IEditorInput input) {
        super.setInput(input);

        //        IFile file = ((IFileEditorInput) input).getFile();
        diagram = new Diagram();
        //        try { // attempt to read from a file
        //            InputStream istream = file.getContents(false);
        //            diagram = Diagram.makeFromStream(istream);
        //        } catch (Exception e) { // but if there's an error, create a new diagram
        //            e.printStackTrace();
        //            diagram = new Diagram();
        //        }
    }

    public Object getAdapter(Class type) {
        if (type == IContentOutlinePage.class)
            return new OutlinePage();
        return super.getAdapter(type);
    }

    protected PaletteRoot getPaletteRoot() {
        if (this.paletteRoot == null) {
            this.paletteRoot = PaletteFactory.createPalette();
        }
        return this.paletteRoot;
    }

    protected void initializePaletteViewer() {
        super.initializePaletteViewer();
        getPaletteViewer().addDragSourceListener(new TemplateTransferDragSourceListener(getPaletteViewer()));
    }

    class OutlinePage extends ContentOutlinePage {
        //        private PageBook pageBook;

        private Control outline;

        public OutlinePage() {
            super(new TreeViewer());
        }

        public void init(IPageSite pageSite) {
            super.init(pageSite);
            ActionRegistry registry = getActionRegistry();
            IActionBars bars = pageSite.getActionBars();
            String id = IWorkbenchActionConstants.UNDO;
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = IWorkbenchActionConstants.REDO;
            bars.setGlobalActionHandler(id, registry.getAction(id));
            id = IWorkbenchActionConstants.DELETE;
            bars.setGlobalActionHandler(id, registry.getAction(id));
            bars.updateActionBars();
        }

        public void createControl(Composite parent) {
            //            pageBook = new PageBook(parent, SWT.NONE);
            //            outline = getViewer().createControl(pageBook);
            //            pageBook.showPage(outline);
            outline = getViewer().createControl(parent);
            getSelectionSynchronizer().addViewer(getViewer());
            getViewer().setEditDomain(getEditDomain());
            getViewer().setEditPartFactory(new TreePartFactory());
            //            getViewer().setKeyHandler(getCommonKeyHandler());
            getViewer().setContents(getDiagram());
        }

        public Control getControl() {
            //            return pageBook;
            return outline;
        }

        public void dispose() {
            getSelectionSynchronizer().removeViewer(getViewer());
            super.dispose();
        }
    }

}

