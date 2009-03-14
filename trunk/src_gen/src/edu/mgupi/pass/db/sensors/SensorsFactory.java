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

public class SensorsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SensorsFactory.class);
	public static Sensors loadSensorsByORMID(int idSensor) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorsByORMID(session, idSensor);
		}
		catch (Exception e) {
			_logger.error("loadSensorsByORMID(int idSensor)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors getSensorsByORMID(int idSensor) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getSensorsByORMID(session, idSensor);
		}
		catch (Exception e) {
			_logger.error("getSensorsByORMID(int idSensor)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByORMID(int idSensor, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorsByORMID(session, idSensor, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorsByORMID(int idSensor, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByORMID(PersistentSession session, int idSensor) throws PersistentException {
		try {
			return (Sensors) session.load(edu.mgupi.pass.db.sensors.Sensors.class, new Integer(idSensor));
		}
		catch (Exception e) {
			_logger.error("loadSensorsByORMID(PersistentSession session, int idSensor)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors getSensorsByORMID(PersistentSession session, int idSensor) throws PersistentException {
		try {
			return (Sensors) session.get(edu.mgupi.pass.db.sensors.Sensors.class, new Integer(idSensor));
		}
		catch (Exception e) {
			_logger.error("getSensorsByORMID(PersistentSession session, int idSensor)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByORMID(PersistentSession session, int idSensor, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Sensors) session.load(edu.mgupi.pass.db.sensors.Sensors.class, new Integer(idSensor), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorsByORMID(PersistentSession session, int idSensor, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors[] listSensorsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors[] listSensorsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listSensorsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors[] listSensorsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.Sensors as Sensors");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Sensors[]) list.toArray(new Sensors[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors[] listSensorsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.Sensors as Sensors");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Sensors[]) list.toArray(new Sensors[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listSensorsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadSensorsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors loadSensorsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Sensors[] sensorses = listSensorsByQuery(session, condition, orderBy);
		if (sensorses != null && sensorses.length > 0)
			return sensorses[0];
		else
			return null;
	}
	
	public static Sensors loadSensorsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Sensors[] sensorses = listSensorsByQuery(session, condition, orderBy, lockMode);
		if (sensorses != null && sensorses.length > 0)
			return sensorses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateSensorsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateSensorsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateSensorsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.Sensors as Sensors");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateSensorsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateSensorsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.sensors.Sensors as Sensors");
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
			_logger.error("iterateSensorsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Sensors createSensors() {
		return new edu.mgupi.pass.db.sensors.Sensors();
	}
	
	public static Sensors loadSensorsByCriteria(SensorsCriteria sensorsCriteria) {
		Sensors[] sensorses = listSensorsByCriteria(sensorsCriteria);
		if(sensorses == null || sensorses.length == 0) {
			return null;
		}
		return sensorses[0];
	}
	
	public static Sensors[] listSensorsByCriteria(SensorsCriteria sensorsCriteria) {
		return sensorsCriteria.listSensors();
	}
}
