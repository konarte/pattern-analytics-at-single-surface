package edu.mgupi.pass.face;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.orm.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.MainFrame;
import edu.mgupi.pass.face.gui.SplashWindow;
import edu.mgupi.pass.util.CacheIFactory;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

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

	private void changeLookAndFeel(String newLookAndFeel) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		logger.debug("Found look and feel: " + newLookAndFeel);

		if (newLookAndFeel == null) {
			return;
		}

		try {

			UIManager.setLookAndFeel(newLookAndFeel);
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

	private void run() throws Exception {

		Secundomer applicationRun = SecundomerList.registerSecundomer("Application run");
		final Secundomer applicationTotal = SecundomerList.registerSecundomer("Application total work");

		applicationRun.start();
		applicationTotal.start();

		// Attempt to lock file
		final FileChannel channel = new FileOutputStream(LOCK_FILE, false).getChannel();
		final FileLock lock = channel.tryLock();

		// Shutdown hook will automatically close locked channel (if it was locked)
		// Then display some additional debug info
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				applicationTotal.stop();
				SecundomerList.printToOutput(System.out);
				CacheIFactory.close();
				try {
					if (lock != null) {
						lock.release();
					}
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				new File(LOCK_FILE).delete();

				try {
					PassPersistentManager.instance().disposePersistentManager();
				} catch (PersistentException e) {
					e.printStackTrace();
				}

				logger.info("Shutdown " + Const.PROGRAM_NAME_FULL);
			}
		}));

		SplashWindow splash = null;
		MainFrame frame = null;
		try {

			// Open splash
			// Splash is not a toy -- loading took at list 2 seconds!
			try {
				splash = new SplashWindow();
				splash.setSplashText("Загрузка...");
				splash.setVisible(true);
			} catch (Exception e) {
				applicationRun.stop();
				AppHelper.showExceptionDialog(null, "Ошибка при инициализации приложения.", e);
				logger.error("Application error", e);
				throw e;
			}

			// Set locale by default to English
			Locale.setDefault(Locale.ENGLISH);

			String newLookAndFeel = Config.getInstance().getLookAndFeel();

			try {
				this.changeLookAndFeel(newLookAndFeel);
			} catch (Exception e) {
				AppHelper.showExceptionDialog(null, "Ошибка при попытке применить стиль " + newLookAndFeel + ".", e);
			}

			// We want to make good impression, isn't it?
			if (lock == null) {
				logger.error("Attempt to open second instance.");
				AppHelper.showErrorDialog(null,
						"Экземпляр приложения уже запущен. Пожалуйста, закройте предыдущий экземпляр.",
						"Ошибка при запуске");
				System.exit(0);

			}

			// Hibernating...
			splash.setSplashText("Подключение и инициализация БД...");

			logger.info("Initializing Hibernate...");
			PassPersistentManager.instance();

			splash.setSplashText("Загрузка приложения...");
			try {

				// Well, initialization
				frame = (MainFrame) AppHelper.getInstance().getFrameImpl(MainFrame.class);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Caching all using windows -- this is good and fast (about 200-300 msec diff)
				frame.preCache();
				frame.setVisible(true);
			} catch (Exception e) {
				applicationRun.stop();
				AppHelper.showExceptionDialog(null, "Ошибка при загрузке приложения.", e);
				logger.error("Application error", e);
				throw e;
			}
		} finally {
			if (splash != null) {
				splash.setVisible(false);
				splash.dispose();
			}
			applicationRun.stop();
		}

		logger.info("Application ready...");

	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.info("Starting " + Const.PROGRAM_NAME_FULL);
		new Application().run();
	}
}
