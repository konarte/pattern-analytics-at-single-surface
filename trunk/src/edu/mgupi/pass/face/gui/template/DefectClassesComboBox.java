package edu.mgupi.pass.face.gui.template;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.orm.PersistentException;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesFactory;

public class DefectClassesComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefectClassesComboBox() {
		super(new DefectClassesModel());
		this.setRenderer(new DefectClassesRenderer());
	}

	public void refreshData() throws PersistentException {
		((DefectClassesModel) this.getModel()).refreshData();
	}

	private static class DefectClassesModel extends DefaultComboBoxModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void refreshData() throws PersistentException {

			super.removeAllElements();
			for (DefectClasses defClass : DefectClassesFactory.listDefectClassesByQuery(null, null)) {
				super.addElement(defClass);
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
			return super.getListCellRendererComponent(list, value == null ? value : ((DefectClasses) value).getName(),
					index, isSelected, cellHasFocus);
		}

	}
}
