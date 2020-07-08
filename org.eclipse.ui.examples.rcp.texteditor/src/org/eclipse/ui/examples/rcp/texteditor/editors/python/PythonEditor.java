package org.eclipse.ui.examples.rcp.texteditor.editors.python;

import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IVerticalRuler;

import org.eclipse.ui.examples.rcp.texteditor.editors.SimpleEditor;
import org.eclipse.ui.examples.rcp.texteditor.editors.common.ColorManager;
import org.eclipse.ui.texteditor.ITextEditorExtension3;
import org.eclipse.ui.texteditor.rulers.IColumnSupport;
import org.eclipse.ui.texteditor.rulers.RulerColumnRegistry;


public class PythonEditor extends SimpleEditor {

	private ColorManager colorManager;

	@Override
    protected void internal_init() {
		configureInsertMode(ITextEditorExtension3.SMART_INSERT, false);
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new PythonConfiguration(colorManager));
		setDocumentProvider(new PythonDocumentProvider());
	}
	
	@Override
    public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
	
	/*
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createColumnSupport()
	 * @since 3.3
	 */
	@Override
	protected IColumnSupport createColumnSupport() {
		return new ColumnSupport(this, RulerColumnRegistry.getDefault());
	}
	
	/*
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createVerticalRuler()
	 * @since 3.3
	 */
	@Override
	protected IVerticalRuler createVerticalRuler() {
		return new CompositeRuler();
	}
	
}
