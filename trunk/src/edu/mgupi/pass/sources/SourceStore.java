package edu.mgupi.pass.sources;

import java.awt.image.BufferedImage;

public class SourceStore {
	private String name;
	private BufferedImage image;

	public SourceStore(String imageName, BufferedImage image) {
		this.name = imageName;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return image;
	}
}
