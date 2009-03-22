/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package ormsamples;

import org.orm.*;
public class DeletePassData {
	public void deleteTestData() throws PersistentException {
		PersistentTransaction t = edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().getSession().beginTransaction();
		try {
			edu.mgupi.pass.db.defects.DefectClasses ledumgupipassdbdefectsDefectClasses = edu.mgupi.pass.db.defects.DefectClassesFactory.loadDefectClassesByQuery(null, null);
			ledumgupipassdbdefectsDefectClasses.delete();
			edu.mgupi.pass.db.defects.DefectTypes ledumgupipassdbdefectsDefectTypes = edu.mgupi.pass.db.defects.DefectTypesFactory.loadDefectTypesByQuery(null, null);
			ledumgupipassdbdefectsDefectTypes.delete();
			edu.mgupi.pass.db.defects.Defects ledumgupipassdbdefectsDefects = edu.mgupi.pass.db.defects.DefectsFactory.loadDefectsByQuery(null, null);
			ledumgupipassdbdefectsDefects.delete();
			edu.mgupi.pass.db.defects.DefectTypeOptions ledumgupipassdbdefectsDefectTypeOptions = edu.mgupi.pass.db.defects.DefectTypeOptionsFactory.loadDefectTypeOptionsByQuery(null, null);
			ledumgupipassdbdefectsDefectTypeOptions.delete();
			edu.mgupi.pass.db.locuses.LModules ledumgupipassdblocusesLModules = edu.mgupi.pass.db.locuses.LModulesFactory.loadLModulesByQuery(null, null);
			ledumgupipassdblocusesLModules.delete();
			edu.mgupi.pass.db.locuses.LFilters ledumgupipassdblocusesLFilters = edu.mgupi.pass.db.locuses.LFiltersFactory.loadLFiltersByQuery(null, null);
			ledumgupipassdblocusesLFilters.delete();
			edu.mgupi.pass.db.locuses.Locuses ledumgupipassdblocusesLocuses = edu.mgupi.pass.db.locuses.LocusesFactory.loadLocusesByQuery(null, null);
			ledumgupipassdblocusesLocuses.delete();
			edu.mgupi.pass.db.locuses.LocusSources ledumgupipassdblocusesLocusSources = edu.mgupi.pass.db.locuses.LocusSourcesFactory.loadLocusSourcesByQuery(null, null);
			ledumgupipassdblocusesLocusSources.delete();
			edu.mgupi.pass.db.locuses.LocusAppliedFilters ledumgupipassdblocusesLocusAppliedFilters = edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory.loadLocusAppliedFiltersByQuery(null, null);
			ledumgupipassdblocusesLocusAppliedFilters.delete();
			edu.mgupi.pass.db.locuses.LocusModuleData ledumgupipassdblocusesLocusModuleData = edu.mgupi.pass.db.locuses.LocusModuleDataFactory.loadLocusModuleDataByQuery(null, null);
			ledumgupipassdblocusesLocusModuleData.delete();
			edu.mgupi.pass.db.locuses.LocusAppliedModule ledumgupipassdblocusesLocusAppliedModule = edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory.loadLocusAppliedModuleByQuery(null, null);
			ledumgupipassdblocusesLocusAppliedModule.delete();
			edu.mgupi.pass.db.locuses.LocusAppliedModuleParams ledumgupipassdblocusesLocusAppliedModuleParams = edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsFactory.loadLocusAppliedModuleParamsByQuery(null, null);
			ledumgupipassdblocusesLocusAppliedModuleParams.delete();
			edu.mgupi.pass.db.locuses.LocusAppliedFilterParams ledumgupipassdblocusesLocusAppliedFilterParams = edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsFactory.loadLocusAppliedFilterParamsByQuery(null, null);
			ledumgupipassdblocusesLocusAppliedFilterParams.delete();
			edu.mgupi.pass.db.sensors.Sensors ledumgupipassdbsensorsSensors = edu.mgupi.pass.db.sensors.SensorsFactory.loadSensorsByQuery(null, null);
			ledumgupipassdbsensorsSensors.delete();
			edu.mgupi.pass.db.sensors.SensorClasses ledumgupipassdbsensorsSensorClasses = edu.mgupi.pass.db.sensors.SensorClassesFactory.loadSensorClassesByQuery(null, null);
			ledumgupipassdbsensorsSensorClasses.delete();
			edu.mgupi.pass.db.sensors.SensorTypes ledumgupipassdbsensorsSensorTypes = edu.mgupi.pass.db.sensors.SensorTypesFactory.loadSensorTypesByQuery(null, null);
			ledumgupipassdbsensorsSensorTypes.delete();
			edu.mgupi.pass.db.surfaces.SurfaceClasses ledumgupipassdbsurfacesSurfaceClasses = edu.mgupi.pass.db.surfaces.SurfaceClassesFactory.loadSurfaceClassesByQuery(null, null);
			ledumgupipassdbsurfacesSurfaceClasses.delete();
			edu.mgupi.pass.db.surfaces.Surfaces ledumgupipassdbsurfacesSurfaces = edu.mgupi.pass.db.surfaces.SurfacesFactory.loadSurfacesByQuery(null, null);
			ledumgupipassdbsurfacesSurfaces.delete();
			edu.mgupi.pass.db.surfaces.SurfaceTypes ledumgupipassdbsurfacesSurfaceTypes = edu.mgupi.pass.db.surfaces.SurfaceTypesFactory.loadSurfaceTypesByQuery(null, null);
			ledumgupipassdbsurfacesSurfaceTypes.delete();
			edu.mgupi.pass.db.surfaces.Materials ledumgupipassdbsurfacesMaterials = edu.mgupi.pass.db.surfaces.MaterialsFactory.loadMaterialsByQuery(null, null);
			ledumgupipassdbsurfacesMaterials.delete();
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			DeletePassData deletePassData = new DeletePassData();
			try {
				deletePassData.deleteTestData();
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
