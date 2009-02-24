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
 * Каталог оригинальных годографов
 */
public class LocusSources implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusSources.class);
	public LocusSources() {
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
	
	private int idLocusSource;
	
	private String filename;
	
	private byte[] sourceImage;
	
	private void setIdLocusSource(int value) {
		this.idLocusSource = value;
	}
	
	public int getIdLocusSource() {
		return idLocusSource;
	}
	
	public int getORMID() {
		return getIdLocusSource();
	}
	
	/**
	 * Название оригинального файла. Просто имя, без пути.
	 */
	public void setFilename(String value) {
		this.filename = value;
	}
	
	/**
	 * Название оригинального файла. Просто имя, без пути.
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Оригинальный файл, двоичные данные. 
	 * Немасштабированный, без применения фильтров. 
	 * Это именно сам файл.
	 * 
	 *  На размер нам плевать – он будет храниться не хуже чем в виде обычной ссылки на файл.
	 *  Т.е. в каком формате был оригинальный файл, так он здесь и будет целиком помещаться.
	 */
	public void setSourceImage(byte[] value) {
		this.sourceImage = value;
	}
	
	/**
	 * Оригинальный файл, двоичные данные. 
	 * Немасштабированный, без применения фильтров. 
	 * Это именно сам файл.
	 * 
	 *  На размер нам плевать – он будет храниться не хуже чем в виде обычной ссылки на файл.
	 *  Т.е. в каком формате был оригинальный файл, так он здесь и будет целиком помещаться.
	 */
	public byte[] getSourceImage() {
		return sourceImage;
	}
	
	public String toString() {
		return String.valueOf(getIdLocusSource());
	}
	
}
