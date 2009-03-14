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

public class LocusModuleParamsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusModuleParamsFactory.class);
	public static LocusModuleParams loadLocusModuleParamsByORMID(int idModuleParam) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleParamsByORMID(session, idModuleParam);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByORMID(int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams getLocusModuleParamsByORMID(int idModuleParam) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusModuleParamsByORMID(session, idModuleParam);
		}
		catch (Exception e) {
			_logger.error("getLocusModuleParamsByORMID(int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByORMID(int idModuleParam, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleParamsByORMID(session, idModuleParam, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByORMID(int idModuleParam, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByORMID(PersistentSession session, int idModuleParam) throws PersistentException {
		try {
			return (LocusModuleParams) session.load(edu.mgupi.pass.db.locuses.LocusModuleParams.class, new Integer(idModuleParam));
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByORMID(PersistentSession session, int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams getLocusModuleParamsByORMID(PersistentSession session, int idModuleParam) throws PersistentException {
		try {
			return (LocusModuleParams) session.get(edu.mgupi.pass.db.locuses.LocusModuleParams.class, new Integer(idModuleParam));
		}
		catch (Exception e) {
			_logger.error("getLocusModuleParamsByORMID(PersistentSession session, int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByORMID(PersistentSession session, int idModuleParam, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusModuleParams) session.load(edu.mgupi.pass.db.locuses.LocusModuleParams.class, new Integer(idModuleParam), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByORMID(PersistentSession session, int idModuleParam, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams[] listLocusModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams[] listLocusModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams[] listLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleParams as LocusModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusModuleParams[]) list.toArray(new LocusModuleParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams[] listLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleParams as LocusModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusModuleParams[]) list.toArray(new LocusModuleParams[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams loadLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusModuleParams[] locusModuleParamses = listLocusModuleParamsByQuery(session, condition, orderBy);
		if (locusModuleParamses != null && locusModuleParamses.length > 0)
			return locusModuleParamses[0];
		else
			return null;
	}
	
	public static LocusModuleParams loadLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusModuleParams[] locusModuleParamses = listLocusModuleParamsByQuery(session, condition, orderBy, lockMode);
		if (locusModuleParamses != null && locusModuleParamses.length > 0)
			return locusModuleParamses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusModuleParamsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusModuleParamsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleParamsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusModuleParamsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleParamsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleParams as LocusModuleParams");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleParams as LocusModuleParams");
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
			_logger.error("iterateLocusModuleParamsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleParams createLocusModuleParams() {
		return new edu.mgupi.pass.db.locuses.LocusModuleParams();
	}
	
	public static LocusModuleParams loadLocusModuleParamsByCriteria(LocusModuleParamsCriteria locusModuleParamsCriteria) {
		LocusModuleParams[] locusModuleParamses = listLocusModuleParamsByCriteria(locusModuleParamsCriteria);
		if(locusModuleParamses == null || locusModuleParamses.length == 0) {
			return null;
		}
		return locusModuleParamses[0];
	}
	
	public static LocusModuleParams[] listLocusModuleParamsByCriteria(LocusModuleParamsCriteria locusModuleParamsCriteria) {
		return locusModuleParamsCriteria.listLocusModuleParams();
	}
}
