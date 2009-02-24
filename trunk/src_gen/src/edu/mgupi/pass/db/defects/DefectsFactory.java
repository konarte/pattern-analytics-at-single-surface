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

public class DefectsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(DefectsFactory.class);
	public static Defects loadDefectsByORMID(int idDefect) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectsByORMID(session, idDefect);
		}
		catch (Exception e) {
			_logger.error("loadDefectsByORMID(int idDefect)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects getDefectsByORMID(int idDefect) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getDefectsByORMID(session, idDefect);
		}
		catch (Exception e) {
			_logger.error("getDefectsByORMID(int idDefect)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByORMID(int idDefect, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectsByORMID(session, idDefect, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectsByORMID(int idDefect, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByORMID(PersistentSession session, int idDefect) throws PersistentException {
		try {
			return (Defects) session.load(edu.mgupi.pass.db.defects.Defects.class, new Integer(idDefect));
		}
		catch (Exception e) {
			_logger.error("loadDefectsByORMID(PersistentSession session, int idDefect)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects getDefectsByORMID(PersistentSession session, int idDefect) throws PersistentException {
		try {
			return (Defects) session.get(edu.mgupi.pass.db.defects.Defects.class, new Integer(idDefect));
		}
		catch (Exception e) {
			_logger.error("getDefectsByORMID(PersistentSession session, int idDefect)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByORMID(PersistentSession session, int idDefect, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Defects) session.load(edu.mgupi.pass.db.defects.Defects.class, new Integer(idDefect), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectsByORMID(PersistentSession session, int idDefect, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects[] listDefectsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects[] listDefectsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listDefectsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects[] listDefectsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.Defects as Defects");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Defects[]) list.toArray(new Defects[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects[] listDefectsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.Defects as Defects");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Defects[]) list.toArray(new Defects[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listDefectsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadDefectsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects loadDefectsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Defects[] defectses = listDefectsByQuery(session, condition, orderBy);
		if (defectses != null && defectses.length > 0)
			return defectses[0];
		else
			return null;
	}
	
	public static Defects loadDefectsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Defects[] defectses = listDefectsByQuery(session, condition, orderBy, lockMode);
		if (defectses != null && defectses.length > 0)
			return defectses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDefectsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateDefectsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateDefectsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.Defects as Defects");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateDefectsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDefectsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.defects.Defects as Defects");
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
			_logger.error("iterateDefectsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Defects createDefects() {
		return new edu.mgupi.pass.db.defects.Defects();
	}
	
	public static Defects loadDefectsByCriteria(DefectsCriteria defectsCriteria) {
		Defects[] defectses = listDefectsByCriteria(defectsCriteria);
		if(defectses == null || defectses.length == 0) {
			return null;
		}
		return defectses[0];
	}
	
	public static Defects[] listDefectsByCriteria(DefectsCriteria defectsCriteria) {
		return defectsCriteria.listDefects();
	}
}
