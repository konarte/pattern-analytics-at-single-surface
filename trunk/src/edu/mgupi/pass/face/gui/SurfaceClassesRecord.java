package edu.mgupi.pass.face.gui;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria;
import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;

public class SurfaceClassesRecord extends RecordEditorTemplate<SurfaceClasses> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private final static Logger logger = LoggerFactory.getLogger(SurfaceClassesRecord.class);

	public SurfaceClassesRecord(Frame owner) {
		super(owner, "surfaceClassesRecordDialog", Messages.getString("SurfaceClassesRecord.title"));
		super.setFormPanelData(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		SurfaceTypes type = (SurfaceTypes) foundObject;
		return Messages.getString("SurfaceClassesRecord.exists", type.getName(), type
				.getSurfaceClass().getName());
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<SurfaceClasses> objects)
			throws Exception {

		SurfaceTypesCriteria criteria = new SurfaceTypesCriteria();
		int[] in = new int[objects.size()];
		int idx = 0;
		for (SurfaceClasses def : objects) {
			in[idx++] = def.getIdSurfaceClass();
		}
		criteria.createSurfaceClassCriteria().idSurfaceClass.in(in);
		return criteria;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(SurfaceClasses object, String newValue)
			throws Exception {
		SurfaceClassesCriteria criteria = new SurfaceClassesCriteria();
		criteria.name.eq(newValue);
		if (object.getIdSurfaceClass() != 0) {
			criteria.idSurfaceClass.ne(object.getIdSurfaceClass());
		}
		return criteria;
	}

	@Override
	protected boolean loadFormFromObjectImpl(SurfaceClasses object) throws Exception {
		name = object.getName();
		data = object.getSurfaceImage();

		jLabelIDValue.setText(String.valueOf(object.getIdSurfaceClass()));
		jTextFieldNameValue.setText(name);

		formPanel.setImageRaw(data);

		return true;
	}

	@Override
	protected void restoreObjectImpl(SurfaceClasses object) throws Exception {
		object.setName(name);
		object.setSurfaceImage(data);
	}

	private String name = null;
	private byte[] data = null;

	@Override
	protected void saveFormToObjectImpl(SurfaceClasses object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
		object.setSurfaceImage(formPanel.getRawImage());
	}

	private RecordFormWithImageTemplate formPanel;

	private RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(getJPanelPlace());
		}
		return formPanel;
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;

	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			jPanelPlace = new JPanel();

			jPanelPlace.setLayout(new GridBagLayout());

			jLabelIDValue = new JLabel("0"); //$NON-NLS-1$
			jTextFieldNameValue = new JTextField();

			super.putComponentPair(jPanelPlace,
					Messages.getString("SurfaceClassesRecord.id"), jLabelIDValue); //$NON-NLS-1$
			super.putUniqueComponentPair(jPanelPlace, Messages
					.getString("SurfaceClassesRecord.name"), jTextFieldNameValue); //$NON-NLS-1$
		}
		return jPanelPlace;
	}

}
