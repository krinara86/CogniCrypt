/**
 * Copyright 2015 Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * @author Ram Kamath
 *
 */

package crossing.e1.configurator.wizard.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.clafer.ast.AstConcreteClafer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import crossing.e1.configurator.Lables;
import crossing.e1.configurator.ReadConfig;
import crossing.e1.featuremodel.clafer.ClaferModel;
import crossing.e1.featuremodel.clafer.ClaferModelUtils;
import crossing.e1.featuremodel.clafer.InstanceGenerator;
import crossing.e1.featuremodel.clafer.PropertiesMapperUtil;


public class ValueSelectionPage extends WizardPage implements Lables {
	private ClaferModel model;
	private List<Spinner> taskCombo;
	private Composite container;
	private List<AstConcreteClafer> label;
	private List<AstConcreteClafer> mainClafer;
	private List<ComboViewer> options;
	private HashMap<ArrayList<AstConcreteClafer>, ArrayList<Integer>> userOptions;
	private HashMap<String, AstConcreteClafer> userGroupOptions;
	List<Composite> widgets = new ArrayList<Composite>();
	boolean statusPage = false;

	public ValueSelectionPage(List<AstConcreteClafer> items,
			ClaferModel claferModel) {
		super(Lables.SELECT_PROPERTIES);
		setTitle(Lables.PROPERTIES);
		setDescription(Lables.DESCRIPTION_VALUE_SELECTION_PAGE);
		userOptions = new HashMap<ArrayList<AstConcreteClafer>, ArrayList<Integer>>();
		userGroupOptions = new HashMap<String, AstConcreteClafer>();
		model = claferModel;

	}

	@Override
	public void createControl(Composite parent) {

		taskCombo = new ArrayList<Spinner>();
		label = new ArrayList<AstConcreteClafer>();
		options = new ArrayList<ComboViewer>();
		mainClafer = new ArrayList<AstConcreteClafer>();

		container = new Composite(parent, SWT.NONE);

		container.setBounds(10, 10, 450, 200);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;

		for (AstConcreteClafer clafer : PropertiesMapperUtil.getPropertiesMap()
				.keySet()) {

			// Label label3 = new Label(container, SWT.NONE);
			// Font boldFont = new Font( label3.getDisplay(), new FontData(
			// "Arial", 10, SWT.BOLD ));
			// label3.setFont(boldFont);
			// label3.setText(parser.trim(parser.trim(clafer.getName())));
			// Label label4 = new Label(container, SWT.NONE);
			// label4.setText("");

			Group titledPanel = new Group(container, SWT.NONE);
			titledPanel.setText(ClaferModelUtils.trimScope(clafer.getName()));
			Font boldFont = new Font(titledPanel.getDisplay(), new FontData(
					"Arial", 9, SWT.BOLD));
			titledPanel.setFont(boldFont);
			GridLayout layout2 = new GridLayout();

			layout2.numColumns = 4;
			titledPanel.setLayout(layout2);
			ArrayList<AstConcreteClafer> x = new ArrayList<AstConcreteClafer>(
					new LinkedHashSet(PropertiesMapperUtil.getPropertiesMap()
							.get(clafer)));
			for (AstConcreteClafer claf : x) {

				if (claf.getGroupCard().getLow() >= 1) {
					getWidget(titledPanel, clafer, claf, claf.getGroupCard()
							.getHigh());
				} else

					getWidget(titledPanel, clafer, claf,
							ClaferModelUtils.trimScope(claf.getName()), 1, 0, 1024, 0, 1, 1);
			}
		}
		setControl(container);
	}

