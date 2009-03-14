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
package edu.mgupi.pass.db.defects;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class DefectTypesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectTypesFactory.class);
	public static DefectTypes loadDefectTypesByORMID(int idDefectType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypesByORMID(session, idDefectType);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByORMID(int idDefectType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes getDefectTypesByORMID(int idDefectType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getDefectTypesByORMID(session, idDefectType);
		}
		catch (Exception e) {
			_logger.error("getDefectTypesByORMID(int idDefectType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByORMID(int idDefectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypesByORMID(session, idDefectType, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByORMID(int idDefectType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByORMID(PersistentSession session, int idDefectType) throws PersistentException {
		try {
			return (DefectTypes) session.load(edu.mgupi.pass.db.defects.DefectTypes.class, new Integer(idDefectType));
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByORMID(PersistentSession session, int idDefectType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes getDefectTypesByORMID(PersistentSession session, int idDefectType) throws PersistentException {
		try {
			return (DefectTypes) session.get(edu.mgupi.pass.db.defects.DefectTypes.class, new Integer(idDefectType));
		}
		catch (Exception e) {
			_logger.error("getDefectTypesByORMID(PersistentSession session, int idDefectType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByORMID(PersistentSession session, int idDefectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DefectTypes) session.load(edu.mgupi.pass.db.defects.DefectTypes.class, new Integer(idDefectType), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByORMID(PersistentSession session, int idDefectType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes[] listDefectTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes[] listDefectTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes[] listDefectTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypes as DefectTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (DefectTypes[]) list.toArray(new DefectTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes[] listDefectTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypes as DefectTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (DefectTypes[]) list.toArray(new DefectTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes loadDefectTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DefectTypes[] defectTypeses = listDefectTypesByQuery(session, condition, orderBy);
		if (defectTypeses != null && defectTypeses.length > 0)
			return defectTypeses[0];
		else
			return null;
	}
	
	public static DefectTypes loadDefectTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DefectTypes[] defectTypeses = listDefectTypesByQuery(session, condition, orderBy, lockMode);
		if (defectTypeses != null && defectTypeses.length > 0)
			return defectTypeses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDefectTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypes as DefectTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypes as DefectTypes");
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
			_logger.error("iterateDefectTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypes createDefectTypes() {
		return new edu.mgupi.pass.db.defects.DefectTypes();
	}
	
	public static DefectTypes loadDefectTypesByCriteria(DefectTypesCriteria defectTypesCriteria) {
		DefectTypes[] defectTypeses = listDefectTypesByCriteria(defectTypesCriteria);
		if(defectTypeses == null || defectTypeses.length == 0) {
			return null;
		}
		return defectTypeses[0];
	}
	
	public static DefectTypes[] listDefectTypesByCriteria(DefectTypesCriteria defectTypesCriteria) {
		return defectTypesCriteria.listDefectTypes();
	}
}
