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

public class LocusFilterOptionsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusFilterOptionsFactory.class);
	public static LocusFilterOptions loadLocusFilterOptionsByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFilterOptionsByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions getLocusFilterOptionsByORMID(int idLocusFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusFilterOptionsByORMID(session, idLocusFilter);
		}
		catch (Exception e) {
			_logger.error("getLocusFilterOptionsByORMID(int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByORMID(int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFilterOptionsByORMID(session, idLocusFilter, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByORMID(int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusFilterOptions) session.load(edu.mgupi.pass.db.locuses.LocusFilterOptions.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions getLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter) throws PersistentException {
		try {
			return (LocusFilterOptions) session.get(edu.mgupi.pass.db.locuses.LocusFilterOptions.class, new Integer(idLocusFilter));
		}
		catch (Exception e) {
			_logger.error("getLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusFilterOptions) session.load(edu.mgupi.pass.db.locuses.LocusFilterOptions.class, new Integer(idLocusFilter), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByORMID(PersistentSession session, int idLocusFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions[] listLocusFilterOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusFilterOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions[] listLocusFilterOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusFilterOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions[] listLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilterOptions as LocusFilterOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusFilterOptions[]) list.toArray(new LocusFilterOptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions[] listLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilterOptions as LocusFilterOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusFilterOptions[]) list.toArray(new LocusFilterOptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFilterOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusFilterOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusFilterOptions[] locusFilterOptionses = listLocusFilterOptionsByQuery(session, condition, orderBy);
		if (locusFilterOptionses != null && locusFilterOptionses.length > 0)
			return locusFilterOptionses[0];
		else
			return null;
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusFilterOptions[] locusFilterOptionses = listLocusFilterOptionsByQuery(session, condition, orderBy, lockMode);
		if (locusFilterOptionses != null && locusFilterOptionses.length > 0)
			return locusFilterOptionses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusFilterOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusFilterOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFilterOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusFilterOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusFilterOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilterOptions as LocusFilterOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusFilterOptions as LocusFilterOptions");
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
			_logger.error("iterateLocusFilterOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusFilterOptions createLocusFilterOptions() {
		return new edu.mgupi.pass.db.locuses.LocusFilterOptions();
	}
	
	public static LocusFilterOptions loadLocusFilterOptionsByCriteria(LocusFilterOptionsCriteria locusFilterOptionsCriteria) {
		LocusFilterOptions[] locusFilterOptionses = listLocusFilterOptionsByCriteria(locusFilterOptionsCriteria);
		if(locusFilterOptionses == null || locusFilterOptionses.length == 0) {
			return null;
		}
		return locusFilterOptionses[0];
	}
	
	public static LocusFilterOptions[] listLocusFilterOptionsByCriteria(LocusFilterOptionsCriteria locusFilterOptionsCriteria) {
		return locusFilterOptionsCriteria.listLocusFilterOptions();
	}
}
