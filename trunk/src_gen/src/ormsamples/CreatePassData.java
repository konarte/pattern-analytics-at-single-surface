/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package ormsamples;

import org.orm.*;
public class CreatePassData {
	public void createTestData() throws PersistentException {
		PersistentTransaction t = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().beginTransaction();
		try {
			edu.mgupi.pass.db.surfaces.SurfaceClasses ledumgupipassdbsurfacesSurfaceClasses = edu.mgupi.pass.db.surfaces.SurfaceClassesFactory.createSurfaceClasses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : surfaceImage
			ledumgupipassdbsurfacesSurfaceClasses.save();
			edu.mgupi.pass.db.surfaces.Surfaces ledumgupipassdbsurfacesSurfaces = edu.mgupi.pass.db.surfaces.SurfacesFactory.createSurfaces();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : type, surfaceMode, surfaceType, multiLayer, length, width, height
			ledumgupipassdbsurfacesSurfaces.save();
			edu.mgupi.pass.db.surfaces.SurfaceTypes ledumgupipassdbsurfacesSurfaceTypes = edu.mgupi.pass.db.surfaces.SurfaceTypesFactory.createSurfaceTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : surfaceMaterial, surfaceClass
			ledumgupipassdbsurfacesSurfaceTypes.save();
			edu.mgupi.pass.db.surfaces.Materials ledumgupipassdbsurfacesMaterials = edu.mgupi.pass.db.surfaces.MaterialsFactory.createMaterials();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sensor, magneticConductivity, electricalConduction
			ledumgupipassdbsurfacesMaterials.save();
			edu.mgupi.pass.db.defects.DefectClasses ledumgupipassdbdefectsDefectClasses = edu.mgupi.pass.db.defects.DefectClassesFactory.createDefectClasses();
			// Initialize the properties of the persistent object here
			ledumgupipassdbdefectsDefectClasses.save();
			edu.mgupi.pass.db.defects.DefectTypes ledumgupipassdbdefectsDefectTypes = edu.mgupi.pass.db.defects.DefectTypesFactory.createDefectTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : defectClass, defectImage
			ledumgupipassdbdefectsDefectTypes.save();
			edu.mgupi.pass.db.defects.Defects ledumgupipassdbdefectsDefects = edu.mgupi.pass.db.defects.DefectsFactory.createDefects();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : defectType, length, width, depth, beddingDepth
			ledumgupipassdbdefectsDefects.save();
			edu.mgupi.pass.db.locuses.LModules ledumgupipassdblocusesLModules = edu.mgupi.pass.db.locuses.LModulesFactory.createLModules();
			// Initialize the properties of the persistent object here
			ledumgupipassdblocusesLModules.save();
			edu.mgupi.pass.db.locuses.LFilters ledumgupipassdblocusesLFilters = edu.mgupi.pass.db.locuses.LFiltersFactory.createLFilters();
			// Initialize the properties of the persistent object here
			ledumgupipassdblocusesLFilters.save();
			edu.mgupi.pass.db.locuses.Locuses ledumgupipassdblocusesLocuses = edu.mgupi.pass.db.locuses.LocusesFactory.createLocuses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sensor, locusSource, defect, surface, module, filteredImage, histogram, thumbImage
			ledumgupipassdblocusesLocuses.save();
			edu.mgupi.pass.db.locuses.LocusSources ledumgupipassdblocusesLocusSources = edu.mgupi.pass.db.locuses.LocusSourcesFactory.createLocusSources();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sourceImage
			ledumgupipassdblocusesLocusSources.save();
			edu.mgupi.pass.db.locuses.LocusFilters ledumgupipassdblocusesLocusFilters = edu.mgupi.pass.db.locuses.LocusFiltersFactory.createLocusFilters();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : filter, order
			ledumgupipassdblocusesLocusFilters.save();
			edu.mgupi.pass.db.locuses.LocusModuleParams ledumgupipassdblocusesLocusModuleParams = edu.mgupi.pass.db.locuses.LocusModuleParamsFactory.createLocusModuleParams();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : paramData
			ledumgupipassdblocusesLocusModuleParams.save();
			edu.mgupi.pass.db.sensors.Sensors ledumgupipassdbsensorsSensors = edu.mgupi.pass.db.sensors.SensorsFactory.createSensors();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : mpathMaterial, sensorType
			ledumgupipassdbsensorsSensors.save();
			edu.mgupi.pass.db.sensors.SensorClasses ledumgupipassdbsensorsSensorClasses = edu.mgupi.pass.db.sensors.SensorClassesFactory.createSensorClasses();
			// Initialize the properties of the persistent object here
			ledumgupipassdbsensorsSensorClasses.save();
			edu.mgupi.pass.db.sensors.SensorTypes ledumgupipassdbsensorsSensorTypes = edu.mgupi.pass.db.sensors.SensorTypesFactory.createSensorTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sensorClass, sensorImage
			ledumgupipassdbsensorsSensorTypes.save();
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			CreatePassData createPassData = new CreatePassData();
			try {
				createPassData.createTestData();
			}
			finally {
				edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().disposePersistentManager();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
