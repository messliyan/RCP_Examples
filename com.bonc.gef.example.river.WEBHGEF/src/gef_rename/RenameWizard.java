package gef_rename;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class RenameWizard extends Wizard {
	
	private class RenamePage extends WizardPage {
		public Text nameText;
		
		public RenamePage(String pageName) {
			super(pageName);
			setTitle("Rename");
			setDescription("Rename a component");
		}

		@Override
		public void createControl(Composite parent) {
			// TODO Auto-generated method stub
			Composite composite = new Composite(parent, SWT.NONE);
			
			Label label = new Label(composite, SWT.NONE);
			label.setText("Rename to:");
			
			nameText = new Text(composite, SWT.NONE);
			nameText.setText(oldName);
			
			RowLayout I = new RowLayout();
			composite.setLayout(I);
			setControl(composite);
		}
	}
	
	private String oldName;
	private String newName;
	
	public RenameWizard(String oldName) {
		this.oldName = oldName;
		this.newName = null;
		
		addPage(new RenamePage("MyRenamePage"));
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		RenamePage page = (RenamePage)getPage("MyRenamePage");
		if(page.nameText.getText().isEmpty()) {
			page.setErrorMessage("名称不可为空！");
			return false;
		}
		newName = page.nameText.getText();
		return true;
	}
	
	public String getRenameValue() {
		return newName;
	}
}