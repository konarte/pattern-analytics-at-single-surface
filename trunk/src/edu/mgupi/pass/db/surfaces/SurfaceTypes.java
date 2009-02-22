package edu.mgupi.pass.db.surfaces;

/**
 * Конфигурация поверхности.
 * Для каждого типа уточняем, что это.
 */
public class SurfaceTypes {
	private int idSurfaceMode;
	private String name;
	edu.mgupi.pass.db.surfaces.SurfaceClasses surfaceClass;
	edu.mgupi.pass.db.surfaces.Materials surfaceMaterial;

	public void setIdSurfaceMode(int aIdSurfaceMode) {
		this.idSurfaceMode = aIdSurfaceMode;
	}

	public int getIdSurfaceMode() {
		return this.idSurfaceMode;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}
}