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

public class LocusesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusesFactory.class);
	public static Locuses loadLocusesByORMID(int idLocus) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusesByORMID(session, idLocus);
		}
		catch (Exception e) {
			_logger.error("loadLocusesByORMID(int idLocus)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses getLocusesByORMID(int idLocus) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusesByORMID(session, idLocus);
		}
		catch (Exception e) {
			_logger.error("getLocusesByORMID(int idLocus)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByORMID(int idLocus, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusesByORMID(session, idLocus, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusesByORMID(int idLocus, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByORMID(PersistentSession session, int idLocus) throws PersistentException {
		try {
			return (Locuses) session.load(edu.mgupi.pass.db.locuses.Locuses.class, new Integer(idLocus));
		}
		catch (Exception e) {
			_logger.error("loadLocusesByORMID(PersistentSession session, int idLocus)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses getLocusesByORMID(PersistentSession session, int idLocus) throws PersistentException {
		try {
			return (Locuses) session.get(edu.mgupi.pass.db.locuses.Locuses.class, new Integer(idLocus));
		}
		catch (Exception e) {
			_logger.error("getLocusesByORMID(PersistentSession session, int idLocus)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByORMID(PersistentSession session, int idLocus, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Locuses) session.load(edu.mgupi.pass.db.locuses.Locuses.class, new Integer(idLocus), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusesByORMID(PersistentSession session, int idLocus, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses[] listLocusesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses[] listLocusesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses[] listLocusesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.Locuses as Locuses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Locuses[]) list.toArray(new Locuses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses[] listLocusesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.Locuses as Locuses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Locuses[]) list.toArray(new Locuses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses loadLocusesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Locuses[] locuseses = listLocusesByQuery(session, condition, orderBy);
		if (locuseses != null && locuseses.length > 0)
			return locuseses[0];
		else
			return null;
	}
	
	public static Locuses loadLocusesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Locuses[] locuseses = listLocusesByQuery(session, condition, orderBy, lockMode);
		if (locuseses != null && locuseses.length > 0)
			return locuseses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.Locuses as Locuses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.Locuses as Locuses");
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
			_logger.error("iterateLocusesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Locuses createLocuses() {
		return new edu.mgupi.pass.db.locuses.Locuses();
	}
	
	public static Locuses loadLocusesByCriteria(LocusesCriteria locusesCriteria) {
		Locuses[] locuseses = listLocusesByCriteria(locusesCriteria);
		if(locuseses == null || locuseses.length == 0) {
			return null;
		}
		return locuseses[0];
	}
	
	public static Locuses[] listLocusesByCriteria(LocusesCriteria locusesCriteria) {
		return locusesCriteria.listLocuses();
	}
}
