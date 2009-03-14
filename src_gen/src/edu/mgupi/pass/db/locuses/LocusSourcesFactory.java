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

public class LocusSourcesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusSourcesFactory.class);
	public static LocusSources loadLocusSourcesByORMID(int idLocusSource) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusSourcesByORMID(session, idLocusSource);
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByORMID(int idLocusSource)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources getLocusSourcesByORMID(int idLocusSource) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusSourcesByORMID(session, idLocusSource);
		}
		catch (Exception e) {
			_logger.error("getLocusSourcesByORMID(int idLocusSource)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByORMID(int idLocusSource, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusSourcesByORMID(session, idLocusSource, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByORMID(int idLocusSource, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByORMID(PersistentSession session, int idLocusSource) throws PersistentException {
		try {
			return (LocusSources) session.load(edu.mgupi.pass.db.locuses.LocusSources.class, new Integer(idLocusSource));
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByORMID(PersistentSession session, int idLocusSource)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources getLocusSourcesByORMID(PersistentSession session, int idLocusSource) throws PersistentException {
		try {
			return (LocusSources) session.get(edu.mgupi.pass.db.locuses.LocusSources.class, new Integer(idLocusSource));
		}
		catch (Exception e) {
			_logger.error("getLocusSourcesByORMID(PersistentSession session, int idLocusSource)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByORMID(PersistentSession session, int idLocusSource, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusSources) session.load(edu.mgupi.pass.db.locuses.LocusSources.class, new Integer(idLocusSource), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByORMID(PersistentSession session, int idLocusSource, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources[] listLocusSourcesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusSourcesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources[] listLocusSourcesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusSourcesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources[] listLocusSourcesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusSources as LocusSources");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusSources[]) list.toArray(new LocusSources[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusSourcesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources[] listLocusSourcesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusSources as LocusSources");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusSources[]) list.toArray(new LocusSources[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusSourcesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusSourcesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusSourcesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources loadLocusSourcesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusSources[] locusSourceses = listLocusSourcesByQuery(session, condition, orderBy);
		if (locusSourceses != null && locusSourceses.length > 0)
			return locusSourceses[0];
		else
			return null;
	}
	
	public static LocusSources loadLocusSourcesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusSources[] locusSourceses = listLocusSourcesByQuery(session, condition, orderBy, lockMode);
		if (locusSourceses != null && locusSourceses.length > 0)
			return locusSourceses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusSourcesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusSourcesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusSourcesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusSourcesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusSourcesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusSourcesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusSources as LocusSources");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusSourcesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusSourcesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusSources as LocusSources");
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
			_logger.error("iterateLocusSourcesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusSources createLocusSources() {
		return new edu.mgupi.pass.db.locuses.LocusSources();
	}
	
	public static LocusSources loadLocusSourcesByCriteria(LocusSourcesCriteria locusSourcesCriteria) {
		LocusSources[] locusSourceses = listLocusSourcesByCriteria(locusSourcesCriteria);
		if(locusSourceses == null || locusSourceses.length == 0) {
			return null;
		}
		return locusSourceses[0];
	}
	
	public static LocusSources[] listLocusSourcesByCriteria(LocusSourcesCriteria locusSourcesCriteria) {
		return locusSourcesCriteria.listLocusSources();
	}
}
