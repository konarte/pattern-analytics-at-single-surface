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

public class LModulesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LModulesFactory.class);
	public static LModules loadLModulesByORMID(int idModule) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLModulesByORMID(session, idModule);
		}
		catch (Exception e) {
			_logger.error("loadLModulesByORMID(int idModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules getLModulesByORMID(int idModule) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLModulesByORMID(session, idModule);
		}
		catch (Exception e) {
			_logger.error("getLModulesByORMID(int idModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByORMID(int idModule, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLModulesByORMID(session, idModule, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLModulesByORMID(int idModule, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByORMID(PersistentSession session, int idModule) throws PersistentException {
		try {
			return (LModules) session.load(edu.mgupi.pass.db.locuses.LModules.class, new Integer(idModule));
		}
		catch (Exception e) {
			_logger.error("loadLModulesByORMID(PersistentSession session, int idModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules getLModulesByORMID(PersistentSession session, int idModule) throws PersistentException {
		try {
			return (LModules) session.get(edu.mgupi.pass.db.locuses.LModules.class, new Integer(idModule));
		}
		catch (Exception e) {
			_logger.error("getLModulesByORMID(PersistentSession session, int idModule)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByORMID(PersistentSession session, int idModule, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LModules) session.load(edu.mgupi.pass.db.locuses.LModules.class, new Integer(idModule), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLModulesByORMID(PersistentSession session, int idModule, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules[] listLModulesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLModulesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules[] listLModulesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLModulesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules[] listLModulesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LModules as LModules");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LModules[]) list.toArray(new LModules[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLModulesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules[] listLModulesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LModules as LModules");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LModules[]) list.toArray(new LModules[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLModulesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLModulesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLModulesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules loadLModulesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LModules[] lModuleses = listLModulesByQuery(session, condition, orderBy);
		if (lModuleses != null && lModuleses.length > 0)
			return lModuleses[0];
		else
			return null;
	}
	
	public static LModules loadLModulesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LModules[] lModuleses = listLModulesByQuery(session, condition, orderBy, lockMode);
		if (lModuleses != null && lModuleses.length > 0)
			return lModuleses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLModulesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLModulesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLModulesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLModulesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLModulesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLModulesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LModules as LModules");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLModulesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLModulesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LModules as LModules");
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
			_logger.error("iterateLModulesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LModules createLModules() {
		return new edu.mgupi.pass.db.locuses.LModules();
	}
	
	public static LModules loadLModulesByCriteria(LModulesCriteria lModulesCriteria) {
		LModules[] lModuleses = listLModulesByCriteria(lModulesCriteria);
		if(lModuleses == null || lModuleses.length == 0) {
			return null;
		}
		return lModuleses[0];
	}
	
	public static LModules[] listLModulesByCriteria(LModulesCriteria lModulesCriteria) {
		return lModulesCriteria.listLModules();
	}
}
