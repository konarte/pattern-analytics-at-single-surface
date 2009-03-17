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

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.IllegalParameterValueException;
import edu.mgupi.pass.filters.NoSuchParamException;

/**
 * Entry class for application. We set up file-lock (to prevent multiple
 * instancing of application), change Look And Feel and open main window.
 * 
 * @author raidan
 * 
 */
public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	private Application() {
		// Not allowed for any other instances
	}

	// Choose Windows Look and Feel by default
	private final static String PREFFERED_LOOK_AND_FEEL;
	static {
		String OS = System.getProperty("os.name");
		String VERSION = System.getProperty("os.version");
		// Well, I don't know how to do this automatically :)
		if (OS != null && VERSION != null) {
			if (OS.startsWith("Windows") && (VERSION.startsWith("3.") || VERSION.startsWith("4."))) {
				// Classic skin for Windows 95, 98, Me
				PREFFERED_LOOK_AND_FEEL = "Windows Classic";
			} else if (OS.startsWith("Windows")) {
				// Windows 200, XP, 2003, Vista, other
				PREFFERED_LOOK_AND_FEEL = "Windows";
			} else if (OS.startsWith("Mac OS")) {
				// Mac OS
				PREFFERED_LOOK_AND_FEEL = "Nimbus";
			} else {
				PREFFERED_LOOK_AND_FEEL = null;
			}
		} else {
			PREFFERED_LOOK_AND_FEEL = null;
		}

	}

	// Changing Look And Feel, depends on current OS
	private void changeLookAndFeel() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		// If unexpected OS -- keep LaF by default
		if (PREFFERED_LOOK_AND_FEEL == null) {
			return;
		}

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (PREFFERED_LOOK_AND_FEEL.equals(info.getName())) {
					logger.debug("Applying look and feel {}({})", info.getName(), info.getClassName());

					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If not found -- we don't care ^_^
			// OK, choose cross-platform LaF
			logger.debug("Applying stardard look and feel by exception ({})", UIManager
					.getCrossPlatformLookAndFeelClassName());

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}

	}

	// Name for file-lock
	private final static String LOCK_FILE = "app.lock";

	private void run() throws IllegalParameterValueException, NoSuchParamException, InstantiationException,
			IllegalAccessException, IOException, PersistentException {

		final FileChannel channel = new FileOutputStream(LOCK_FILE, false).getChannel();
		final FileLock lock = channel.tryLock();
		if (lock == null) {
			JOptionPane.showMessageDialog(null, "Another instance of PASS already runned. Please, "
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

		SplashWindow splash = new SplashWindow();
		splash.setVisible(true);
		splash.setSplashText("Применение Look And Feel...");

		try {
			this.changeLookAndFeel();
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(null, "Unexpected error when applying LookAndFeel. Try to run "
							+ "application with '-nolaf' key (" + e + ")", "Invalid look and feel",
							JOptionPane.WARNING_MESSAGE);
		}

		splash.setSplashText("Подключение и инициализация БД...");

		logger.debug("Initializing Hibernate...");
		PassPersistentManager.instance();

		splash.setSplashText("Загрузка приложения...");
		MainFrame frame = (MainFrame) AppHelper.getInstance().openWindow(MainFrame.class);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splash.dispose();
		frame.setVisible(true);

		logger.debug("Application PASS ready...");

	}

	public static void main(String[] args) throws Exception {
		logger.debug("Starting PASS.");
		new Application().run();
	}
}
