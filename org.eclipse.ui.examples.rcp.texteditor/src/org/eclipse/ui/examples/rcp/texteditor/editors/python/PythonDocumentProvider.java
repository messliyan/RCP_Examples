package org.eclipse.ui.examples.rcp.texteditor.editors.python;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;

import org.eclipse.ui.examples.rcp.texteditor.editors.SimpleDocumentProvider;

public class PythonDocumentProvider extends SimpleDocumentProvider {

	@Override
    protected void setupDocument(IDocument document) {
		PyPartitionScanner.checkPartitionScanner(document);
	}

	@Override
    protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		return null;
	}
}