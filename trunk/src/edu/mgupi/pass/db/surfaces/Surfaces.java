package edu.mgupi.pass.db.surfaces;

/**
 * Каталог поверхностей
 */
public class Surfaces {
	private int idSurface;
	/**
	 * Высота, мм
	 */
	private float height;
	/**
	 * Ширина, мм
	 */
	private float width;
	/**
	 * Длина, мм
	 */
	private float length;
	/**
	 * Многослойный объект (например печатная плата)
	 */
	private boolean multiLayer;
	edu.mgupi.pass.db.surfaces.SurfaceClasses surfaceType;
	edu.mgupi.pass.db.surfaces.SurfaceTypes surfaceMode;
	edu.mgupi.pass.db.surfaces.SurfaceTypes type;

	public void setSurfaceType(edu.mgupi.pass.db.surfaces.SurfaceClasses aSurfaceType) {
		this.surfaceType = aSurfaceType;
	}

	public edu.mgupi.pass.db.surfaces.SurfaceClasses getSurfaceType() {
		return this.surfaceType;
	}

	public void setIdSurface(int aIdSurface) {
		this.idSurface = aIdSurface;
	}

	public int getIdSurface() {
		return this.idSurface;
	}

	public void setHeight(float aHeight) {
		this.height = aHeight;
	}

	public float getHeight() {
		return this.height;
	}

	public void setWidth(float aWidth) {
		this.width = aWidth;
	}

	public float getWidth() {
		return this.width;
	}

	public void setLength(float aLength) {
		this.length = aLength;
	}

	public float getLength() {
		return this.length;
	}

	public void setMultiLayer(boolean aMultiLayer) {
		this.multiLayer = aMultiLayer;
	}

	public boolean isMultiLayer() {
		return this.multiLayer;
	}
}