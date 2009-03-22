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

public class DefectTypeOptionsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectTypeOptionsFactory.class);
	public static DefectTypeOptions loadDefectTypeOptionsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypeOptionsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions getDefectTypeOptionsByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getDefectTypeOptionsByORMID(session, ID);
		}
		catch (Exception e) {
			_logger.error("getDefectTypeOptionsByORMID(int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypeOptionsByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByORMID(int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (DefectTypeOptions) session.load(edu.mgupi.pass.db.defects.DefectTypeOptions.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions getDefectTypeOptionsByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (DefectTypeOptions) session.get(edu.mgupi.pass.db.defects.DefectTypeOptions.class, new Integer(ID));
		}
		catch (Exception e) {
			_logger.error("getDefectTypeOptionsByORMID(PersistentSession session, int ID)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DefectTypeOptions) session.load(edu.mgupi.pass.db.defects.DefectTypeOptions.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions[] listDefectTypeOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypeOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions[] listDefectTypeOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypeOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions[] listDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeOptions as DefectTypeOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (DefectTypeOptions[]) list.toArray(new DefectTypeOptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions[] listDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeOptions as DefectTypeOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (DefectTypeOptions[]) list.toArray(new DefectTypeOptions[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypeOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypeOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DefectTypeOptions[] defectTypeOptionses = listDefectTypeOptionsByQuery(session, condition, orderBy);
		if (defectTypeOptionses != null && defectTypeOptionses.length > 0)
			return defectTypeOptionses[0];
		else
			return null;
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DefectTypeOptions[] defectTypeOptionses = listDefectTypeOptionsByQuery(session, condition, orderBy, lockMode);
		if (defectTypeOptionses != null && defectTypeOptionses.length > 0)
			return defectTypeOptionses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDefectTypeOptionsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypeOptionsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypeOptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypeOptionsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypeOptionsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeOptions as DefectTypeOptions");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeOptions as DefectTypeOptions");
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
			_logger.error("iterateDefectTypeOptionsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeOptions createDefectTypeOptions() {
		return new edu.mgupi.pass.db.defects.DefectTypeOptions();
	}
	
	public static DefectTypeOptions loadDefectTypeOptionsByCriteria(DefectTypeOptionsCriteria defectTypeOptionsCriteria) {
		DefectTypeOptions[] defectTypeOptionses = listDefectTypeOptionsByCriteria(defectTypeOptionsCriteria);
		if(defectTypeOptionses == null || defectTypeOptionses.length == 0) {
			return null;
		}
		return defectTypeOptionses[0];
	}
	
	public static DefectTypeOptions[] listDefectTypeOptionsByCriteria(DefectTypeOptionsCriteria defectTypeOptionsCriteria) {
		return defectTypeOptionsCriteria.listDefectTypeOptions();
	}
}
