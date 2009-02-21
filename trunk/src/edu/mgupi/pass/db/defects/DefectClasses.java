package edu.mgupi.pass.db.defects;

/**
 * Классы дефектов (поверхностый, внутренний)
 */
public class DefectClasses {
	private int idDefectClass;
	private String name;

	public void setIdDefectClass(int aIdDefectClass) {
		this.idDefectClass = aIdDefectClass;
	}

	public int getIdDefectClass() {
		return this.idDefectClass;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}
}