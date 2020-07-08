package com.jtauber.river.ui;

import java.io.InputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.jtauber.river.dnd.DiagramTemplateTransferDropTargetListener;
import com.jtauber.river.model.Diagram;
import com.jtauber.river.parts.PartFactory;
import com.jtauber.river.parts.TreePartFactory;

public class DiagramEditor extends GraphicalEditorWithPalette {

   private PaletteRoot paletteRoot;
   private KeyHandler sharedKeyHandler;

   private boolean savePreviouslyNeeded;

   private Diagram diagram = new Diagram();

   public DiagramEditor() {
      // an EditDomain is a "session" of editing which contains things
      // like the CommandStack
      setEditDomain(new DefaultEditDomain(this));
   }

   public Diagram getDiagram() {
      return this.diagram;
   }

   protected KeyHandler getCommonKeyHandler(){
      if (sharedKeyHandler == null){
         sharedKeyHandler = new KeyHandler();
         sharedKeyHandler.put(
            KeyStroke.getPressed(SWT.DEL, 127, 0),
            getActionRegistry().getAction(GEFActionConstants.DELETE));
      }
      return sharedKeyHandler;
   }

   //------------------------------------------------------------------------
   // Overridden from GraphicalEditor

   protected void configureGraphicalViewer() {
      super.configureGraphicalViewer();
      getGraphicalViewer().setRootEditPart(new ScalableFreeformRootEditPart());

      // set the factory to use for creating EditParts for elements in the model
      getGraphicalViewer().setEditPartFactory(new PartFactory());

      getGraphicalViewer().setKeyHandler(new GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(getCommonKeyHandler()));
   }

   public void commandStackChanged(EventObject event) {
      if (isDirty()) {
         if (!this.savePreviouslyNeeded) {
            this.savePreviouslyNeeded = true;
            firePropertyChange(IEditorPart.PROP_DIRTY);
         }
      }
      else {
         savePreviouslyNeeded = false;
         firePropertyChange(IEditorPart.PROP_DIRTY);
      }
      super.commandStackChanged(event);
   }

   public Object getAdapter(Class type){
      if (type == IContentOutlinePage.class)
         return new OutlinePage();
      return super.getAdapter(type);
   }

   //------------------------------------------------------------------------
   // Overridden from EditorPart

   protected void setInput(IEditorInput input) {
      super.setInput(input);

//      IFile file = ((IFileEditorInput) input).getFile();
//      try { // attempt to read from a file
//         InputStream istream = file.getContents(false);
//         diagram = Diagram.makeFromStream(istream);
//      }
//      catch (Exception e) { // but if there's an error, create a new diagram
//         e.printStackTrace();
//         diagram = new Diagram();
//      }
   }


   //------------------------------------------------------------------------
   // Abstract methods from GraphicalEditor

   protected void initializeGraphicalViewer() {
      // this uses the PartFactory set in configureGraphicalViewer
      // to create an EditPart for the diagram and sets it as the
      // content for the viewer
      getGraphicalViewer().setContents(this.diagram);
      
      getGraphicalViewer().addDropTargetListener(new DiagramTemplateTransferDropTargetListener(getGraphicalViewer()));
   }


   //------------------------------------------------------------------------
   // Overridden methods from GraphicalEditorWithPalette

   protected void initializePaletteViewer() {
      super.initializePaletteViewer();
      getPaletteViewer().addDragSourceListener(
         new TemplateTransferDragSourceListener(getPaletteViewer()));
   }


   //------------------------------------------------------------------------
   // Abstract methods from EditorPart

   public void doSave(IProgressMonitor monitor) {
	   	System.out.println("保存");
   }

   public void doSaveAs() {
      System.out.println("保存");
   }

   public void gotoMarker(IMarker marker) {
   }

   public boolean isDirty() {
      // rely on the command stack to determine dirty flag
      return getCommandStack().isDirty();
   }

   public boolean isSaveAsAllowed() {
      // allow Save As
      return true;
   }


   //------------------------------------------------------------------------
   // Abstract methods from GraphicalEditorWithPalette

   protected PaletteRoot getPaletteRoot() {
      if (this.paletteRoot == null) {
         this.paletteRoot = PaletteFactory.createPalette();
      }
      return this.paletteRoot;
   }


   //========================================================================
   // Inner Classes

   public class OutlinePage extends ContentOutlinePage {

      private PageBook pageBook;
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
         pageBook = new PageBook(parent, SWT.NONE);
         outline = getViewer().createControl(pageBook);
         pageBook.showPage(outline);
         getSelectionSynchronizer().addViewer(getViewer());
         getViewer().setEditDomain(getEditDomain());
         getViewer().setEditPartFactory(new TreePartFactory());
         getViewer().setKeyHandler(getCommonKeyHandler());
         getViewer().setContents(getDiagram());
      }

      public Control getControl() {
         return this.pageBook;
      }

      public void dispose() {
         getSelectionSynchronizer().removeViewer(getViewer());
         super.dispose();
      }
   }
}