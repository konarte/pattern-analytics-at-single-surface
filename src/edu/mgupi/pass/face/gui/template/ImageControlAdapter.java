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

	public ImageControlAdapter(ImagePanel panel) {
		this.imagePanel = panel;

		this.imagePanel.addPropertyChangeListener(ImagePanel.RPOPERTY_NAME, this);

		logger.debug("Initialize image control adapter " + this);

		singleFilePick = new SingleFilePick();
		singleFilePick.init();
	}

	public void close() {

		logger.debug("Closing image control adapter " + this);

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
			logger.debug("Execute load image...");
			try {
				BufferedImage newImage = singleFilePick.getImage();

				logger.debug("Set up new image {}.", newImage);
				this.imagePanel.setImage(newImage);
			} catch (IOException e1) {
				AppHelper.showExceptionDialog("Ошибка при загрузке изображения", e1);
			}
		} else if (command.equals("reset")) {
			this.imagePanel.setImage(null);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (ImagePanel.RPOPERTY_NAME.equals(evt.getPropertyName())) {
			if (this.resetButton != null) {
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
			throw new IllegalStateException("'loadButton' already registered (" + loadButton.getText() + ").");
		}

		this.loadButton = button;

		button.setActionCommand("load");
		button.addActionListener(this);

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
			throw new IllegalStateException("'resetButton' already registered (" + resetButton.getText() + ").");
		}

		this.resetButton = button;
		button.setActionCommand("reset");

		button.addActionListener(this);
		button.setEnabled(this.imagePanel.getImage() != null);

	}

}
