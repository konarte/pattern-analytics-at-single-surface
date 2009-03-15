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
package edu.mgupi.pass.db;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class NameMappingFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(NameMappingFactory.class);
	public static NameMapping loadNameMappingByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadNameMappingByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping getNameMappingByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getNameMappingByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("getNameMappingByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadNameMappingByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByORMID(int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (NameMapping) session.load(edu.mgupi.pass.db.NameMapping.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping getNameMappingByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (NameMapping) session.get(edu.mgupi.pass.db.NameMapping.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("getNameMappingByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (NameMapping) session.load(edu.mgupi.pass.db.NameMapping.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping[] listNameMappingByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listNameMappingByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping[] listNameMappingByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listNameMappingByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping[] listNameMappingByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.NameMapping as NameMapping");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (NameMapping[]) list.toArray(new NameMapping[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listNameMappingByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping[] listNameMappingByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.NameMapping as NameMapping");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (NameMapping[]) list.toArray(new NameMapping[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listNameMappingByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadNameMappingByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadNameMappingByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping loadNameMappingByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		NameMapping[] nameMappings = listNameMappingByQuery(session, condition, orderBy);
		if (nameMappings != null && nameMappings.length > 0)
			return nameMappings[0];
		else
			return null;
	}
	
	public static NameMapping loadNameMappingByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		NameMapping[] nameMappings = listNameMappingByQuery(session, condition, orderBy, lockMode);
		if (nameMappings != null && nameMappings.length > 0)
			return nameMappings[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateNameMappingByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateNameMappingByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateNameMappingByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateNameMappingByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateNameMappingByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateNameMappingByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.NameMapping as NameMapping");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateNameMappingByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateNameMappingByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.NameMapping as NameMapping");
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
			_logger.error("iterateNameMappingByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static NameMapping createNameMapping() {
		return new edu.mgupi.pass.db.NameMapping();
	}
	
	public static NameMapping loadNameMappingByCriteria(NameMappingCriteria nameMappingCriteria) {
		NameMapping[] nameMappings = listNameMappingByCriteria(nameMappingCriteria);
		if(nameMappings == null || nameMappings.length == 0) {
			return null;
		}
		return nameMappings[0];
	}
	
	public static NameMapping[] listNameMappingByCriteria(NameMappingCriteria nameMappingCriteria) {
		return nameMappingCriteria.listNameMapping();
	}
}
