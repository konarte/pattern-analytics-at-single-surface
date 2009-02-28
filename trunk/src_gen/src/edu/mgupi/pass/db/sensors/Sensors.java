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
import javax.persistence.*;
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Sensors")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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
	
	@Column(name="IdSensor", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D311FBD12FD4901C14")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D311FBD12FD4901C14", strategy="native")	
	private int idSensor;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.sensors.SensorTypes.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SensorTypesIdSensorType") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.sensors.SensorTypes sensorType;
	
	@OneToOne(mappedBy="sensor", targetEntity=edu.mgupi.pass.db.surfaces.Materials.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@Basic(fetch=FetchType.LAZY)	
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
