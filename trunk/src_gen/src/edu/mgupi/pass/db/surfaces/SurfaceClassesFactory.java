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
package edu.mgupi.pass.db.surfaces;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class SurfaceClassesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfaceClassesFactory.class);
	public static SurfaceClasses loadSurfaceClassesByORMID(int idSurfaceType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceClassesByORMID(session, idSurfaceType);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByORMID(int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses getSurfaceClassesByORMID(int idSurfaceType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSurfaceClassesByORMID(session, idSurfaceType);
		}
		catch (Exception e) {
			_logger.error("getSurfaceClassesByORMID(int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByORMID(int idSurfaceType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceClassesByORMID(session, idSurfaceType, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByORMID(int idSurfaceType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByORMID(PersistentSession session, int idSurfaceType) throws PersistentException {
		try {
			return (SurfaceClasses) session.load(edu.mgupi.pass.db.surfaces.SurfaceClasses.class, new Integer(idSurfaceType));
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByORMID(PersistentSession session, int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses getSurfaceClassesByORMID(PersistentSession session, int idSurfaceType) throws PersistentException {
		try {
			return (SurfaceClasses) session.get(edu.mgupi.pass.db.surfaces.SurfaceClasses.class, new Integer(idSurfaceType));
		}
		catch (Exception e) {
			_logger.error("getSurfaceClassesByORMID(PersistentSession session, int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByORMID(PersistentSession session, int idSurfaceType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SurfaceClasses) session.load(edu.mgupi.pass.db.surfaces.SurfaceClasses.class, new Integer(idSurfaceType), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByORMID(PersistentSession session, int idSurfaceType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses[] listSurfaceClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfaceClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses[] listSurfaceClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfaceClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses[] listSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceClasses as SurfaceClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (SurfaceClasses[]) list.toArray(new SurfaceClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses[] listSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceClasses as SurfaceClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (SurfaceClasses[]) list.toArray(new SurfaceClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses loadSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		SurfaceClasses[] surfaceClasseses = listSurfaceClassesByQuery(session, condition, orderBy);
		if (surfaceClasseses != null && surfaceClasseses.length > 0)
			return surfaceClasseses[0];
		else
			return null;
	}
	
	public static SurfaceClasses loadSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		SurfaceClasses[] surfaceClasseses = listSurfaceClassesByQuery(session, condition, orderBy, lockMode);
		if (surfaceClasseses != null && surfaceClasseses.length > 0)
			return surfaceClasseses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSurfaceClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfaceClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfaceClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceClasses as SurfaceClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceClasses as SurfaceClasses");
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
			_logger.error("iterateSurfaceClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceClasses createSurfaceClasses() {
		return new edu.mgupi.pass.db.surfaces.SurfaceClasses();
	}
	
	public static SurfaceClasses loadSurfaceClassesByCriteria(SurfaceClassesCriteria surfaceClassesCriteria) {
		SurfaceClasses[] surfaceClasseses = listSurfaceClassesByCriteria(surfaceClassesCriteria);
		if(surfaceClasseses == null || surfaceClasseses.length == 0) {
			return null;
		}
		return surfaceClasseses[0];
	}
	
	public static SurfaceClasses[] listSurfaceClassesByCriteria(SurfaceClassesCriteria surfaceClassesCriteria) {
		return surfaceClassesCriteria.listSurfaceClasses();
	}
}
