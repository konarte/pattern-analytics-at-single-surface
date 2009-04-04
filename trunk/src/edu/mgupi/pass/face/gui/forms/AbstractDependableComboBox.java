/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)AbstractDependableComboBox.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractDependableComboBox<T, M> extends AbstractComboBox<T> {

	public AbstractDependableComboBox(final AbstractComboBox<M> parent, boolean canBeEmpty) {
		super(canBeEmpty);
		if (parent != null) {
			parent.addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					M selected = (M) parent.getSelectedItem();
					setFilteredValue(selected);
				}
			});
		}
	}

	private M filteredValue = null;

	protected void setFilteredValue(M value) {
		this.filteredValue = value;
		this.refreshImpl();
	}

	@Override
	protected void refreshImpl2() {
		
		if (filteredValue == null || super.lastLoadedItems == null) {
			return;
		}

		for (T item : super.lastLoadedItems) {
			if (item != null && this.acceptFilter(item, filteredValue)) {
				super.model.addElement(item);
			}
		}
	}

	protected abstract boolean acceptFilter(T item, M filter);

}
