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
 * Каталог поверхностей
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Surfaces")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Surfaces implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Surfaces.class);
	public Surfaces() {
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
	
	@Column(name="IdSurface", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31204C98ACAF08FD9")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31204C98ACAF08FD9", strategy="native")	
	private int idSurface;
	
	@Column(name="Height", nullable=false)	
	private float height;
	
	@Column(name="Width", nullable=false)	
	private float width;
	
	@Column(name="Length", nullable=false)	
	private float length;
	
	@Column(name="MultiLayer", nullable=false, length=1)	
	private boolean multiLayer;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.surfaces.SurfaceTypes.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SurfaceTypesIdSurfaceType") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.surfaces.SurfaceTypes surfaceType;
	
	private void setIdSurface(int value) {
		this.idSurface = value;
	}
	
	public int getIdSurface() {
		return idSurface;
	}
	
	public int getORMID() {
		return getIdSurface();
	}
	
	/**
	 * Высота, мм
	 */
	public void setHeight(float value) {
		this.height = value;
	}
	
	/**
	 * Высота, мм
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Ширина, мм
	 */
	public void setWidth(float value) {
		this.width = value;
	}
	
	/**
	 * Ширина, мм
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Длина, мм
	 */
	public void setLength(float value) {
		this.length = value;
	}
	
	/**
	 * Длина, мм
	 */
	public float getLength() {
		return length;
	}
	
	/**
	 * Многослойный объект (например печатная плата)
	 */
	public void setMultiLayer(boolean value) {
		this.multiLayer = value;
	}
	
	/**
	 * Многослойный объект (например печатная плата)
	 */
	public boolean getMultiLayer() {
		return multiLayer;
	}
	
	public void setSurfaceType(edu.mgupi.pass.db.surfaces.SurfaceTypes value) {
		this.surfaceType = value;
	}
	
	public edu.mgupi.pass.db.surfaces.SurfaceTypes getSurfaceType() {
		return surfaceType;
	}
	
	public String toString() {
		return String.valueOf(getIdSurface());
	}
	
}
