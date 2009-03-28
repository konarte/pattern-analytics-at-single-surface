package edu.mgupi.pass.face.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesCriteria;
import edu.mgupi.pass.db.defects.DefectClassesFactory;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.DefectTypesFactory;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.util.Utils;

public class DefectClassesRecord extends RecordEditorTemplate {

	private static final long serialVersionUID = 1L;

	public DefectClassesRecord(Frame owner) {
		super(owner);
		super.setFormPanelData(getFormPanel());
		this.setName("defectClassesRecordDialog");
		this.setTitle("Классы дефектов");
	}

	@Override
	protected void setRequiredFields(Map<JTextComponent, JLabel> map) {
		map.put(jTextFieldNameValue, jLabelName);
	}

	@Override
	public boolean isDeleteAllowed(Object[] objects) throws Exception {
		for (Object object : objects) {
			DefectClasses defectObject = ((DefectClasses) object);
			int id = defectObject.getIdDefectClass();

			if (id == 0) {
				continue;
			}

			DefectTypesCriteria criteria = new DefectTypesCriteria();
			criteria.createDefectClassCriteria().idDefectClass.eq(defectObject.getIdDefectClass());

			DefectTypes foundType = DefectTypesFactory.loadDefectTypesByCriteria(criteria);
			if (foundType != null) {
				AppHelper.showErrorDialog("Класс дефекта " + defectObject.getName()
						+ " удалить нельзя. К нему привязан дефект с типом " + foundType.getName() + ".");
				return false;
			}
		}

		return true;
	}

	@Override
	protected boolean isSaveAllowed(Object object) throws Exception {
		DefectClasses defectObject = ((DefectClasses) object);
		String className = jTextFieldNameValue.getText();

		if (Utils.equals(className, defectObject.getName())) {
			return true;
		}

		DefectClassesCriteria criteria = new DefectClassesCriteria();
		criteria.name.eq(className);
		if (defectObject.getIdDefectClass() != 0) {
			criteria.idDefectClass.ne(defectObject.getIdDefectClass());
		}

		DefectClasses foundClass = DefectClassesFactory.loadDefectClassesByCriteria(criteria);
		if (foundClass != null) {
			AppHelper.showErrorDialog(null, "Класс дефекта с названием " + className + " уже существует.");
			return false;
		}

		return true;
	}

	private String defectClass = null;

	@Override
	protected boolean loadFormFromObject(Object object) throws Exception {
		DefectClasses defectObject = ((DefectClasses) object);
		this.defectClass = defectObject.getName();

		jLabelIDValue.setText(String.valueOf(defectObject.getIdDefectClass()));
		jTextFieldNameValue.setText(defectObject.getName());

		jTextFieldNameValue.requestFocusInWindow();

		return true;
	}

	@Override
	protected void restoreObjectImpl(Object object) throws Exception {
		DefectClasses defectObject = ((DefectClasses) object);
		defectObject.setName(defectClass);
	}

	@Override
	protected void saveFormToObjectImpl(Object object) throws Exception {
		DefectClasses defectObject = ((DefectClasses) object);
		defectObject.setName(jTextFieldNameValue.getText());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelID = null;
	private JLabel jLabelName = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;

	private JPanel formPanel = null;

	protected JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new JPanel();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridy = 0;
			formPanel.setSize(300, 200);
			formPanel.setLayout(new GridBagLayout());

			formPanel.setBorder(BorderFactory.createTitledBorder(null, "Редактирование класса",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			formPanel.add(getFormPanelData(), gridBagConstraints4);
		}
		return formPanel;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFormPanelData() {
		if (jPanelPlace == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints2.gridy = 0;
			jLabelIDValue = new JLabel();
			jLabelIDValue.setText("0");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.fill = GridBagConstraints.NONE;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.gridy = 1;
			jLabelName = new JLabel();
			jLabelName.setText("Название класса:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.weightx = 0.0D;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridy = 0;
			jLabelID = new JLabel();
			jLabelID.setText("Код:");
			jPanelPlace = new JPanel();
			jPanelPlace.setLayout(new GridBagLayout());
			jPanelPlace.add(jLabelID, gridBagConstraints);
			jPanelPlace.add(jLabelIDValue, gridBagConstraints2);
			jPanelPlace.add(jLabelName, gridBagConstraints1);
			jPanelPlace.add(getJTextFieldNameValue(), gridBagConstraints3);

			jPanelPlace.setMinimumSize(new Dimension(300, 200));
		}
		return jPanelPlace;
	}

	/**
	 * This method initializes jTextFieldNameValue
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNameValue() {
		if (jTextFieldNameValue == null) {
			jTextFieldNameValue = new JTextField();
		}
		return jTextFieldNameValue;
	}
}
