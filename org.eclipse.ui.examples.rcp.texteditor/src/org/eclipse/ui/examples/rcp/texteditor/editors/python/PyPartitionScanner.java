/*
 * Author: atotic
 * Created: July 10, 2003
 * License: Common Public License v1.0
 */

package org.eclipse.ui.examples.rcp.texteditor.editors.python;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * Rule-based partition scanner
 * 
 * Simple, fast parsing of the document into partitions.<p>
 * This is like a rough 1st pass at parsing. We only parse
 * out for comments, single-line strings, and multiline strings<p>
 * The results are parsed again inside {@link org.python.pydev.editor.PyEditConfiguration#getPresentationReconciler}
 * and colored there.<p>
 * 
 * "An IPartitionTokenScanner can also start in the middle of a partition,
 * if it knows the type of the partition."
 */
public class PyPartitionScanner extends RuleBasedPartitionScanner {
	public final static String PY_COMMENT = "__python_comment";
	public final static String PY_SINGLELINE_STRING = "__python_singleline_string";
	public final static String PY_MULTILINE_STRING1 = "__python_multiline_string1";
	public final static String PY_MULTILINE_STRING2 = "__python_multiline_string2";
	public final static String PY_BACKQUOTES = "__python_backquotes";
    
	// TODO check if we need IDocument.DEFAULT_CONTENT_TYPE
    public final static String[] types = {PyPartitionScanner.PY_COMMENT, PyPartitionScanner.PY_SINGLELINE_STRING, PyPartitionScanner.PY_MULTILINE_STRING1, PyPartitionScanner.PY_MULTILINE_STRING2, PyPartitionScanner.PY_BACKQUOTES};
    public static final String PYTHON_PARTITION_TYPE = "__PYTHON_PARTITION_TYPE";
    
	public PyPartitionScanner() {
		super();
		List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		addMultilineStringRule(rules);
		addSinglelineStringRule(rules);
		addReprRule(rules);
		addCommentRule(rules);
		
		setPredicateRules(rules.toArray(new IPredicateRule[0]));
	}

	private void addReprRule(List<IPredicateRule> rules) {
		rules.add(new SingleLineRule("`", "`", new Token(PyPartitionScanner.PY_BACKQUOTES)));
	}

	private void addSinglelineStringRule(List<IPredicateRule> rules) {
//		IToken singleLineString = new Token(PY_SINGLELINE_STRING);
//		rules.add(new SingleLineRule("\"", "\"", singleLineString, '\\'));
//		rules.add(new SingleLineRule("'", "'", singleLineString, '\\')); -- changed to the construct below because we need to continue on escape

		
		IToken singleLineString = new Token(PyPartitionScanner.PY_SINGLELINE_STRING);
		// deal with "" and '' strings
		boolean breaksOnEOL = true;
		boolean breaksOnEOF = false;
		boolean escapeContinuesLine = true;
		rules.add(new PatternRule("'", "'", singleLineString, '\\', breaksOnEOL, breaksOnEOF, escapeContinuesLine));
		rules.add(new PatternRule("\"", "\"", singleLineString, '\\', breaksOnEOL, breaksOnEOF, escapeContinuesLine));
	}

	private void addMultilineStringRule(List<IPredicateRule> rules) {
		// deal with ''' and """ strings
		rules.add(new MultiLineRule("'''", "'''", new Token(PyPartitionScanner.PY_MULTILINE_STRING1), '\\')); 
		rules.add(new MultiLineRule("\"\"\"", "\"\"\"", new Token(PyPartitionScanner.PY_MULTILINE_STRING2),'\\'));
		
		//there is a bug in this construct: When parsing a simple document such as:
		//
		//"""ttt"""
		//print 'a'
		//
		//if lines are feed after 'ttt', it ends up considering the whole document as a multiline string.
		//the bug is reported at: http://sourceforge.net/tracker/index.php?func=detail&aid=1402165&group_id=85796&atid=577329
		//
		//some regards on the bug:
		//- it does not happen if the multiline has ''' instead of """
		//- also, if we first add the """ rule and after the ''' rule, the bug happens with ''' and not """
		//- if the user later changes the first line of that multiline or a line above it, it ends up parsing correctly again
		//- if we let just one of the constructs, no problem happens
		//
		//I also tried creating a new token for it, but it had problems too (not the same ones, but had other problems).
	}

	private void addCommentRule(List<IPredicateRule> rules) {
		IToken comment = new Token(PyPartitionScanner.PY_COMMENT);
		rules.add(new EndOfLineRule("#", comment));
	}
	
	/**
	 * @return all types recognized by this scanner (used by doc partitioner)
	 */
	static public String[] getTypes() {
		return PyPartitionScanner.types;
	}

	public static void checkPartitionScanner(IDocument document) {
	    if(document == null){
	        return;
        }
        
        IDocumentExtension3 docExtension= (IDocumentExtension3) document;
	    IDocumentPartitioner partitioner = docExtension.getDocumentPartitioner(PyPartitionScanner.PYTHON_PARTITION_TYPE);
	    if (partitioner == null){
            addPartitionScanner(document);
            //get it again for the next check
            partitioner = docExtension.getDocumentPartitioner(PyPartitionScanner.PYTHON_PARTITION_TYPE);
        }
//	    if (!(partitioner instanceof PyPartitioner)){
//            PydevPlugin.log("Partitioner should be subclass of PyPartitioner. It is "+partitioner.getClass());
//	    }
    }
    
    /*
     * @see http://help.eclipse.org/help31/index.jsp?topic=/org.eclipse.platform.doc.isv/guide/editors_documents.htm
     * @see http://jroller.com/page/bobfoster -  Saturday July 16, 2005
     * @param element
     * @param document
     */
    public static void addPartitionScanner(IDocument document) {
        if (document != null) {
            IDocumentExtension3 docExtension= (IDocumentExtension3) document;
            if(docExtension.getDocumentPartitioner(PyPartitionScanner.PYTHON_PARTITION_TYPE) == null){
                //set the new one
                FastPartitioner partitioner = new FastPartitioner(new PyPartitionScanner(), getTypes());
                partitioner.connect(document);
                docExtension.setDocumentPartitioner(PyPartitionScanner.PYTHON_PARTITION_TYPE,partitioner);
            }
        }
    }
    
    
    
    
}
