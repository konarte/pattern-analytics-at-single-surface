package edu.mgupi.pass.face.gui.template;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.FilterException;
import edu.mgupi.pass.filters.IllegalParameterValueException;
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

	/**
	 * Default constructor.
	 */
	public ImagePanel() {
		super();
	}

	private boolean centerImage;
	private ResizeFilter resizeFilter;

	private int imageWidht;
	private int imageHeight;

	/**
	 * Constructor with special arguments.
	 * 
	 * Width and height can define expected size of image, if image was larger
	 * or smaller -- it will scaled to appropriate size.
	 * 
	 * @param width
	 *            width of expected image
	 * @param height
	 *            height of expected image
	 * @param autoResize
	 *            if true, that we'll resize given picture to this width and
	 *            height. AutoResize work only if we don't feet image to window
	 *            size.
	 * 
	 * @param borderTitle
	 *            if not null, we draw special border around this image with
	 *            this title
	 */
	public ImagePanel(int width, int height, boolean autoResize, String borderTitle) {
		this();

		this.setOpaque(true);

		this.centerImage = true;
		if (autoResize) {
			resizeFilter = new ResizeFilter();
			try {
				resizeFilter.getWIDTH().setValue(width);
				resizeFilter.getHEIGHT().setValue(height);
				resizeFilter.getINTERPOLATION_METHOD().setValue("bicubic");

			} catch (IllegalParameterValueException e) {
				throw new RuntimeException(e);
			}
		}

		this.imageWidht = width;
		this.imageHeight = height;

		if (borderTitle != null) {
			this.setBorder(BorderFactory.createTitledBorder(borderTitle));
		}

		this.updateUI();

	}

	@Override
	public void updateUI() {
		Dimension dim = new Dimension(imageWidht, imageHeight);
		if (this.getBorder() != null && this.getBorder() instanceof TitledBorder && imageWidht > 0
				&& imageHeight > 0) {

			TitledBorder titleBorder = (TitledBorder) this.getBorder();

			Insets insets = titleBorder.getBorderInsets(this);
			dim.width = dim.width + insets.left + insets.right;
			dim.height = dim.height + insets.top + insets.bottom;

			// Don't ask, why 4
			topInset = (insets.top - insets.bottom) / 4;
		}
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		super.updateUI();
	}

	private int topInset = 0;

	private BufferedImage myImage;
	public final static String RPOPERTY_NAME = "image";

	/**
	 * Setting image to display.
	 * 
	 * @param image
	 *            default image for display. If null -- no image shown on this
	 *            panel.
	 */
	public void setImage(BufferedImage image) {

		BufferedImage currentImage = this.myImage;

		if (resizeFilter != null && image != null) {
			try {
				this.myImage = resizeFilter.convert(image);
			} catch (FilterException e) {
				throw new RuntimeException(e);
			}
		} else {
			this.myImage = image;
		}

		super.firePropertyChange(RPOPERTY_NAME, currentImage, this.myImage);

		this.refreshFit();
	}

	/**
	 * Return resulted image. This image can be differ than you set up it
	 * through {@link #setImage(BufferedImage)}, when constructor received width
	 * and height
	 * 
	 * @return loaded and scaled (if specified in constructor) image or null
	 */
	public BufferedImage getImage() {
		return this.myImage;
	}

	/**
	 * Return about image
	 * 
	 * @return true if last image sets by {@link #setImage(BufferedImage)} was
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

	private void fitImageImpl() {
		this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(), (int) parent
				.getVisibleRect().getHeight()));
		int width = parent.getWidth();
		int height = parent.getHeight();

		this.setSize(width, height);
		this.setLocation(0, 0);

		parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	}

	/**
	 * Refreshing mode, change current size if need
	 */
	private void refreshFit() {

		if (logger.isTraceEnabled()) {
			logger.trace("Refresh fit (" + this.fitImageToWindowSize + ") as " + this);
		}

		if (myImage == null) {
			/*
			 * Do nothing, except repaint.
			 */
			if (parent != null && !fitImageToWindowSize) {
				/*
				 * We must reset fit, otherwise huge scroll-bars may be stay.
				 * 
				 * And immediately clear preferred size, cause we don't care
				 * about :)
				 */
				this.fitImageImpl();
				this.setPreferredSize(new Dimension(0, 0));
			}
			this.repaint();
			return;
		}

		/*
		 * If parent is empty, this is not a problem.
		 */
		if (parent == null) {
			Container parentContainer = this.getParent();
			if (parentContainer == null
					|| !((parentContainer = parentContainer.getParent()) instanceof JScrollPane)) {
				// otherwise, we don't care
				this.repaint();

				return;
			}

			parent = (JScrollPane) parentContainer;
		}

		// We must redefine scrollPane constants
		if (this.fitImageToWindowSize) {
			this.fitImageImpl();

		} else {
			int width = myImage.getWidth();
			int height = myImage.getHeight();

			width = Const.MAIN_IMAGE_WIDTH > width ? width : Const.MAIN_IMAGE_WIDTH;
			height = Const.MAIN_IMAGE_HEIGHT > height ? height : Const.MAIN_IMAGE_HEIGHT;

			this.setPreferredSize(new Dimension(width, height));
			this.setBounds(0, 0, width, height);
			parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			parent.getHorizontalScrollBar().setValue(0);
			parent.getVerticalScrollBar().setValue(0);

		}
		this.parent.repaint();
	}

	private Secundomer secundomerNormalDraw = null;
	private Secundomer secundomerScaleDraw = null;

	private final static String NO_IMAGE_TEXT = Messages.getString("ImagePanel.noImage");

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Painting our image
		synchronized (getTreeLock()) {

			if (myImage == null) { // #1
				// Simple visual cake :)
				String text = NO_IMAGE_TEXT;

				Graphics2D graphics2D = (Graphics2D) g;
				graphics2D.setFont(new Font("Courier", Font.PLAIN, 22));
				graphics2D.setColor(Color.BLACK);
				Rectangle2D rect = graphics2D.getFontMetrics().getStringBounds(text, g);

				int X = this.getWidth() / 2 - (int) rect.getCenterX();
				int Y = this.getHeight() / 2 - (int) rect.getCenterY();
				graphics2D.drawString(text, X, Y);

			} else { // #1 if

				// Initializing counters
				if (secundomerNormalDraw == null) {
					Container topContainer = this.getTopLevelAncestor();
					secundomerNormalDraw = SecundomerList
							.registerSecundomer("Draw simple image for "
									+ (topContainer == null ? this.getName() : topContainer
											.getName()));
					secundomerScaleDraw = SecundomerList
							.registerSecundomer("Draw scaled image for "
									+ (topContainer == null ? this.getName() : topContainer
											.getName()));
				}

				if (this.fitImageToWindowSize) {
					// If fit image to window size

					secundomerScaleDraw.start();

					// Must set preferred size!
					this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(),
							(int) parent.getVisibleRect().getHeight()));

					try {
						Dimension thumb = new Dimension(this.getWidth(), this.getHeight());
						// Use Resize helper
						ResizeFilter.calcThumbSize(this.myImage, thumb);
						g.drawImage(this.myImage, 0, 0, thumb.width, thumb.height, null);

						/*
						 * There is another way to draw images (and faster)...
						 * 
						 * But, it's can't properly work with images in
						 * not-equal ColorModel (RGB and GrayScale, for example)
						 */
					} finally {
						secundomerScaleDraw.stop();
					}
				} else {
					int X = 0;
					int Y = 0;

					if (centerImage) {

						X = this.getWidth() / 2 - this.myImage.getWidth() / 2;
						Y = topInset + this.getHeight() / 2 - this.myImage.getHeight() / 2;
					}

					// No fit, draw as it
					secundomerNormalDraw.start();
					try {
						g.drawImage(this.myImage, X, Y, null);
					} finally {
						secundomerNormalDraw.stop();
					}
				}

			} // #1 if-else
		}
	};

}
