package org.eclipse.ui.examples.rcp.texteditor.editors.xml;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.IAnnotationModel;

import org.eclipse.ui.examples.rcp.texteditor.editors.SimpleDocumentProvider;

public class XMLDocumentProvider extends SimpleDocumentProvider {

	@Override
    protected void setupDocument(IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new XMLPartitionScanner(),
					new String[] {
						XMLPartitionScanner.XML_TAG,
						XMLPartitionScanner.XML_COMMENT });
			partitioner.connect(document);
			((IDocumentExtension3) document).setDocumentPartitioner(XMLPartitionScanner.XML_PARTITIONING, partitioner);
		}
	}

	@Override
    protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		return null;
	}
}