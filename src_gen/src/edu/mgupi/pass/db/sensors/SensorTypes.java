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
/**
 * ��� ������� ��� ����������: � ���������������� ������������� �������, 
 * � ������������ ������������� ������� (���������), 
 * ���������������. 
 * 
 * http://www.acta-ndt.ru/?id=2007, ��������� �������, ���������������(��� ��������).
 * 
 * ��� ������  ����� �������� ���� ����� ������, ����������.
 */
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
	
	private int idSensorType;
	
	private String name;
	
	private byte[] sensorImage;
	
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
	 * ����������� (�������������� �����) ������� � ���� ��������. 
	 * �������� ������ ���� �������������������� �������. 256x256 ��������.
	 * 
	 * ������ �������� -- PNG
	 */
	public void setSensorImage(byte[] value) {
		this.sensorImage = value;
	}
	
	/**
	 * ����������� (�������������� �����) ������� � ���� ��������. 
	 * �������� ������ ���� �������������������� �������. 256x256 ��������.
	 * 
	 * ������ �������� -- PNG
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
