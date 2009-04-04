package edu.mgupi.pass.face.gui.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.inputs.gui.SingleFilePick;

public class ImageControlAdapter implements ActionListener, PropertyChangeListener {

	private final static Logger logger = LoggerFactory.getLogger(ImageControlAdapter.class);

	private SingleFilePick singleFilePick = null;

	private ImagePanel imagePanel;

	private boolean readOnly = false;

	public ImageControlAdapter(ImagePanel panel, boolean readOnly) {
		this.imagePanel = panel;
		this.readOnly = readOnly;

		this.imagePanel.addPropertyChangeListener(ImagePanel.RPOPERTY_NAME, this);

		logger.trace("Initialize image control adapter " + this);

		singleFilePick = new SingleFilePick();
		singleFilePick.init();
	}

	public void close() {

		logger.trace("Closing image control adapter " + this);

		if (singleFilePick != null) {
			singleFilePick.close();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		if (command.equals("load")) {
			logger.trace("Execute load image...");
			try {
				BufferedImage newImage = singleFilePick.getImage();

				logger.trace("Set up new image {}.", newImage);
				this.imagePanel.setImage(newImage);
			} catch (IOException e1) {
				AppHelper.showExceptionDialog(null, Messages
						.getString("ImageControlAdapter.err.imageSet"), e1);
			}
		} else if (command.equals("reset")) {
			this.imagePanel.setImage(null);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (ImagePanel.RPOPERTY_NAME.equals(evt.getPropertyName())) {
			if (this.resetButton != null && !this.readOnly) {
				this.resetButton.setEnabled(evt.getNewValue() != null);
			}
		}

	}

	private JButton loadButton;

	/**
	 * Register 'load' button on dialog. Clicking this button will provide
	 * 'load' event
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 */
	public void registerLoadImageButton(JButton button) {

		if (loadButton != null) {
			throw new IllegalStateException("'loadButton' already registered ("
					+ loadButton.getText() + ").");
		}

		if (button == null) {
			throw new IllegalArgumentException("Internal error. 'button' must be not null.");
		}

		this.loadButton = button;

		button.setActionCommand("load");
		button.addActionListener(this);

		if (this.readOnly) {
			button.setEnabled(false);
		}
	}

	private JButton resetButton;

	/**
	 * Register 'reset' button. Clicking this button will provide 'reset' event
	 * 
	 * @param button
	 *            instance of button, required
	 * 
	 */
	public void registerResetImageButton(JButton button) {

		if (resetButton != null) {
			throw new IllegalStateException("'resetButton' already registered ("
					+ resetButton.getText() + ").");
		}

		if (button == null) {
			throw new IllegalArgumentException("Internal error. 'button' must be not null.");
		}

		this.resetButton = button;
		button.setActionCommand("reset");

		button.addActionListener(this);
		button.setEnabled(this.imagePanel.getImage() != null);

		if (this.readOnly) {
			button.setEnabled(false);
		}
	}

}
