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
package edu.mgupi.pass.db.defects;

import org.orm.*;
import java.io.Serializable;
/**
 * Типы дефектов (каверна, окалина, прижог).
 * Их много согласно классификации -- конкретику позже
 */
public class DefectTypes implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectTypes.class);
	public DefectTypes() {
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
	
	private int idDefectType;
	
	private String name;
	
	private byte[] defectImage;
	
	private edu.mgupi.pass.db.defects.DefectClasses defectClass;
	
	private void setIdDefectType(int value) {
		this.idDefectType = value;
	}
	
	public int getIdDefectType() {
		return idDefectType;
	}
	
	public int getORMID() {
		return getIdDefectType();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Изображение дефекта в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public void setDefectImage(byte[] value) {
		this.defectImage = value;
	}
	
	/**
	 * Изображение дефекта в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public byte[] getDefectImage() {
		return defectImage;
	}
	
	public void setDefectClass(edu.mgupi.pass.db.defects.DefectClasses value) {
		this.defectClass = value;
	}
	
	public edu.mgupi.pass.db.defects.DefectClasses getDefectClass() {
		return defectClass;
	}
	
	public String toString() {
		return String.valueOf(getIdDefectType());
	}
	
}
