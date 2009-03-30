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
 * Каталог примененных фильтров к годографу
 */
@Entity
@org.hibernate.annotations.Proxy(lazy=false)
@Table(name="LocusAppliedFilters")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class LocusAppliedFilters implements Serializable {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedFilters.class);
	public LocusAppliedFilters() {
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
	
	@Column(name="IdLocusFilter", nullable=false)	
	@Id	
	@GeneratedValue(generator="V0A1070D31205627A6E50317C")	
	@org.hibernate.annotations.GenericGenerator(name="V0A1070D31205627A6E50317C", strategy="native")	
	private int idLocusFilter;
	
	@OneToOne(targetEntity=edu.mgupi.pass.db.locuses.LFilters.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="LFiltersIdLFilter") })	
	@Basic(fetch=FetchType.LAZY)	
	private edu.mgupi.pass.db.locuses.LFilters filter;
	
	@OneToMany(targetEntity=edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumn(name="LocusAppliedFiltersIdLocusFilter", nullable=true)	
	@org.hibernate.annotations.IndexColumn(name="LocusAppliedFiltersIndex")	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedFilterParams> params = new java.util.ArrayList<edu.mgupi.pass.db.locuses.LocusAppliedFilterParams>();
	
	private void setIdLocusFilter(int value) {
		this.idLocusFilter = value;
	}
	
	public int getIdLocusFilter() {
		return idLocusFilter;
	}
	
	public int getORMID() {
		return getIdLocusFilter();
	}
	
	public void setFilter(edu.mgupi.pass.db.locuses.LFilters value) {
		this.filter = value;
	}
	
	public edu.mgupi.pass.db.locuses.LFilters getFilter() {
		return filter;
	}
	
	public void setParams(java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedFilterParams> value) {
		this.params = value;
	}
	
	public java.util.List<edu.mgupi.pass.db.locuses.LocusAppliedFilterParams> getParams() {
		return params;
	}
	
	
	public String toString() {
		return String.valueOf(getIdLocusFilter());
	}
	
}
