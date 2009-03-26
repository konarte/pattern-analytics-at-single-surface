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
 * Примененный модуль анализа к годографу
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="LocusAppliedModule")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class LocusAppliedModule implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedModule.class);
	public LocusAppliedModule() {
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
	
	@Column(name="IdLocusModule", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D312044C35C6501918")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D312044C35C6501918", strategy="native")	
	private int idLocusModule;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.locuses.LModules.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="LModulesIdLModule") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.locuses.LModules module;
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.locuses.LocusAppliedModuleParams.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="LocusAppliedModuleIdLocusModule", nullable=true)	
	@org.hibernate.annotations.IndexColumn(name="LocusAppliedModuleIndex")	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedModuleParams> params = new java.util.ArrayList<edu.mgupi.pass.db.locuses.LocusAppliedModuleParams>();
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.locuses.LocusModuleData.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="LocusAppliedModuleIdLocusModule", nullable=true)	
	@org.hibernate.annotations.IndexColumn(name="LocusAppliedModuleIndex")	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.List<edu.mgupi.pass.db.locuses.LocusModuleData> data = new java.util.ArrayList<edu.mgupi.pass.db.locuses.LocusModuleData>();
	
	private void setIdLocusModule(int value) {
		this.idLocusModule = value;
	}
	
	public int getIdLocusModule() {
		return idLocusModule;
	}
	
	public int getORMID() {
		return getIdLocusModule();
	}
	
	public void setModule(edu.mgupi.pass.db.locuses.LModules value) {
		this.module = value;
	}
	
	public edu.mgupi.pass.db.locuses.LModules getModule() {
		return module;
	}
	
	public void setParams(java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedModuleParams> value) {
		this.params = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedModuleParams> getParams() {
		return params;
	}
	
	
	public void setData(java.util.List<edu.mgupi.pass.db.locuses.LocusModuleData> value) {
		this.data = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.locuses.LocusModuleData> getData() {
		return data;
	}
	
	
	public String toString() {
		return String.valueOf(getIdLocusModule());
	}
	
}
