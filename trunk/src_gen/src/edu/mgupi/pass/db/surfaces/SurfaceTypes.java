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
package edu.mgupi.pass.db.surfaces;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Конфигурация поверхности.
 * Для каждого типа уточняем, что это.
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="SurfaceTypes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SurfaceTypes implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfaceTypes.class);
	public SurfaceTypes() {
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
	
	@Column(name="IdSurfaceType", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312023AEC6E005F02")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312023AEC6E005F02", strategy="native")	
	private int idSurfaceType;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.surfaces.SurfaceClasses.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SurfaceClassesIdSurfaceType") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.surfaces.SurfaceClasses surfaceClass;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.surfaces.Materials.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="MaterialsIdSurfaceMaterial") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.surfaces.Materials surfaceMaterial;
	
	private void setIdSurfaceType(int value) {
		this.idSurfaceType = value;
	}
	
	public int getIdSurfaceType() {
		return idSurfaceType;
	}
	
	public int getORMID() {
		return getIdSurfaceType();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSurfaceClass(edu.mgupi.pass.db.surfaces.SurfaceClasses value) {
		this.surfaceClass = value;
	}
	
	public edu.mgupi.pass.db.surfaces.SurfaceClasses getSurfaceClass() {
		return surfaceClass;
	}
	
	public void setSurfaceMaterial(edu.mgupi.pass.db.surfaces.Materials value) {
		this.surfaceMaterial = value;
	}
	
	public edu.mgupi.pass.db.surfaces.Materials getSurfaceMaterial() {
		return surfaceMaterial;
	}
	
	public String toString() {
		return String.valueOf(getIdSurfaceType());
	}
	
}
