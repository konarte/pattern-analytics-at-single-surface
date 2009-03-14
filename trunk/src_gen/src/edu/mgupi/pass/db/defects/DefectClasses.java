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
 * Классы дефектов (поверхностый, внутренний)
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="DefectClasses")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class DefectClasses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectClasses.class);
	public DefectClasses() {
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
	
	@Column(name="IdDefectClass", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312006D6FD8D0B582")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312006D6FD8D0B582", strategy="native")	
	private int idDefectClass;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	private void setIdDefectClass(int value) {
		this.idDefectClass = value;
	}
	
	public int getIdDefectClass() {
		return idDefectClass;
	}
	
	public int getORMID() {
		return getIdDefectClass();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return String.valueOf(getIdDefectClass());
	}
	
}
