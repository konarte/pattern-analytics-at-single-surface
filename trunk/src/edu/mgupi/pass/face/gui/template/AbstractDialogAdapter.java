package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;

/**
 * Special adapter for providing control from dialogs. We open them, give
 * control and wait for result.
 * 
 * Special registered buttons {@link #registerOKButton(JButton)} and
 * {@link #registerCancelButton(JButton)} helps to open and close page.
 * 
 * @author raidan
 * 
 */
public abstract class AbstractDialogAdapter implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(AbstractDialogAdapter.class);

	private JDialog owner;
	private boolean saveRequired = false;
	private AbstractEditorTableModel editorMode = null;

	/**
	 * Common constructor.
	 * 
	 * @param owner
	 *            reference to dialog we adapting, required
	 */
	public AbstractDialogAdapter(JDialog owner) {
		this(owner, null, false);
	}

	/**
	 * Common constructor.
	 * 
	 * @param owner
	 *            reference to dialog we adapting, required
	 * @param editorModel
	 *            table list model for integration with
	 */
	public AbstractDialogAdapter(JDialog owner, AbstractEditorTableModel editorModel) {
		this(owner, editorModel, false);
	}

	/**
	 * Constructor for owner and special flag.
	 * 
	 * @param owner
	 *            reference to dialog we adapting, required
	 * @param saveRequired
	 *            if true -- this dialog will require entering value, i.e.
	 *            method {@link #saveImpl()} must return true for accepting and
	 *            closing dialog; if false -- this method can return true or
	 *            false
	 */
	public AbstractDialogAdapter(JDialog owner, boolean saveRequired) {
		this(owner, null, saveRequired);
	}

	/**
	 * Expanded constructor.
	 * 
	 * @param owner
	 *            reference to dialog we adapting, required
	 * @param editorModel
	 *            table list model for integration with
	 * @param saveRequired
	 *            if true -- this dialog will require entering value, i.e.
	 *            method {@link #saveImpl()} must return true for accepting and
	 *            closing dialog; if false -- this method can return true or
	 *            false
	 */
	public AbstractDialogAdapter(JDialog owner, final AbstractEditorTableModel editorModel, boolean saveRequired) {

		if (owner == null) {
			throw new IllegalArgumentException("Internal error. 'instance' must be not null.");
		}

		logger.debug("Initialize dialog control adapter " + this);

		this.owner = owner;
		this.saveRequired = saveRequired;
		this.editorMode = editorModel;

		// Do not forget about listeners
		this.owner.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AbstractDialogAdapter.this.cancel();
			}
		});

		/*
		 * Provide support for pressing 'Esc' key on keyboard.
		 * 
		 * If 'Esc' will be pressing -- this method will call 'Cancel'
		 */
		this.owner.getRootPane().registerKeyboardAction(this, "escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		/*
		 * Provide support for pressing 'Enter' key on keyboard.
		 * 
		 * If 'Enter' will be pressing -- this method will call 'Save'
		 */

		this.owner.getRootPane().registerKeyboardAction(this, "accept", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command == null) {
			return;
		}
		if (command.equals("escape") || command.equals("cancel")) {
			this.cancel();
		} else if (command.equals("accept") || command.equals("OK")) {
			this.save();
		}

	}

	private JButton okButton;

	/**
	 * Register 'OK' button on dialog. Clicking this button will provide 'OK'
	 * (save) event.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #save()
	 */
	public void registerOKButton(JButton button) {

		if (okButton != null) {
			throw new IllegalStateException("'okButton' already registered (" + okButton.getText() + ").");
		}
		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

		this.okButton = button;

		if (!button.getText().equals("OK")) {
			throw new RuntimeException("Internal error. Please name OK button as 'OK'.");
		}

		button.setText("OK");

		button.setName("OK");
		button.setActionCommand("OK");
		button.addActionListener(this);

	}

	private JButton cancelButton;

	/**
	 * Register 'cancel' button. Clicking this button will provide 'cancel'
	 * event.
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 * @see #cancel()
	 */
	public void registerCancelButton(JButton button) {

		if (cancelButton != null) {
			throw new IllegalStateException("'cancelButton' already registered (" + cancelButton.getText() + ").");
		}
		if (button == null) {
			throw new IllegalStateException("Internal error. Parameter 'button' must be not null.");
		}

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
	 * wrong -- message will be shown.
	 * 
	 * @return true if pressed 'OK' button, false if 'Cancel' button or window
	 *         was closed by window-button
	 * 
	 * @see #openDialogImpl()
	 */
	public boolean openDialog() {
		logger.debug("Dialog '{}' about to open.", owner.getTitle());
		return this.openDialog(false);
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
			AppHelper.showErrorDialog(this.owner, "Ошибка при открытии окна '" + this.owner.getTitle()
					+ "'. Данное окно не может работать в режиме 'Только отмена'. "
					+ "Кнопка 'cancel' не зарегестрирована.", "Неверный режим работы");
			return;
		}

		logger.debug("Dialog '{}' about to open in ReadOnly.", owner.getTitle());
		this.openDialog(true);

	}

	private boolean openDialog(boolean cancelOnly) {
		setOK = false;
		cancelledAlready = false;
		this.cancelOnly = cancelOnly;

		if (!cancelOnly && this.okButton != null && !this.okButton.isVisible()) {
			this.okButton.setVisible(true);
			this.cancelButton.setText("Отмена");
		} else if (cancelOnly && this.okButton != null && this.okButton.isVisible()) {
			this.okButton.setVisible(false);
			this.cancelButton.setText("Закрыть");
		}

		try {
			if (editorMode != null) {
				editorMode.onOpenWindow();
			}
			this.openDialogImpl();

			if (this.cancelOnly) {
				logger.trace("Dialog '{}' will be invoked later.", owner.getTitle());

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							owner.setVisible(true);
							logger.debug("Dialog '{}' finisheds.", owner.getTitle());
						} catch (Throwable t) {
							AppHelper.showExceptionDialog(owner, "Ошибка при открытии окна '" + owner.getTitle() + "'",
									t);
						}
					}
				});
			} else {
				this.owner.setVisible(true);
				logger.debug("Dialog '' finished. Return {}.", owner.getTitle(), setOK);
			}

			return setOK;

		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, "Ошибка при открытии окна '" + this.owner.getTitle() + "'", t);
			return false;
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
		// This is potentially strange situation, at least during tests
		// Do not forget, that dialogs int 'cancelOnly' mode can't do 'save' action
		if (cancelOnly) {
			logger.debug("Dialog '{}' skipped saving. ReadOnly mode.", owner.getTitle());
			return;
		}

		logger.debug("Dialog '{}' about to save.", owner.getTitle());

		setOK = false;
		try {

			setOK = this.saveImpl();
			if (editorMode != null) {
				editorMode.onCloseWindow();
			}

			if (setOK || !saveRequired) {
				logger.debug("Dialog '{}' done job. After save is {}.", owner.getTitle(), setOK);

				this.owner.setVisible(false);
			}

		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, "Ошибка при выполнении сохранения.", t);
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
			logger.debug("Dialog '{}' already cancelled.", owner.getTitle());
			return;
		}

		logger.debug("Dialog '{}' about to cancel.", owner.getTitle());

		setOK = false;
		try {

			this.cancelImpl();
			if (editorMode != null) {
				editorMode.onCloseWindow();
			}

			logger.debug("Dialog '{}' done job. After cancel is {}.", owner.getTitle(), setOK);
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(this.owner, "Ошибка при выполнении отмены.", t);
		} finally {
			cancelledAlready = true;
			this.owner.setVisible(false);
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
	 * @return true if you actually save data, false if you nothing to do.
	 *         Method {@link #save()} return this value.
	 * @throws Exception
	 */
	protected abstract boolean saveImpl() throws Exception;

	/**
	 * Method used when user pressed 'Cancel' or escape button. You may do some
	 * canceling stuff.
	 * 
	 * @throws Exception
	 */
	protected abstract void cancelImpl() throws Exception;

}
