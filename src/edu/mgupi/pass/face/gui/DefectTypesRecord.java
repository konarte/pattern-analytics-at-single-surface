package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.DefectTypesFactory;
import edu.mgupi.pass.db.defects.Defects;
import edu.mgupi.pass.db.defects.DefectsCriteria;
import edu.mgupi.pass.db.defects.DefectsFactory;
import edu.mgupi.pass.face.gui.template.DefectClassesComboBox;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;
import edu.mgupi.pass.util.Utils;

public class DefectTypesRecord extends RecordEditorTemplate {

	//private final static Logger logger = LoggerFactory.getLogger(DefectTypesRecord.class);

	private static final long serialVersionUID = 1L;

	public DefectTypesRecord(Frame owner) {
		super(owner);
		super.setFormPanelData(getFormPanel());
		this.setName("defectTypesRecordDialog");
		this.setTitle("Типы дефектов");
	}

	public void close() {
		formPanel.getImageControlAdapter().close();
	}

	@Override
	protected void setRequiredFields(Map<JTextComponent, JLabel> map) {
		map.put(jTextFieldNameValue, jLabelName);
	}

	@Override
	public boolean isDeleteAllowed(Object[] objects) throws Exception {
		for (Object object : objects) {
			DefectTypes defectObject = ((DefectTypes) object);
			int id = defectObject.getIdDefectType();

			if (id == 0) {
				continue;
			}

			DefectsCriteria criteria = new DefectsCriteria();
			criteria.createDefectTypeCriteria().idDefectType.eq(defectObject.getIdDefectType());

			Defects found = DefectsFactory.loadDefectsByCriteria(criteria);
			if (found != null) {
				AppHelper.showErrorDialog("Тип дефекта " + defectObject.getName()
						+ " удалить нельзя. К нему привязан существующий дефект с кодом " + found.getIdDefect() + ".");
				return false;
			}
		}

		return true;
	}

	@Override
	protected boolean isSaveAllowed(Object object) throws Exception {
		DefectTypes defectObject = ((DefectTypes) object);
		String typeName = jTextFieldNameValue.getText();

		if (Utils.equals(typeName, defectObject.getName())) {
			return true;
		}

		DefectTypesCriteria criteria = new DefectTypesCriteria();
		criteria.name.eq(typeName);
		if (defectObject.getIdDefectType() != 0) {
			criteria.idDefectType.ne(defectObject.getIdDefectType());
		}

		DefectTypes foundClass = DefectTypesFactory.loadDefectTypesByCriteria(criteria);
		if (foundClass != null) {
			AppHelper.showErrorDialog("Тип дефекта с названием " + typeName + " уже существует.");
			return false;
		}

		return true;
	}

	private String name = null;
	private byte[] image = null;
	private DefectClasses defectClass = null;

	@Override
	protected boolean loadFormFromObject(Object object) throws Exception {
		DefectTypes defectObject = ((DefectTypes) object);

		this.name = defectObject.getName();
		this.defectClass = defectObject.getDefectClass();
		this.image = defectObject.getDefectImage();

		jComboBoxClassValue.refreshData();

		if (jComboBoxClassValue.getModel().getSize() == 0) {
			AppHelper.showErrorDialog("Список классов дефекта пуст. Необходимо создать хотя бы один класс дефекта.");
			return false;
		}

		jLabelIDValue.setText(String.valueOf(defectObject.getIdDefectType()));
		jTextFieldNameValue.setText(name);

		if (defectClass != null) {
			jComboBoxClassValue.setSelectedItem(defectClass);
		} else {
			jComboBoxClassValue.setSelectedIndex(0);
		}

		formPanel.setImageRaw(image);

		jTextFieldNameValue.requestFocusInWindow();

		return true;
	}

	@Override
	protected void restoreObjectImpl(Object object) throws Exception {
		DefectTypes defectObject = ((DefectTypes) object);
		defectObject.setName(this.name);
		defectObject.setDefectImage(this.image);
		defectObject.setDefectClass(this.defectClass);
	}

	@Override
	protected void saveFormToObjectImpl(Object object) throws Exception {
		DefectTypes defectObject = ((DefectTypes) object);
		defectObject.setName(jTextFieldNameValue.getText());
		defectObject.setDefectClass((DefectClasses) jComboBoxClassValue.getSelectedItem());
		defectObject.setDefectImage(formPanel.getRawImage());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelID = null;
	private JLabel jLabelName = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;
	private JLabel jLabelClass = null;
	private DefectClassesComboBox jComboBoxClassValue = null;

	private RecordFormWithImageTemplate formPanel = null;

	protected RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(this, getJPanelPlace());
		}
		return formPanel;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = -1;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = -1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints41.anchor = GridBagConstraints.EAST;
			gridBagConstraints41.gridy = 1;
			jLabelClass = new JLabel();
			jLabelClass.setText("Класс дефекта");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 2;
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
			gridBagConstraints1.gridy = 2;
			jLabelName = new JLabel();
			jLabelName.setText("Название типа:");
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
			jPanelPlace.add(jLabelClass, gridBagConstraints41);
			jPanelPlace.add(getJComboBoxClassValue(), gridBagConstraints5);
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

	/**
	 * This method initializes jComboBoxClassValue
	 * 
	 * @return javax.swing.JComboBox
	 */
	private DefectClassesComboBox getJComboBoxClassValue() {
		if (jComboBoxClassValue == null) {
			jComboBoxClassValue = new DefectClassesComboBox();
		}
		return jComboBoxClassValue;
	}

} //  @jve:decl-index=0:visual-constraint="16,-1"
