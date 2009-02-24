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
 * Каталог модулей анализа
 */
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
	
	private int idModule;
	
	private String name;
	
	private String codename;
	
	private void setIdModule(int value) {
		this.idModule = value;
	}
	
	public int getIdModule() {
		return idModule;
	}
	
	public int getORMID() {
		return getIdModule();
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
	
	public String toString() {
		return String.valueOf(getIdModule());
	}
	
}
