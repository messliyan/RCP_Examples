package com.bonc.rcp.dbtool.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

import com.bonc.rcp.dbtool.Constants.preferenceConstants.CloseWindowPreferenceConstants;

/** A sample preference page to show how it works */
public class CloseBehaviourPage extends FieldEditorPreferencePage
{
	
	public CloseBehaviourPage()
	{
		super(GRID);
	}

	@Override
	protected void createFieldEditors()
	{
		
		addField(new ComboFieldEditor(CloseWindowPreferenceConstants.CLOSETYPE_PREF_KEY, "窗口关闭行为",new String[][] {{ "显示关闭提示框","Null"}
		,{"直接关闭程序","Close"},{"窗口最小化","Minimize"}}, getFieldEditorParent()));
	}

}
