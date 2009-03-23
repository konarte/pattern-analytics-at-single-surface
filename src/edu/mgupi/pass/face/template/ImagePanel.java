package edu.mgupi.pass.face.template;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.service.ResizeFilter;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

/**
 * Panel where we draw images. Support fit image to window size or display as
 * it. Usually must include into JScrollPane.
 * 
 * @author raidan
 * 
 */
public class ImagePanel extends JPanel implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(ImagePanel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean square = false;
	private BufferedImage myImage;

	/**
	 * Setting image to display.
	 * 
	 * @param image
	 *            default image for display. If null -- no image shown on this
	 *            panel.
	 * @param square
	 *            true if panel MUST be a square size
	 */
	public void setImage(BufferedImage image, boolean square) {
		this.myImage = image;
		this.square = square;
		this.refreshFit();
	}

	/**
	 * Setting image to display.
	 * 
	 * @param image
	 *            default image for display. If null -- no image shown on this
	 *            panel.
	 * 
	 * @see #setImage(BufferedImage, boolean)
	 */
	public void setImage(BufferedImage image) {
		this.setImage(image, false);
	}

	/**
	 * Return about image
	 * 
	 * @return true if last image setted by {@link #setImage(BufferedImage)} was
	 *         not null.
	 */
	public boolean hasImage() {
		return this.myImage != null;
	}

	/**
	 * Registering checkbox control resizing image
	 * 
	 * @param fitBox
	 *            checkbox, that controls -- fit image to size of parent panel
	 *            (Frame, Window, etc.) or show it's in natural size
	 */
	public void registerFitButton(JCheckBox fitBox) {

		if (fitBox == null) {
			throw new IllegalArgumentException("Internal error. 'fitBox' must be not null.");
		}
		fitBox.addActionListener(this);

		this.setFitMode(fitBox.isSelected());
	}

	public void actionPerformed(ActionEvent e) {
		JCheckBox checkBox = (JCheckBox) e.getSource();
		if (logger.isTraceEnabled()) {
			logger.trace("Set action " + checkBox.isSelected() + " for " + ImagePanel.this);
		}

		this.setFitMode(checkBox.isSelected());
	}

	/**
	 * Set up current mode
	 * 
	 * @param fitImageToWindowSize
	 */
	private void setFitMode(boolean fitImageToWindowSize) {
		this.fitImageToWindowSize = fitImageToWindowSize;
		this.refreshFit();
	}

	private boolean fitImageToWindowSize;
	private JScrollPane parent = null;

	/**
	 * Refreshing mode, change current size if need
	 */
	private void refreshFit() {

		if (logger.isTraceEnabled()) {
			logger.trace("Refresh fit (" + this.fitImageToWindowSize + ") as " + this);
		}

		if (myImage == null) {
			// do nothing, except repaint
			this.repaint();
			return;
		}

		// If parent is empty
		// This is a developer error
		if (parent == null) {
			Container parentContainer = this.getParent();
			if (parentContainer == null || !((parentContainer = parentContainer.getParent()) instanceof JScrollPane)) {
				// otherwise, we don't care
				return;
			}

			parent = (JScrollPane) parentContainer;
		}

		// We must redefine scrollPane constants
		if (this.fitImageToWindowSize) {
			this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(), (int) parent.getVisibleRect()
					.getHeight()));
			int width = parent.getWidth();
			int height = parent.getHeight();

			if (this.square) {
				if (width > height) {
					height = width;
				} else if (width < height) {
					width = height;
				}
			}

			this.setSize(width, height);
			this.setLocation(0, 0);

			parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		} else {
			int width = myImage.getWidth();
			int height = myImage.getHeight();

			width = Const.MAIN_IMAGE_WIDTH > width ? width : Const.MAIN_IMAGE_WIDTH;
			height = Const.MAIN_IMAGE_HEIGHT > height ? height : Const.MAIN_IMAGE_HEIGHT;

			if (this.square) {
				if (width > height) {
					height = width;
				} else if (width < height) {
					width = height;
				}
			}

			this.setPreferredSize(new Dimension(width, height));
			this.setBounds(0, 0, width, height);
			parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			parent.getHorizontalScrollBar().setValue(0);
			parent.getVerticalScrollBar().setValue(0);

		}
		// parent.scrollRectToVisible(new Rectangle(0, 0, (int)
		// parent.getVisibleRect().getWidth(), (int) parent
		// .getVisibleRect().getHeight()));

		// this.repaint();
		this.parent.repaint();
	}

	private Secundomer secundomerNormalDraw = null;
	private Secundomer secundomerScaleDraw = null;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Painting our image
		synchronized (getTreeLock()) {

			if (myImage == null) { // #1
				// Simple visual cake :)
				String text = "No image";

				Graphics2D graphics2D = (Graphics2D) g;
				graphics2D.setFont(new Font("Courier", Font.PLAIN, 22));
				graphics2D.setColor(Color.BLACK);
				Rectangle2D rect = graphics2D.getFontMetrics().getStringBounds(text, g);

				graphics2D.drawString(text, this.getWidth() / 2 - (int) rect.getCenterX(), this.getHeight() / 2
						- (int) rect.getCenterY());

			} else { // #1 if

				// Initializing counters
				if (secundomerNormalDraw == null) {
					Container topContainer = this.getTopLevelAncestor();
					secundomerNormalDraw = SecundomerList.registerSecundomer("Draw simple image for "
							+ (topContainer == null ? this.getName() : topContainer.getName()));
					secundomerScaleDraw = SecundomerList.registerSecundomer("Draw scaled image for "
							+ (topContainer == null ? this.getName() : topContainer.getName()));
				}

				if (this.fitImageToWindowSize) {
					// If fit image to window size

					secundomerScaleDraw.start();

					// Must set preferred size!
					this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(), (int) parent
							.getVisibleRect().getHeight()));

					try {
						Dimension thumb = new Dimension(this.getWidth(), this.getHeight());
						// Use Resize helper
						ResizeFilter.calcThumbSize(this.myImage, thumb);
						g.drawImage(this.myImage, 0, 0, thumb.width, thumb.height, null);

						// There is another way to draw images (and faster)...
						// But, it's can't properly work with images in not-equal ColorModel (RGB and GrayScale, for example) 
					} finally {
						secundomerScaleDraw.stop();
					}
				} else {
					// No fit, draw as it
					secundomerNormalDraw.start();
					try {
						g.drawImage(this.myImage, 0, 0, null);
					} finally {
						secundomerNormalDraw.stop();
					}
				}

			} // #1 if-else
		}
	};

}
