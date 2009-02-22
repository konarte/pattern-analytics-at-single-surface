package edu.mgupi.pass.db.locuses;

/**
 * Каталог оригинальных годографов
 */
public class LocusSources {
	private int idLocusSource;
	/**
	 * Название оригинального файла. Просто имя, без пути.
	 */
	private String filename;
	/**
	 * Оригинальный файл, двоичные данные. Немасштабированный, без применения
	 * фильтров. Это именно сам файл.
	 * 
	 * На размер нам плевать – он будет храниться не хуже чем в виде обычной
	 * ссылки на файл. Т.е. в каком формате был оригинальный файл, так он здесь
	 * и будет целиком помещаться.
	 */
	private byte[] sourceImage;

	public void setIdLocusSource(int aIdLocusSource) {
		this.idLocusSource = aIdLocusSource;
	}

	public int getIdLocusSource() {
		return this.idLocusSource;
	}

	public void setFilename(String aFilename) {
		this.filename = aFilename;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setSourceImage(byte[] aSourceImage) {
		this.sourceImage = aSourceImage;
	}

	public byte[] getSourceImage() {
		return this.sourceImage;
	}
}