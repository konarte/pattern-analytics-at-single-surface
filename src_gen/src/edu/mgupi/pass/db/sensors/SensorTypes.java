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
package edu.mgupi.pass.db.sensors;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Тип датчика для вихретоков: с перпендикулярным расположением катушки, 
 * с параллельным расположением катушки (проходной), 
 * многокатушечный. 
 * 
 * http://www.acta-ndt.ru/?id=2007, пленочный плоский, многосекционный(под вопросом).
 * 
 * Для других  видов датчиков типы будут другие, разумеется.
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="SensorTypes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SensorTypes implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SensorTypes.class);
	public SensorTypes() {
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
	
	@Column(name="IdSensorType", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31201F3044AA04838")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31201F3044AA04838", strategy="native")	
	private int idSensorType;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="SensorImage", nullable=true)	
	@Basic(fetch=FetchType.LAZY)	
	private byte[] sensorImage;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.sensors.SensorClasses.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="SensorClassesIdSensorClass") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.sensors.SensorClasses sensorClass;
	
	private void setIdSensorType(int value) {
		this.idSensorType = value;
	}
	
	public int getIdSensorType() {
		return idSensorType;
	}
	
	public int getORMID() {
		return getIdSensorType();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Изображение (конструктивная схема) датчика в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public void setSensorImage(byte[] value) {
		this.sensorImage = value;
	}
	
	/**
	 * Изображение (конструктивная схема) датчика в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	public byte[] getSensorImage() {
		return sensorImage;
	}
	
	public void setSensorClass(edu.mgupi.pass.db.sensors.SensorClasses value) {
		this.sensorClass = value;
	}
	
	public edu.mgupi.pass.db.sensors.SensorClasses getSensorClass() {
		return sensorClass;
	}
	
	public String toString() {
		return String.valueOf(getIdSensorType());
	}
	
}
