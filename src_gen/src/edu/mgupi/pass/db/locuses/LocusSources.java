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
 * Каталог оригинальных годографов
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="LocusSources")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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
	
	@Column(name="IdLocusSource", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312009D42E86029E6")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312009D42E86029E6", strategy="native")	
	private int idLocusSource;
	
	@Column(name="Filename", nullable=false, length=255)	
	private String filename;
	
	@Column(name="SourceImage", nullable=false)	
	@Basic(fetch=FetchType.LAZY)	
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
