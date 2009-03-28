package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.Defects;
import edu.mgupi.pass.db.defects.DefectsCriteria;
import edu.mgupi.pass.face.gui.template.DefectClassesComboBox;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;

public class DefectTypesRecord extends RecordEditorTemplate<DefectTypes> {

	//private final static Logger logger = LoggerFactory.getLogger(DefectTypesRecord.class);

	private static final long serialVersionUID = 1L;

	public DefectTypesRecord(Frame owner) {
		super(owner, "defectTypesRecordDialog", "Типы дефектов");
		super.setFormPanelData(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		Defects type = (Defects) foundObject;
		return "Дефект с кодом '" + type.getIdDefect() + "' использует тип '" + type.getDefectType().getName() + "'.";
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<DefectTypes> objects) throws Exception {
		DefectsCriteria criteria = new DefectsCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (DefectTypes def : objects) {
			in[idx++] = def.getIdDefectType();
		}
		criteria.createDefectTypeCriteria().idDefectType.in(in);
		return criteria;
	}

	@Override
	protected Criteria getSaveAllowCriteria(DefectTypes object, String newValue) throws Exception {
		DefectTypesCriteria criteria = new DefectTypesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdDefectType() != 0) {
			criteria.idDefectType.ne(object.getIdDefectType());
		}
		return criteria;
	}

	private String name = null;
	private byte[] image = null;
	private DefectClasses defectClass = null;

	@Override
	protected boolean loadFormFromObjectImpl(DefectTypes object) throws Exception {
		this.name = object.getName();
		this.defectClass = object.getDefectClass();
		this.image = object.getDefectImage();

		jLabelIDValue.setText(String.valueOf(object.getIdDefectType()));
		jTextFieldNameValue.setText(name);

		if (defectClass != null) {
			jComboBoxClassValue.setSelectedItem(defectClass);
		} else {
			jComboBoxClassValue.setSelectedIndex(0);
		}

		formPanel.setImageRaw(image);

		return true;
	}

	@Override
	protected void restoreObjectImpl(DefectTypes object) throws Exception {
		object.setName(this.name);
		object.setDefectImage(this.image);
		object.setDefectClass(this.defectClass);
	}

	@Override
	protected void saveFormToObjectImpl(DefectTypes object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
		object.setDefectClass((DefectClasses) jComboBoxClassValue.getSelectedItem());
		object.setDefectImage(formPanel.getRawImage());
	}

	private JPanel jPanelPlace = null;
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

			super.putComponentPair(jPanelPlace, "Код", jLabelIDValue);
			super.putComponentPair(jPanelPlace, "Класс дефекта", jComboBoxClassValue);
			super.putUniqueComponentPair(jPanelPlace, "Название типа", jTextFieldNameValue);
		}
		return jPanelPlace;
	}

} //  @jve:decl-index=0:visual-constraint="16,-1"
