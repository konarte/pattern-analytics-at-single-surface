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
package edu.mgupi.pass.db.sensors;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class SensorClassesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SensorClassesFactory.class);
	public static SensorClasses loadSensorClassesByORMID(int idSensorClass) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorClassesByORMID(session, idSensorClass);
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByORMID(int idSensorClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses getSensorClassesByORMID(int idSensorClass) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSensorClassesByORMID(session, idSensorClass);
		}
		catch (Exception e) {
			_logger.error("getSensorClassesByORMID(int idSensorClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByORMID(int idSensorClass, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorClassesByORMID(session, idSensorClass, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByORMID(int idSensorClass, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByORMID(PersistentSession session, int idSensorClass) throws PersistentException {
		try {
			return (SensorClasses) session.load(edu.mgupi.pass.db.sensors.SensorClasses.class, new Integer(idSensorClass));
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByORMID(PersistentSession session, int idSensorClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses getSensorClassesByORMID(PersistentSession session, int idSensorClass) throws PersistentException {
		try {
			return (SensorClasses) session.get(edu.mgupi.pass.db.sensors.SensorClasses.class, new Integer(idSensorClass));
		}
		catch (Exception e) {
			_logger.error("getSensorClassesByORMID(PersistentSession session, int idSensorClass)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByORMID(PersistentSession session, int idSensorClass, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SensorClasses) session.load(edu.mgupi.pass.db.sensors.SensorClasses.class, new Integer(idSensorClass), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByORMID(PersistentSession session, int idSensorClass, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses[] listSensorClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses[] listSensorClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses[] listSensorClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorClasses as SensorClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (SensorClasses[]) list.toArray(new SensorClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses[] listSensorClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorClasses as SensorClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (SensorClasses[]) list.toArray(new SensorClasses[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses loadSensorClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		SensorClasses[] sensorClasseses = listSensorClassesByQuery(session, condition, orderBy);
		if (sensorClasseses != null && sensorClasseses.length > 0)
			return sensorClasseses[0];
		else
			return null;
	}
	
	public static SensorClasses loadSensorClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		SensorClasses[] sensorClasseses = listSensorClassesByQuery(session, condition, orderBy, lockMode);
		if (sensorClasseses != null && sensorClasseses.length > 0)
			return sensorClasseses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSensorClassesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorClassesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorClassesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorClassesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSensorClassesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorClassesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorClasses as SensorClasses");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSensorClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorClassesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorClasses as SensorClasses");
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
			_logger.error("iterateSensorClassesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorClasses createSensorClasses() {
		return new edu.mgupi.pass.db.sensors.SensorClasses();
	}
	
	public static SensorClasses loadSensorClassesByCriteria(SensorClassesCriteria sensorClassesCriteria) {
		SensorClasses[] sensorClasseses = listSensorClassesByCriteria(sensorClassesCriteria);
		if(sensorClasseses == null || sensorClasseses.length == 0) {
			return null;
		}
		return sensorClasseses[0];
	}
	
	public static SensorClasses[] listSensorClassesByCriteria(SensorClassesCriteria sensorClassesCriteria) {
		return sensorClassesCriteria.listSensorClasses();
	}
}
