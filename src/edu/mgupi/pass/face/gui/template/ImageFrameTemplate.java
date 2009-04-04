package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.util.Config;

/**
 * Special Frame for showing images (hisogram and module images).
 * 
 * @author raidan
 * 
 */
public class ImageFrameTemplate extends JDialogControlled {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private ImagePanel jPanelImage = null;

	/**
	 * This is the default constructor
	 * 
	 * @param owner
	 * 
	 */
	public ImageFrameTemplate(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * Set image to display on panel
	 * 
	 * @param image
	 *            to display. Can be null (no image will shown).
	 */
	public void setImage(BufferedImage image) {
		this.jPanelImage.setImage(image);
	}

	/**
	 * Return info about image
	 * 
	 * @return true if last {@link #setImage(BufferedImage)} method received
	 *         image, false if not (or not received either)
	 */
	public boolean hasImage() {
		return this.jPanelImage != null && this.jPanelImage.hasImage();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setName("nameFrameTemplate");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setResizable(true);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");

		this.setMinimumSize(new Dimension(150, 80));
		this.setBounds(10, 10, 300, 330);
	}

	private JPanel jPanelScaleBox = null;
	private JCheckBox jCheckBoxScaleBox = null;

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanelScaleBox(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJPanelImage());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanelImage
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelImage() {
		if (jPanelImage == null) {
			jPanelImage = new ImagePanel();
			jPanelImage.setLayout(new GridBagLayout());
		}
		return jPanelImage;
	}

	/**
	 * This method initializes jPanelScaleBox
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelScaleBox() {
		if (jPanelScaleBox == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			jPanelScaleBox = new JPanel();
			jPanelScaleBox.setLayout(new GridBagLayout());
			jPanelScaleBox.add(getJCheckBoxScaleBox(), gridBagConstraints);
		}
		return jPanelScaleBox;
	}

	/**
	 * This method initializes jCheckBoxScaleBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxScaleBox() {
		if (jCheckBoxScaleBox == null) {
			jCheckBoxScaleBox = new JCheckBox();
			if (jPanelImage != null) {
				jPanelImage.registerFitButton(jCheckBoxScaleBox);
			} else {
				AppHelper.showErrorDialog(this, Messages
						.getString("ImageFrameTemplate.err.formNotSet"));
			}
			jCheckBoxScaleBox.setText(Messages.getString("ImageFrameTemplate.scaleButton"));
			jCheckBoxScaleBox.setName(Config.DEFAULT_SCALE_BUTTON_NAME);
		}
		return jCheckBoxScaleBox;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