	void getWidget(Composite container, AstConcreteClafer key1,
			AstConcreteClafer key2, String label, int selection, int min,
			int max, int digits, int incement, int pageincrement) {
		List<String> values = new ArrayList<String>();
		values.add(Lables.LESS_THAN);
		values.add(Lables.GREATER_THAN);
		values.add(Lables.EQUALS);
		values.add(Lables.LESS_THAN_EQUAL);
		values.add(Lables.GREATER_THAN_EQUAL);
		Label label5 = new Label(container, SWT.NONE);
		label5.setText("	");
		Label label1 = new Label(container, SWT.NONE);
		label1.setText(label);
		ComboViewer option = new ComboViewer(container, SWT.NONE);
		option.setContentProvider(ArrayContentProvider.getInstance());
		option.setInput(values);
		option.setSelection(new StructuredSelection(values.get(2)));

		Spinner taskComb = new Spinner(container, SWT.BORDER | SWT.SINGLE);
		taskComb.setValues(selection, min, max, digits, incement, pageincrement);
		taskComb.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				setComplete(true);

			}

		});

		this.mainClafer.add(key1);
		this.label.add(key2);
		this.options.add(option);
		this.taskCombo.add(taskComb);
	}

	void getsubPanel() {
		// TODO method takes 2 widget as an input and adds an X mark
	}

	void getWidget(Composite container, AstConcreteClafer claferMain,
			AstConcreteClafer claferProperty, int groupCard) {

		Label label5 = new Label(container, SWT.NONE);
		label5.setText("	");
		ArrayList<String> optionLables = new ArrayList<String>();
		Label label1 = new Label(container, SWT.NONE);
		label1.setText(ClaferModelUtils.trimScope(claferProperty.getName()));
		for (AstConcreteClafer astClafer : claferProperty.getChildren()) {
			userGroupOptions.put(
					claferMain.getName() + ClaferModelUtils.trimScope(astClafer.getName()),
					astClafer);
			optionLables.add(ClaferModelUtils.trimScope(astClafer.getName()));
		}
		ComboViewer option = new ComboViewer(container, SWT.NONE);
		option.setContentProvider(ArrayContentProvider.getInstance());
		option.setInput(optionLables);
		option.setSelection(new StructuredSelection(optionLables.get(0)));
		// Button button = new Button(composite, SWT.TOGGLE);
		// button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
		// false));
		// button.setText("X");
		// button.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		//
		// composite.setEnabled(!composite.isEnabled());
		// ;
		// container.redraw();
		//
		// }
		// });
		Label label6 = new Label(container, SWT.NONE);
		label6.setText("	");
		this.mainClafer.add(claferMain);
		this.label.add(claferProperty);
		this.options.add(option);
		this.taskCombo.add(null);

	}

	private void setComplete(boolean b) {

		statusPage = b;
	}

	public boolean getPageStatus() {
		setMap();
		return statusPage;
	}

	public Map<ArrayList<AstConcreteClafer>, ArrayList<Integer>> getMap() {
		return userOptions;
	}

	/**
	 * Set user selected values to the clafer properties
	 */
	public void setMap() {
		ArrayList<Integer> values;
		for (int i = 0; i < label.size(); i++) {
			if (taskCombo.get(i) == null) {
				values = new ArrayList<Integer>();
				ArrayList<AstConcreteClafer> keys = new ArrayList<AstConcreteClafer>();
				keys.add(mainClafer.get(i));
				keys.add(label.get(i));
				String test = mainClafer.get(i).getName()
						+ options.get(i).getSelection().toString()
								.replace("[", "").replace("]", "");
				keys.add(userGroupOptions.get(test));
				values.add(6);
				values.add(label.get(i).getGroupCard().getLow());
				userOptions.put(keys, values);
			} else {
				values = new ArrayList<Integer>();
				ArrayList<AstConcreteClafer> keys = new ArrayList<AstConcreteClafer>();
				keys.add(mainClafer.get(i));
				keys.add(label.get(i));
				values.add(toNumber(options.get(i).getSelection().toString()));
				values.add(taskCombo.get(i).getSelection());
				userOptions.put(keys, values);
			}
		}
	}

	/**
	 * @param selection
	 * @return Map quantifier to integer
	 */
	private Integer toNumber(String selection) {
		if (selection.contains(Lables.LESS_THAN_EQUAL))
			return 4;
		if (selection.contains(Lables.GREATER_THAN_EQUAL))
			return 5;
		if (selection.contains(Lables.EQUALS))
			return 1;
		if (selection.contains(Lables.LESS_THAN))
			return 2;
		if (selection.contains(Lables.GREATER_THAN))
			return 3;

		return 999;
	}
}