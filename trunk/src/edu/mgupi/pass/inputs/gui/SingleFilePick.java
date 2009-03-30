package edu.mgupi.pass.inputs.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.MainFrame;
import edu.mgupi.pass.inputs.IInput;
import edu.mgupi.pass.inputs.InputStore;

public class SingleFilePick implements IInput {

	private final static Logger logger = LoggerFactory.getLogger(SingleFilePick.class);

	private JFileChooser chooser = null;

	public void init() {
		//
		logger.debug("{}. Init single file pick.", this); //$NON-NLS-1$

		chooser = new JFileChooser();
		AppHelper.getInstance().registerAdditionalComponent(chooser);

		chooser.setCurrentDirectory(new File(".")); //$NON-NLS-1$
		chooser.setMultiSelectionEnabled(false);

		String formatNames[] = ImageIO.getReaderFileSuffixes();
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(Messages.getString(
				"SingleFilePick.supportedImages", Arrays.toString(formatNames)), formatNames)); //$NON-NLS-1$
		chooser.setAccessory(new ImagePreviewer(chooser));

	}

	private class ImagePreviewer extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ImagePreviewer(JFileChooser chooser) {
			setPreferredSize(new Dimension(256, 256));
			setBorder(BorderFactory.createEtchedBorder());
			chooser.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) { // #1
					if (event.getPropertyName() != JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
						return;
					}
					File newFile = (File) event.getNewValue();

					if (newFile != null) {
						ImageIcon icon = new ImageIcon(newFile.getPath());
						if (icon.getIconWidth() > getWidth())
							icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1,
									Image.SCALE_DEFAULT));
						setIcon(icon);
						repaint();
					}

				} // #1 addPropertyChangeListener
			});
		}
	}

	public void close() {
		if (chooser != null) {
			logger.debug("{}. Close single file pick.", this); //$NON-NLS-1$
			chooser.removeAll();
			AppHelper.getInstance().unregisterAdditionalComponent(chooser);
			chooser = null;
		}
	}

	public InputStore getSingleSource() throws IOException {
		return this.getSingleSourceImpl(true);
	}

	public BufferedImage getImage() throws IOException {
		InputStore input = this.getSingleSourceImpl(false);
		return input == null ? null : input.getSourceImage();
	}

	private InputStore getSingleSourceImpl(boolean loadFileData) throws IOException {
		// Do not forget about that!
		// We can change LaF!
		chooser.repaint();

		// Let's pick them
		int result = chooser.showOpenDialog(AppHelper.getInstance().searchWindow(MainFrame.class));
		if (result == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null) {
			String imagePath = chooser.getSelectedFile().getPath();

			// Like an TestSourceImpl
			File file = new File(imagePath);

			byte buffer[] = null;
			if (loadFileData) {

				FileInputStream input = new FileInputStream(file);
				buffer = new byte[(int) input.getChannel().size()];
				try {
					input.read(buffer);
				} finally {
					input.close();
				}
			}

			return new InputStore(file.getName(), ImageIO.read(file), buffer);
		} else {
			return null;
		}
	}

}
