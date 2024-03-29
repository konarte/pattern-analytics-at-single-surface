package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.util.Const;

public class RecordFormWithImageTemplate extends JPanel {

	private JPanel jPanelForm = null;
	private JPanel jPanelImagePlace = null;
	private ImagePanel imagePanel = null;
	private JPanel jPanelFormImageControl = null;
	private JLabel jLabelImage = null;
	private JPanel jPanelImageButtons = null;
	private JButton jButtonLoad = null;
	private JButton jButtonReset = null;
	private JPanel jPanelFormData = null;

	/**
	 * This is the default constructor
	 * 
	 * @param jPanelFormData
	 */
	public RecordFormWithImageTemplate(JPanel jPanelFormData) {
		this(jPanelFormData, Messages.getString("RecordFormWithImageTemplate.image"), false);
	}

	private boolean readOnly = false;
	private String imageBorderTitle;

	/**
	 * This is the default constructor
	 * 
	 * @param jPanelFormData
	 * @param imageBorderTitle 
	 * @param readOnly
	 */
	public RecordFormWithImageTemplate(JPanel jPanelFormData, String imageBorderTitle, boolean readOnly) {
		super();
		this.readOnly = readOnly;
		this.imageBorderTitle = imageBorderTitle;
		this.jPanelFormData = jPanelFormData;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getJPanelForm(), BorderLayout.CENTER);
		this.add(getJPanelImagePlace(), BorderLayout.EAST);
	}

	private ImageControlAdapter imageControlAdapter = null;

	public ImageControlAdapter getImageControlAdapter() {
		if (imageControlAdapter == null) {
			imageControlAdapter = new ImageControlAdapter(this.getImagePanel(), this.readOnly);
		}
		return imageControlAdapter;
	}

	private BufferedImage convertedImage = null; //  @jve:decl-index=0:

	public void setImageRaw(byte[] rawImage) throws IOException {
		if (rawImage != null) {
			convertedImage = ModuleHelper.covertRawToImage(rawImage);
			imagePanel.setImage(convertedImage);
		} else {
			imagePanel.setImage(null);
		}
		// Force repainting
		imagePanel.paintComponent(imagePanel.getGraphics());
	}

	public byte[] getRawImage() throws IOException {
		BufferedImage newImage = imagePanel.getImage();
		if (convertedImage != newImage) {
			if (newImage != null) {
				byte[] rawImage = ModuleHelper.convertImageToRaw(imagePanel.getImage());
				return rawImage;
			}
		}
		return null;
	}

	/**
	 * This method initializes jPanelForm
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelForm() {
		if (jPanelForm == null) {

			if (jPanelFormData == null) {
				throw new IllegalStateException(
						"Internal error. 'jPanelFormData' must be sets already.");
			}

			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.weighty = 1.0D;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.anchor = GridBagConstraints.NORTH;
			gridBagConstraints1.weightx = 1.0D;

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.anchor = GridBagConstraints.NORTH;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			jPanelForm = new JPanel();
			jPanelForm.setLayout(new GridBagLayout());
			jPanelForm.add(this.jPanelFormData, gridBagConstraints);

			jPanelForm.add(getJPanelFormImageControl(), gridBagConstraints1);
			if (readOnly) {
				this.jLabelImage.setVisible(false);
				this.jButtonLoad.setVisible(false);
				this.jButtonReset.setVisible(false);
			}
		}
		return jPanelForm;
	}

	private JPanel getJPanelImagePlace() {
		if (jPanelImagePlace == null) {

			GridBagConstraints constraints1 = new GridBagConstraints();
			constraints1.gridx = 0;
			constraints1.gridy = 0;
			constraints1.weightx = 1;
			constraints1.weighty = 1;
			constraints1.anchor = GridBagConstraints.NORTHWEST;

			jPanelImagePlace = new JPanel();
			jPanelImagePlace.setLayout(new GridBagLayout());
			jPanelImagePlace.add(getImagePanel(), constraints1);
		}
		return jPanelImagePlace;
	}

	/**
	 * This method initializes jPanelImage
	 * 
	 * @return javax.swing.JPanel
	 */
	public ImagePanel getImagePanel() {
		if (imagePanel == null) {
			imagePanel = new ImagePanel(Const.THUMB_WIDTH, Const.THUMB_WIDTH, true,
					this.imageBorderTitle);
		}
		return imagePanel;
	}

	/**
	 * This method initializes jPanelFormImageControl
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFormImageControl() {
		if (jPanelFormImageControl == null) {
			GridBagConstraints gridBagConstraintsButtons = new GridBagConstraints();
			gridBagConstraintsButtons.gridx = 1;
			gridBagConstraintsButtons.gridy = 0;
			gridBagConstraintsButtons.weightx = 0;
			GridBagConstraints gridBagConstraintsLabel = new GridBagConstraints();
			gridBagConstraintsLabel.gridx = 0;
			gridBagConstraintsLabel.anchor = GridBagConstraints.EAST;
			gridBagConstraintsLabel.gridy = 0;
			gridBagConstraintsLabel.weightx = 1;
			jLabelImage = new JLabel();
			jLabelImage.setText(Messages.getString("RecordFormWithImageTemplate.image") + ":");
			jPanelFormImageControl = new JPanel();
			jPanelFormImageControl.setLayout(new GridBagLayout());
			jPanelFormImageControl.add(jLabelImage, gridBagConstraintsLabel);
			jPanelFormImageControl.add(getJPanelImageButtons(), gridBagConstraintsButtons);
		}
		return jPanelFormImageControl;
	}

	/**
	 * This method initializes jPanelImageButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelImageButtons() {
		if (jPanelImageButtons == null) {
			jPanelImageButtons = new JPanel();
			jPanelImageButtons.setLayout(new FlowLayout());
			jPanelImageButtons.add(getJButtonLoad(), null);
			jPanelImageButtons.add(getJButtonReset(), null);
		}
		return jPanelImageButtons;
	}

	/**
	 * This method initializes jButtonLoad
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonLoad() {
		if (jButtonLoad == null) {
			jButtonLoad = new JButton();
			jButtonLoad.setText(Messages.getString("RecordFormWithImageTemplate.load"));
			getImageControlAdapter().registerLoadImageButton(jButtonLoad);
		}
		return jButtonLoad;
	}

	/**
	 * This method initializes jButtonReset
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonReset() {
		if (jButtonReset == null) {
			jButtonReset = new JButton();
			jButtonReset.setText(Messages.getString("RecordFormWithImageTemplate.clear"));
			getImageControlAdapter().registerResetImageButton(jButtonReset);
		}
		return jButtonReset;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
