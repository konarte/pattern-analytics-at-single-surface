package edu.mgupi.pass.face;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.hibernate.cfg.Configuration;
import org.hibernate.exception.GenericJDBCException;
import org.orm.PersistentException;
import org.orm.cfg.JDBCConnectionSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.LoginWindow;
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
public class Application implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	// Name for file-lock
	private final static String LOCK_FILE = "app.lock";

	private FileChannel channel;
	private FileLock lock;

	private Secundomer applicationStart = SecundomerList.registerSecundomer("Application start");
	private Secundomer applicationTotal = SecundomerList
			.registerSecundomer("Application total work");

	private SplashWindow splash = null;

	/**
	 * Default constructor.
	 */
	private Application() {

		applicationStart.start();
		applicationTotal.start();

		/*
		 * This is pretty safe.
		 * 
		 * Java does not initialize static classes and methods before it's been
		 * needed.
		 */
		AppHelper.setLocale(Config.getInstance().getCurrentLocale());

		// Attempt to lock file
		try {
			File file = new File(LOCK_FILE);
			channel = new FileOutputStream(file, false).getChannel();
			lock = channel.tryLock();

			restartAvailable = Utils.isWeLoadedFromJar()
					&& new File(Const.DEFAULT_PACKED_JAR_NAME).exists();

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
				Application.this.onShutdownImpl();
			}
		}));

		String newLookAndFeel = Config.getInstance().getLookAndFeel();
		try {
			logger.debug("Found look and feel: " + newLookAndFeel);

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

		} catch (Exception e) {
			AppHelper.showExceptionDialog(null, Messages.getString("Application.err.laf",
					newLookAndFeel), e);
		}

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
		 * Big time is initializing Hibernate, but loading and filling data by
		 * all caches took some time too.
		 */
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					try {
						splash = new SplashWindow(Const.SPLASH_IMAGE_PATH);
						splash.setSplashText(Messages.getString("Application.mess.loading"));
						splash.setVisible(true);
					} catch (Exception e) {
						logger.error("Unable to create splash window", e);
						if (splash != null) {
							splash.dispose();
							splash = null;
						}
					}
				}
			});
		} catch (Exception e) {
			logger.error("Unable to create splash window", e);
			if (splash != null) {
				splash.dispose();
				splash = null;
			}
		}
	}

	/**
	 * Actions we do after application shutdown.
	 */
	private void onShutdownImpl() {
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

		try {
			if (restartAvailable && restartRequired) {
				logger.info("Restart {}.", Const.PROGRAM_NAME_FULL);
				Runtime.getRuntime().exec("java -jar " + Const.DEFAULT_PACKED_JAR_NAME);
			} else {
				logger.info("Shutdown {}.", Const.PROGRAM_NAME_FULL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static volatile boolean restartAvailable = false;
	private static volatile boolean restartRequired = false;

	/**
	 * Restart availably only if we launched through packed jar! <br>
	 * 
	 * @return true is restart can be done
	 */
	public static boolean isRestartAvailable() {
		return restartAvailable;
	}

	/**
	 * If restart available, then settings this method will restart program
	 * (start new process after closing this).
	 */
	public static void enqueRestart() {
		restartRequired = true;
	}

	/**
	 * Is restart enqueued?
	 * 
	 * @return true if restart required.
	 */
	public static boolean isRestartRequired() {
		return restartRequired;
	}

	/**
	 * Loading settings for login to database. We read them from {@link Config},
	 * if something missed (password, for example) -- we show dialog where user
	 * can input there settings.
	 * 
	 * @return true if login was success, false if user click 'Cancel'.
	 *         Actually, we can't allow skipping login to real MySQL database.
	 *         But we don't check that database correct (for this is really PASS
	 *         database)
	 * @throws PersistentException
	 * @throws SQLException
	 */
	private boolean loginImpl() throws PersistentException, SQLException {

		LoginWindow loginDialog = null;

		JDBCConnectionSetting settings = new JDBCConnectionSetting();
		Configuration config = new Configuration().configure("ormmapping/Pass.cfg.xml");

		settings.setDialect(config.getProperty("dialect"));
		settings.setDriverClass(config.getProperty("connection.driver_class"));

		try {
			String password = Config.getInstance().getPassword();
			do {
				String url = Config.getInstance().getURL();
				String login = Config.getInstance().getLogin();

				logger.debug("Try to connect to " + url + ", login = " + login);

				if (url != null && !url.isEmpty() && login != null && !login.isEmpty()
						&& password != null) {

					settings.setConnectionURL(url);
					settings.setUserName(login);
					settings.setPassword(password);

					PassPersistentManager.setJDBCConnectionSetting(settings);

					try {
						PassPersistentManager.instance().getSession().connection().isClosed();
						logger.debug("Database connected.");
						return true;
					} catch (GenericJDBCException jdbcE) {
						AppHelper.showExceptionDialog(null, Messages.getString(
								"Application.err.notConnected", url, login), jdbcE);
						PassPersistentManager.instance().disposePersistentManager();
					}

				}

				if (loginDialog == null) {
					loginDialog = new LoginWindow(null);
				}
				if (!loginDialog.openDialog()) {
					return false;
				}

				logger.debug("Next attempt to login...");
				password = new String(loginDialog.getPassword());

			} while (true);
		} finally {
			if (loginDialog != null) {
				loginDialog.setVisible(false);
				loginDialog.dispose();
			}
		}
	}

	private void setSplashText(String message) {
		if (splash != null) {
			splash.setSplashText(message);
		}
	}

	public void run() {
		try {

			try {

				// We want to make good impression, isn't it?
				if (channel != null && lock == null) {
					AppHelper.showErrorDialog(null, Messages
							.getString("Application.err.alreadyStarted"), Messages
							.getString("Application.title.alreadyStarted"));

					System.exit(1); // Error exit
				}

				try {

					// Hibernating...
					setSplashText(Messages.getString("Application.mess.initDb"));

					logger.info("Initializing Hibernate...");
					if (!this.loginImpl()) {
						logger.debug("User cancelled login.");
						System.exit(0); // Normal exit
					}

					setSplashText(Messages.getString("Application.mess.loadingApp"));

					// Well, initialization
					final MainFrame frame = (MainFrame) AppHelper.getInstance().getFrameImpl(null,
							MainFrame.class);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					// Caching all using windows -- this is good and fast (about 200-300 msec diff)
					frame.preCache();
					frame.setVisible(true);
					logger.info("Application ready...");

				} catch (Throwable t) {
					applicationStart.stop();

					if (t.getClass() == InvocationTargetException.class && t.getCause() != null) {
						t = t.getCause();
					}

					AppHelper.showExceptionDialog(null, Messages
							.getString("Application.err.loadingApp"), t);

					System.exit(1); // Error exit
				}
			} finally {
				if (splash != null) {
					splash.setVisible(false);
					splash.dispose();
				}
				applicationStart.stop();
			}

		} catch (final Throwable t) {
			AppHelper.showExceptionDialog(null, Messages.getString("Application.err.critical"), t);
			logger.error("Error when loading application. Exit.");

			System.exit(1); // Error exit
		}
	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Starting {}.", Const.PROGRAM_NAME_FULL);
		SwingUtilities.invokeLater(new Application());

	}
}
