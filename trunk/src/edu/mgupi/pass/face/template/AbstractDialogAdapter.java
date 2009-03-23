package edu.mgupi.pass.face.template;

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

import edu.mgupi.pass.face.AppHelper;

public abstract class AbstractDialogAdapter implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(AbstractDialogAdapter.class);

	private JDialog instance;
	private boolean saveRequired = false;

	public AbstractDialogAdapter(JDialog instance) {
		this(instance, false);
	}

	public AbstractDialogAdapter(JDialog instance, boolean saveRequired) {
		if (instance == null) {
			throw new IllegalArgumentException("Internal error. 'instance' must be not null.");
		}
		this.instance = instance;
		this.saveRequired = saveRequired;

		this.instance.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				AbstractDialogAdapter.this.cancel();
			}
		});

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
			AppHelper.showExceptionDialog(this.instance, "Ошибка при открытии окна '" + this.instance.getTitle() + "'",
					e);
			return false;
		}
	}

	private boolean cancelOnly = false;

	public void showDialogCancelOnly() {
		if (this.cancelButton == null) {
			JOptionPane.showMessageDialog(this.instance, "Ошибка при открытии окна '" + this.instance.getTitle()
					+ "'. Данное окно не может работать в режиме 'Только отмена'. Кнопка 'cance' не зарегестрирована.",
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
			AppHelper.showExceptionDialog(this.instance, "Ошибка при открытии окна '" + this.instance.getTitle() + "'",
					e);
		}
	}

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
			AppHelper.showExceptionDialog(this.instance, "Ошибка при выполнении сохранения.", e);
		}
	}

	private boolean cancelledAlready = false;

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
			AppHelper.showExceptionDialog(this.instance, "Ошибка при выполнении отмены.", e);
		} finally {
			cancelledAlready = true;
			this.instance.setVisible(false);
		}
	}

	protected abstract void openDialogImpl() throws Exception;

	protected abstract boolean saveImpl() throws Exception;

	protected abstract void cancelImpl() throws Exception;

}
