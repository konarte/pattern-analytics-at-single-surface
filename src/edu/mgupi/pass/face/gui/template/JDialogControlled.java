/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)JDialogControlled.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui.template;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class JDialogControlled extends JDialog implements ActionListener {

	public JDialogControlled(Frame owner) {
		super(owner);
	}

	public JDialogControlled(Frame owner, boolean modal) {
		super(owner, modal);
	}

	private JCheckBox controlCheckBox;
	boolean registeredAlready = false;

	/**
	 * Register checkbox of parent frame, when it clicked -- we'll be show and
	 * hide :)
	 * 
	 * @param controlCheckBox
	 *            checkbox on parent window
	 * 
	 * @throws Exception
	 */
	public void registerControlCheckbox(final JCheckBox controlCheckBox) throws Exception {

		if (registeredAlready) {
			throw new IllegalStateException("Error when registering " + controlCheckBox + " for "
					+ this + ". Already registered.");
		}

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlCheckBox.setSelected(false);
			}
		});

		this.controlCheckBox = controlCheckBox;
		controlCheckBox.addActionListener(this);
		//		
		//		final Window parent = (Window) controlCheckBox.getTopLevelAncestor();
		//
		//		String text = controlCheckBox.getText();
		//		controlCheckBox.setAction(new AbstractAction() {
		//
		//			@Override
		//			public void actionPerformed(ActionEvent e) { // #1
		//				// If window opened -- immediately set state of this window 
		//				
		//
		//			} // #1 method
		//		});
		//
		//		// Strange workaround. 'text' just disappearing.
		//		controlCheckBox.setText(text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() != controlCheckBox) {
			return;
		}

		final Window parent = (Window) controlCheckBox.getTopLevelAncestor();
		if (parent.isVisible()) {
			this.setVisible(controlCheckBox.isSelected());
		} else {
			// If not already opened -- add to events
			parent.addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(WindowEvent e) {
					JDialogControlled.this.setVisible(controlCheckBox.isSelected());
					parent.removeWindowListener(this);
				}
			});
		}
	}
}
