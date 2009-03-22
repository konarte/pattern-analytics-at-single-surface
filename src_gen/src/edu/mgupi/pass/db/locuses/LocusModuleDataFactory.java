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

public class LocusModuleDataFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(LocusModuleDataFactory.class);
	public static LocusModuleData loadLocusModuleDataByORMID(int idModuleParam) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleDataByORMID(session, idModuleParam);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByORMID(int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData getLocusModuleDataByORMID(int idModuleParam) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getLocusModuleDataByORMID(session, idModuleParam);
		}
		catch (Exception e) {
			_logger.error("getLocusModuleDataByORMID(int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByORMID(int idModuleParam, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleDataByORMID(session, idModuleParam, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByORMID(int idModuleParam, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByORMID(PersistentSession session, int idModuleParam) throws PersistentException {
		try {
			return (LocusModuleData) session.load(edu.mgupi.pass.db.locuses.LocusModuleData.class, new Integer(idModuleParam));
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByORMID(PersistentSession session, int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData getLocusModuleDataByORMID(PersistentSession session, int idModuleParam) throws PersistentException {
		try {
			return (LocusModuleData) session.get(edu.mgupi.pass.db.locuses.LocusModuleData.class, new Integer(idModuleParam));
		}
		catch (Exception e) {
			_logger.error("getLocusModuleDataByORMID(PersistentSession session, int idModuleParam)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByORMID(PersistentSession session, int idModuleParam, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocusModuleData) session.load(edu.mgupi.pass.db.locuses.LocusModuleData.class, new Integer(idModuleParam), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByORMID(PersistentSession session, int idModuleParam, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData[] listLocusModuleDataByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusModuleDataByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData[] listLocusModuleDataByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listLocusModuleDataByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData[] listLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleData as LocusModuleData");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (LocusModuleData[]) list.toArray(new LocusModuleData[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData[] listLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleData as LocusModuleData");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (LocusModuleData[]) list.toArray(new LocusModuleData[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleDataByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadLocusModuleDataByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData loadLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocusModuleData[] locusModuleDatas = listLocusModuleDataByQuery(session, condition, orderBy);
		if (locusModuleDatas != null && locusModuleDatas.length > 0)
			return locusModuleDatas[0];
		else
			return null;
	}
	
	public static LocusModuleData loadLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocusModuleData[] locusModuleDatas = listLocusModuleDataByQuery(session, condition, orderBy, lockMode);
		if (locusModuleDatas != null && locusModuleDatas.length > 0)
			return locusModuleDatas[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocusModuleDataByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusModuleDataByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleDataByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateLocusModuleDataByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleDataByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleData as LocusModuleData");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.locuses.LocusModuleData as LocusModuleData");
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
			_logger.error("iterateLocusModuleDataByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static LocusModuleData createLocusModuleData() {
		return new edu.mgupi.pass.db.locuses.LocusModuleData();
	}
	
	public static LocusModuleData loadLocusModuleDataByCriteria(LocusModuleDataCriteria locusModuleDataCriteria) {
		LocusModuleData[] locusModuleDatas = listLocusModuleDataByCriteria(locusModuleDataCriteria);
		if(locusModuleDatas == null || locusModuleDatas.length == 0) {
			return null;
		}
		return locusModuleDatas[0];
	}
	
	public static LocusModuleData[] listLocusModuleDataByCriteria(LocusModuleDataCriteria locusModuleDataCriteria) {
		return locusModuleDataCriteria.listLocusModuleData();
	}
}
