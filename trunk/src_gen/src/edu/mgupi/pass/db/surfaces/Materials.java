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
package edu.mgupi.pass.db.surfaces;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Материалы поверхностей.
 * 
 * Далее я бы захерачил все физ-хим свойства, включая атомную структуру в картинках, 
 * но это для будущего анализатора материи, пока ограничимся «электрикой»
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Materials")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Materials implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Materials.class);
	public Materials() {
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
	
	@Column(name="IdSurfaceMaterial", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31200A31A86500B8E")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31200A31A86500B8E", strategy="native")	
	private int idSurfaceMaterial;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	@Column(name="ElectricalConduction", nullable=false)	
	private float electricalConduction;
	
	@Column(name="MagneticConductivity", nullable=false)	
	private float magneticConductivity;
	
	private void setIdSurfaceMaterial(int value) {
		this.idSurfaceMaterial = value;
	}
	
	public int getIdSurfaceMaterial() {
		return idSurfaceMaterial;
	}
	
	public int getORMID() {
		return getIdSurfaceMaterial();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Электрическая проводимость от 200 и выше до 2000 в сименсах (1/Ом)
	 */
	public void setElectricalConduction(float value) {
		this.electricalConduction = value;
	}
	
	/**
	 * Электрическая проводимость от 200 и выше до 2000 в сименсах (1/Ом)
	 */
	public float getElectricalConduction() {
		return electricalConduction;
	}
	
	/**
	 * Магнитная проницаемость, будем считать что без названия.
	 */
	public void setMagneticConductivity(float value) {
		this.magneticConductivity = value;
	}
	
	/**
	 * Магнитная проницаемость, будем считать что без названия.
	 */
	public float getMagneticConductivity() {
		return magneticConductivity;
	}
	
	public String toString() {
		return String.valueOf(getIdSurfaceMaterial());
	}
	
}
