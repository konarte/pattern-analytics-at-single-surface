/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package edu.mgupi.pass.db.locuses;

import org.orm.*;
import java.io.Serializable;
/**
 * Каталог годографов
 */
public class Locuses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Locuses.class);
	public Locuses() {
	}
	
	public boolean save() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().saveObject(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("save()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean delete() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().deleteObject(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("delete()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().refresh(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("refresh()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean evict() throws PersistentException {
		try {
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().evict(this);
			return true;
		}
		catch (Exception e) {
			_logger.error("evict()", e);
			throw new PersistentException(e);
		}
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == edu.mgupi.pass.db.surfaces.ORMConstants.KEY_LOCUSES_PARAMS) {
			return ORM_params;
		}
		else if (key == edu.mgupi.pass.db.surfaces.ORMConstants.KEY_LOCUSES_FILTERS) {
			return ORM_filters;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private int idLocus;
	
	private String name;
	
	private byte[] thumbImage;
	
	private byte[] histogram;
	
	private byte[] filteredImage;
	
	private edu.mgupi.pass.db.locuses.LModules module;
	
	private edu.mgupi.pass.db.surfaces.Surfaces surface;
	
	private edu.mgupi.pass.db.defects.Defects defect;
	
	private edu.mgupi.pass.db.locuses.LocusSources locusSource;
	
	private edu.mgupi.pass.db.sensors.Sensors sensor;
	
	private java.util.Set ORM_params = new java.util.HashSet();
	
	private java.util.Set ORM_filters = new java.util.HashSet();
	
	private void setIdLocus(int value) {
		this.idLocus = value;
	}
	
	public int getIdLocus() {
		return idLocus;
	}
	
	public int getORMID() {
		return getIdLocus();
	}
	
	/**
	 * Название годографа (задается пользователем). По-умолчанию выбирается из названия файла
	 */
	public void setName(String value) {
		this.name = value;
	}
	
	/**
	 * Название годографа (задается пользователем). По-умолчанию выбирается из названия файла
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Изображение предпросмотра, который был отмасштабирован до необходимого размера, 
	 * к нему были применены все выбранные фильтры, 
	 * после чего он сжат до более мелкого размера (для отображения в интерфейсе) 
	 * и представлен в виде изображения. 
	 * 
	 * Размер изображения должен составлять 256x256 пикселей и храниться в формате, 
	 * удобном для загрузки в приложение.
	 */
	public void setThumbImage(byte[] value) {
		this.thumbImage = value;
	}
	
	/**
	 * Изображение предпросмотра, который был отмасштабирован до необходимого размера, 
	 * к нему были применены все выбранные фильтры, 
	 * после чего он сжат до более мелкого размера (для отображения в интерфейсе) 
	 * и представлен в виде изображения. 
	 * 
	 * Размер изображения должен составлять 256x256 пикселей и храниться в формате, 
	 * удобном для загрузки в приложение.
	 */
	public byte[] getThumbImage() {
		return thumbImage;
	}
	
	/**
	 * Гистограмма изображения с примененными фильтрами.
	 * 
	 * Формат хранения -- матрица (т.е. двумерный массив).
	 * Размер гистограммы зависит от размера изображения.
	 */
	public void setHistogram(byte[] value) {
		this.histogram = value;
	}
	
	/**
	 * Гистограмма изображения с примененными фильтрами.
	 * 
	 * Формат хранения -- матрица (т.е. двумерный массив).
	 * Размер гистограммы зависит от размера изображения.
	 */
	public byte[] getHistogram() {
		return histogram;
	}
	
	/**
	 * Базовое изображение – уже отмасштабированное и с примененными фильтрами. 
	 * Размер изображения 1024x1024 пикселей.
	 * Формат хранения -- PNG.
	 * 
	 * Это основа для кросс-модульных сравнений (поскольку каждый модуль 
	 * будет хранить свои собственные данные в отдельной таблице).
	 */
	public void setFilteredImage(byte[] value) {
		this.filteredImage = value;
	}
	
	/**
	 * Базовое изображение – уже отмасштабированное и с примененными фильтрами. 
	 * Размер изображения 1024x1024 пикселей.
	 * Формат хранения -- PNG.
	 * 
	 * Это основа для кросс-модульных сравнений (поскольку каждый модуль 
	 * будет хранить свои собственные данные в отдельной таблице).
	 */
	public byte[] getFilteredImage() {
		return filteredImage;
	}
	
	public void setModule(edu.mgupi.pass.db.locuses.LModules value) {
		this.module = value;
	}
	
	public edu.mgupi.pass.db.locuses.LModules getModule() {
		return module;
	}
	
	public void setSurface(edu.mgupi.pass.db.surfaces.Surfaces value) {
		this.surface = value;
	}
	
	public edu.mgupi.pass.db.surfaces.Surfaces getSurface() {
		return surface;
	}
	
	public void setDefect(edu.mgupi.pass.db.defects.Defects value) {
		this.defect = value;
	}
	
	public edu.mgupi.pass.db.defects.Defects getDefect() {
		return defect;
	}
	
	public void setLocusSource(edu.mgupi.pass.db.locuses.LocusSources value) {
		this.locusSource = value;
	}
	
	public edu.mgupi.pass.db.locuses.LocusSources getLocusSource() {
		return locusSource;
	}
	
	private void setORM_Params(java.util.Set value) {
		this.ORM_params = value;
	}
	
	private java.util.Set getORM_Params() {
		return ORM_params;
	}
	
	public final edu.mgupi.pass.db.locuses.LocusModuleParamsSetCollection params = new edu.mgupi.pass.db.locuses.LocusModuleParamsSetCollection(this, _ormAdapter, edu.mgupi.pass.db.surfaces.ORMConstants.KEY_LOCUSES_PARAMS, edu.mgupi.pass.db.surfaces.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	private void setORM_Filters(java.util.Set value) {
		this.ORM_filters = value;
	}
	
	private java.util.Set getORM_Filters() {
		return ORM_filters;
	}
	
	public final edu.mgupi.pass.db.locuses.LocusFiltersSetCollection filters = new edu.mgupi.pass.db.locuses.LocusFiltersSetCollection(this, _ormAdapter, edu.mgupi.pass.db.surfaces.ORMConstants.KEY_LOCUSES_FILTERS, edu.mgupi.pass.db.surfaces.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public void setSensor(edu.mgupi.pass.db.sensors.Sensors value) {
		this.sensor = value;
	}
	
	public edu.mgupi.pass.db.sensors.Sensors getSensor() {
		return sensor;
	}
	
	public String toString() {
		return String.valueOf(getIdLocus());
	}
	
}
