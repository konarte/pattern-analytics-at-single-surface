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
import org.hibernate.Query;
import java.util.List;

public class LocusAppliedFiltersFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedFiltersFactory.class);
	public static LocusAppliedFilters loadLocusAppliedFiltersByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFiltersByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters getLocusAppliedFiltersByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusAppliedFiltersByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedFiltersByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByORMID(int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFiltersByORMID(session, idLocusFilter, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByORMID(int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusAppliedFilters) session.load(edu.mgupi.pass.db.locuses.LocusAppliedFilters.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters getLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusAppliedFilters) session.get(edu.mgupi.pass.db.locuses.LocusAppliedFilters.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusAppliedFilters) session.load(edu.mgupi.pass.db.locuses.LocusAppliedFilters.class, new Integer(idLocusFilter), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters[] listLocusAppliedFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters[] listLocusAppliedFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters[] listLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilters as LocusAppliedFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusAppliedFilters[]) list.toArray(new LocusAppliedFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters[] listLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilters as LocusAppliedFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusAppliedFilters[]) list.toArray(new LocusAppliedFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusAppliedFilters[] locusAppliedFilterses = listLocusAppliedFiltersByQuery(session, condition, orderBy);
		if (locusAppliedFilterses != null && locusAppliedFilterses.length > 0)
			return locusAppliedFilterses[0];
		else
			return null;
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusAppliedFilters[] locusAppliedFilterses = listLocusAppliedFiltersByQuery(session, condition, orderBy, lockMode);
		if (locusAppliedFilterses != null && locusAppliedFilterses.length > 0)
			return locusAppliedFilterses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusAppliedFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilters as LocusAppliedFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilters as LocusAppliedFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilters createLocusAppliedFilters() {
		return new edu.mgupi.pass.db.locuses.LocusAppliedFilters();
	}
	
	public static LocusAppliedFilters loadLocusAppliedFiltersByCriteria(LocusAppliedFiltersCriteria locusAppliedFiltersCriteria) {
		LocusAppliedFilters[] locusAppliedFilterses = listLocusAppliedFiltersByCriteria(locusAppliedFiltersCriteria);
		if(locusAppliedFilterses == null || locusAppliedFilterses.length == 0) {
			return null;
		}
		return locusAppliedFilterses[0];
	}
	
	public static LocusAppliedFilters[] listLocusAppliedFiltersByCriteria(LocusAppliedFiltersCriteria locusAppliedFiltersCriteria) {
		return locusAppliedFiltersCriteria.listLocusAppliedFilters();
	}
}
