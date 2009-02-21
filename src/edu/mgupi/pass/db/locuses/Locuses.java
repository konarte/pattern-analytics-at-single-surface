package edu.mgupi.pass.db.locuses;

import edu.mgupi.pass.db.surfaces.Surfaces;
import edu.mgupi.pass.db.defects.Defects;
import java.util.ArrayList;
import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.LocusFilters;
import edu.mgupi.pass.db.sensors.Sensors;

/**
 * Каталог годографов
 */
public class Locuses {
	private int idLocus;
	/**
	 * Название годографа (задается пользователем). По-умолчанию выбирается из названия файла
	 */
	private String name;
	/**
	 * Изображение предпросмотра, который был отмасштабирован до необходимого размера, 
	 * к нему были применены все выбранные фильтры, 
	 * после чего он сжат до более мелкого размера (для отображения в интерфейсе) 
	 * и представлен в виде изображения. 
	 * 
	 * Размер изображения должен составлять 256x256 пикселей и храниться в формате, 
	 * удобном для загрузки в приложение.
	 */
	private byte[] thumbImage;
	/**
	 * Гистограмма изображения с примененными фильтрами.
	 * 
	 * Формат хранения -- матрица (т.е. двумерный массив).
	 * Размер гистограммы зависит от размера изображения.
	 */
	private byte[] histogram;
	/**
	 * Базовое изображение – уже отмасштабированное и с примененными фильтрами. 
	 * Размер изображения 1024x1024 пикселей.
	 * Формат хранения -- PNG.
	 * 
	 * Это основа для кросс-модульных сравнений (поскольку каждый модуль 
	 * будет хранить свои собственные данные в отдельной таблице).
	 */
	private byte[] filteredImage;
	edu.mgupi.pass.db.locuses.LModules module;
	Surfaces surface;
	Defects defect;
	edu.mgupi.pass.db.locuses.LocusSources locusSource;
	ArrayList<LocusModuleParams> params = new ArrayList<LocusModuleParams>();
	ArrayList<LocusFilters> filters = new ArrayList<LocusFilters>();
	Sensors sensor;

	public void setIdLocus(int aIdLocus) {
		this.idLocus = aIdLocus;
	}

	public int getIdLocus() {
		return this.idLocus;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setThumbImage(byte[] aThumbImage) {
		this.thumbImage = aThumbImage;
	}

	public byte[] getThumbImage() {
		return this.thumbImage;
	}

	public void setHistogram(byte[] aHistogram) {
		this.histogram = aHistogram;
	}

	public byte[] getHistogram() {
		return this.histogram;
	}

	public void setFilteredImage(byte[] aFilteredImage) {
		this.filteredImage = aFilteredImage;
	}

	public byte[] getFilteredImage() {
		return this.filteredImage;
	}
}