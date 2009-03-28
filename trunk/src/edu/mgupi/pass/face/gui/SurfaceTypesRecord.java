package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria;
import edu.mgupi.pass.db.surfaces.Surfaces;
import edu.mgupi.pass.db.surfaces.SurfacesCriteria;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.SurfacesClassesComboBox;

public class SurfaceTypesRecord extends RecordEditorTemplate<SurfaceTypes> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SurfaceTypesRecord(Frame owner) {
		super(owner, "surfaceTypesRecordDialog", "Типы поверхностей");
		setFormPanelData(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		Surfaces surf = (Surfaces) foundObject;
		return "Поверхность с кодом '" + surf.getIdSurface() + "' использует тип '" + surf.getSurfaceType().getName()
				+ "'.";
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<SurfaceTypes> objects) throws Exception {
		SurfacesCriteria criteria = new SurfacesCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (SurfaceTypes def : objects) {
			in[idx++] = def.getIdSurfaceType();
		}
		criteria.createSurfaceTypeCriteria().idSurfaceType.in(in);
		return criteria;
	}

	@Override
	protected Criteria getSaveAllowCriteria(SurfaceTypes object, String newValue) throws Exception {
		SurfaceTypesCriteria criteria = new SurfaceTypesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdSurfaceType() != 0) {
			criteria.idSurfaceType.ne(object.getIdSurfaceType());
		}
		return criteria;
	}

	private String name = null;
	private SurfaceClasses surfaceClass = null;

	@Override
	protected boolean loadFormFromObjectImpl(SurfaceTypes object) throws Exception {

		this.name = object.getName();
		this.surfaceClass = object.getSurfaceClass();

		jLabelIDValue.setText(String.valueOf(object.getIdSurfaceType()));
		jTextFieldNameValue.setText(name);

		if (surfaceClass != null) {
			jComboBoxClassValue.setSelectedItem(surfaceClass);
		} else {
			jComboBoxClassValue.setSelectedIndex(0);
		}

		return true;
	}

	@Override
	protected void restoreObjectImpl(SurfaceTypes object) throws Exception {
		object.setName(this.name);
		object.setSurfaceClass(this.surfaceClass);
	}

	@Override
	protected void saveFormToObjectImpl(SurfaceTypes object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
		object.setSurfaceClass((SurfaceClasses) jComboBoxClassValue.getSelectedItem());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;
	private SurfacesClassesComboBox jComboBoxClassValue = null;

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

			jPanelPlace = new JPanel();

			jPanelPlace.setLayout(new GridBagLayout());

			jLabelIDValue = new JLabel("0");
			jTextFieldNameValue = new JTextField();
			jComboBoxClassValue = new SurfacesClassesComboBox();

			super.putComponentPair(jPanelPlace, "Код", jLabelIDValue);
			super.putComponentPair(jPanelPlace, "Класс поверхности", jComboBoxClassValue);
			super.putUniqueComponentPair(jPanelPlace, "Название типа", jTextFieldNameValue);
		}
		return jPanelPlace;
	}

}
