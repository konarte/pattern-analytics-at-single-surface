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

public class DefectTypePropertiesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectTypePropertiesFactory.class);
	public static DefectTypeProperties loadDefectTypePropertiesByORMID(int idDefectProperty) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypePropertiesByORMID(session, idDefectProperty);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByORMID(int idDefectProperty)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties getDefectTypePropertiesByORMID(int idDefectProperty) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getDefectTypePropertiesByORMID(session, idDefectProperty);
		}
		catch (Exception e) {
			_logger.error("getDefectTypePropertiesByORMID(int idDefectProperty)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByORMID(int idDefectProperty, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypePropertiesByORMID(session, idDefectProperty, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByORMID(int idDefectProperty, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty) throws PersistentException {
		try {
			return (DefectTypeProperties) session.load(edu.mgupi.pass.db.defects.DefectTypeProperties.class, new Integer(idDefectProperty));
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties getDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty) throws PersistentException {
		try {
			return (DefectTypeProperties) session.get(edu.mgupi.pass.db.defects.DefectTypeProperties.class, new Integer(idDefectProperty));
		}
		catch (Exception e) {
			_logger.error("getDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DefectTypeProperties) session.load(edu.mgupi.pass.db.defects.DefectTypeProperties.class, new Integer(idDefectProperty), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByORMID(PersistentSession session, int idDefectProperty, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties[] listDefectTypePropertiesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypePropertiesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties[] listDefectTypePropertiesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectTypePropertiesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties[] listDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeProperties as DefectTypeProperties");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (DefectTypeProperties[]) list.toArray(new DefectTypeProperties[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties[] listDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeProperties as DefectTypeProperties");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (DefectTypeProperties[]) list.toArray(new DefectTypeProperties[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypePropertiesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectTypePropertiesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DefectTypeProperties[] defectTypePropertieses = listDefectTypePropertiesByQuery(session, condition, orderBy);
		if (defectTypePropertieses != null && defectTypePropertieses.length > 0)
			return defectTypePropertieses[0];
		else
			return null;
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DefectTypeProperties[] defectTypePropertieses = listDefectTypePropertiesByQuery(session, condition, orderBy, lockMode);
		if (defectTypePropertieses != null && defectTypePropertieses.length > 0)
			return defectTypePropertieses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDefectTypePropertiesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypePropertiesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypePropertiesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectTypePropertiesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypePropertiesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeProperties as DefectTypeProperties");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.DefectTypeProperties as DefectTypeProperties");
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
			_logger.error("iterateDefectTypePropertiesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static DefectTypeProperties createDefectTypeProperties() {
		return new edu.mgupi.pass.db.defects.DefectTypeProperties();
	}
	
	public static DefectTypeProperties loadDefectTypePropertiesByCriteria(DefectTypePropertiesCriteria defectTypePropertiesCriteria) {
		DefectTypeProperties[] defectTypePropertieses = listDefectTypePropertiesByCriteria(defectTypePropertiesCriteria);
		if(defectTypePropertieses == null || defectTypePropertieses.length == 0) {
			return null;
		}
		return defectTypePropertieses[0];
	}
	
	public static DefectTypeProperties[] listDefectTypePropertiesByCriteria(DefectTypePropertiesCriteria defectTypePropertiesCriteria) {
		return defectTypePropertiesCriteria.listDefectTypeProperties();
	}
}
