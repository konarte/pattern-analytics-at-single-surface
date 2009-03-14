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

public class SurfaceTypesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SurfaceTypesFactory.class);
	public static SurfaceTypes loadSurfaceTypesByORMID(int idSurfaceType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceTypesByORMID(session, idSurfaceType);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByORMID(int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes getSurfaceTypesByORMID(int idSurfaceType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSurfaceTypesByORMID(session, idSurfaceType);
		}
		catch (Exception e) {
			_logger.error("getSurfaceTypesByORMID(int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByORMID(int idSurfaceType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceTypesByORMID(session, idSurfaceType, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByORMID(int idSurfaceType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByORMID(PersistentSession session, int idSurfaceType) throws PersistentException {
		try {
			return (SurfaceTypes) session.load(edu.mgupi.pass.db.surfaces.SurfaceTypes.class, new Integer(idSurfaceType));
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByORMID(PersistentSession session, int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes getSurfaceTypesByORMID(PersistentSession session, int idSurfaceType) throws PersistentException {
		try {
			return (SurfaceTypes) session.get(edu.mgupi.pass.db.surfaces.SurfaceTypes.class, new Integer(idSurfaceType));
		}
		catch (Exception e) {
			_logger.error("getSurfaceTypesByORMID(PersistentSession session, int idSurfaceType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByORMID(PersistentSession session, int idSurfaceType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SurfaceTypes) session.load(edu.mgupi.pass.db.surfaces.SurfaceTypes.class, new Integer(idSurfaceType), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByORMID(PersistentSession session, int idSurfaceType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes[] listSurfaceTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfaceTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes[] listSurfaceTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSurfaceTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes[] listSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceTypes as SurfaceTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (SurfaceTypes[]) list.toArray(new SurfaceTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes[] listSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceTypes as SurfaceTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (SurfaceTypes[]) list.toArray(new SurfaceTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSurfaceTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes loadSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		SurfaceTypes[] surfaceTypeses = listSurfaceTypesByQuery(session, condition, orderBy);
		if (surfaceTypeses != null && surfaceTypeses.length > 0)
			return surfaceTypeses[0];
		else
			return null;
	}
	
	public static SurfaceTypes loadSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		SurfaceTypes[] surfaceTypeses = listSurfaceTypesByQuery(session, condition, orderBy, lockMode);
		if (surfaceTypeses != null && surfaceTypeses.length > 0)
			return surfaceTypeses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSurfaceTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfaceTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSurfaceTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceTypes as SurfaceTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.SurfaceTypes as SurfaceTypes");
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
			_logger.error("iterateSurfaceTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SurfaceTypes createSurfaceTypes() {
		return new edu.mgupi.pass.db.surfaces.SurfaceTypes();
	}
	
	public static SurfaceTypes loadSurfaceTypesByCriteria(SurfaceTypesCriteria surfaceTypesCriteria) {
		SurfaceTypes[] surfaceTypeses = listSurfaceTypesByCriteria(surfaceTypesCriteria);
		if(surfaceTypeses == null || surfaceTypeses.length == 0) {
			return null;
		}
		return surfaceTypeses[0];
	}
	
	public static SurfaceTypes[] listSurfaceTypesByCriteria(SurfaceTypesCriteria surfaceTypesCriteria) {
		return surfaceTypesCriteria.listSurfaceTypes();
	}
}
