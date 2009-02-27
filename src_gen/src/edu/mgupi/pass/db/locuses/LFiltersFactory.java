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

public class LFiltersFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LFiltersFactory.class);
	public static LFilters loadLFiltersByORMID(int idLFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLFiltersByORMID(session, idLFilter);
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByORMID(int idLFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters getLFiltersByORMID(int idLFilter) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLFiltersByORMID(session, idLFilter);
		}
		catch (Exception e) {
			_logger.error("getLFiltersByORMID(int idLFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByORMID(int idLFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLFiltersByORMID(session, idLFilter, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByORMID(int idLFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByORMID(PersistentSession session, int idLFilter) throws PersistentException {
		try {
			return (LFilters) session.load(edu.mgupi.pass.db.locuses.LFilters.class, new Integer(idLFilter));
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByORMID(PersistentSession session, int idLFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters getLFiltersByORMID(PersistentSession session, int idLFilter) throws PersistentException {
		try {
			return (LFilters) session.get(edu.mgupi.pass.db.locuses.LFilters.class, new Integer(idLFilter));
		}
		catch (Exception e) {
			_logger.error("getLFiltersByORMID(PersistentSession session, int idLFilter)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByORMID(PersistentSession session, int idLFilter, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LFilters) session.load(edu.mgupi.pass.db.locuses.LFilters.class, new Integer(idLFilter), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByORMID(PersistentSession session, int idLFilter, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters[] listLFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters[] listLFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters[] listLFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LFilters as LFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LFilters[]) list.toArray(new LFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters[] listLFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LFilters as LFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LFilters[]) list.toArray(new LFilters[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters loadLFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LFilters[] lFilterses = listLFiltersByQuery(session, condition, orderBy);
		if (lFilterses != null && lFilterses.length > 0)
			return lFilterses[0];
		else
			return null;
	}
	
	public static LFilters loadLFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LFilters[] lFilterses = listLFiltersByQuery(session, condition, orderBy, lockMode);
		if (lFilterses != null && lFilterses.length > 0)
			return lFilterses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLFiltersByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLFiltersByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLFiltersByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLFiltersByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLFiltersByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLFiltersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LFilters as LFilters");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLFiltersByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LFilters as LFilters");
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
			_logger.error("iterateLFiltersByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LFilters createLFilters() {
		return new edu.mgupi.pass.db.locuses.LFilters();
	}
	
	public static LFilters loadLFiltersByCriteria(LFiltersCriteria lFiltersCriteria) {
		LFilters[] lFilterses = listLFiltersByCriteria(lFiltersCriteria);
		if(lFilterses == null || lFilterses.length == 0) {
			return null;
		}
		return lFilterses[0];
	}
	
	public static LFilters[] listLFiltersByCriteria(LFiltersCriteria lFiltersCriteria) {
		return lFiltersCriteria.listLFilters();
	}
}
