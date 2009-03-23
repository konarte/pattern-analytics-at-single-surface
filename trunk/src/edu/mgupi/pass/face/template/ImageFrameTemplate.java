package edu.mgupi.pass.face.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class ImageFrameTemplate extends JDialog implements ActionListener {

	//private final static Logger logger = LoggerFactory.getLogger(ImageFrameTemplate.class);  //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private ImagePanel jPanelImage = null;

	/**
	 * This is the default constructor
	 * 
	 */
	public ImageFrameTemplate() {
		super();
		initialize();
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

	private Frame parent = null;
	private JCheckBox controlCheckBox = null;

	public void registerControlCheckbox(Frame parent, JCheckBox controlCheckBox) throws Exception {

		if (this.controlCheckBox != null) {
			throw new Exception("Error when registering " + controlCheckBox + " for " + this + ". Already set.");
		}
		if (parent == null) {
			throw new IllegalArgumentException("Internal error. 'parent' must be not null.");
		}
		if (controlCheckBox == null) {
			throw new IllegalArgumentException("Internal error. 'controlCheckBox' must be not null.");
		}

		this.parent = parent;
		this.controlCheckBox = controlCheckBox;
		this.addWindowListener(new OnHideWindowAdapter(controlCheckBox));
		controlCheckBox.setActionCommand("hideMe");
		controlCheckBox.addActionListener(this);

	}

	/**
	 * Set image to display on panel
	 * 
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.jPanelImage.setImage(image);
	}

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

	private class OnHideWindowAdapter extends WindowAdapter {

		private JCheckBox controlCheckBox;

		public OnHideWindowAdapter(JCheckBox controlCheckBox) {
			this.controlCheckBox = controlCheckBox;
		}

		public void windowClosing(WindowEvent e) {
			controlCheckBox.setSelected(false);
		}
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
				JOptionPane.showMessageDialog(null, "Internal error. Expected panelImage layout not initialized yet.",
						"Invalid layout programming", JOptionPane.ERROR_MESSAGE);
			}
			jCheckBoxScaleBox.setText("Масштаб под размеры окна");
			jCheckBoxScaleBox.setName("scaleButton");
		}
		return jCheckBoxScaleBox;
	}

	public boolean hasImage() {
		return this.jPanelImage != null && this.jPanelImage.hasImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		if (command == null) {
			return;
		}

		if (command.equals("hideMe")) {
			if (parent.isVisible()) {
				this.setVisible(controlCheckBox.isSelected());
			} else {
				parent.addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						ImageFrameTemplate.this.setVisible(controlCheckBox.isSelected());
						parent.removeWindowListener(this);
					}
				});
			}
		}

	}

} // @jve:decl-index=0:visual-constraint="10,10"
