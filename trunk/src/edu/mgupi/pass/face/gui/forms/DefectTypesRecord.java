package edu.mgupi.pass.face.gui.forms;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.Defects;
import edu.mgupi.pass.db.defects.DefectsCriteria;
import edu.mgupi.pass.face.gui.template.ParametersEditorPanel;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;
import edu.mgupi.pass.filters.Param;

public class DefectTypesRecord extends RecordEditorTemplate<DefectTypes> {

	//private final static Logger logger = LoggerFactory.getLogger(DefectTypesRecord.class);

	public DefectTypesRecord(Frame owner) {
		super(owner, "defectTypesRecordDialog", Messages.getString("DefectTypesRecord.title"));
		super.setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		Defects type = (Defects) foundObject;
		return Messages.getString("DefectTypesRecord.exists", type.getIdDefect(), type
				.getDefectType().getName());
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
	protected Criteria getUniqueCheckCriteria(DefectTypes object, String newValue) throws Exception {
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

		jComboBoxClassValue.setValue(defectClass);

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
	protected void putFormToObjectImpl(DefectTypes object) throws Exception {
		object.setName(jTextFieldNameValue.getText());
		object.setDefectClass(jComboBoxClassValue.getValue());
		object.setDefectImage(formPanel.getRawImage());
	}

	private JPanel jPanelPlace = null;
	private JLabel jLabelIDValue = null;
	private JTextField jTextFieldNameValue = null;
	private ComboBoxDefectClasses jComboBoxClassValue = null;
	private ParametersEditorPanel parametersEditorPanel = null;

	private RecordFormWithImageTemplate formPanel = null;

	protected RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			formPanel = new RecordFormWithImageTemplate(getJPanelPlace());
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
			jComboBoxClassValue = new ComboBoxDefectClasses();

			super.putComponentPair(jPanelPlace, Messages.getString("DefectTypesRecord.id"),
					jLabelIDValue);
			super.putComponentPair(jPanelPlace, Messages.getString("DefectTypesRecord.class"),
					jComboBoxClassValue);
			super.putUniqueComponentPair(jPanelPlace, Messages.getString("DefectTypesRecord.name"),
					jTextFieldNameValue);

			this.parametersEditorPanel = new ParametersEditorPanel();

			JPanel jCover = new JPanel();
			jCover.setLayout(new BorderLayout());
			jCover.setBorder(BorderFactory.createTitledBorder(Messages
					.getString("DefectTypesRecord.additionalParams")));
			jCover.add(this.parametersEditorPanel, BorderLayout.CENTER);

			GridBagConstraints jbcParametersEditor = new GridBagConstraints();
			jbcParametersEditor.gridx = 0;
			jbcParametersEditor.gridy = super.getNextComponentGridY();
			jbcParametersEditor.weightx = 1;
			jbcParametersEditor.weighty = 1;
			jbcParametersEditor.fill = GridBagConstraints.HORIZONTAL;
			jbcParametersEditor.anchor = GridBagConstraints.NORTH;
			jbcParametersEditor.gridwidth = 2;
			jPanelPlace.add(jCover, jbcParametersEditor);

			parametersEditorPanel.setModelData(new ArrayList<Param>());

		}
		return jPanelPlace;
	}
} //  @jve:decl-index=0:visual-constraint="16,-1"
