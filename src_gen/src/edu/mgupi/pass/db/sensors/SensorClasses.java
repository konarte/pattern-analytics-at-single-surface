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
 * ¬ид датчика (вид системы контрол€).
 * 
 * вихретокова€(показывает дефекты до 5-7 мм в поверхности металла), магнитошумова€, 
 * ультразвукова€, магнитоиндукционна€, акустическа€.
 * 
 * ƒл€ каждой из систем соответственно свои датчики со своими параметрами Ц 
 * на будущее предусмотреть выбор системы контрол€, 
 * например подкл соотв базу данных по системе контрол€ и датчикам Ц 
 * пока  делаем только вихретоковую)
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="SensorClasses")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SensorClasses implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SensorClasses.class);
	public SensorClasses() {
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
	
	@Column(name="IdSensorClass", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312070BAEDDF0E734")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312070BAEDDF0E734", strategy="native")	
	private int idSensorClass;
	
	@Column(name="Name", nullable=false, length=255)	
	private String name;
	
	private void setIdSensorClass(int value) {
		this.idSensorClass = value;
	}
	
	public int getIdSensorClass() {
		return idSensorClass;
	}
	
	public int getORMID() {
		return getIdSensorClass();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return String.valueOf(getIdSensorClass());
	}
	
}
