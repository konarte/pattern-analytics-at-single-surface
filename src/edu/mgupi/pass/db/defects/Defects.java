package edu.mgupi.pass.db.defects;

/**
 * Каталог дефектов
 */
public class Defects {
	private int idDefect;
	/**
	 * Глубина залегания, мм: от 0 до 10 мм для вихретоков, к примеру. 
	 * Глубина залегания задается для всех дефектов.
	 */
	private float beddingDepth;
	/**
	 * Глубина, мм
	 */
	private float depth;
	/**
	 * Ширина (радиус), мм 
	 */
	private float width;
	/**
	 * Длина, мм 
	 */
	private float length;
	edu.mgupi.pass.db.defects.DefectTypes defectType;

	public void setIdDefect(int aIdDefect) {
		this.idDefect = aIdDefect;
	}

	public int getIdDefect() {
		return this.idDefect;
	}

	public void setBeddingDepth(float aBeddingDepth) {
		this.beddingDepth = aBeddingDepth;
	}

	public float getBeddingDepth() {
		return this.beddingDepth;
	}

	public void setDepth(float aDepth) {
		this.depth = aDepth;
	}

	public float getDepth() {
		return this.depth;
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
}