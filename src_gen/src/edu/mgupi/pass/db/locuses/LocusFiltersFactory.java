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
package edu.mgupi.pass.db.locuses;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class LocusFiltersFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusFiltersFactory.class);
	public static LocusFilters loadLocusFiltersByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFiltersByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters getLocusFiltersByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusFiltersByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("getLocusFiltersByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByORMID(int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFiltersByORMID(session, idLocusFilter, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByORMID(int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusFilters) session.load(edu.mgupi.pass.db.locuses.LocusFilters.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters getLocusFiltersByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusFilters) session.get(edu.mgupi.pass.db.locuses.LocusFilters.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("getLocusFiltersByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusFilters) session.load(edu.mgupi.pass.db.locuses.LocusFilters.class, new Integer(idLocusFilter), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters[] listLocusFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters[] listLocusFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters[] listLocusFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilters as LocusFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusFilters[]) list.toArray(new LocusFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters[] listLocusFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilters as LocusFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusFilters[]) list.toArray(new LocusFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters loadLocusFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusFilters[] locusFilterses = listLocusFiltersByQuery(session, condition, orderBy);
		if (locusFilterses != null && locusFilterses.length > 0)
			return locusFilterses[0];
		else
			return null;
	}
	
	public static LocusFilters loadLocusFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusFilters[] locusFilterses = listLocusFiltersByQuery(session, condition, orderBy, lockMode);
		if (locusFilterses != null && locusFilterses.length > 0)
			return locusFilterses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilters as LocusFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilters as LocusFilters");
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
			_logger.error("iterateLocusFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilters createLocusFilters() {
		return new edu.mgupi.pass.db.locuses.LocusFilters();
	}
	
	public static LocusFilters loadLocusFiltersByCriteria(LocusFiltersCriteria locusFiltersCriteria) {
		LocusFilters[] locusFilterses = listLocusFiltersByCriteria(locusFiltersCriteria);
		if(locusFilterses == null || locusFilterses.length == 0) {
			return null;
		}
		return locusFilterses[0];
	}
	
	public static LocusFilters[] listLocusFiltersByCriteria(LocusFiltersCriteria locusFiltersCriteria) {
		return locusFiltersCriteria.listLocusFilters();
	}
}
