package edu.mgupi.pass.db.defects;

/**
 * Типы дефектов (каверна, окалина, прижог). Их много согласно классификации --
 * конкретику позже
 */
public class DefectTypes {
	private int idDefectType;
	private String name;
	/**
	 * Изображение дефекта в виде картинки. Картинка должна быть
	 * стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	private byte[] defectImage;
	edu.mgupi.pass.db.defects.DefectClasses defectClass;

	public void setIdDefectType(int aIdDefectType) {
		this.idDefectType = aIdDefectType;
	}

	public int getIdDefectType() {
		return this.idDefectType;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setDefectImage(byte[] aDefectImage) {
		this.defectImage = aDefectImage;
	}

	public byte[] getDefectImage() {
		return this.defectImage;
	}
}