package crossing.e1.configurator.wizard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.clafer.ast.AstConcreteClafer;
import org.clafer.ast.AstConstraint;
import org.clafer.common.Check;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.*;

import crossing.e1.featuremodel.clafer.ClaferModel;
import crossing.e1.featuremodel.clafer.InstanceGenerator;

public class TaskSelectionPage extends WizardPage {
	private ClaferModel model;
	private Spinner taskCombo;
	private Composite container;
	private Button securityLevelSecured;
	private Button securityLevelInSecured;
	private Spinner outPutSize;
	private Label label1;
	private Label label2;
	private Label label3;
	private HashMap<String, Integer> userOptions;

	public TaskSelectionPage(List<AstConcreteClafer> items,
			ClaferModel claferModel) {
		super("Select Task");
		setTitle("Chonfigure");
		setDescription("Here the user selects her options and security levels");
		userOptions = new HashMap<String, Integer>();
		this.model = claferModel;

	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		setControl(container);
		canFlipToNextPage();
		for (AstConcreteClafer clafer : model.getConstraintClafers()) {
			getWidget(container, clafer.getName(), 1, 0, 5, 0, 1, 1);
		}
	}

	void getRadio() {
		securityLevelSecured = new Button(container, SWT.RADIO);
		securityLevelSecured.setToolTipText("Secured Encryption");
		securityLevelSecured.setText("Secure");
		securityLevelSecured.setEnabled(true);
		securityLevelInSecured = new Button(container, SWT.RADIO);
		securityLevelInSecured.setToolTipText("Insecure");
		securityLevelInSecured.setText("Do Not Secure");
		securityLevelInSecured.setEnabled(true);

		securityLevelSecured.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				securityLevelInSecured.setSelection(false);
				securityLevelSecured.setSelection(true);
				outPutSize.setVisible(true);
				taskCombo.setVisible(true);
				label1.setVisible(true);
				label2.setVisible(true);
				label3.setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

		});

		securityLevelInSecured.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				securityLevelSecured.setSelection(false);
				securityLevelInSecured.setSelection(true);
				outPutSize.setVisible(false);
				taskCombo.setVisible(false);
				label1.setVisible(false);
				label2.setVisible(false);
				label3.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

		});
	}

	void getWidget(Composite container, String label, int selection, int min,
			int max, int digits, int incement, int pageincrement) {
		label1 = new Label(container, SWT.NONE);
		label1.setText(label);
		taskCombo = new Spinner(container, SWT.BORDER | SWT.SINGLE);
		taskCombo.setValues(selection, min, max, digits, incement,
				pageincrement);

	}

	public boolean isSecure() {
		return true;
	}

	public Map<String, Integer> getMap() {
		return userOptions;
	}

}
