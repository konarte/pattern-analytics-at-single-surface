package edu.mgupi.pass.face;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.NoSuchParamException;

public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	private Application() {
		//
	}

	// Choose Windows Look and Feel by default
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
			// OK, choose cross-platform LaF
			logger.debug("Applying stardard look and feel by exception ({})", UIManager
					.getCrossPlatformLookAndFeelClassName());

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}

	}

	private final static String LOCK_FILE = "app.lock";

	private void run() throws IllegalParameterValueException, NoSuchParamException, InstantiationException,
			IllegalAccessException, IOException {

		final FileChannel channel = new FileOutputStream(LOCK_FILE, false).getChannel();
		final FileLock lock = channel.tryLock();
		if (lock == null) {
			JOptionPane.showMessageDialog(null, "Another instance of jNoteXP already runned. Please, "
					+ "close other application.", "Unable to start", JOptionPane.ERROR_MESSAGE);
			System.exit(0);

		}
		// new File(LOCK_FILE).deleteOnExit();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			public void run() {
				logger.debug("Shutdown PASS.");
				try {
					lock.release();
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				new File(LOCK_FILE).delete();
			}
		}));

		try {
			this.changeLookAndFeel();
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null, "Unexpected error when applying LookAndFeel. Try to run "
							+ "application with '-nolaf' key (" + e + ")", "Invalid look and feel",
							JOptionPane.WARNING_MESSAGE);
		}

		MainFrame frame = (MainFrame) AppHelper.getInstance().openWindow(MainFrame.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		logger.debug("Application PASS ready...");
	}

	public static void main(String[] args) throws Exception {
		logger.debug("Starting PASS.");
		new Application().run();
	}
}
