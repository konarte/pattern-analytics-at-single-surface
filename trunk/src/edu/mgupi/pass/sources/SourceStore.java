package edu.mgupi.pass.sources;

import java.awt.image.BufferedImage;

public class SourceStore {
	private String name;
	private BufferedImage mainImage;

	public SourceStore(String imageName, BufferedImage mainImage) {
		this.name = imageName;
		this.mainImage = mainImage;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getMainImage() {
		return mainImage;
	}
}
