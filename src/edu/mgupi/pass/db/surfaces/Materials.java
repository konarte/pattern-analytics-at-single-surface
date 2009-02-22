package edu.mgupi.pass.db.surfaces;

import edu.mgupi.pass.db.sensors.Sensors;

/**
 * Материалы поверхностей.
 * 
 * Далее я бы захерачил все физ-хим свойства, включая атомную структуру в картинках, 
 * но это для будущего анализатора материи, пока ограничимся «электрикой»
 */
public class Materials {
	private int idSurfaceMaterial;
	private String name;
	/**
	 * Электрическая проводимость от 200 и выше до 2000 в сименсах (1/Ом)
	 */
	private float electricalConduction;
	/**
	 * Магнитная проницаемость, будем считать что без названия.
	 */
	private float magneticConductivity;
	Sensors sensor;

	public void setIdSurfaceMaterial(int aIdSurfaceMaterial) {
		this.idSurfaceMaterial = aIdSurfaceMaterial;
	}

	public int getIdSurfaceMaterial() {
		return this.idSurfaceMaterial;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setElectricalConduction(float aElectricalConduction) {
		this.electricalConduction = aElectricalConduction;
	}

	public float getElectricalConduction() {
		return this.electricalConduction;
	}

	public void setMagneticConductivity(float aMagneticConductivity) {
		this.magneticConductivity = aMagneticConductivity;
	}

	public float getMagneticConductivity() {
		return this.magneticConductivity;
	}
}