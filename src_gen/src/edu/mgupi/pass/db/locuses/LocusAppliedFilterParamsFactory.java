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

public class LocusAppliedFilterParamsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedFilterParamsFactory.class);
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFilterParamsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams getLocusAppliedFilterParamsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusAppliedFilterParamsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedFilterParamsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFilterParamsByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByORMID(int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (LocusAppliedFilterParams) session.load(edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams getLocusAppliedFilterParamsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (LocusAppliedFilterParams) session.get(edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedFilterParamsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusAppliedFilterParams) session.load(edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams[] listLocusAppliedFilterParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedFilterParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams[] listLocusAppliedFilterParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedFilterParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams[] listLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilterParams as LocusAppliedFilterParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusAppliedFilterParams[]) list.toArray(new LocusAppliedFilterParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams[] listLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilterParams as LocusAppliedFilterParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusAppliedFilterParams[]) list.toArray(new LocusAppliedFilterParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFilterParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedFilterParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusAppliedFilterParams[] locusAppliedFilterParamses = listLocusAppliedFilterParamsByQuery(session, condition, orderBy);
		if (locusAppliedFilterParamses != null && locusAppliedFilterParamses.length > 0)
			return locusAppliedFilterParamses[0];
		else
			return null;
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusAppliedFilterParams[] locusAppliedFilterParamses = listLocusAppliedFilterParamsByQuery(session, condition, orderBy, lockMode);
		if (locusAppliedFilterParamses != null && locusAppliedFilterParamses.length > 0)
			return locusAppliedFilterParamses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusAppliedFilterParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedFilterParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFilterParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedFilterParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFilterParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilterParams as LocusAppliedFilterParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedFilterParams as LocusAppliedFilterParams");
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
			_logger.error("iterateLocusAppliedFilterParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedFilterParams createLocusAppliedFilterParams() {
		return new edu.mgupi.pass.db.locuses.LocusAppliedFilterParams();
	}
	
	public static LocusAppliedFilterParams loadLocusAppliedFilterParamsByCriteria(LocusAppliedFilterParamsCriteria locusAppliedFilterParamsCriteria) {
		LocusAppliedFilterParams[] locusAppliedFilterParamses = listLocusAppliedFilterParamsByCriteria(locusAppliedFilterParamsCriteria);
		if(locusAppliedFilterParamses == null || locusAppliedFilterParamses.length == 0) {
			return null;
		}
		return locusAppliedFilterParamses[0];
	}
	
	public static LocusAppliedFilterParams[] listLocusAppliedFilterParamsByCriteria(LocusAppliedFilterParamsCriteria locusAppliedFilterParamsCriteria) {
		return locusAppliedFilterParamsCriteria.listLocusAppliedFilterParams();
	}
}
