package edu.mgupi.pass.sources.visual;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.AppHelper;
import edu.mgupi.pass.face.MainFrame;
import edu.mgupi.pass.sources.ISource;
import edu.mgupi.pass.sources.SourceStore;

public class SingleFilePick implements ISource {

	private final static Logger logger = LoggerFactory.getLogger(SingleFilePick.class);

	private JFileChooser chooser = null;

	public void init() {
		//
		logger.debug("Init single file pick.");

		chooser = new JFileChooser();
		AppHelper.getInstance().registerAdditionalComponent(chooser);

		chooser.setCurrentDirectory(new File("."));
		chooser.setMultiSelectionEnabled(false);

		chooser.addChoosableFileFilter(new FileNameExtensionFilter("All suppored image types", ImageIO
				.getReaderFormatNames()));
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
							icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
						setIcon(icon);
						repaint();
					}

				} // #1 addPropertyChangeListener
			});
		}
	}

	public void close() {
		if (chooser != null) {
			logger.debug("Closing single file pick.");
			chooser.removeAll();
			AppHelper.getInstance().unregisterAdditionalComponent(chooser);
			chooser = null;
		}
	}

	public SourceStore getSingleSource() throws IOException {
		// Do not forget about that!
		// We can change LaF!
		chooser.repaint();

		// Let's pick them
		int result = chooser.showOpenDialog(AppHelper.getInstance().searchWindow(MainFrame.class));
		if (result == JFileChooser.APPROVE_OPTION) {
			String imagePath = chooser.getSelectedFile().getPath();

			// Lake an TestSourceImpl
			File file = new File(imagePath);

			FileInputStream input = new FileInputStream(file);
			byte buffer[] = new byte[(int) input.getChannel().size()];
			try {
				input.read(buffer);
			} finally {
				input.close();
			}

			return new SourceStore(file.getName(), ImageIO.read(file), buffer);
		} else {
			return null;
		}
	}

}
