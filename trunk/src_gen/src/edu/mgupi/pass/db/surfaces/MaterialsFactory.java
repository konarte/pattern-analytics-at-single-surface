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
package edu.mgupi.pass.db.surfaces;

import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class MaterialsFactory {
	private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(MaterialsFactory.class);
	public static Materials loadMaterialsByORMID(int idSurfaceMaterial) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadMaterialsByORMID(session, idSurfaceMaterial);
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByORMID(int idSurfaceMaterial)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials getMaterialsByORMID(int idSurfaceMaterial) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return getMaterialsByORMID(session, idSurfaceMaterial);
		}
		catch (Exception e) {
			_logger.error("getMaterialsByORMID(int idSurfaceMaterial)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByORMID(int idSurfaceMaterial, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadMaterialsByORMID(session, idSurfaceMaterial, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByORMID(int idSurfaceMaterial, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByORMID(PersistentSession session, int idSurfaceMaterial) throws PersistentException {
		try {
			return (Materials) session.load(edu.mgupi.pass.db.surfaces.Materials.class, new Integer(idSurfaceMaterial));
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByORMID(PersistentSession session, int idSurfaceMaterial)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials getMaterialsByORMID(PersistentSession session, int idSurfaceMaterial) throws PersistentException {
		try {
			return (Materials) session.get(edu.mgupi.pass.db.surfaces.Materials.class, new Integer(idSurfaceMaterial));
		}
		catch (Exception e) {
			_logger.error("getMaterialsByORMID(PersistentSession session, int idSurfaceMaterial)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByORMID(PersistentSession session, int idSurfaceMaterial, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Materials) session.load(edu.mgupi.pass.db.surfaces.Materials.class, new Integer(idSurfaceMaterial), lockMode);
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByORMID(PersistentSession session, int idSurfaceMaterial, org.hibernate.LockMode lockMode)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials[] listMaterialsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listMaterialsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("listMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials[] listMaterialsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return listMaterialsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("listMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials[] listMaterialsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Materials as Materials");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			List list = query.list();
			return (Materials[]) list.toArray(new Materials[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listMaterialsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials[] listMaterialsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Materials as Materials");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("this", lockMode);
			List list = query.list();
			return (Materials[]) list.toArray(new Materials[list.size()]);
		}
		catch (Exception e) {
			_logger.error("listMaterialsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadMaterialsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return loadMaterialsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("loadMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials loadMaterialsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Materials[] materialses = listMaterialsByQuery(session, condition, orderBy);
		if (materialses != null && materialses.length > 0)
			return materialses[0];
		else
			return null;
	}
	
	public static Materials loadMaterialsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Materials[] materialses = listMaterialsByQuery(session, condition, orderBy, lockMode);
		if (materialses != null && materialses.length > 0)
			return materialses[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateMaterialsByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateMaterialsByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			_logger.error("iterateMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateMaterialsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession();
			return iterateMaterialsByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			_logger.error("iterateMaterialsByQuery(String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateMaterialsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Materials as Materials");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			_logger.error("iterateMaterialsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateMaterialsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From edu.mgupi.pass.db.surfaces.Materials as Materials");
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
			_logger.error("iterateMaterialsByQuery(PersistentSession session, String condition, String orderBy)", e);
			throw new PersistentException(e);
		}
	}
	
	public static Materials createMaterials() {
		return new edu.mgupi.pass.db.surfaces.Materials();
	}
	
	public static Materials loadMaterialsByCriteria(MaterialsCriteria materialsCriteria) {
		Materials[] materialses = listMaterialsByCriteria(materialsCriteria);
		if(materialses == null || materialses.length == 0) {
			return null;
		}
		return materialses[0];
	}
	
	public static Materials[] listMaterialsByCriteria(MaterialsCriteria materialsCriteria) {
		return materialsCriteria.listMaterials();
	}
}
