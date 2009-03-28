package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.util.Const;

public class RecordFormWithImageTemplate extends JPanel {

	private static final long serialVersionUID = 1L;
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
	 * @param top
	 * @param jPanelFormData
	 */
	public RecordFormWithImageTemplate(Window top, JPanel jPanelFormData) {
		super();
		this.jPanelFormData = jPanelFormData;
		initialize();
		if (top != null) {
			top.setMinimumSize(new Dimension(Const.THUMB_WIDTH + 300 + 50, Const.THUMB_HEIGHT + 100));
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Редактирование записи",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12),
				new Color(51, 51, 51)));
		this.add(getJPanelForm(), BorderLayout.CENTER);
		this.add(getJPanelImagePlace(), BorderLayout.EAST);
	}

	private ImageControlAdapter imageControlAdapter = null;

	public ImageControlAdapter getImageControlAdapter() {
		if (imageControlAdapter == null) {
			imageControlAdapter = new ImageControlAdapter(this.getImagePanel());
		}
		return imageControlAdapter;
	}

	private BufferedImage convertedImage = null;

	public void setImageRaw(byte[] rawImage) throws IOException {
		if (rawImage != null) {
			convertedImage = ModuleHelper.covertRawToImage(rawImage);
			imagePanel.setImage(convertedImage);
		} else {
			imagePanel.setImage(null);
		}
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
			imagePanel = new ImagePanel(Const.THUMB_WIDTH, Const.THUMB_WIDTH);
			imagePanel.setLayout(new GridBagLayout());
			imagePanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));

			Dimension dim = new Dimension(Const.THUMB_WIDTH, Const.THUMB_WIDTH);
			imagePanel.setMinimumSize(dim);
			imagePanel.setPreferredSize(dim);
			imagePanel.setMaximumSize(dim);
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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 0;
			jLabelImage = new JLabel();
			jLabelImage.setText("Изображение:");
			jPanelFormImageControl = new JPanel();
			jPanelFormImageControl.setLayout(new GridBagLayout());
			jPanelFormImageControl.add(jLabelImage, gridBagConstraints2);
			jPanelFormImageControl.add(getJPanelImageButtons(), gridBagConstraints3);
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
			jButtonLoad.setText("Загрузить");
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
			jButtonReset.setText("Очистить");
			getImageControlAdapter().registerResetImageButton(jButtonReset);
		}
		return jButtonReset;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
