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

public class LocusAppliedModuleFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusAppliedModuleFactory.class);
	public static LocusAppliedModule loadLocusAppliedModuleByORMID(int idLocusModule) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleByORMID(session, idLocusModule);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByORMID(int idLocusModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule getLocusAppliedModuleByORMID(int idLocusModule) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusAppliedModuleByORMID(session, idLocusModule);
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedModuleByORMID(int idLocusModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByORMID(int idLocusModule, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleByORMID(session, idLocusModule, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByORMID(int idLocusModule, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule) throws PersistentException {
		try {
			return (LocusAppliedModule) session.load(edu.mgupi.pass.db.locuses.LocusAppliedModule.class, new Integer(idLocusModule));
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule getLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule) throws PersistentException {
		try {
			return (LocusAppliedModule) session.get(edu.mgupi.pass.db.locuses.LocusAppliedModule.class, new Integer(idLocusModule));
		}
		catch (Exception e) {
			_logger.error("getLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusAppliedModule) session.load(edu.mgupi.pass.db.locuses.LocusAppliedModule.class, new Integer(idLocusModule), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByORMID(PersistentSession session, int idLocusModule, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule[] listLocusAppliedModuleByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedModuleByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule[] listLocusAppliedModuleByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusAppliedModuleByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule[] listLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModule as LocusAppliedModule");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusAppliedModule[]) list.toArray(new LocusAppliedModule[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule[] listLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModule as LocusAppliedModule");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusAppliedModule[]) list.toArray(new LocusAppliedModule[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusAppliedModuleByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusAppliedModule[] locusAppliedModules = listLocusAppliedModuleByQuery(session, condition, orderBy);
		if (locusAppliedModules != null && locusAppliedModules.length > 0)
			return locusAppliedModules[0];
		else
			return null;
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusAppliedModule[] locusAppliedModules = listLocusAppliedModuleByQuery(session, condition, orderBy, lockMode);
		if (locusAppliedModules != null && locusAppliedModules.length > 0)
			return locusAppliedModules[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedModuleByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusAppliedModuleByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModule as LocusAppliedModule");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusAppliedModule as LocusAppliedModule");
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
			_logger.error("iterateLocusAppliedModuleByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusAppliedModule createLocusAppliedModule() {
		return new edu.mgupi.pass.db.locuses.LocusAppliedModule();
	}
	
	public static LocusAppliedModule loadLocusAppliedModuleByCriteria(LocusAppliedModuleCriteria locusAppliedModuleCriteria) {
		LocusAppliedModule[] locusAppliedModules = listLocusAppliedModuleByCriteria(locusAppliedModuleCriteria);
		if(locusAppliedModules == null || locusAppliedModules.length == 0) {
			return null;
		}
		return locusAppliedModules[0];
	}
	
	public static LocusAppliedModule[] listLocusAppliedModuleByCriteria(LocusAppliedModuleCriteria locusAppliedModuleCriteria) {
		return locusAppliedModuleCriteria.listLocusAppliedModule();
	}
}
