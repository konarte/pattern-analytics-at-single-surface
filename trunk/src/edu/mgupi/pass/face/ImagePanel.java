package edu.mgupi.pass.face;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.mgupi.pass.util.Const;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage myImage;

	public void setImage(BufferedImage image) {
		this.myImage = image;
		this.refreshFit();
	}

	private JScrollPane parent = null;
	private boolean fitImageToWindowSize;

	public void registerFitButton(final JCheckBox fitBox) {
		fitBox.setAction(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ImagePanel.this.setFitMode(fitBox.isSelected());
			}
		});
		this.setFitMode(fitBox.isSelected());
	}

	private void setFitMode(boolean fitImageToWindowSize) {
		this.fitImageToWindowSize = fitImageToWindowSize;
		this.refreshFit();
	}

	private void refreshFit() {

		if (myImage == null) {
			// do nothing
			return;
		}

		// If parent is empty
		// This is a developer error
		if (parent == null) {
			Container parentContainer = this.getParent();
			if (parentContainer == null || !((parentContainer = parentContainer.getParent()) instanceof JScrollPane)) {
				// if (!noScrollPane) {
				// JOptionPane.showMessageDialog(null,
				// "Internal error. Inappropriate layout mode -- "
				// + "ImagePanel does not on JScrollPane.",
				// "Invalid layout programming",
				// JOptionPane.ERROR_MESSAGE);
				// }
				// otherwise, we don't care
				return;
			}

			parent = (JScrollPane) parentContainer;
		}

		// We must redefine scrollPane constants
		if (this.fitImageToWindowSize) {
			this.setPreferredSize(new Dimension((int) parent.getVisibleRect().getWidth(), (int) parent.getVisibleRect()
					.getHeight()));
			this.setBounds(parent.getBounds());
			parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		} else {
			int width = myImage.getWidth();
			int height = myImage.getHeight();

			width = Const.MAIN_IMAGE_WIDTH > width ? width : Const.MAIN_IMAGE_WIDTH;
			height = Const.MAIN_IMAGE_HEIGHT > height ? height : Const.MAIN_IMAGE_HEIGHT;

			this.setPreferredSize(new Dimension(width, height));
			this.setBounds(0, 0, width, height);
			parent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			parent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (myImage != null) {

			if (this.fitImageToWindowSize) {
				/*
				 * Based of thumb maker by Marco Schmidt
				 */
				int thumbWidth = this.getWidth();
				int thumbHeight = this.getHeight();
				double thumbRatio = (double) thumbWidth / (double) thumbHeight;
				int imageWidth = myImage.getWidth();
				int imageHeight = myImage.getHeight();
				double imageRatio = (double) imageWidth / (double) imageHeight;
				if (thumbRatio < imageRatio) {
					thumbHeight = (int) (thumbWidth / imageRatio);
				} else {
					thumbWidth = (int) (thumbHeight * imageRatio);
				}

				g.drawImage(this.myImage, 0, 0, thumbWidth, thumbHeight, null);

			} else {
				g.drawImage(this.myImage, 0, 0, null);
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
	};
}
