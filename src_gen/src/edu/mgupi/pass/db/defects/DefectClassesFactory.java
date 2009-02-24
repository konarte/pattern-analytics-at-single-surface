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
package edu.mgupi.pass.db.defects;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class DefectClassesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectClassesFactory.class);
	public static DefectClasses loadDefectClassesByORMID(int idDefectClass) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectClassesByORMID(session, idDefectClass);
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByORMID(int idDefectClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses getDefectClassesByORMID(int idDefectClass) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getDefectClassesByORMID(session, idDefectClass);
		}
		catch (Exception e) {
			_logger.error("getDefectClassesByORMID(int idDefectClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByORMID(int idDefectClass, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectClassesByORMID(session, idDefectClass, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByORMID(int idDefectClass, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByORMID(PersistentSession session, int idDefectClass) throws PersistentException {
		try {
			return (DefectClasses) session.load(edu.mgupi.pass.db.defects.DefectClasses.class, new Integer(idDefectClass));
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByORMID(PersistentSession session, int idDefectClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses getDefectClassesByORMID(PersistentSession session, int idDefectClass) throws PersistentException {
		try {
			return (DefectClasses) session.get(edu.mgupi.pass.db.defects.DefectClasses.class, new Integer(idDefectClass));
		}
		catch (Exception e) {
			_logger.error("getDefectClassesByORMID(PersistentSession session, int idDefectClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByORMID(PersistentSession session, int idDefectClass, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DefectClasses) session.load(edu.mgupi.pass.db.defects.DefectClasses.class, new Integer(idDefectClass), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByORMID(PersistentSession session, int idDefectClass, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses[] listDefectClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses[] listDefectClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses[] listDefectClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectClasses as DefectClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (DefectClasses[]) list.toArray(new DefectClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses[] listDefectClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectClasses as DefectClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (DefectClasses[]) list.toArray(new DefectClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses loadDefectClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DefectClasses[] defectClasseses = listDefectClassesByQuery(session, condition, orderBy);
		if (defectClasseses != null && defectClasseses.length > 0)
			return defectClasseses[0];
		else
			return null;
	}
	
	public static DefectClasses loadDefectClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DefectClasses[] defectClasseses = listDefectClassesByQuery(session, condition, orderBy, lockMode);
		if (defectClasseses != null && defectClasseses.length > 0)
			return defectClasseses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDefectClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateDefectClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectClasses as DefectClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateDefectClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectClasses as DefectClasses");
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
			_logger.error("iterateDefectClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectClasses createDefectClasses() {
		return new edu.mgupi.pass.db.defects.DefectClasses();
	}
	
	public static DefectClasses loadDefectClassesByCriteria(DefectClassesCriteria defectClassesCriteria) {
		DefectClasses[] defectClasseses = listDefectClassesByCriteria(defectClassesCriteria);
		if(defectClasseses == null || defectClasseses.length == 0) {
			return null;
		}
		return defectClasseses[0];
	}
	
	public static DefectClasses[] listDefectClassesByCriteria(DefectClassesCriteria defectClassesCriteria) {
		return defectClassesCriteria.listDefectClasses();
	}
}
