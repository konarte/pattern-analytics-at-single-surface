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
package edu.mgupi.pass.db.defects;

import org.orm.*;
import java.io.Serializable;
import javax.persistence.*;
/**
 * Каталог дефектов
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="Defects")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Defects implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(Defects.class);
	public Defects() {
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
	
	@Column(name="IdDefect", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31202DC6AAE3052C7")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31202DC6AAE3052C7", strategy="native")	
	private int idDefect;
	
	@Column(name="BeddingDepth", nullable=false)	
	private float beddingDepth;
	
	@Column(name="Depth", nullable=false)	
	private float depth;
	
	@Column(name="Width", nullable=false)	
	private float width;
	
	@Column(name="Length", nullable=false)	
	private float length;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.defects.DefectTypes.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="DefectTypesIdDefectType") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.defects.DefectTypes defectType;
	
	private void setIdDefect(int value) {
		this.idDefect = value;
	}
	
	public int getIdDefect() {
		return idDefect;
	}
	
	public int getORMID() {
		return getIdDefect();
	}
	
	/**
	 * Глубина залегания, мм: от 0 до 10 мм для вихретоков, к примеру. 
	 * Глубина залегания задается для всех дефектов.
	 */
	public void setBeddingDepth(float value) {
		this.beddingDepth = value;
	}
	
	/**
	 * Глубина залегания, мм: от 0 до 10 мм для вихретоков, к примеру. 
	 * Глубина залегания задается для всех дефектов.
	 */
	public float getBeddingDepth() {
		return beddingDepth;
	}
	
	/**
	 * Глубина, мм
	 */
	public void setDepth(float value) {
		this.depth = value;
	}
	
	/**
	 * Глубина, мм
	 */
	public float getDepth() {
		return depth;
	}
	
	/**
	 * Ширина (радиус), мм 
	 */
	public void setWidth(float value) {
		this.width = value;
	}
	
	/**
	 * Ширина (радиус), мм 
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
	
	public void setDefectType(edu.mgupi.pass.db.defects.DefectTypes value) {
		this.defectType = value;
	}
	
	public edu.mgupi.pass.db.defects.DefectTypes getDefectType() {
		return defectType;
	}
	
	public String toString() {
		return String.valueOf(getIdDefect());
	}
	
}
