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
import javax.swing.filechooser.FileFilter;

import edu.mgupi.pass.face.AppHelper;
import edu.mgupi.pass.sources.ISource;
import edu.mgupi.pass.sources.SourceStore;

public class SingleFilePick implements ISource {

	private JFileChooser chooser = null;

	public void init() {
		//
		chooser = new JFileChooser();
		AppHelper.getInstance().registerAdditionalComponent(chooser);

		chooser.setCurrentDirectory(new File("."));
		chooser.setMultiSelectionEnabled(false);

		final String extensions[] = ImageIO.getReaderFormatNames();

		final FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String name = f.getName();
				for (String ext : extensions) {
					if (name.endsWith(ext)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "All supported image types";
			}
		};

		chooser.addChoosableFileFilter(filter);
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
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
						File newFile = (File) event.getNewValue();

						if (newFile != null) {
							ImageIcon icon = new ImageIcon(newFile.getPath());
							if (icon.getIconWidth() > getWidth())
								icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1,
										Image.SCALE_DEFAULT));
							setIcon(icon);
							repaint();
						}
					}
				}
			});
		}
	}

	public void close() {
		if (chooser != null) {
			AppHelper.getInstance().unregisterAdditionalComponent(chooser);
			chooser = null;
		}
	}

	public SourceStore getSingleSource() throws IOException {
		// Do not forget about that!
		// We can change LaF!
		chooser.repaint();

		// Let's pick them
		int result = chooser.showOpenDialog(null);
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
