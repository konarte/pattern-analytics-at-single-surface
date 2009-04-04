package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
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

public class SurfaceTypesRecord extends RecordEditorTemplate<SurfaceTypes> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SurfaceTypesRecord(Frame owner) {
		super(owner, "surfaceTypesRecordDialog", Messages.getString("SurfaceTypesRecord.title"));
		setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		Surfaces surf = (Surfaces) foundObject;
		return Messages.getString("SurfaceTypesRecord.exists", surf.getIdSurface(), surf
				.getSurfaceType().getName());
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
	protected Criteria getUniqueCheckCriteria(SurfaceTypes object, String newValue)
			throws Exception {
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
		
		jComboBoxClassValue.setValue(surfaceClass);

		return true;
	}

	@Override
	protected void restoreObjectImpl(SurfaceTypes object) throws Exception {
		object.setName(this.name);
		object.setSurfaceClass(this.surfaceClass);
	}

	@Override
	protected void putFormToObjectImpl(SurfaceTypes object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
		object.setSurfaceClass(jComboBoxClassValue.getValue());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;
	private ComboBoxSurfaceClasses jComboBoxClassValue = null;

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

			jLabelIDValue = new JLabel("0"); //$NON-NLS-1$
			jTextFieldNameValue = new JTextField();
			jComboBoxClassValue = new ComboBoxSurfaceClasses();

			super.putComponentPair(jPanelPlace, Messages.getString("SurfaceTypesRecord.id"),
					jLabelIDValue);
			super.putComponentPair(jPanelPlace, Messages.getString("SurfaceTypesRecord.class"),
					jComboBoxClassValue);
			super.putUniqueComponentPair(jPanelPlace,
					Messages.getString("SurfaceTypesRecord.name"), jTextFieldNameValue);
		}
		return jPanelPlace;
	}

}
