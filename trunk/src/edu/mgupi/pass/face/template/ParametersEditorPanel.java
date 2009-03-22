package edu.mgupi.pass.face.template;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.TYPES;

public class ParametersEditorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor
	 */
	public ParametersEditorPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		gridBagConstraints.gridy = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setName("paramEditorPanel");
		this.add(getJPanelPlace(), gridBagConstraints);
	}

	private Collection<Param> parameters = null;
	private Map<Param, Color> selectedColors = new HashMap<Param, Color>(); //  @jve:decl-index=0:
	private Map<Param, Component> controlComponents = new HashMap<Param, Component>(); //  @jve:decl-index=0:
	private JPanel jPanelPlace = null;

	public void setParameters(String title, Collection<Param> editableParameters) throws Exception {

		if (editableParameters == null) {
			return; // ---------
		}
		if (title == null) {
			throw new IllegalAccessException("Internal error. 'Title' must be not null.");
		}

		selectedColors.clear();
		controlComponents.clear();
		parameters = editableParameters;

		((TitledBorder) jPanelPlace.getBorder()).setTitle(title);

		//		int size = editableParameters.size();
		//		GridLayout grid = (GridLayout) jPanelPlace.getLayout();
		//		grid.setRows(size);
		//		grid.setColumns(2);

		int index = 0;
		for (Iterator<Param> iter = editableParameters.iterator(); iter.hasNext();) {
			final Param param = iter.next();
			JLabel label = new JLabel();
			label.setText(param.getTitle());
			label.setName(param.getName() + "_label");

			jPanelPlace.add(label, "skip");

			final TYPES type = param.getType();
			final String name = param.getName();

			Component renderComponent = null;
			if (type == TYPES.STRING) {
				JTextField field = new JTextField();
				field.setName(name + "_field");

				renderComponent = field;
				controlComponents.put(param, field);
			} else if (type == TYPES.INT) {

				if (param.getLow_border() != Integer.MIN_VALUE && param.getHi_border() != Integer.MAX_VALUE) {

					SpinnerModel model = new SpinnerNumberModel(param.getLow_border(), param.getLow_border(), param
							.getHi_border(), 1);
					JSpinner spinner = new JSpinner(model);
					spinner.setName(name + "_spinner");

					renderComponent = spinner;
					controlComponents.put(param, spinner);

				} else {
					JFormattedTextField field = new JFormattedTextField(NumberFormat.getIntegerInstance());
					field.setName(param.getName() + "_int_field");

					renderComponent = field;
					controlComponents.put(param, field);
				}
			} else if (type == TYPES.DOUBLE) {
				JFormattedTextField field = new JFormattedTextField(new DecimalFormat("0.0#######"));
				field.setName(name + "_double_field");

				renderComponent = field;
				controlComponents.put(param, field);
			} else if (type == TYPES.COLOR) {

				selectedColors.put(param, (Color) param.getValue());

				JPanel colorPanel = new JPanel();
				colorPanel.setLayout(new GridBagLayout());
				colorPanel.setName(name + "_color_panel");

				JButton colorSelector = new JButton();
				final JLabel colorSample = new JLabel(" ");

				colorSelector.setAction(new AbstractAction() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						Color selectedValue = JColorChooser.showDialog(ParametersEditorPanel.this,
								"Выбор цвета для параметра " + name, selectedColors.get(param));
						if (selectedValue != null) {
							selectedColors.put(param, selectedValue);
							colorSample.setBackground(selectedColors.get(param));
						}
					}
				});
				colorSelector.setText("...");
				colorSelector.setName(name + "_color_selector");
				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.weightx = 0;
				colorPanel.add(colorSelector, gridBagConstraints);

				colorSample.setName(name + "_color_sample");
				colorSample.setOpaque(true);
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.weightx = 1;
				gridBagConstraints.insets = new Insets(0, 5, 0, 0);
				gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
				colorPanel.add(colorSample, gridBagConstraints);

				renderComponent = colorPanel;
				controlComponents.put(param, colorSample);

			} else if (type == TYPES.LIST) {
				JComboBox comboBox = new JComboBox(param.getVisual_values());
				comboBox.setName(name + "_combo_box");

				renderComponent = comboBox;
				controlComponents.put(param, comboBox);

			} else {
				throw new IllegalArgumentException("Internal error. Unable to properly render parameter '" + name
						+ "' with type " + type + ". Unknown type. Please, consult with developers.");
			}

			jPanelPlace.add(renderComponent, iter.hasNext() ? "span, growx" : "wrap para");

			index++;
		}

		this.resetParameterValues();

	}

	public void resetParameterValues() {
		for (Map.Entry<Param, Component> entry : controlComponents.entrySet()) {
			Param param = entry.getKey();
			Component comp = entry.getValue();

			TYPES type = param.getType();
			if (type == TYPES.STRING) {
				((JTextField) comp).setText(param.getValue() == null ? null : String.valueOf(param.getValue()));
			} else if (type == TYPES.INT) {
				if (comp instanceof JFormattedTextField) {
					((JFormattedTextField) comp).setValue(param.getValue());
				} else if (comp instanceof JSpinner) {
					((JSpinner) comp).setValue(param.getValue());
				} else {
					throw new IllegalArgumentException(
							"Internal error. Unable to properly cast stored component for parameter '"
									+ param.getName() + "' with component class " + comp.getClass()
									+ ". Unknown class. Please, consult with developers.");
				}
			} else if (type == TYPES.DOUBLE) {
				((JFormattedTextField) comp).setValue(param.getValue());
			} else if (type == TYPES.COLOR) {
				Color color = (Color) param.getValue();
				((JLabel) comp).setBackground(color);
				selectedColors.put(param, color);
			} else if (type == TYPES.LIST) {
				((JComboBox) comp).setSelectedIndex(0);
				if (param.getValue() != null) {
					for (int i = 0; i <= param.getAllowed_values().length; i++) {
						if (param.getValue().equals(param.getAllowed_values()[i])) {
							((JComboBox) comp).setSelectedItem(param.getVisual_values()[i]);
							break;
						}
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Internal error. Unable to properly set default value to parameter '" + param.getName()
								+ "' with type " + type + ". Unknown type. Please, consult with developers.");
			}
		}
	}

	public Collection<Param> saveParameterValues() throws IllegalParameterValueException {

		for (Map.Entry<Param, Component> entry : controlComponents.entrySet()) {
			Param param = entry.getKey();
			Component comp = entry.getValue();

			TYPES type = param.getType();
			if (type == TYPES.STRING) {
				param.setValue(((JTextField) comp).getText());
			} else if (type == TYPES.INT) {
				if (comp instanceof JFormattedTextField) {
					param.setValue(((JFormattedTextField) comp).getValue());
				} else if (comp instanceof JSpinner) {
					param.setValue(((JSpinner) comp).getValue());
				} else {
					throw new IllegalArgumentException(
							"Internal error. Unable to properly cast stored component for parameter '"
									+ param.getName() + "' with component class " + comp.getClass()
									+ ". Unknown class. Please, consult with developers.");
				}
			} else if (type == TYPES.DOUBLE) {
				param.setValue(((JFormattedTextField) comp).getValue());
			} else if (type == TYPES.COLOR) {
				param.setValue(selectedColors.get(param));
			} else if (type == TYPES.LIST) {
				param.setValue(param.getAllowed_values()[((JComboBox) comp).getSelectedIndex()]);
			} else {
				throw new IllegalArgumentException(
						"Internal error. Unable to properly set default value to parameter '" + param.getName()
								+ "' with type " + type + ". Unknown type. Please, consult with developers.");
			}
		}

		return this.parameters;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			MigLayout mig = new MigLayout("", "[]0[right][200lp, fill]", "");

			jPanelPlace = new JPanel();
			jPanelPlace.setLayout(mig);
			jPanelPlace.setName("paramPlace");
			jPanelPlace.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return jPanelPlace;
	}
}
