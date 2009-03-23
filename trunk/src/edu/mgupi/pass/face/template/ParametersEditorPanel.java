package edu.mgupi.pass.face.template;

import java.awt.Color;
import java.awt.Component;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.TYPES;

public class ParametersEditorPanel extends JPanel {

	private final static Logger logger = LoggerFactory.getLogger(ParametersEditorPanel.class);
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
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.ipady = 0;
		gridBagConstraints.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		this.setSize(400, 200);
		this.setLayout(new GridBagLayout());
		this.setName("paramEditorPanel");
		this.add(getJPanelPlace(), gridBagConstraints);
	}

	private boolean hasPreviousWork = false;
	private JPanel jPanelPlace = null;

	private Collection<Param> parameters = null; //  @jve:decl-index=0:
	private Map<Param, Color> selectedColors = new HashMap<Param, Color>(); //  @jve:decl-index=0:
	private Map<Param, Component> controlComponents = new HashMap<Param, Component>(); //  @jve:decl-index=0:

	public void setParameters(Collection<Param> editableParameters) {

		logger.debug("Applying parameters " + editableParameters);

		// if received already loaded 
		if (parameters == editableParameters && hasPreviousWork) {
			return;
		}

		selectedColors.clear();
		controlComponents.clear();
		parameters = editableParameters;

		jPanelPlace.removeAll();

		// If received no parameters
		if (editableParameters == null) {
			JLabel label = new JLabel();
			label.setText("Параметры отсутствуют");
			label.setName("noparams");
			jPanelPlace.add(label);
			return;
		}

		//((TitledBorder) jPanelPlace.getBorder()).setTitle(title);

		int index = 0;
		for (Iterator<Param> iter = editableParameters.iterator(); iter.hasNext();) {
			final Param param = iter.next();
			JLabel label = new JLabel();
			label.setText(param.getTitle() + ":");
			label.setName(param.getName() + "_label");

			//jPanelPlace.add(label, "skip");

			GridBagConstraints topGrid = new GridBagConstraints();
			topGrid.weightx = 0;
			topGrid.gridx = 0;
			topGrid.gridy = index;
			topGrid.anchor = GridBagConstraints.EAST;
			topGrid.insets = new Insets(5, 5, 5, 5);
			jPanelPlace.add(label, topGrid);

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

			//jPanelPlace.add(renderComponent, iter.hasNext() ? "span, growx" : "wrap para");

			topGrid = new GridBagConstraints();
			topGrid.weightx = 1;
			topGrid.gridx = 1;
			topGrid.gridy = index;
			topGrid.anchor = GridBagConstraints.WEST;
			topGrid.fill = GridBagConstraints.HORIZONTAL;
			jPanelPlace.add(renderComponent, topGrid);

			//jPanelPlace.add(renderComponent, "width 100%!");
			//jPanelPlace.add(renderComponent);

			index++;
		}

		hasPreviousWork = true;
		//		this.lastDimension = mig.preferredLayoutSize(this.jPanelPlace);
		this.resetParameterValues();

	}

	//	
	//
	//	public Dimension getLastDimension() {
	//		return lastDimension;
	//	}

	public void resetParameterValues() {
		this.resetParameterValues(false);
	}

	public void resetParameterValues(boolean fromDefaults) {
		for (Map.Entry<Param, Component> entry : controlComponents.entrySet()) {
			Param param = entry.getKey();
			Component comp = entry.getValue();

			TYPES type = param.getType();
			Object value = fromDefaults ? param.getDefault_() : param.getValue();
			if (type == TYPES.STRING) {
				((JTextField) comp).setText(value == null ? null : String.valueOf(value));
			} else if (type == TYPES.INT) {
				if (comp instanceof JFormattedTextField) {
					((JFormattedTextField) comp).setValue(value);
				} else if (comp instanceof JSpinner) {
					((JSpinner) comp).setValue(value);
				} else {
					throw new IllegalArgumentException(
							"Internal error. Unable to properly cast stored component for parameter '"
									+ param.getName() + "' with component class " + comp.getClass()
									+ ". Unknown class. Please, consult with developers.");
				}
			} else if (type == TYPES.DOUBLE) {
				((JFormattedTextField) comp).setValue(value);
			} else if (type == TYPES.COLOR) {
				Color color = (Color) value;
				((JLabel) comp).setBackground(color);
				selectedColors.put(param, color);
			} else if (type == TYPES.LIST) {
				((JComboBox) comp).setSelectedIndex(0);
				if (value != null) {
					for (int i = 0; i <= param.getAllowed_values().length; i++) {
						if (value.equals(param.getAllowed_values()[i])) {
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

			try {
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
			} catch (IllegalParameterValueException ive) {
				throw new IllegalParameterValueException("Error when applying parameter '" + param.getName() + "'", ive);
			}
		}

		return this.parameters;
	}

	public void restoreDefaults() throws IllegalParameterValueException {
		this.resetParameterValues(true);
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			//			migLayout = new MigLayout("", "[]0[right][100%, fill]", "");
			//migLayout = new MigLayout("", "wrap", "[right][0:pref,grow]", "");

			//migLayout = new MigLayout("wrap,nocache", "[right][100%,fill]", "");
			//			migLayout = new MigLayout("", "[grow]0[200, grow]", "");
			//			new FlowLayout().;
			jPanelPlace = new JPanel();
			jPanelPlace.setLayout(new GridBagLayout());
			//jPanelPlace.setLayout(new BoxLayout(jPanelPlace, BoxLayout.Y_AXIS));
			jPanelPlace.setName("paramPlace");
			//			jPanelPlace.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
			//					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return jPanelPlace;
	}
}
