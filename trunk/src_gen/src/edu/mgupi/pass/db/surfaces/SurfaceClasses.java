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
 * ����� ������������ (���� ��������, ���������)
 */
public class SurfaceClasses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfaceClasses.class);
	public SurfaceClasses() {
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
	
	private int idSurfaceType;
	
	private String name;
	
	private byte[] surfaceImage;
	
	private void setIdSurfaceType(int value) {
		this.idSurfaceType = value;
	}
	
	public int getIdSurfaceType() {
		return idSurfaceType;
	}
	
	public int getORMID() {
		return getIdSurfaceType();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * ����������� ����������� � ���� ��������.
	 * �������� ������ ���� ������������������ �������. 256x256 ��������.
	 * 
	 * ������ �������� -- PNG
	 */
	public void setSurfaceImage(byte[] value) {
		this.surfaceImage = value;
	}
	
	/**
	 * ����������� ����������� � ���� ��������.
	 * �������� ������ ���� ������������������ �������. 256x256 ��������.
	 * 
	 * ������ �������� -- PNG
	 */
	public byte[] getSurfaceImage() {
		return surfaceImage;
	}
	
	public String toString() {
		return String.valueOf(getIdSurfaceType());
	}
	
}
