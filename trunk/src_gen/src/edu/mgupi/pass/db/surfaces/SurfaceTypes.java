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
package edu.mgupi.pass.db.surfaces;

import org.orm.*;
import java.io.Serializable;
/**
 * Конфигурация поверхности.
 * Для каждого типа уточняем, что это.
 */
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
	
	private int idSurfaceMode;
	
	private String name;
	
	private edu.mgupi.pass.db.surfaces.SurfaceClasses surfaceClass;
	
	private edu.mgupi.pass.db.surfaces.Materials surfaceMaterial;
	
	private void setIdSurfaceMode(int value) {
		this.idSurfaceMode = value;
	}
	
	public int getIdSurfaceMode() {
		return idSurfaceMode;
	}
	
	public int getORMID() {
		return getIdSurfaceMode();
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
		return String.valueOf(getIdSurfaceMode());
	}
	
}
