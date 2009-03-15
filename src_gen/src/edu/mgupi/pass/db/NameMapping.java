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
package edu.mgupi.pass.db;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Маппинг наименований кодовых названий в JSON-параметрах 
 * реальным наименованиям.
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="NameMapping")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class NameMapping implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(NameMapping.class);
	public NameMapping() {
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
	
	@Column(name="ID", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312009D42DDA029DB")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312009D42DDA029DB", strategy="native")	
	private int ID;
	
	@Column(name="NameType", nullable=false, length=11)	
	private int nameType;
	
	@Column(name="Name", nullable=true, length=255)	
	private String name;
	
	@Column(name="Title", nullable=true, length=255)	
	private String title;
	
	private void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	/**
	 * Тип записи:
	 * 1 - дефект
	 */
	public void setNameType(int value) {
		this.nameType = value;
	}
	
	/**
	 * Тип записи:
	 * 1 - дефект
	 */
	public int getNameType() {
		return nameType;
	}
	
	/**
	 * Название параметра (латиницей)
	 */
	public void setName(String value) {
		this.name = value;
	}
	
	/**
	 * Название параметра (латиницей)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Видимое название параметра
	 */
	public void setTitle(String value) {
		this.title = value;
	}
	
	/**
	 * Видимое название параметра
	 */
	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
