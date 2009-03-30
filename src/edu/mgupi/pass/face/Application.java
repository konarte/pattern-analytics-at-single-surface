package edu.mgupi.pass.face;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.ImageIcon;
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
import edu.mgupi.pass.util.Utils;

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
			/*
			 * If not found -- we don't care ^_^.
			 * 
			 * OK, choose cross-platform LaF.
			 */
			logger.debug("Applying stardard look and feel by exception ({})", UIManager
					.getCrossPlatformLookAndFeelClassName());

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}

	}

	// Name for file-lock
	private final static String LOCK_FILE = "app.lock";

	private FileChannel channel;
	private FileLock lock;

	private void run() {

		Secundomer applicationRun = SecundomerList.registerSecundomer("Application run");
		final Secundomer applicationTotal = SecundomerList.registerSecundomer("Application total work");

		applicationRun.start();
		applicationTotal.start();

		// Attempt to lock file
		try {
			channel = new FileOutputStream(LOCK_FILE, false).getChannel();
			lock = channel.tryLock();
		} catch (Exception e) {
			logger.error("Error when try to lock file. At this point we continue work.", e);
		}

		/*
		 * Shutdown hook will automatically close locked channel (if it was
		 * locked).
		 * 
		 * Then display some additional debug info.
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				applicationTotal.stop();
				SecundomerList.printToDebugLogger(logger);

				CacheIFactory.close();
				try {
					if (lock != null) {
						lock.release();
						lock = null;
					}
					if (channel != null) {
						channel.close();
						channel = null;
					}
					new File(LOCK_FILE).delete();
				} catch (IOException e) {
					logger.error("Error when releasing file lock.", e);
				}

				try {
					PassPersistentManager.instance().disposePersistentManager();
				} catch (PersistentException e) {
					logger.error("Error when closing Hibernate.", e);
				}

				logger.info("Shutdown " + Const.PROGRAM_NAME_FULL);
			}
		}));

		SplashWindow splash = null;
		try {

			byte[] iconData = null;
			try {
				iconData = Utils.loadFromResource(Const.FORM_ICON_IMAGE_PATH);
				ImageIcon icon = new ImageIcon(iconData);
				AppHelper.getInstance().setWindowsIcon(icon.getImage());
			} catch (IOException io) {
				logger.error("Error when setting image icon.", io);
			}

			/*
			 * Open splash window.
			 * 
			 * Splash is not a toy -- loading took's at list 2-3 seconds!
			 * 
			 * Big time is initializing Hibernate, but loading and filling data
			 * by all caches took some time too.
			 */
			try {
				splash = new SplashWindow(Const.SPLASH_IMAGE_PATH);
				splash.setSplashText("Загрузка...");
				splash.setVisible(true);
			} catch (Exception e) {
				applicationRun.stop();
				AppHelper.showExceptionDialog(null, "Ошибка при инициализации приложения.", e);
				return; //
			}

			// We do not change local
			String newLookAndFeel = Config.getInstance().getLookAndFeel();

			try {
				this.changeLookAndFeel(newLookAndFeel);
			} catch (Exception e) {
				AppHelper.showExceptionDialog(null, "Ошибка при попытке применить стиль " + newLookAndFeel + ".", e);
			}

			// We want to make good impression, isn't it?
			if (channel != null && lock == null) {
				AppHelper.showErrorDialog(null,
						"Экземпляр приложения уже запущен. Пожалуйста, закройте предыдущий экземпляр.",
						"Ошибка при запуске");
				System.exit(0);
			}

			// Hibernating...
			splash.setSplashText("Подключение и инициализация БД...");

			try {
				logger.info("Initializing Hibernate...");
				PassPersistentManager.instance();

				splash.setSplashText("Загрузка приложения...");

				// Well, initialization
				MainFrame frame = (MainFrame) AppHelper.getInstance().getFrameImpl(MainFrame.class);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Caching all using windows -- this is good and fast (about 200-300 msec diff)
				frame.preCache();
				frame.setVisible(true);
			} catch (Throwable t) {
				applicationRun.stop();
				AppHelper.showExceptionDialog(null, "Ошибка при загрузке приложения.", t);
				return; //
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
	 */
	public static void main(String[] args) {
		logger.info("Starting " + Const.PROGRAM_NAME_FULL);
		try {
			new Application().run();
		} catch (Throwable t) {
			AppHelper.showExceptionDialog(null, "Критическая ошибка при запуске приложения.", t);
		}
	}
}
