package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesCriteria;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;

public class DefectClassesRecord extends RecordEditorTemplate<DefectClasses> {

	private static final long serialVersionUID = 1L;

	public DefectClassesRecord(Frame owner) {
		super(owner, "defectClassesRecordDialog", "Классы дефектов");
		super.setFormPanelData(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		DefectTypes type = (DefectTypes) foundObject;
		return "Тип дефекта '" + type.getName() + "' использует класс '" + type.getDefectClass().getName() + "'.";
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<DefectClasses> objects) throws Exception {
		DefectTypesCriteria criteria = new DefectTypesCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (DefectClasses def : objects) {
			in[idx++] = def.getIdDefectClass();
		}
		criteria.createDefectClassCriteria().idDefectClass.in(in);
		return criteria;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(DefectClasses object, String newValue) throws Exception {
		DefectClassesCriteria criteria = new DefectClassesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdDefectClass() != 0) {
			criteria.idDefectClass.ne(object.getIdDefectClass());
		}
		return criteria;
	}

	private String defectClass = null;

	@Override
	protected boolean loadFormFromObjectImpl(DefectClasses object) throws Exception {
		this.defectClass = object.getName();

		jLabelIDValue.setText(String.valueOf(object.getIdDefectClass()));
		jTextFieldNameValue.setText(object.getName());

		return true;
	}

	@Override
	protected void restoreObjectImpl(DefectClasses object) throws Exception {
		object.setName(defectClass);
	}

	@Override
	protected void saveFormToObjectImpl(DefectClasses object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;

	private JPanel formPanel = null;

	protected JPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = super.createDefaultFormPanel(getJPanelPlace());
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

			super.putComponentPair(jPanelPlace, "Код", jLabelIDValue);
			super.putUniqueComponentPair(jPanelPlace, "Название класса", jTextFieldNameValue);
		}
		return jPanelPlace;
	}

}
