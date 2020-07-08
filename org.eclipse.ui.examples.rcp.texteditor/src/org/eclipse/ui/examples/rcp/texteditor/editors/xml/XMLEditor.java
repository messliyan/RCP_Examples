package org.eclipse.ui.examples.rcp.texteditor.editors.xml;

import org.eclipse.ui.examples.rcp.texteditor.editors.SimpleEditor;
import org.eclipse.ui.examples.rcp.texteditor.editors.common.ColorManager;
import org.eclipse.ui.texteditor.ITextEditorExtension3;


public class XMLEditor extends SimpleEditor {

	private ColorManager colorManager;

	@Override
    protected void internal_init() {
		configureInsertMode(ITextEditorExtension3.SMART_INSERT, false);
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	
	@Override
    public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
