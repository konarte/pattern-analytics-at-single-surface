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

public class LocusAppliedModuleParamsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedModuleParamsFactory.class);
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleParamsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams getLocusAppliedModuleParamsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusAppliedModuleParamsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedModuleParamsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleParamsByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByORMID(int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (LocusAppliedModuleParams) session.load(edu.mgupi.pass.db.locuses.LocusAppliedModuleParams.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams getLocusAppliedModuleParamsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (LocusAppliedModuleParams) session.get(edu.mgupi.pass.db.locuses.LocusAppliedModuleParams.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedModuleParamsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusAppliedModuleParams) session.load(edu.mgupi.pass.db.locuses.LocusAppliedModuleParams.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams[] listLocusAppliedModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams[] listLocusAppliedModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams[] listLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModuleParams as LocusAppliedModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusAppliedModuleParams[]) list.toArray(new LocusAppliedModuleParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams[] listLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModuleParams as LocusAppliedModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusAppliedModuleParams[]) list.toArray(new LocusAppliedModuleParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusAppliedModuleParams[] locusAppliedModuleParamses = listLocusAppliedModuleParamsByQuery(session, condition, orderBy);
		if (locusAppliedModuleParamses != null && locusAppliedModuleParamses.length > 0)
			return locusAppliedModuleParamses[0];
		else
			return null;
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusAppliedModuleParams[] locusAppliedModuleParamses = listLocusAppliedModuleParamsByQuery(session, condition, orderBy, lockMode);
		if (locusAppliedModuleParamses != null && locusAppliedModuleParamses.length > 0)
			return locusAppliedModuleParamses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModuleParams as LocusAppliedModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModuleParams as LocusAppliedModuleParams");
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
			_logger.error("iterateLocusAppliedModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModuleParams createLocusAppliedModuleParams() {
		return new edu.mgupi.pass.db.locuses.LocusAppliedModuleParams();
	}
	
	public static LocusAppliedModuleParams loadLocusAppliedModuleParamsByCriteria(LocusAppliedModuleParamsCriteria locusAppliedModuleParamsCriteria) {
		LocusAppliedModuleParams[] locusAppliedModuleParamses = listLocusAppliedModuleParamsByCriteria(locusAppliedModuleParamsCriteria);
		if(locusAppliedModuleParamses == null || locusAppliedModuleParamses.length == 0) {
			return null;
		}
		return locusAppliedModuleParamses[0];
	}
	
	public static LocusAppliedModuleParams[] listLocusAppliedModuleParamsByCriteria(LocusAppliedModuleParamsCriteria locusAppliedModuleParamsCriteria) {
		return locusAppliedModuleParamsCriteria.listLocusAppliedModuleParams();
	}
}
