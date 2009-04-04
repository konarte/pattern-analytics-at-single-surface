/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)SplashWindow2.java 1.0 04.04.2009
 */

package edu.mgupi.pass.face.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Utils;

/**
 * Well, I don't like that way.
 * 
 * @author raidan
 * 
 */

@Deprecated
public class SplashWindow2 {

	private final static Logger logger = LoggerFactory.getLogger(SplashWindow2.class);

	private SplashScreen splashScreen;
	private Graphics2D g2D;

	private Font defaultMessageFont = null;

	public SplashWindow2() throws IOException {

		splashScreen = SplashScreen.getSplashScreen();
		if (splashScreen == null) {
			logger.error("Unable to create splash screen.");
			return;
		}
		g2D = splashScreen.createGraphics();
		if (g2D == null) {
			splashScreen = null;
			logger.error("Unable to create surface for splash screeen.");
			return;
		}
		defaultMessageFont = new Font("Dialog", Font.BOLD, 12);

		String imagePath = Const.SPLASH_IMAGE_PATH;

		if (imagePath == null) {
			String images[] = Utils.listFilesFromJAR(Const.SPLASH_IMAGE_PLACE_DIR, null);
			if (images != null && images.length > 0) {
				imagePath = images[new Random().nextInt(images.length)];
				logger.debug("Using random image splash: " + imagePath);
			}
		} else {
			logger.debug("Using predefine splash: " + imagePath);
		}

		splashScreen.setImageURL(ClassLoader.getSystemResource(imagePath));

		Dimension size = splashScreen.getSize();
		int top = size.height;

		top = this.drawString(g2D, Messages.getString("SplashWindow.loading"), defaultMessageFont,
				top, true);
		top = this.drawString(g2D, Const.PROGRAM_NAME_LAST, new Font("DialogInput", Font.BOLD, 14),
				top, false);
		top = this.drawString(g2D, Const.PROGRAM_NAME_FIRST,
				new Font("DialogInput", Font.BOLD, 18), top, false);

		splashScreen.update();
	}

	public void show() {
		if (splashScreen == null) {
			return;
		}

		logger.debug("Show splash window...");
		splashScreen.update();

	}

	private int drawString(Graphics2D g2D, String text, Font font, int top, boolean clear) {

		g2D.setFont(font);
		Rectangle2D rectangle = g2D.getFontMetrics().getStringBounds(text, g2D);

		if (clear) {
			Dimension size = splashScreen.getSize();
			g2D.setColor(Color.WHITE);
			g2D.fillRect(0, size.height, size.width, size.height - (int) rectangle.getHeight() - 7);
		}

		g2D.setColor(Color.BLACK);

		top = top - (int) rectangle.getHeight() - 5;
		g2D.drawString(text, 0 + 5, top);

		return top;
	}

	public void setSplashText(String message) {
		if (splashScreen == null) {
			return;
		}
		this.drawString(g2D, message, defaultMessageFont, splashScreen.getSize().height, true);

		splashScreen.update();
	}

	public void close() {
		if (splashScreen != null) {
			splashScreen.close();
		}
	}
}
