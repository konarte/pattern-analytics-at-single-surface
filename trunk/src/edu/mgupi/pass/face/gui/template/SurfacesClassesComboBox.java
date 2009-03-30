package edu.mgupi.pass.face.gui.template;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.orm.PersistentException;

import edu.mgupi.pass.db.surfaces.SurfaceClasses;
import edu.mgupi.pass.db.surfaces.SurfaceClassesFactory;
import edu.mgupi.pass.util.IRefreshable;

public class SurfacesClassesComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SurfacesClassesComboBox() {
		super(new SurfaceClassesModel());
		this.setRenderer(new DefectClassesRenderer());
	}

	private static class SurfaceClassesModel extends DefaultComboBoxModel implements IRefreshable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int refresh() {

			try {
				super.removeAllElements();
				for (SurfaceClasses defClass : SurfaceClassesFactory.listSurfaceClassesByQuery(null, null)) {
					super.addElement(defClass);
				}
				return super.getSize();
			} catch (PersistentException pe) {
				throw new RuntimeException(pe);
			}
		}
	}

	private static class DefectClassesRenderer extends BasicComboBoxRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, value == null ? value : ((SurfaceClasses) value).getName(),
					index, isSelected, cellHasFocus);
		}

	}
}