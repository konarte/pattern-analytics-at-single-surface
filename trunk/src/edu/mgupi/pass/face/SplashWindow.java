package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import edu.mgupi.pass.face.template.ImagePanel;
import edu.mgupi.pass.util.Const;

public class SplashWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private ImagePanel jPanelSplash = null;
	private JLabel jLabelInfo = null;
	private JLabel jLabelProgram = null;
	private JLabel jLabelVersion = null;

	/**
	 * @throws IOException
	 */
	public SplashWindow() throws IOException {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @throws IOException
	 * 
	 */
	private void initialize() throws IOException {
		this.setResizable(false);
		this.setName("splashFrame");
		this.setBounds(new Rectangle(0, 0, 450, 200));
		Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);

		// ---------
		BufferedImage image = ImageIO.read(ClassLoader.getSystemResourceAsStream("splash-temp.jpg"));
		this.setSize(image.getWidth(), image.getHeight());
		this.jPanelSplash.setImage(image);

		this.setLocation((int) point.getX() - this.getWidth() / 2, (int) point.getY() - this.getHeight() / 2);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(true);
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setToolTipText("Splash");
			jContentPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			jContentPane.add(getJPanelSplash(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelSplash
	 * 
	 * @return javax.swing.JPanel
	 * @throws IOException
	 */
	private JPanel getJPanelSplash() {
		if (jPanelSplash == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints.insets = new Insets(0, 5, 5, 5);
			gridBagConstraints.gridy = 2;
			jLabelVersion = new JLabel();
			jLabelVersion.setText(Const.LAST_PROGRAM_NAME);
			jLabelVersion.setFont(new Font("DialogInput", Font.BOLD, 14));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints11.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.fill = GridBagConstraints.NONE;
			jLabelProgram = new JLabel();
			jLabelProgram.setText(Const.PROGRAM_NAME);
			jLabelProgram.setFont(new Font("DialogInput", Font.BOLD, 18));
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.anchor = GridBagConstraints.SOUTHWEST;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.gridy = 3;
			jLabelInfo = new JLabel();
			jLabelInfo.setText("Загрузка...");
			jPanelSplash = new ImagePanel();
			jPanelSplash.setLayout(new GridBagLayout());
			jPanelSplash.add(jLabelProgram, gridBagConstraints11);
			jPanelSplash.add(jLabelVersion, gridBagConstraints);
			jPanelSplash.add(jLabelInfo, gridBagConstraints1);
		}
		return jPanelSplash;
	}

	public void setSplashText(String text) {
		this.jLabelInfo.setText(text);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
