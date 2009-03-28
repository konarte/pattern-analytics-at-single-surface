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
 * Формы поверхностей (тело вращения, плоскость)
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="SurfaceClasses")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SurfaceClasses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfaceClasses.class);
	public SurfaceClasses() {
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
	
	@Column(name="IdSurfaceClass", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31204C98ACA008FD8")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31204C98ACA008FD8", strategy="native")	
	private int idSurfaceClass;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="SurfaceImage", nullable=true)	
	@Basic(fetch=FetchType.LAZY)	
	private byte[] surfaceImage;
	
	private void setIdSurfaceClass(int value) {
		this.idSurfaceClass = value;
	}
	
	public int getIdSurfaceClass() {
		return idSurfaceClass;
	}
	
	public int getORMID() {
		return getIdSurfaceClass();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Изображение поверхности в виде картинки.
	 * Картинка должна быть стандартизованного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public void setSurfaceImage(byte[] value) {
		this.surfaceImage = value;
	}
	
	/**
	 * Изображение поверхности в виде картинки.
	 * Картинка должна быть стандартизованного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public byte[] getSurfaceImage() {
		return surfaceImage;
	}
	
	public String toString() {
		return String.valueOf(getIdSurfaceClass());
	}
	
}
