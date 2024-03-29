/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package ormsamples;

import org.orm.*;
public class CreatePassData {
	public void createTestData() throws PersistentException {
		PersistentTransaction t = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().beginTransaction();
		try {
			edu.mgupi.pass.db.defects.DefectClasses ledumgupipassdbdefectsDefectClasses = edu.mgupi.pass.db.defects.DefectClassesFactory.createDefectClasses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : name
			ledumgupipassdbdefectsDefectClasses.save();
			edu.mgupi.pass.db.defects.DefectTypes ledumgupipassdbdefectsDefectTypes = edu.mgupi.pass.db.defects.DefectTypesFactory.createDefectTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : defectClass, name
			ledumgupipassdbdefectsDefectTypes.save();
			edu.mgupi.pass.db.defects.Defects ledumgupipassdbdefectsDefects = edu.mgupi.pass.db.defects.DefectsFactory.createDefects();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : defectType, length, width, depth, beddingDepth
			ledumgupipassdbdefectsDefects.save();
			edu.mgupi.pass.db.defects.DefectTypeOptions ledumgupipassdbdefectsDefectTypeOptions = edu.mgupi.pass.db.defects.DefectTypeOptionsFactory.createDefectTypeOptions();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : value, name
			ledumgupipassdbdefectsDefectTypeOptions.save();
			edu.mgupi.pass.db.locuses.LModules ledumgupipassdblocusesLModules = edu.mgupi.pass.db.locuses.LModulesFactory.createLModules();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : codename, name
			ledumgupipassdblocusesLModules.save();
			edu.mgupi.pass.db.locuses.LFilters ledumgupipassdblocusesLFilters = edu.mgupi.pass.db.locuses.LFiltersFactory.createLFilters();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : serviceFilter, codename, name
			ledumgupipassdblocusesLFilters.save();
			edu.mgupi.pass.db.locuses.Locuses ledumgupipassdblocusesLocuses = edu.mgupi.pass.db.locuses.LocusesFactory.createLocuses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : module, sensor, locusSource, surface, filteredImage, thumbImage
			ledumgupipassdblocusesLocuses.save();
			edu.mgupi.pass.db.locuses.LocusSources ledumgupipassdblocusesLocusSources = edu.mgupi.pass.db.locuses.LocusSourcesFactory.createLocusSources();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sourceImage, filename
			ledumgupipassdblocusesLocusSources.save();
			edu.mgupi.pass.db.locuses.LocusAppliedFilters ledumgupipassdblocusesLocusAppliedFilters = edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory.createLocusAppliedFilters();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : filter
			ledumgupipassdblocusesLocusAppliedFilters.save();
			edu.mgupi.pass.db.locuses.LocusModuleData ledumgupipassdblocusesLocusModuleData = edu.mgupi.pass.db.locuses.LocusModuleDataFactory.createLocusModuleData();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : dataType, paramData, paramName
			ledumgupipassdblocusesLocusModuleData.save();
			edu.mgupi.pass.db.locuses.LocusAppliedModule ledumgupipassdblocusesLocusAppliedModule = edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory.createLocusAppliedModule();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : module
			ledumgupipassdblocusesLocusAppliedModule.save();
			edu.mgupi.pass.db.locuses.LocusAppliedModuleParams ledumgupipassdblocusesLocusAppliedModuleParams = edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsFactory.createLocusAppliedModuleParams();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : value, name
			ledumgupipassdblocusesLocusAppliedModuleParams.save();
			edu.mgupi.pass.db.locuses.LocusAppliedFilterParams ledumgupipassdblocusesLocusAppliedFilterParams = edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsFactory.createLocusAppliedFilterParams();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : value, name
			ledumgupipassdblocusesLocusAppliedFilterParams.save();
			edu.mgupi.pass.db.sensors.Sensors ledumgupipassdbsensorsSensors = edu.mgupi.pass.db.sensors.SensorsFactory.createSensors();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sensorType, name
			ledumgupipassdbsensorsSensors.save();
			edu.mgupi.pass.db.sensors.SensorClasses ledumgupipassdbsensorsSensorClasses = edu.mgupi.pass.db.sensors.SensorClassesFactory.createSensorClasses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : name
			ledumgupipassdbsensorsSensorClasses.save();
			edu.mgupi.pass.db.sensors.SensorTypes ledumgupipassdbsensorsSensorTypes = edu.mgupi.pass.db.sensors.SensorTypesFactory.createSensorTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : sensorClass, name
			ledumgupipassdbsensorsSensorTypes.save();
			edu.mgupi.pass.db.surfaces.SurfaceClasses ledumgupipassdbsurfacesSurfaceClasses = edu.mgupi.pass.db.surfaces.SurfaceClassesFactory.createSurfaceClasses();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : name
			ledumgupipassdbsurfacesSurfaceClasses.save();
			edu.mgupi.pass.db.surfaces.Surfaces ledumgupipassdbsurfacesSurfaces = edu.mgupi.pass.db.surfaces.SurfacesFactory.createSurfaces();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : surfaceType, multiLayer, length, width, height
			ledumgupipassdbsurfacesSurfaces.save();
			edu.mgupi.pass.db.surfaces.SurfaceTypes ledumgupipassdbsurfacesSurfaceTypes = edu.mgupi.pass.db.surfaces.SurfaceTypesFactory.createSurfaceTypes();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : surfaceClass, name
			ledumgupipassdbsurfacesSurfaceTypes.save();
			edu.mgupi.pass.db.surfaces.Materials ledumgupipassdbsurfacesMaterials = edu.mgupi.pass.db.surfaces.MaterialsFactory.createMaterials();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : magneticConductivity, electricalConduction, name
			ledumgupipassdbsurfacesMaterials.save();
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
