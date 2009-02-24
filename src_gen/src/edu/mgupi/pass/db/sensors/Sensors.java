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
package edu.mgupi.pass.db.sensors;

import org.orm.*;
import java.io.Serializable;
public class Sensors implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Sensors.class);
	public Sensors() {
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
	
	public boolean deleteAndDissociate()throws PersistentException {
		try {
			if(getMpathMaterial() != null) {
				getMpathMaterial().setSensor(null);
			}
			
			return delete();
		}
		catch(Exception e) {
			_logger.error("deleteAndDissociate()", e);
			throw new PersistentException(e);
		}
	}
	
	public boolean deleteAndDissociate(org.orm.PersistentSession session)throws PersistentException {
		try {
			if(getMpathMaterial() != null) {
				getMpathMaterial().setSensor(null);
			}
			
			try {
				session.delete(this);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		catch(Exception e) {
			_logger.error("deleteAndDissociate(org.orm.PersistentSession session)", e);
			throw new PersistentException(e);
		}
	}
	
	private int idSensor;
	
	private edu.mgupi.pass.db.sensors.SensorTypes sensorType;
	
	private edu.mgupi.pass.db.surfaces.Materials mpathMaterial;
	
	private void setIdSensor(int value) {
		this.idSensor = value;
	}
	
	public int getIdSensor() {
		return idSensor;
	}
	
	public int getORMID() {
		return getIdSensor();
	}
	
	public void setSensorType(edu.mgupi.pass.db.sensors.SensorTypes value) {
		this.sensorType = value;
	}
	
	public edu.mgupi.pass.db.sensors.SensorTypes getSensorType() {
		return sensorType;
	}
	
	public void setMpathMaterial(edu.mgupi.pass.db.surfaces.Materials value) {
		this.mpathMaterial = value;
	}
	
	public edu.mgupi.pass.db.surfaces.Materials getMpathMaterial() {
		return mpathMaterial;
	}
	
	public String toString() {
		return String.valueOf(getIdSensor());
	}
	
}
