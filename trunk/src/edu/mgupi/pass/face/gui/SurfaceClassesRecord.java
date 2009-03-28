package edu.mgupi.pass.face.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria;
import edu.mgupi.pass.db.surfaces.SurfaceClassesFactory;
import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria;
import edu.mgupi.pass.db.surfaces.SurfaceTypesFactory;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;
import edu.mgupi.pass.util.Utils;

public class SurfaceClassesRecord extends RecordEditorTemplate {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private final static Logger logger = LoggerFactory.getLogger(SurfaceClassesRecord.class);

	public SurfaceClassesRecord(Frame owner) {
		super(owner);
		super.setFormPanelData(getFormPanel());
	}

	@Override
	public boolean isDeleteAllowed(Object[] objects) throws Exception {
		for (Object object : objects) {
			SurfaceClasses surfaceClass = ((SurfaceClasses) object);
			int id = surfaceClass.getIdSurfaceClass();

			if (id == 0) {
				continue;
			}

			SurfaceTypesCriteria criteria = new SurfaceTypesCriteria();
			criteria.createSurfaceClassCriteria().idSurfaceClass.eq(surfaceClass.getIdSurfaceClass());

			SurfaceTypes foundType = SurfaceTypesFactory.loadSurfaceTypesByCriteria(criteria);
			if (foundType != null) {
				AppHelper.showErrorDialog("Класс поверхности " + surfaceClass.getName()
						+ " удалить нельзя. К нему привязана поверхности с типом " + foundType.getName() + ".");
				return false;
			}
		}

		return true;
	}

	@Override
	protected boolean isSaveAllowed(Object object) throws Exception {
		SurfaceClasses defectObject = ((SurfaceClasses) object);
		String className = jTextFieldNameValue.getText();

		if (Utils.equals(className, defectObject.getName())) {
			return true;
		}

		SurfaceClassesCriteria criteria = new SurfaceClassesCriteria();
		criteria.name.eq(className);
		if (defectObject.getIdSurfaceClass() != 0) {
			criteria.idSurfaceClass.ne(defectObject.getIdSurfaceClass());
		}

		SurfaceClasses foundClass = SurfaceClassesFactory.loadSurfaceClassesByCriteria(criteria);
		if (foundClass != null) {
			AppHelper.showErrorDialog(null, "Класс поверхности с названием " + className + " уже существует.");
			return false;
		}

		return true;
	}

	@Override
	protected boolean loadFormFromObject(Object object) throws Exception {
		SurfaceClasses surface = (SurfaceClasses) object;
		name = surface.getName();
		data = surface.getSurfaceImage();

		formPanel.setImageRaw(data);
		
		jLabelIDValue.setText(String.valueOf(surface.getIdSurfaceClass()));
		jTextFieldNameValue.setText(name);
		
		jTextFieldNameValue.requestFocusInWindow();

		return true;
	}

	@Override
	protected void restoreObjectImpl(Object object) throws Exception {
		SurfaceClasses surface = (SurfaceClasses) object;
		surface.setName(name);
		surface.setSurfaceImage(data);
	}

	private String name = null;
	private byte[] data = null;

	@Override
	protected void saveFormToObjectImpl(Object object) throws Exception {
		SurfaceClasses surface = (SurfaceClasses) object;
		surface.setName(jTextFieldNameValue.getText());
		surface.setSurfaceImage(formPanel.getRawImage());
	}

	@Override
	protected void setRequiredFields(Map<JTextComponent, JLabel> map) {
		map.put(jTextFieldNameValue, jLabelName);
	}

	private RecordFormWithImageTemplate formPanel;

	private RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(this, getJPanelPlace());
		}
		return formPanel;
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelID = null;
	private JLabel jLabelName = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;

	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			jPanelPlace = new JPanel();

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
