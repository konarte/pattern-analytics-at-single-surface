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
 * Каталог параметров примененного модуля к годографу
 */
public class LocusModuleParams implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusModuleParams.class);
	public LocusModuleParams() {
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
	
	private int idModuleParam;
	
	private String paramName;
	
	private byte[] paramData;
	
	private void setIdModuleParam(int value) {
		this.idModuleParam = value;
	}
	
	public int getIdModuleParam() {
		return idModuleParam;
	}
	
	public int getORMID() {
		return getIdModuleParam();
	}
	
	public void setParamName(String value) {
		this.paramName = value;
	}
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamData(byte[] value) {
		this.paramData = value;
	}
	
	public byte[] getParamData() {
		return paramData;
	}
	
	public String toString() {
		return String.valueOf(getIdModuleParam());
	}
	
}
