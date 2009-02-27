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
 * Каталог примененных фильтров к годографу
 */
public class LocusFilters implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusFilters.class);
	public LocusFilters() {
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
	
	private int idLocusFilter;
	
	private String options;
	
	private edu.mgupi.pass.db.locuses.LFilters filter;
	
	private void setIdLocusFilter(int value) {
		this.idLocusFilter = value;
	}
	
	public int getIdLocusFilter() {
		return idLocusFilter;
	}
	
	public int getORMID() {
		return getIdLocusFilter();
	}
	
	public void setOptions(String value) {
		this.options = value;
	}
	
	public String getOptions() {
		return options;
	}
	
	public void setFilter(edu.mgupi.pass.db.locuses.LFilters value) {
		this.filter = value;
	}
	
	public edu.mgupi.pass.db.locuses.LFilters getFilter() {
		return filter;
	}
	
	public String toString() {
		return String.valueOf(getIdLocusFilter());
	}
	
}
