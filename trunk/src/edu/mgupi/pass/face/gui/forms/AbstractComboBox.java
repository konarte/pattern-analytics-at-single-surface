/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)AbstractComboBox.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.forms;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.orm.PersistentException;

import edu.mgupi.pass.util.IRefreshable;

public abstract class AbstractComboBox<T> extends JComboBox implements IRefreshable {

	protected DefaultComboBoxModel model = null;

	private boolean canBeEmpty = false;

	public AbstractComboBox(boolean canBeEmpty) {
		super();
		this.setRenderer(new Renderer());
		this.canBeEmpty = canBeEmpty;
		this.model = (DefaultComboBoxModel) super.getModel();
	}

	public DefaultComboBoxModel getMyModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setValue(T value) {
		if (value == null) {
			return;
		}
		
		System.out.println("Try to set " + value + " to " + this.getName());
		for (int i = 0; i < model.getSize(); i++) {
			T currentValue = (T) model.getElementAt(i);
			if (objectEquals(currentValue, value)) {
				this.setSelectedIndex(i);
				return;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) model.getSelectedItem();
	}

	public boolean objectEquals(T first, T second) {
		if (first != null && second != null) {
			return this.objectEqualsImpl(first, second);
		}
		return false;
	}

	protected abstract boolean objectEqualsImpl(T first, T second);

	protected T[] lastLoadedItems = null;

	@SuppressWarnings("unchecked")
	public int refresh() {
		try {

			T oldSelectedItem = (T) this.getSelectedItem();

			this.lastLoadedItems = this.getRowsImpl();
			this.refreshImpl();

			this.setValue(oldSelectedItem);
			return lastLoadedItems == null ? 0 : lastLoadedItems.length;
		} catch (PersistentException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected void refreshImpl() {
		model.removeAllElements();
		if (this.canBeEmpty) {
			model.addElement(null);
		}
		this.refreshImpl2();
	}

	protected void refreshImpl2() {
		if (lastLoadedItems == null) {
			return;
		}
		for (T element : this.lastLoadedItems) {
			model.addElement(element);
		}
	}

	protected abstract T[] getRowsImpl() throws PersistentException;

	private class Renderer extends BasicComboBoxRenderer {
		@SuppressWarnings("unchecked")
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, value == null ? value
					: AbstractComboBox.this.getRenderedValue((T) value), index, isSelected,
					cellHasFocus);
		}

	}

	protected abstract String getRenderedValue(T value);
}
