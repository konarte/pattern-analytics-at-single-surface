/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SurfaceFilteredTable.java 1.0 31.03.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.Surfaces;
import edu.mgupi.pass.db.surfaces.SurfacesCriteria;
import edu.mgupi.pass.db.surfaces.SurfacesFactory;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.template.AbstractEditorTableModel;
import edu.mgupi.pass.face.gui.template.CommonEditorTableModel;
import edu.mgupi.pass.face.gui.template.TableViewerTemplate;
import edu.mgupi.pass.util.IRefreshable;

public class SurfacesFilteredTable extends TableViewerTemplate<Surfaces> {

	/**
	 * Default constructor.
	 * 
	 * @param owner
	 * 
	 */
	public SurfacesFilteredTable(Frame owner) {
		super(owner, "surfaceFilteredTable", Messages.getString("SurfacesFilteredTable.title"));
		super.setFiltersPanel(getFiltersPanel());
	}

	public Surfaces openWindow() {
		((IRefreshable) jClass).refresh();
		((IRefreshable) jType).refresh();

		super.getDialogAdapter().openDialog();
		return super.selectedObject;
	}

	private CommonEditorTableModel<Surfaces> myTableMode = null;

	private final static String ML_SURFACE = Messages
			.getString("SurfacesFilteredTable.head.ml.yes");
	private final static String SL_SURFACE = Messages.getString("SurfacesFilteredTable.head.ml.no");

	@Override
	protected CommonEditorTableModel<Surfaces> getTableModelImpl(JTable owner) {
		if (myTableMode == null) {
			myTableMode = new CommonEditorTableModel<Surfaces>(owner, SurfacesRecord.class) {

				@Override
				protected Surfaces createInstanceImpl() {
					return SurfacesFactory.createSurfaces();
				}

				@Override
				protected String[] getColumns() {
					return new String[] { Messages.getString("SurfacesFilteredTable.head.id"),
							Messages.getString("SurfacesFilteredTable.head.type"),
							Messages.getString("SurfacesFilteredTable.head.length"),
							Messages.getString("SurfacesFilteredTable.head.width"),
							Messages.getString("SurfacesFilteredTable.head.height"),
							Messages.getString("SurfacesFilteredTable.head.ml") };
				}

				private SurfacesCriteria lastCriteria = null;

				private SurfacesCriteria getCriteria() throws PersistentException {
					if (lastCriteria == null) {
						lastCriteria = new SurfacesCriteria();
					}
					return lastCriteria;
				}

				@Override
				protected List<Surfaces> getDataImpl() throws Exception {
					lastCriteria = null;

					if (jType.getSelectedItem() != null) {
						getCriteria().createSurfaceTypeCriteria().idSurfaceType.eq(jType.getValue()
								.getIdSurfaceType());
					} else {
						if (jClass.getSelectedItem() != null) {
							getCriteria().createSurfaceTypeCriteria().createSurfaceClassCriteria().idSurfaceClass
									.eq(jClass.getValue().getIdSurfaceClass());
						}
					}

					if (lastCriteria != null) {
						return Arrays.asList(SurfacesFactory.listSurfacesByCriteria(lastCriteria));
					}

					return Arrays.asList(SurfacesFactory.listSurfacesByQuery(null, null));
				}

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Surfaces surface = super.getRowAt(rowIndex);
					switch (columnIndex) {
					case 0:
						return surface.getIdSurface();
					case 1:
						return surface.getSurfaceType().getName();
					case 2:
						return surface.getLength();
					case 3:
						return surface.getWidth();
					case 4:
						return surface.getHeight();
					case 5:
						return surface.getMultiLayer() ? ML_SURFACE : SL_SURFACE;
					default:
						return columnIndex;
					}
				}
			};
			myTableMode.setAddOnlyMode();
		}
		return myTableMode;
	}

	private ComboBoxSurfaceClasses jClass;
	private ComboBoxSurfaceTypes jType;

	private JPanel panelFilters;

	private JPanel getFiltersPanel() {
		if (panelFilters == null) {
			panelFilters = new JPanel();
			panelFilters.setLayout(new GridBagLayout());

			jClass = new ComboBoxSurfaceClasses(true);
			jType = new ComboBoxSurfaceTypes(jClass, true);

			panelFilters.add(new JLabel(Messages.getString("SurfacesRecord.class")), AppHelper
					.getJBCForm(0, 0));
			panelFilters.add(jClass, AppHelper.getJBCForm(1, 0));

			panelFilters.add(new JLabel(Messages.getString("SurfacesRecord.type")), AppHelper
					.getJBCForm(0, 1));
			panelFilters.add(jType, AppHelper.getJBCForm(1, 1));
		}
		return panelFilters;
	}

	@Override
	protected void tablePostInit(JTable owner) {
		AbstractEditorTableModel model = (AbstractEditorTableModel) owner.getModel();
		model.setHorizontalAlignMode(0, JLabel.CENTER);
		model.setHorizontalAlignMode(5, JLabel.CENTER);
		model.setColumnWidth(1, 100, 100, 100, 100, 100);

	}

}
