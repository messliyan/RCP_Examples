package org.eclipse.ui.examples.rcp.texteditor.editors.python;

import org.eclipse.swt.graphics.RGB;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import org.eclipse.ui.examples.rcp.texteditor.editors.common.ColorManager;


public class PythonConfiguration extends SourceViewerConfiguration {
	private ColorManager colorManager;

	public PythonConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	/*
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
	 * @since 3.2
	 */
	@Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return PyPartitionScanner.getTypes();
	}
	
	/*
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredDocumentPartitioning(org.eclipse.jface.text.source.ISourceViewer)
	 * @since 3.2
	 */
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return PyPartitionScanner.PYTHON_PARTITION_TYPE;
	}
	
	private ITokenScanner createSimpleTokenScanner(RGB color) {
		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setDefaultReturnToken(
			new Token(
				new TextAttribute(
					colorManager.getColor(color))));
		
		return scanner;
	}
	
	/*
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 * @since 3.2
	 */
	@Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		DefaultDamagerRepairer dr= new DefaultDamagerRepairer(createSimpleTokenScanner(IPythonColorConstants.MULTILINE_STRING));
		reconciler.setDamager(dr, PyPartitionScanner.PY_MULTILINE_STRING1);
		reconciler.setRepairer(dr, PyPartitionScanner.PY_MULTILINE_STRING1);
		reconciler.setDamager(dr, PyPartitionScanner.PY_MULTILINE_STRING2);
		reconciler.setRepairer(dr, PyPartitionScanner.PY_MULTILINE_STRING2);

		dr= new DefaultDamagerRepairer(createSimpleTokenScanner(IPythonColorConstants.DEFAULT));
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr= new DefaultDamagerRepairer(createSimpleTokenScanner(IPythonColorConstants.SINGLELINE_STRING));
		reconciler.setDamager(dr, PyPartitionScanner.PY_SINGLELINE_STRING);
		reconciler.setRepairer(dr, PyPartitionScanner.PY_SINGLELINE_STRING);

		dr= new DefaultDamagerRepairer(createSimpleTokenScanner(IPythonColorConstants.COMMENT));
		reconciler.setDamager(dr, PyPartitionScanner.PY_COMMENT);
		reconciler.setRepairer(dr, PyPartitionScanner.PY_COMMENT);
		
		dr= new DefaultDamagerRepairer(createSimpleTokenScanner(IPythonColorConstants.BACKQUOTE));
		reconciler.setDamager(dr, PyPartitionScanner.PY_BACKQUOTES);
		reconciler.setRepairer(dr, PyPartitionScanner.PY_BACKQUOTES);
		
		return reconciler;
	}

}