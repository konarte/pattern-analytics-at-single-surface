package edu.mgupi.pass.sources;

import java.awt.image.BufferedImage;

public class SourceStore {
	private String name;
	private BufferedImage image;
	private byte[] fileData;

	public SourceStore(String imageName, BufferedImage image, byte[] fileData) {
		this.name = imageName;
		this.image = image;
		this.fileData = fileData;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return image;
	}
}
