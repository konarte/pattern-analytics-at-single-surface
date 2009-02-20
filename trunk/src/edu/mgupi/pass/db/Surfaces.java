package edu.mgupi.pass.db;

public class Surfaces {
	private int idSurface;
	private boolean multiLayer;
	edu.mgupi.pass.db.SurfaceTypes surfaceType;

	public void setSurfaceType(edu.mgupi.pass.db.SurfaceTypes aSurfaceType) {
		this.surfaceType = aSurfaceType;
	}

	public edu.mgupi.pass.db.SurfaceTypes getSurfaceType() {
		return this.surfaceType;
	}

	public void setIdSurface(int aIdSurface) {
		this.idSurface = aIdSurface;
	}

	public int getIdSurface() {
		return this.idSurface;
	}

	public void setMultiLayer(boolean aMultiLayer) {
		this.multiLayer = aMultiLayer;
	}

	public boolean isMultiLayer() {
		return this.multiLayer;
	}
}