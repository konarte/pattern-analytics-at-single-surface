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

public class SensorTypesFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SensorTypesFactory.class);
	public static SensorTypes loadSensorTypesByORMID(int idSensorType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorTypesByORMID(session, idSensorType);
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByORMID(int idSensorType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes getSensorTypesByORMID(int idSensorType) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSensorTypesByORMID(session, idSensorType);
		}
		catch (Exception e) {
			_logger.error("getSensorTypesByORMID(int idSensorType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByORMID(int idSensorType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorTypesByORMID(session, idSensorType, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByORMID(int idSensorType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByORMID(PersistentSession session, int idSensorType) throws PersistentException {
		try {
			return (SensorTypes) session.load(edu.mgupi.pass.db.sensors.SensorTypes.class, new Integer(idSensorType));
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByORMID(PersistentSession session, int idSensorType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes getSensorTypesByORMID(PersistentSession session, int idSensorType) throws PersistentException {
		try {
			return (SensorTypes) session.get(edu.mgupi.pass.db.sensors.SensorTypes.class, new Integer(idSensorType));
		}
		catch (Exception e) {
			_logger.error("getSensorTypesByORMID(PersistentSession session, int idSensorType)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByORMID(PersistentSession session, int idSensorType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (SensorTypes) session.load(edu.mgupi.pass.db.sensors.SensorTypes.class, new Integer(idSensorType), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByORMID(PersistentSession session, int idSensorType, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes[] listSensorTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes[] listSensorTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes[] listSensorTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorTypes as SensorTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (SensorTypes[]) list.toArray(new SensorTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes[] listSensorTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorTypes as SensorTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (SensorTypes[]) list.toArray(new SensorTypes[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes loadSensorTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		SensorTypes[] sensorTypeses = listSensorTypesByQuery(session, condition, orderBy);
		if (sensorTypeses != null && sensorTypeses.length > 0)
			return sensorTypeses[0];
		else
			return null;
	}
	
	public static SensorTypes loadSensorTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		SensorTypes[] sensorTypeses = listSensorTypesByQuery(session, condition, orderBy, lockMode);
		if (sensorTypeses != null && sensorTypeses.length > 0)
			return sensorTypeses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSensorTypesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorTypesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorTypesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorTypesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSensorTypesByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorTypesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorTypes as SensorTypes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSensorTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorTypesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.SensorTypes as SensorTypes");
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
			_logger.error("iterateSensorTypesByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static SensorTypes createSensorTypes() {
		return new edu.mgupi.pass.db.sensors.SensorTypes();
	}
	
	public static SensorTypes loadSensorTypesByCriteria(SensorTypesCriteria sensorTypesCriteria) {
		SensorTypes[] sensorTypeses = listSensorTypesByCriteria(sensorTypesCriteria);
		if(sensorTypeses == null || sensorTypeses.length == 0) {
			return null;
		}
		return sensorTypeses[0];
	}
	
	public static SensorTypes[] listSensorTypesByCriteria(SensorTypesCriteria sensorTypesCriteria) {
		return sensorTypesCriteria.listSensorTypes();
	}
}
