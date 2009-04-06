/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SurfaceRecord.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.hibernate.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceTypes;
import edu.mgupi.pass.db.surfaces.Surfaces;
import edu.mgupi.pass.face.gui.template.RecordEditorTemplate;
import edu.mgupi.pass.face.gui.template.RecordFormWithImageTemplate;

public class SurfacesRecord extends RecordEditorTemplate<Surfaces> {

	private final static Logger logger = LoggerFactory.getLogger(SurfacesRecord.class);

	public SurfacesRecord(Frame owner) {
		this(owner, "surfaceRecordTable", Messages.getString("SurfacesRecord.title"), false);
	}

	public SurfacesRecord(Frame owner, String name, String title, boolean readOnly) {
		super(owner, name, title, readOnly);
		super.setFormPanel(getFormPanel());
	}

	@Override
	protected String getDenyDeletionMessage(Object foundObject) {
		return null;
	}

	@Override
	protected Criteria getMultipleDeleteCriteria(Collection<Surfaces> objects) throws Exception {
		return null;
	}

	@Override
	protected Criteria getUniqueCheckCriteria(Surfaces object, String newValue) throws Exception {
		return null;
	}

	private SurfaceTypes type;
	private float width;
	private float height;
	private float length;
	private boolean multiLayer;

	@Override
	protected boolean loadFormFromObjectImpl(Surfaces object) throws Exception {
		type = object.getSurfaceType();
		width = object.getWidth();
		height = object.getHeight();
		length = object.getLength();
		multiLayer = object.getMultiLayer();

		jLabelID.setText(String.valueOf(object.getIdSurface()));
		if (type != null) {
			if (type.getSurfaceClass() != null) {
				jClass.setValue(type.getSurfaceClass());
			}
			jType.setValue(type);
		}
		jWidth.setValue((double) width);
		jHeight.setValue((double) height);
		jLength.setValue((double) length);
		jMultiLayer.setSelectedIndex(multiLayer ? 0 : 1);

		return true;
	}

	@Override
	protected void restoreObjectImpl(Surfaces object) throws Exception {
		object.setSurfaceType(type);
		object.setHeight(height);
		object.setLength(length);
		object.setWidth(width);
		object.setMultiLayer(multiLayer);
	}

	@Override
	protected void putFormToObjectImpl(Surfaces object) throws Exception {
		object.setHeight(((Double) jHeight.getValue()).floatValue());
		object.setLength(((Double) jLength.getValue()).floatValue());
		object.setWidth(((Double) jWidth.getValue()).floatValue());
		object.setMultiLayer(jMultiLayer.getSelectedIndex() == 0);
		object.setSurfaceType(jType.getValue());
	}

	private JLabel jLabelID;
	private ComboBoxSurfaceClasses jClass;
	private ComboBoxSurfaceTypes jType;
	private JFormattedTextField jWidth;
	private JFormattedTextField jHeight;
	private JFormattedTextField jLength;
	private JComboBox jMultiLayer;

	private RecordFormWithImageTemplate formPanel = null;
	private JPanel jPanelPlace = null;

	protected RecordFormWithImageTemplate getFormPanel() {
		if (formPanel == null) {
			//formPanel = super.createDefaultFormPanel(getJPanelPlace());
			formPanel = new RecordFormWithImageTemplate(getJPanelPlace(), Messages
					.getString("SurfacesRecord.imageBorder"), true);
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

			jLabelID = new JLabel("0");
			jClass = new ComboBoxSurfaceClasses();
			jClass.setName("surfaceRecordClass");
			jClass.addActionListener(this);

			jType = new ComboBoxSurfaceTypes(jClass);
			jType.setName("surfaceRecordType");

			NumberFormatter positiveFormatter = new NumberFormatter(new DecimalFormat("0.0#######"));
			positiveFormatter.setMinimum(0.0);

			jWidth = new JFormattedTextField(positiveFormatter);
			jWidth.setName("jWidth");
			
			jHeight = new JFormattedTextField(positiveFormatter);
			jHeight.setName("jHeight");
			
			jLength = new JFormattedTextField(positiveFormatter);
			jLength.setName("jLength");

			jMultiLayer = new JComboBox(new String[] { Messages.getString("SurfacesRecord.ml.yes"),
					Messages.getString("SurfacesRecord.ml.no") });

			super.putComponentPair(jPanelPlace, Messages.getString("SurfacesRecord.id"), jLabelID);
			super.putComponentPair(jPanelPlace, Messages.getString("SurfacesRecord.class"), jClass);
			super.putComponentPair(jPanelPlace, Messages.getString("SurfacesRecord.type"), jType);
			super.putRequiredComponentPair(jPanelPlace,
					Messages.getString("SurfacesRecord.length"), jLength);
			super.putRequiredComponentPair(jPanelPlace, Messages.getString("SurfacesRecord.width"),
					jWidth);
			super.putRequiredComponentPair(jPanelPlace,
					Messages.getString("SurfacesRecord.height"), jHeight);
			super.putComponentPair(jPanelPlace, Messages.getString("SurfacesRecord.ml"),
					jMultiLayer);

		}
		return jPanelPlace;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == jClass) {

			SurfaceClasses selectedClass = (SurfaceClasses) jClass.getSelectedItem();
			if (selectedClass != null) {
				try {
					this.formPanel.setImageRaw(selectedClass.getSurfaceImage());
				} catch (IOException e1) {
					logger.error("Error when applying image", e1);
				}
			}
		}
	}

}
