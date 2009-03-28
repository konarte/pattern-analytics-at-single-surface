package edu.mgupi.pass.face.gui.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import edu.mgupi.pass.face.gui.AppHelper;

/**
 * Special Frame for showing images (hisogram and module images).
 * 
 * @author raidan
 * 
 */
public class ImageFrameTemplate extends JDialog {

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

	boolean registeredAlready = false;

	/**
	 * Register checkbox of parent frame, when it clicked -- we'll be show and
	 * hide :)
	 * 
	 * @param controlCheckBox
	 *            checkbox on parent window
	 * 
	 * @throws Exception
	 */
	public void registerControlCheckbox(final JCheckBox controlCheckBox) throws Exception {

		if (registeredAlready) {
			throw new IllegalStateException("Error when registering " + controlCheckBox + " for " + this
					+ ". Already registered.");
		}

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				controlCheckBox.setSelected(false);
			}
		});

		final Window parent = (Window) controlCheckBox.getTopLevelAncestor();

		String text = controlCheckBox.getText();
		controlCheckBox.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) { // #1
				// If window opened -- immediately set state of this window 
				if (parent.isVisible()) {
					ImageFrameTemplate.this.setVisible(controlCheckBox.isSelected());
				} else {
					// If not already opened -- add to events
					parent.addWindowListener(new WindowAdapter() {
						public void windowOpened(WindowEvent e) {
							ImageFrameTemplate.this.setVisible(controlCheckBox.isSelected());
							parent.removeWindowListener(this);
						}
					});
				}

			} // #1 method
		});

		// Strange workaround. 'text' just disappearing.
		controlCheckBox.setText(text);

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
				AppHelper.showErrorDialog("Internal error. Expected panelImage layout not initialized yet.");
			}
			jCheckBoxScaleBox.setText("Масштаб под размеры окна");
			jCheckBoxScaleBox.setName("scaleButton");
		}
		return jCheckBoxScaleBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
