package edu.mgupi.pass.db.surfaces;

/**
 * Формы поверхностей (тело вращения, плоскость)
 */
public class SurfaceClasses {
	private int idSurfaceType;
	private String name;
	/**
	 * Изображение поверхности в виде картинки. Картинка должна быть
	 * стандартизованного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
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