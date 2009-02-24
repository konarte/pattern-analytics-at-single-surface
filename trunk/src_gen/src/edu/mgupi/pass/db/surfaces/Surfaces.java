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
 * Каталог поверхностей
 */
public class Surfaces implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Surfaces.class);
	public Surfaces() {
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
	
	private int idSurface;
	
	private float height;
	
	private float width;
	
	private float length;
	
	private boolean multiLayer;
	
	private edu.mgupi.pass.db.surfaces.SurfaceClasses surfaceType;
	
	private edu.mgupi.pass.db.surfaces.SurfaceTypes surfaceMode;
	
	private edu.mgupi.pass.db.surfaces.SurfaceTypes type;
	
	private void setIdSurface(int value) {
		this.idSurface = value;
	}
	
	public int getIdSurface() {
		return idSurface;
	}
	
	public int getORMID() {
		return getIdSurface();
	}
	
	/**
	 * Высота, мм
	 */
	public void setHeight(float value) {
		this.height = value;
	}
	
	/**
	 * Высота, мм
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Ширина, мм
	 */
	public void setWidth(float value) {
		this.width = value;
	}
	
	/**
	 * Ширина, мм
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Длина, мм
	 */
	public void setLength(float value) {
		this.length = value;
	}
	
	/**
	 * Длина, мм
	 */
	public float getLength() {
		return length;
	}
	
	/**
	 * Многослойный объект (например печатная плата)
	 */
	public void setMultiLayer(boolean value) {
		this.multiLayer = value;
	}
	
	/**
	 * Многослойный объект (например печатная плата)
	 */
	public boolean getMultiLayer() {
		return multiLayer;
	}
	
	public void setSurfaceType(edu.mgupi.pass.db.surfaces.SurfaceClasses value) {
		this.surfaceType = value;
	}
	
	public edu.mgupi.pass.db.surfaces.SurfaceClasses getSurfaceType() {
		return surfaceType;
	}
	
	public void setSurfaceMode(edu.mgupi.pass.db.surfaces.SurfaceTypes value) {
		this.surfaceMode = value;
	}
	
	public edu.mgupi.pass.db.surfaces.SurfaceTypes getSurfaceMode() {
		return surfaceMode;
	}
	
	public void setType(edu.mgupi.pass.db.surfaces.SurfaceTypes value) {
		this.type = value;
	}
	
	public edu.mgupi.pass.db.surfaces.SurfaceTypes getType() {
		return type;
	}
	
	public String toString() {
		return String.valueOf(getIdSurface());
	}
	
}
