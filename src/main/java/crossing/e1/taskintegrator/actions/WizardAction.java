package crossing.e1.taskintegrator.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import crossing.e1.configurator.Constants;
import crossing.e1.taskintegrator.wizard.TaskIntegrationWizard;

public class WizardAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void run(IAction arg0) {
		Constants.WizardActionFromContextMenuFlag = false;
		final WizardDialog dialog = new WizardDialog(new Shell(), new TaskIntegrationWizard());
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {}

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow arg0) {}

}
