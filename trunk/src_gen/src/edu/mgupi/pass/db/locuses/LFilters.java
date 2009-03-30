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
 * Каталог модулей анализа
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="LFilters")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class LFilters implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LFilters.class);
	public LFilters() {
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
	
	@Column(name="IdLFilter", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31205627A6B603179")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31205627A6B603179", strategy="native")	
	private int idLFilter;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="Codename", nullable=false, length=255)	
	private String codename;
	
	@Column(name="ServiceFilter", nullable=false, length=1)	
	private boolean serviceFilter = false;
	
	private void setIdLFilter(int value) {
		this.idLFilter = value;
	}
	
	public int getIdLFilter() {
		return idLFilter;
	}
	
	public int getORMID() {
		return getIdLFilter();
	}
	
	/**
	 * Название фильтра
	 */
	public void setName(String value) {
		this.name = value;
	}
	
	/**
	 * Название фильтра
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Уникальный код фильтра (пакет+имя)
	 */
	public void setCodename(String value) {
		this.codename = value;
	}
	
	/**
	 * Уникальный код фильтра (пакет+имя)
	 */
	public String getCodename() {
		return codename;
	}
	
	/**
	 * Если true, то это специальный, сервисный фильтр (он не выбирается
	 * пользователем и может использоваться только в фильтрах
	 * пре-процессинга).
	 */
	public void setServiceFilter(boolean value) {
		this.serviceFilter = value;
	}
	
	/**
	 * Если true, то это специальный, сервисный фильтр (он не выбирается
	 * пользователем и может использоваться только в фильтрах
	 * пре-процессинга).
	 */
	public boolean getServiceFilter() {
		return serviceFilter;
	}
	
	public String toString() {
		return String.valueOf(getIdLFilter());
	}
	
}
