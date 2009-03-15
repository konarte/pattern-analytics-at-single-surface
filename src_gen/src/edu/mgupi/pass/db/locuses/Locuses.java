/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package edu.mgupi.pass.db.locuses;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Каталог годографов
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Locuses")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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
	
	@Column(name="IdLocus", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312009D42E76029E5")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312009D42E76029E5", strategy="native")	
	private int idLocus;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="ThumbImage", nullable=false)	
	@Basic(fetch=FetchType.LAZY)	
	private byte[] thumbImage;
	
	@Column(name="Histogram", nullable=false)	
	@Basic(fetch=FetchType.LAZY)	
	private int[] histogram;
	
	@Column(name="FilteredImage", nullable=false)	
	@Basic(fetch=FetchType.LAZY)	
	private byte[] filteredImage;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.locuses.LModules.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="LModulesIdLModule") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.locuses.LModules module;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.surfaces.Surfaces.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SurfacesIdSurface") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.surfaces.Surfaces surface;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.defects.Defects.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="DefectsIdDefect") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.defects.Defects defect;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.locuses.LocusSources.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="LocusSourcesIdLocusSource") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.locuses.LocusSources locusSource;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.sensors.Sensors.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SensorsIdSensor") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.sensors.Sensors sensor;
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.locuses.LocusModuleParams.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="LocusesIdLocus", nullable=true)	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> params = new java.util.HashSet<edu.mgupi.pass.db.locuses.LocusModuleParams>();
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.locuses.LocusFilterOptions.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="LocusesIdLocus", nullable=true)	
	@org.hibernate.annotations.IndexColumn(name="LocusesIndex")	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.List<edu.mgupi.pass.db.locuses.LocusFilterOptions> filters = new java.util.ArrayList<edu.mgupi.pass.db.locuses.LocusFilterOptions>();
	
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
	public void setHistogram(int[] value) {
		this.histogram = value;
	}
	
	/**
	 * Гистограмма изображения с примененными фильтрами.
	 * 
	 * Формат хранения -- матрица (т.е. двумерный массив).
	 * Размер гистограммы зависит от размера изображения.
	 */
	public int[] getHistogram() {
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
	
	public void setParams(java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> value) {
		this.params = value;
	}
	
	public java.util.Set<edu.mgupi.pass.db.locuses.LocusModuleParams> getParams() {
		return params;
	}
	
	
	public void setFilters(java.util.List<edu.mgupi.pass.db.locuses.LocusFilterOptions> value) {
		this.filters = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.locuses.LocusFilterOptions> getFilters() {
		return filters;
	}
	
	
	public void setSensor(edu.mgupi.pass.db.sensors.Sensors value) {
		this.sensor = value;
	}
	
	public edu.mgupi.pass.db.sensors.Sensors getSensor() {
		return sensor;
	}
	
	@Transient	
	private boolean processed;
	
	public boolean getProcessed() {
		return processed;
	}
	
	public void setProcessed(boolean aProcessed) {
		processed = aProcessed;
	}
	
	public String toString() {
		return String.valueOf(getIdLocus());
	}
	
}
