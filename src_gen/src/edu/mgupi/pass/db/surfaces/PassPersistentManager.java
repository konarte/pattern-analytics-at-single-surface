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
import org.orm.cfg.JDBCConnectionSetting;
import org.hibernate.*;
import java.util.Properties;
import org.hibernate.cfg.*;

public class PassPersistentManager extends PersistentManager {
	private static final String PROJECT_NAME = "Pass";
	private static PersistentManager _instance = null;
	private static SessionType _sessionType = SessionType.THREAD_BASE;
	private static int _timeToAlive = 60000;
	private static JDBCConnectionSetting _connectionSetting = null;
	private static Properties _extraProperties = null;
	
	private PassPersistentManager() throws PersistentException {
		super(_connectionSetting, _sessionType, _timeToAlive, new String[] {}, _extraProperties);
		setFlushMode(FlushMode.AUTO);
	}
	
	@Override
	public Configuration createConfiguration() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.addAnnotatedClass(edu.mgupi.pass.db.defects.DefectClasses.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.defects.DefectTypes.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.defects.Defects.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.defects.DefectTypeOptions.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LModules.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LFilters.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.Locuses.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusSources.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusAppliedFilters.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusModuleData.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusAppliedModule.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusAppliedModuleParams.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.locuses.LocusAppliedFilterParams.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.sensors.Sensors.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.sensors.SensorClasses.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.sensors.SensorTypes.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.surfaces.SurfaceClasses.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.surfaces.Surfaces.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.surfaces.SurfaceTypes.class);
		configuration.addAnnotatedClass(edu.mgupi.pass.db.surfaces.Materials.class);
		configuration.buildMappings();
		return configuration;
	}
	
	public String getProjectName() {
		return PROJECT_NAME;
	}
	
	public static synchronized final PersistentManager instance() throws PersistentException {
		if (_instance == null) {
			_instance = new PassPersistentManager();
		}
		
		return _instance;
	}
	
	public void disposePersistentManager() throws PersistentException {
		_instance = null;
		super.disposePersistentManager();
	}
	
	public static void setSessionType(SessionType sessionType) throws PersistentException {
		if (_instance != null) {
			throw new PersistentException("Cannot set session type after create PersistentManager instance");
		}
		else {
			_sessionType = sessionType;
		}
		
	}
	
	public static void setAppBaseSessionTimeToAlive(int timeInMs) throws PersistentException {
		if (_instance != null) {
			throw new PersistentException("Cannot set session time to alive after create PersistentManager instance");
		}
		else {
			_timeToAlive = timeInMs;
		}
		
	}
	
	public static void setJDBCConnectionSetting(JDBCConnectionSetting aConnectionSetting) throws PersistentException {
		if (_instance != null) {
			throw new PersistentException("Cannot set connection setting after create PersistentManager instance");
		}
		else {
			_connectionSetting = aConnectionSetting;
		}
		
	}
	
	public static void setHibernateProperties(Properties aProperties) throws PersistentException {
		if (_instance != null) {
			throw new PersistentException("Cannot set hibernate properties after create PersistentManager instance");
		}
		else {
			_extraProperties = aProperties;
		}
		
	}
	
	public static void saveJDBCConnectionSetting() {
		PersistentManager.saveJDBCConnectionSetting(PROJECT_NAME, _connectionSetting);
	}
}
