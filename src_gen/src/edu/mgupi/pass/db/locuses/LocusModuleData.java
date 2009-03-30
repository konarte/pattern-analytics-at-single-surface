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
package edu.mgupi.pass.db.locuses;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Специальные данные модуля анализа (например, матрица), примененного к годографу
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="LocusModuleData")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class LocusModuleData implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusModuleData.class);
	public LocusModuleData() {
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
	
	@Column(name="IdModuleParam", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31205627A6F40317D")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31205627A6F40317D", strategy="native")	
	private int idModuleParam;
	
	@Column(name="ParamName", nullable=false, length=255)	
	private String paramName;
	
	@Column(name="ParamData", nullable=false)	
	private byte[] paramData;
	
	@Column(name="DataType", nullable=false, length=11)	
	private int dataType;
	
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
	
	/**
	 * Тип параметра:
	 * 1 - обычный сериализуемый
	 * 2 - изображение
	 */
	public void setDataType(int value) {
		this.dataType = value;
	}
	
	/**
	 * Тип параметра:
	 * 1 - обычный сериализуемый
	 * 2 - изображение
	 */
	public int getDataType() {
		return dataType;
	}
	
	@Transient	
	private Object objectData;
	
	public Object getObjectData() {
		return objectData;
	}
	
	public void setObjectData(Object aObjectData) {
		objectData = aObjectData;
	}
	
	public String toString() {
		return String.valueOf(getIdModuleParam());
	}
	
}
