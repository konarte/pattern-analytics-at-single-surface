/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package ormsamples;

import org.orm.*;
public class RetrieveAndUpdatePassData {
	public void retrieveAndUpdateTestData() throws PersistentException {
		PersistentTransaction t = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().beginTransaction();
		try {
			edu.mgupi.pass.db.defects.DefectClasses ledumgupipassdbdefectsDefectClasses = edu.mgupi.pass.db.defects.DefectClassesFactory.loadDefectClassesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbdefectsDefectClasses.save();
			edu.mgupi.pass.db.defects.DefectTypes ledumgupipassdbdefectsDefectTypes = edu.mgupi.pass.db.defects.DefectTypesFactory.loadDefectTypesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbdefectsDefectTypes.save();
			edu.mgupi.pass.db.defects.Defects ledumgupipassdbdefectsDefects = edu.mgupi.pass.db.defects.DefectsFactory.loadDefectsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbdefectsDefects.save();
			edu.mgupi.pass.db.defects.DefectTypeOptions ledumgupipassdbdefectsDefectTypeOptions = edu.mgupi.pass.db.defects.DefectTypeOptionsFactory.loadDefectTypeOptionsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbdefectsDefectTypeOptions.save();
			edu.mgupi.pass.db.locuses.LModules ledumgupipassdblocusesLModules = edu.mgupi.pass.db.locuses.LModulesFactory.loadLModulesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLModules.save();
			edu.mgupi.pass.db.locuses.LFilters ledumgupipassdblocusesLFilters = edu.mgupi.pass.db.locuses.LFiltersFactory.loadLFiltersByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLFilters.save();
			edu.mgupi.pass.db.locuses.Locuses ledumgupipassdblocusesLocuses = edu.mgupi.pass.db.locuses.LocusesFactory.loadLocusesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocuses.save();
			edu.mgupi.pass.db.locuses.LocusSources ledumgupipassdblocusesLocusSources = edu.mgupi.pass.db.locuses.LocusSourcesFactory.loadLocusSourcesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusSources.save();
			edu.mgupi.pass.db.locuses.LocusAppliedFilters ledumgupipassdblocusesLocusAppliedFilters = edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory.loadLocusAppliedFiltersByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusAppliedFilters.save();
			edu.mgupi.pass.db.locuses.LocusModuleData ledumgupipassdblocusesLocusModuleData = edu.mgupi.pass.db.locuses.LocusModuleDataFactory.loadLocusModuleDataByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusModuleData.save();
			edu.mgupi.pass.db.locuses.LocusAppliedModule ledumgupipassdblocusesLocusAppliedModule = edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory.loadLocusAppliedModuleByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusAppliedModule.save();
			edu.mgupi.pass.db.locuses.LocusAppliedModuleParams ledumgupipassdblocusesLocusAppliedModuleParams = edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsFactory.loadLocusAppliedModuleParamsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusAppliedModuleParams.save();
			edu.mgupi.pass.db.locuses.LocusAppliedFilterParams ledumgupipassdblocusesLocusAppliedFilterParams = edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsFactory.loadLocusAppliedFilterParamsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdblocusesLocusAppliedFilterParams.save();
			edu.mgupi.pass.db.sensors.Sensors ledumgupipassdbsensorsSensors = edu.mgupi.pass.db.sensors.SensorsFactory.loadSensorsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsensorsSensors.save();
			edu.mgupi.pass.db.sensors.SensorClasses ledumgupipassdbsensorsSensorClasses = edu.mgupi.pass.db.sensors.SensorClassesFactory.loadSensorClassesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsensorsSensorClasses.save();
			edu.mgupi.pass.db.sensors.SensorTypes ledumgupipassdbsensorsSensorTypes = edu.mgupi.pass.db.sensors.SensorTypesFactory.loadSensorTypesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsensorsSensorTypes.save();
			edu.mgupi.pass.db.surfaces.SurfaceClasses ledumgupipassdbsurfacesSurfaceClasses = edu.mgupi.pass.db.surfaces.SurfaceClassesFactory.loadSurfaceClassesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsurfacesSurfaceClasses.save();
			edu.mgupi.pass.db.surfaces.Surfaces ledumgupipassdbsurfacesSurfaces = edu.mgupi.pass.db.surfaces.SurfacesFactory.loadSurfacesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsurfacesSurfaces.save();
			edu.mgupi.pass.db.surfaces.SurfaceTypes ledumgupipassdbsurfacesSurfaceTypes = edu.mgupi.pass.db.surfaces.SurfaceTypesFactory.loadSurfaceTypesByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsurfacesSurfaceTypes.save();
			edu.mgupi.pass.db.surfaces.Materials ledumgupipassdbsurfacesMaterials = edu.mgupi.pass.db.surfaces.MaterialsFactory.loadMaterialsByQuery(null, null);
			// Update the properties of the persistent object
			ledumgupipassdbsurfacesMaterials.save();
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public void retrieveLFiltersBycodename() {
		System.out.println("Retrieving LFilters by codename...");
		// Please uncomment the follow line and fill in parameter(s)
		//System.out.println(edu.mgupi.pass.db.locuses.LFiltersFactory.loadBycodename());
	}
	
	public void retrieveByCriteria() throws PersistentException {
		System.out.println("Retrieving DefectClasses by DefectClassesCriteria");
		edu.mgupi.pass.db.defects.DefectClassesCriteria defectClassesCriteria = new edu.mgupi.pass.db.defects.DefectClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//defectClassesCriteria.idDefectClass.eq();
		System.out.println(defectClassesCriteria.uniqueDefectClasses());
		
		System.out.println("Retrieving DefectTypes by DefectTypesCriteria");
		edu.mgupi.pass.db.defects.DefectTypesCriteria defectTypesCriteria = new edu.mgupi.pass.db.defects.DefectTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//defectTypesCriteria.idDefectType.eq();
		System.out.println(defectTypesCriteria.uniqueDefectTypes());
		
		System.out.println("Retrieving Defects by DefectsCriteria");
		edu.mgupi.pass.db.defects.DefectsCriteria defectsCriteria = new edu.mgupi.pass.db.defects.DefectsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//defectsCriteria.idDefect.eq();
		System.out.println(defectsCriteria.uniqueDefects());
		
		System.out.println("Retrieving DefectTypeOptions by DefectTypeOptionsCriteria");
		edu.mgupi.pass.db.defects.DefectTypeOptionsCriteria defectTypeOptionsCriteria = new edu.mgupi.pass.db.defects.DefectTypeOptionsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//defectTypeOptionsCriteria.ID.eq();
		System.out.println(defectTypeOptionsCriteria.uniqueDefectTypeOptions());
		
		System.out.println("Retrieving LModules by LModulesCriteria");
		edu.mgupi.pass.db.locuses.LModulesCriteria lModulesCriteria = new edu.mgupi.pass.db.locuses.LModulesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lModulesCriteria.idLModule.eq();
		System.out.println(lModulesCriteria.uniqueLModules());
		
		System.out.println("Retrieving LFilters by LFiltersCriteria");
		edu.mgupi.pass.db.locuses.LFiltersCriteria lFiltersCriteria = new edu.mgupi.pass.db.locuses.LFiltersCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//lFiltersCriteria.idLFilter.eq();
		System.out.println(lFiltersCriteria.uniqueLFilters());
		
		System.out.println("Retrieving Locuses by LocusesCriteria");
		edu.mgupi.pass.db.locuses.LocusesCriteria locusesCriteria = new edu.mgupi.pass.db.locuses.LocusesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusesCriteria.idLocus.eq();
		System.out.println(locusesCriteria.uniqueLocuses());
		
		System.out.println("Retrieving LocusSources by LocusSourcesCriteria");
		edu.mgupi.pass.db.locuses.LocusSourcesCriteria locusSourcesCriteria = new edu.mgupi.pass.db.locuses.LocusSourcesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusSourcesCriteria.idLocusSource.eq();
		System.out.println(locusSourcesCriteria.uniqueLocusSources());
		
		System.out.println("Retrieving LocusAppliedFilters by LocusAppliedFiltersCriteria");
		edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria locusAppliedFiltersCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusAppliedFiltersCriteria.idLocusFilter.eq();
		System.out.println(locusAppliedFiltersCriteria.uniqueLocusAppliedFilters());
		
		System.out.println("Retrieving LocusModuleData by LocusModuleDataCriteria");
		edu.mgupi.pass.db.locuses.LocusModuleDataCriteria locusModuleDataCriteria = new edu.mgupi.pass.db.locuses.LocusModuleDataCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusModuleDataCriteria.idModuleParam.eq();
		System.out.println(locusModuleDataCriteria.uniqueLocusModuleData());
		
		System.out.println("Retrieving LocusAppliedModule by LocusAppliedModuleCriteria");
		edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria locusAppliedModuleCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusAppliedModuleCriteria.idLocusModule.eq();
		System.out.println(locusAppliedModuleCriteria.uniqueLocusAppliedModule());
		
		System.out.println("Retrieving LocusAppliedModuleParams by LocusAppliedModuleParamsCriteria");
		edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsCriteria locusAppliedModuleParamsCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusAppliedModuleParamsCriteria.ID.eq();
		System.out.println(locusAppliedModuleParamsCriteria.uniqueLocusAppliedModuleParams());
		
		System.out.println("Retrieving LocusAppliedFilterParams by LocusAppliedFilterParamsCriteria");
		edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria locusAppliedFilterParamsCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//locusAppliedFilterParamsCriteria.ID.eq();
		System.out.println(locusAppliedFilterParamsCriteria.uniqueLocusAppliedFilterParams());
		
		System.out.println("Retrieving Sensors by SensorsCriteria");
		edu.mgupi.pass.db.sensors.SensorsCriteria sensorsCriteria = new edu.mgupi.pass.db.sensors.SensorsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//sensorsCriteria.idSensor.eq();
		System.out.println(sensorsCriteria.uniqueSensors());
		
		System.out.println("Retrieving SensorClasses by SensorClassesCriteria");
		edu.mgupi.pass.db.sensors.SensorClassesCriteria sensorClassesCriteria = new edu.mgupi.pass.db.sensors.SensorClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//sensorClassesCriteria.idSensorClass.eq();
		System.out.println(sensorClassesCriteria.uniqueSensorClasses());
		
		System.out.println("Retrieving SensorTypes by SensorTypesCriteria");
		edu.mgupi.pass.db.sensors.SensorTypesCriteria sensorTypesCriteria = new edu.mgupi.pass.db.sensors.SensorTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//sensorTypesCriteria.idSensorType.eq();
		System.out.println(sensorTypesCriteria.uniqueSensorTypes());
		
		System.out.println("Retrieving SurfaceClasses by SurfaceClassesCriteria");
		edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria surfaceClassesCriteria = new edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//surfaceClassesCriteria.idSurfaceClass.eq();
		System.out.println(surfaceClassesCriteria.uniqueSurfaceClasses());
		
		System.out.println("Retrieving Surfaces by SurfacesCriteria");
		edu.mgupi.pass.db.surfaces.SurfacesCriteria surfacesCriteria = new edu.mgupi.pass.db.surfaces.SurfacesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//surfacesCriteria.idSurface.eq();
		System.out.println(surfacesCriteria.uniqueSurfaces());
		
		System.out.println("Retrieving SurfaceTypes by SurfaceTypesCriteria");
		edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria surfaceTypesCriteria = new edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//surfaceTypesCriteria.idSurfaceType.eq();
		System.out.println(surfaceTypesCriteria.uniqueSurfaceTypes());
		
		System.out.println("Retrieving Materials by MaterialsCriteria");
		edu.mgupi.pass.db.surfaces.MaterialsCriteria materialsCriteria = new edu.mgupi.pass.db.surfaces.MaterialsCriteria();
		// Please uncomment the follow line and fill in parameter(s)
		//materialsCriteria.idSurfaceMaterial.eq();
		System.out.println(materialsCriteria.uniqueMaterials());
		
	}
	
	
	public static void main(String[] args) {
		try {
			RetrieveAndUpdatePassData retrieveAndUpdatePassData = new RetrieveAndUpdatePassData();
			try {
				retrieveAndUpdatePassData.retrieveAndUpdateTestData();
				//retrieveAndUpdatePassData.retrieveLFiltersBycodename();
				//retrieveAndUpdatePassData.retrieveByCriteria();
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
