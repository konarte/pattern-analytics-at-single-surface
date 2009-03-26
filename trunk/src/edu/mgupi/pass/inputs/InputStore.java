package edu.mgupi.pass.inputs;

import java.awt.image.BufferedImage;

public class InputStore {
	private String name;
	private BufferedImage sourceImage;
	private byte[] fileData;

	public InputStore(String imageName, BufferedImage sourceImage, byte[] fileData) {
		this.name = imageName;
		this.sourceImage = sourceImage;
		this.fileData = fileData;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getSourceImage() {
		return sourceImage;
	}
}
