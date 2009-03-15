package edu.mgupi.pass.face;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	private Application() {
		//
	}

	private final static String PREFFERED_LOOK_AND_FEEL = "Windows";

	private void changeLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		try {
			// If not found -- we don't care ^_^
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (PREFFERED_LOOK_AND_FEEL.equals(info.getName())) {
					logger.debug("Applying look and feel {}({})", info.getName(), info.getClassName());

					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// Choose Windows Look and Feel by default
			logger.debug("Applying stardard look and feel by exception ({})", UIManager
					.getCrossPlatformLookAndFeelClassName());

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}

	}

	private void run() {

		try {
			this.changeLookAndFeel();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unexpected error when applying LookAndFeel. Try to run "
					+ "application with '-nolaf' key", "Invalid look and feel", JOptionPane.WARNING_MESSAGE);
		}

		JFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}

	public static void main(String[] args) throws Exception {
		new Application().run();
	}
}
