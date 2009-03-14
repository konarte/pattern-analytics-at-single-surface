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

public class SurfacesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfacesFactory.class);
	public static Surfaces loadSurfacesByORMID(int idSurface) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfacesByORMID(session, idSurface);
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByORMID(int idSurface)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces getSurfacesByORMID(int idSurface) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSurfacesByORMID(session, idSurface);
		}
		catch (Exception e) {
			_logger.error("getSurfacesByORMID(int idSurface)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByORMID(int idSurface, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfacesByORMID(session, idSurface, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByORMID(int idSurface, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByORMID(PersistentSession session, int idSurface) throws PersistentException {
		try {
			return (Surfaces) session.load(edu.mgupi.pass.db.surfaces.Surfaces.class, new Integer(idSurface));
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByORMID(PersistentSession session, int idSurface)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces getSurfacesByORMID(PersistentSession session, int idSurface) throws PersistentException {
		try {
			return (Surfaces) session.get(edu.mgupi.pass.db.surfaces.Surfaces.class, new Integer(idSurface));
		}
		catch (Exception e) {
			_logger.error("getSurfacesByORMID(PersistentSession session, int idSurface)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByORMID(PersistentSession session, int idSurface, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Surfaces) session.load(edu.mgupi.pass.db.surfaces.Surfaces.class, new Integer(idSurface), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByORMID(PersistentSession session, int idSurface, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces[] listSurfacesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfacesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces[] listSurfacesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfacesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces[] listSurfacesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Surfaces as Surfaces");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Surfaces[]) list.toArray(new Surfaces[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfacesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces[] listSurfacesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Surfaces as Surfaces");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Surfaces[]) list.toArray(new Surfaces[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfacesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfacesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfacesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces loadSurfacesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Surfaces[] surfaceses = listSurfacesByQuery(session, condition, orderBy);
		if (surfaceses != null && surfaceses.length > 0)
			return surfaceses[0];
		else
			return null;
	}
	
	public static Surfaces loadSurfacesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Surfaces[] surfaceses = listSurfacesByQuery(session, condition, orderBy, lockMode);
		if (surfaceses != null && surfaceses.length > 0)
			return surfaceses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSurfacesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfacesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfacesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfacesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSurfacesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfacesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Surfaces as Surfaces");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSurfacesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfacesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Surfaces as Surfaces");
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
			_logger.error("iterateSurfacesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Surfaces createSurfaces() {
		return new edu.mgupi.pass.db.surfaces.Surfaces();
	}
	
	public static Surfaces loadSurfacesByCriteria(SurfacesCriteria surfacesCriteria) {
		Surfaces[] surfaceses = listSurfacesByCriteria(surfacesCriteria);
		if(surfaceses == null || surfaceses.length == 0) {
			return null;
		}
		return surfaceses[0];
	}
	
	public static Surfaces[] listSurfacesByCriteria(SurfacesCriteria surfacesCriteria) {
		return surfacesCriteria.listSurfaces();
	}
}
