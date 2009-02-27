package edu.mgupi.pass.sources;

import java.awt.image.BufferedImage;

public class SourceStore {
	private String name;
	private BufferedImage sourceImage;
	private byte[] fileData;
	private int[] historgram;

	public SourceStore(String imageName, BufferedImage sourceImage, byte[] fileData) {
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

	public void setHistorgram(int[] historgram) {
		this.historgram = historgram;
	}

	public int[] getHistorgram() {
		return this.historgram;
	}
}
