package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;

/**
 * Help class for providing selection from opening dialogs. We open them, give
 * control and wait for result.
 * 
 * @author raidan
 * 
 */
public abstract class AbstractDialogAdapter implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(AbstractDialogAdapter.class);

	private JDialog instance;
	private boolean saveRequired = false;

	/**
	 * Common constructor.
	 * 
	 * @param instance
	 *            reference to dialog we adapting, required
	 */
	public AbstractDialogAdapter(JDialog instance) {
		this(instance, false);
	}

	/**
	 * 
	 * @param instance
	 *            is required parameter, contains reference to dialog we
	 *            adapting
	 * 
	 * @param saveRequired
	 *            if true -- this dialog will require entering value, i.e.
	 *            method {@link #saveImpl()} must return true for accepting and
	 *            closing dialog; if false -- this method can return true or
	 *            false
	 */
	public AbstractDialogAdapter(JDialog instance, boolean saveRequired) {

		if (instance == null) {
			throw new IllegalArgumentException("Internal error. 'instance' must be not null.");
		}

		this.instance = instance;
		this.saveRequired = saveRequired;

		// Do not forget about listeners
		this.instance.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AbstractDialogAdapter.this.cancel();
			}
		});

		// Provide support for pressing 'Esc' key on keyboard
		// If 'Esc' will be pressing -- this method do 'Cancel' 
		this.instance.getRootPane().registerKeyboardAction(this, "escape",
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command == null) {
			return;
		}
		if (cancelButton != null && command.equals(cancelButton.getActionCommand())) {
			this.cancel();
		} else if (okButton != null && command.equals(okButton.getActionCommand())) {
			this.save();
		} else if (command.equals("escape")) {
			this.cancel();
		}

	}

	private JButton okButton;

	/**
	 * Register 'OK' button on dialog. Clicking this button will provide 'save'
	 * event
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #save()
	 */
	public void registerOKButton(JButton button) {
		this.okButton = button;

		if (!button.getText().equals("OK")) {
			throw new RuntimeException("Internal error. Please name OK button as 'OK'.");
		}

		button.setText("OK");

		button.setName("ok");
		button.setActionCommand("ok");
		button.addActionListener(this);

	}

	private JButton cancelButton;

	/**
	 * Register 'cance' button. Clicking this button will provide 'cancel' event
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #cancel()
	 */
	public void registerCancelButton(JButton button) {
		this.cancelButton = button;

		if (!button.getText().equals("cancel")) {
			throw new RuntimeException("Internal error. Please name CANCEL button as 'cancel'.");
		}

		button.setText("Отмена");
		button.setName("cancel");
		button.setActionCommand("cancel");

		button.addActionListener(this);

	}

	private boolean setOK = false;

	/**
	 * Main method, open dialog and waiting til it's done. If anything goes
	 * wrong -- message will be shown
	 * 
	 * @return true if pressed 'OK' button, false if 'Cancel' button or window
	 *         was closed by window-button
	 * 
	 * @see #openDialogImpl()
	 */
	public boolean openDialog() {

		logger.debug("Dialog " + instance.getTitle() + " about to open.");

		setOK = false;
		cancelledAlready = false;
		cancelOnly = false;

		if (this.okButton != null && !this.okButton.isVisible()) {
			this.okButton.setVisible(true);
			this.cancelButton.setText("Отмена");
		}

		try {
			this.openDialogImpl();
			this.instance.setVisible(true);
			logger.debug("Dialog " + instance.getTitle() + " finished. Return " + setOK);

			return setOK;

		} catch (Exception e) {
			logger.error("Error when opening window", e);
			AppHelper.showExceptionDialog("Ошибка при открытии окна '" + this.instance.getTitle() + "'", e);
			return false;
		}
	}

	private boolean cancelOnly = false;

	/**
	 * Special method, open dialog and don't wait. We don't care about 'OK' or
	 * 'Cancel' button was closed. Only one button remains ('OK' will be
	 * hidden). If anything goes wrong -- message will be shown.
	 * 
	 * Method returns immediately.
	 * 
	 * @see #openDialogImpl()
	 */
	public void showDialogCancelOnly() {
		if (this.cancelButton == null) {
			JOptionPane
					.showMessageDialog(
							this.instance,
							"Ошибка при открытии окна '"
									+ this.instance.getTitle()
									+ "'. Данное окно не может работать в режиме 'Только отмена'. Кнопка 'cancel' не зарегестрирована.",
							"Неверный режим работы", JOptionPane.OK_OPTION);
			return;
		}

		logger.debug("Dialog " + instance.getTitle() + " about to open in ReadOnly.");

		setOK = false;
		cancelledAlready = false;
		cancelOnly = true;
		if (this.okButton != null && this.okButton.isVisible()) {
			this.okButton.setVisible(false);
			this.cancelButton.setText("Закрыть");
		}

		try {
			this.openDialogImpl();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					instance.setVisible(true);

					logger.debug("Dialog " + instance.getTitle() + " finished.");
				}
			});

		} catch (Exception e) {
			logger.error("Error when opening window", e);
			AppHelper.showExceptionDialog("Ошибка при открытии окна '" + this.instance.getTitle() + "'", e);
		}
	}

	/**
	 * Saving event. If 'OK' was pressed or method called directly. If anything
	 * goes wrong -- message will be shown.
	 * 
	 * @see #saveImpl()
	 */
	public void save() {

		// kekeke
		if (cancelOnly) {
			logger.debug("Dialog " + instance.getTitle() + " skipped saving. ReadOnly mode.");
			return;
		}

		logger.debug("Dialog " + instance.getTitle() + " about to save.");

		setOK = false;
		try {
			setOK = this.saveImpl();

			if (setOK || !saveRequired) {
				logger.debug("Dialog " + instance.getTitle() + " done job. After save is " + setOK);

				this.instance.setVisible(false);
			}

		} catch (Exception e) {
			logger.error("Error when applying settings", e);
			AppHelper.showExceptionDialog("Ошибка при выполнении сохранения.", e);
		}
	}

	private boolean cancelledAlready = false;

	/**
	 * Cancel. If anything goes wrong -- message will be shown.
	 * 
	 * @see #cancelImpl()
	 */
	public void cancel() {

		if (cancelledAlready) {
			logger.debug("Dialog " + instance.getTitle() + " already cancelled.");
			return;
		}

		logger.debug("Dialog " + instance.getTitle() + " about to cancel.");

		setOK = false;
		try {
			this.cancelImpl();

			logger.debug("Dialog " + instance.getTitle() + " done job. After cancel is " + setOK);
		} catch (Exception e) {
			logger.error("Error when applying settings", e);
			AppHelper.showExceptionDialog("Ошибка при выполнении отмены.", e);
		} finally {
			cancelledAlready = true;
			this.instance.setVisible(false);
		}
	}

	/**
	 * This is what called on open dialog. Every time is open -- this method
	 * will run.
	 * 
	 * @throws Exception
	 */
	protected abstract void openDialogImpl() throws Exception;

	/**
	 * This method user for saving data you need. Called only if pressed 'OK'
	 * button (or {@link #save()} called directly.
	 * 
	 * @return true if you actually save/receive data, false if you nothing to
	 *         do. Method {@link #save()} return this value.
	 * @throws Exception
	 */
	protected abstract boolean saveImpl() throws Exception;

	/**
	 * Method used when user pressed 'Cancel' or escape button. You may do
	 * uninitializing stuff.
	 * 
	 * @throws Exception
	 */
	protected abstract void cancelImpl() throws Exception;

}
