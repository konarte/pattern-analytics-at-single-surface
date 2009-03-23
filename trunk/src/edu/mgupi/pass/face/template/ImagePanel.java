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

public class ImagePanel extends JPanel implements ActionListener {

	private final static Logger logger = LoggerFactory.getLogger(ImagePanel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean square = false;
	private BufferedImage myImage;

	public void setImage(BufferedImage image, boolean square) {
		this.myImage = image;
		this.square = square;
		this.refreshFit();
	}

	public void setImage(BufferedImage image) {
		this.setImage(image, false);
	}

	private JScrollPane parent = null;
	private boolean fitImageToWindowSize;

	public void registerFitButton(final JCheckBox fitBox) {
		this.registerFitButton(fitBox, null);
	}

	private ImagePanel previousImagePanel;
	private JCheckBox fitBox;

	public void registerFitButton(final JCheckBox fitBox, final ImagePanel previousImagePanel) {

		if (previousImagePanel == this) {
			throw new IllegalArgumentException(
					"Internal error. Attempt to create link to the same imagePanel as previous panel.");
		}
		if (fitBox == null) {
			throw new IllegalArgumentException("Internal error. 'fitBox' must be not null.");
		}

		this.fitBox = fitBox;
		this.previousImagePanel = previousImagePanel;
		fitBox.setActionCommand("changeMode");
		fitBox.addActionListener(this);
		this.setFitMode(fitBox.isSelected());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == null) {
			return;
		}
		if (command.equals("changeMode")) {
			if (logger.isTraceEnabled()) {
				logger.trace("Set action " + fitBox.isSelected() + " for " + ImagePanel.this);
			}
			this.setFitModeCascade(fitBox.isSelected());
		}

	}

	private boolean cycleActionProtection = false;

	private void setFitModeCascade(boolean fitImageToWindowSize) {
		if (cycleActionProtection) {
			return;
		}
		cycleActionProtection = true;
		ImagePanel.this.setFitMode(fitImageToWindowSize);
		if (ImagePanel.this.previousImagePanel != null) {
			ImagePanel.this.previousImagePanel.setFitModeCascade(fitImageToWindowSize);
		}
		cycleActionProtection = false;
	}

	private void setFitMode(boolean fitImageToWindowSize) {
		this.fitImageToWindowSize = fitImageToWindowSize;
		this.refreshFit();
	}

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

		synchronized (getTreeLock()) {
			if (myImage != null) {

				if (secundomerNormalDraw == null) {
					Container topContainer = this.getTopLevelAncestor();
					secundomerNormalDraw = SecundomerList.registerSecundomer("Draw simple image for "
							+ (topContainer == null ? this.getName() : topContainer.getName()));
					secundomerScaleDraw = SecundomerList.registerSecundomer("Draw scaled image for "
							+ (topContainer == null ? this.getName() : topContainer.getName()));
				}

				if (this.fitImageToWindowSize) {
					secundomerScaleDraw.start();

					// Must set preferred size!
					this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(), (int) parent
							.getVisibleRect().getHeight()));

					try {
						/*
						 * Based of thumb maker by Marco Schmidt
						 */
						Dimension thumb = new Dimension(this.getWidth(), this.getHeight());

						ResizeFilter.calcThumbSize(this.myImage, thumb);

						g.drawImage(this.myImage, 0, 0, thumb.width, thumb.height, null);
					} finally {
						secundomerScaleDraw.stop();
					}
				} else {
					secundomerNormalDraw.start();
					try {
						g.drawImage(this.myImage, 0, 0, null);
					} finally {
						secundomerNormalDraw.stop();
					}
				}

			} else {

				// Simple visual cake :)
				String text = "No image";

				Graphics2D graphics2D = (Graphics2D) g;
				graphics2D.setFont(new Font("Courier", Font.PLAIN, 22));
				graphics2D.setColor(Color.BLACK);
				Rectangle2D rect = graphics2D.getFontMetrics().getStringBounds(text, g);

				graphics2D.drawString(text, this.getWidth() / 2 - (int) rect.getCenterX(), this.getHeight() / 2
						- (int) rect.getCenterY());

			}
		}
	};

	public boolean hasImage() {
		return this.myImage != null;
	}

}
