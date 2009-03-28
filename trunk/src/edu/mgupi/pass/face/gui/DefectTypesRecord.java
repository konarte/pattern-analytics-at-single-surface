package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;
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
		super(owner, "defectTypesRecordDialog", "Типы дефектов");
		super.setFormPanelData(getFormPanel());
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
	private JLabel jLabelName = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;
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
			
			jPanelPlace = new JPanel();
			
			jPanelPlace.setLayout(new GridBagLayout());

			jLabelIDValue = new JLabel("0");
			jTextFieldNameValue = new JTextField();
			jComboBoxClassValue = new DefectClassesComboBox();

			super.putComponentPair(jPanelPlace, "Код:", jLabelIDValue);
			super.putComponentPair(jPanelPlace, "Класс дефекта:", jComboBoxClassValue);
			super.putComponentPair(jPanelPlace, "Название типа:", jTextFieldNameValue);
		}
		return jPanelPlace;
	}

} //  @jve:decl-index=0:visual-constraint="16,-1"
