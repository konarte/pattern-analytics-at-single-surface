package edu.mgupi.pass.db;

public class SurfaceTypes {
	private int idSurfaceType;
	private String name;
	private byte[] surfaceImage;

	public void setIdSurfaceType(int aIdSurfaceType) {
		this.idSurfaceType = aIdSurfaceType;
	}

	public int getIdSurfaceType() {
		return this.idSurfaceType;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setSurfaceImage(byte[] aSurfaceImage) {
		this.surfaceImage = aSurfaceImage;
	}

	public byte[] getSurfaceImage() {
		return this.surfaceImage;
	}
}