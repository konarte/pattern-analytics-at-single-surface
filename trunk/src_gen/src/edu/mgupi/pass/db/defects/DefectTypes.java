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
package edu.mgupi.pass.db.defects;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Типы дефектов (каверна, окалина, прижог).
 * Их много согласно классификации -- конкретику позже
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="DefectTypes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class DefectTypes implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectTypes.class);
	public DefectTypes() {
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
	
	@Column(name="IdDefectType", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31202B70650003330")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31202B70650003330", strategy="native")	
	private int idDefectType;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="DefectImage", nullable=false)	
	@Basic(fetch=FetchType.LAZY)	
	private byte[] defectImage;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.defects.DefectClasses.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="DefectClassesIdDefectClass") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.defects.DefectClasses defectClass;
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.defects.DefectTypeOptions.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="DefectTypesIdDefectType", nullable=true)	
	@org.hibernate.annotations.IndexColumn(name="DefectTypesIndex")	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.List<edu.mgupi.pass.db.defects.DefectTypeOptions> options = new java.util.ArrayList<edu.mgupi.pass.db.defects.DefectTypeOptions>();
	
	private void setIdDefectType(int value) {
		this.idDefectType = value;
	}
	
	public int getIdDefectType() {
		return idDefectType;
	}
	
	public int getORMID() {
		return getIdDefectType();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Изображение дефекта в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public void setDefectImage(byte[] value) {
		this.defectImage = value;
	}
	
	/**
	 * Изображение дефекта в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public byte[] getDefectImage() {
		return defectImage;
	}
	
	public void setDefectClass(edu.mgupi.pass.db.defects.DefectClasses value) {
		this.defectClass = value;
	}
	
	public edu.mgupi.pass.db.defects.DefectClasses getDefectClass() {
		return defectClass;
	}
	
	public void setOptions(java.util.List<edu.mgupi.pass.db.defects.DefectTypeOptions> value) {
		this.options = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.defects.DefectTypeOptions> getOptions() {
		return options;
	}
	
	
	public String toString() {
		return String.valueOf(getIdDefectType());
	}
	
}
