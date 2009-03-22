/**
 * Licensee: Anonymous
 * License Type: Purchased
 */
package ormsamples;

import org.orm.*;
public class ListPassData {
	private static final int ROW_COUNT = 100;
	
	public void listTestData() throws PersistentException {
		System.out.println("Listing DefectClasses...");
		edu.mgupi.pass.db.defects.DefectClasses[] edumgupipassdbdefectsDefectClasseses = edu.mgupi.pass.db.defects.DefectClassesFactory.listDefectClassesByQuery(null, null);
		int length = Math.min(edumgupipassdbdefectsDefectClasseses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbdefectsDefectClasseses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing DefectTypes...");
		edu.mgupi.pass.db.defects.DefectTypes[] edumgupipassdbdefectsDefectTypeses = edu.mgupi.pass.db.defects.DefectTypesFactory.listDefectTypesByQuery(null, null);
		length = Math.min(edumgupipassdbdefectsDefectTypeses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbdefectsDefectTypeses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Defects...");
		edu.mgupi.pass.db.defects.Defects[] edumgupipassdbdefectsDefectses = edu.mgupi.pass.db.defects.DefectsFactory.listDefectsByQuery(null, null);
		length = Math.min(edumgupipassdbdefectsDefectses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbdefectsDefectses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing DefectTypeOptions...");
		edu.mgupi.pass.db.defects.DefectTypeOptions[] edumgupipassdbdefectsDefectTypeOptionses = edu.mgupi.pass.db.defects.DefectTypeOptionsFactory.listDefectTypeOptionsByQuery(null, null);
		length = Math.min(edumgupipassdbdefectsDefectTypeOptionses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbdefectsDefectTypeOptionses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LModules...");
		edu.mgupi.pass.db.locuses.LModules[] edumgupipassdblocusesLModuleses = edu.mgupi.pass.db.locuses.LModulesFactory.listLModulesByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLModuleses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLModuleses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LFilters...");
		edu.mgupi.pass.db.locuses.LFilters[] edumgupipassdblocusesLFilterses = edu.mgupi.pass.db.locuses.LFiltersFactory.listLFiltersByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLFilterses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLFilterses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Locuses...");
		edu.mgupi.pass.db.locuses.Locuses[] edumgupipassdblocusesLocuseses = edu.mgupi.pass.db.locuses.LocusesFactory.listLocusesByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocuseses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocuseses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusSources...");
		edu.mgupi.pass.db.locuses.LocusSources[] edumgupipassdblocusesLocusSourceses = edu.mgupi.pass.db.locuses.LocusSourcesFactory.listLocusSourcesByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusSourceses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusSourceses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusAppliedFilters...");
		edu.mgupi.pass.db.locuses.LocusAppliedFilters[] edumgupipassdblocusesLocusAppliedFilterses = edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory.listLocusAppliedFiltersByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusAppliedFilterses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusAppliedFilterses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusModuleData...");
		edu.mgupi.pass.db.locuses.LocusModuleData[] edumgupipassdblocusesLocusModuleDatas = edu.mgupi.pass.db.locuses.LocusModuleDataFactory.listLocusModuleDataByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusModuleDatas.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusModuleDatas[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusAppliedModule...");
		edu.mgupi.pass.db.locuses.LocusAppliedModule[] edumgupipassdblocusesLocusAppliedModules = edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory.listLocusAppliedModuleByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusAppliedModules.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusAppliedModules[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusAppliedModuleParams...");
		edu.mgupi.pass.db.locuses.LocusAppliedModuleParams[] edumgupipassdblocusesLocusAppliedModuleParamses = edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsFactory.listLocusAppliedModuleParamsByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusAppliedModuleParamses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusAppliedModuleParamses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing LocusAppliedFilterParams...");
		edu.mgupi.pass.db.locuses.LocusAppliedFilterParams[] edumgupipassdblocusesLocusAppliedFilterParamses = edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsFactory.listLocusAppliedFilterParamsByQuery(null, null);
		length = Math.min(edumgupipassdblocusesLocusAppliedFilterParamses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdblocusesLocusAppliedFilterParamses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Sensors...");
		edu.mgupi.pass.db.sensors.Sensors[] edumgupipassdbsensorsSensorses = edu.mgupi.pass.db.sensors.SensorsFactory.listSensorsByQuery(null, null);
		length = Math.min(edumgupipassdbsensorsSensorses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsensorsSensorses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing SensorClasses...");
		edu.mgupi.pass.db.sensors.SensorClasses[] edumgupipassdbsensorsSensorClasseses = edu.mgupi.pass.db.sensors.SensorClassesFactory.listSensorClassesByQuery(null, null);
		length = Math.min(edumgupipassdbsensorsSensorClasseses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsensorsSensorClasseses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing SensorTypes...");
		edu.mgupi.pass.db.sensors.SensorTypes[] edumgupipassdbsensorsSensorTypeses = edu.mgupi.pass.db.sensors.SensorTypesFactory.listSensorTypesByQuery(null, null);
		length = Math.min(edumgupipassdbsensorsSensorTypeses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsensorsSensorTypeses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing SurfaceClasses...");
		edu.mgupi.pass.db.surfaces.SurfaceClasses[] edumgupipassdbsurfacesSurfaceClasseses = edu.mgupi.pass.db.surfaces.SurfaceClassesFactory.listSurfaceClassesByQuery(null, null);
		length = Math.min(edumgupipassdbsurfacesSurfaceClasseses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsurfacesSurfaceClasseses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Surfaces...");
		edu.mgupi.pass.db.surfaces.Surfaces[] edumgupipassdbsurfacesSurfaceses = edu.mgupi.pass.db.surfaces.SurfacesFactory.listSurfacesByQuery(null, null);
		length = Math.min(edumgupipassdbsurfacesSurfaceses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsurfacesSurfaceses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing SurfaceTypes...");
		edu.mgupi.pass.db.surfaces.SurfaceTypes[] edumgupipassdbsurfacesSurfaceTypeses = edu.mgupi.pass.db.surfaces.SurfaceTypesFactory.listSurfaceTypesByQuery(null, null);
		length = Math.min(edumgupipassdbsurfacesSurfaceTypeses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsurfacesSurfaceTypeses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Materials...");
		edu.mgupi.pass.db.surfaces.Materials[] edumgupipassdbsurfacesMaterialses = edu.mgupi.pass.db.surfaces.MaterialsFactory.listMaterialsByQuery(null, null);
		length = Math.min(edumgupipassdbsurfacesMaterialses.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(edumgupipassdbsurfacesMaterialses[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
	}
	
	public void listLFiltersBycodename() {
		System.out.println("Listing LFilters by codename...");
		// Please uncomment the follow lines and fill in parameters 
		//edu.mgupi.pass.db.locuses.LFilters[] lFilterss = edu.mgupi.pass.db.locuses.LFiltersFactory.listBycodename();
		//int length = lFilterss == null ? 0 : Math.min(lFilterss.length, ROW_COUNT);
		//for (int i = 0; i < length; i++) {
			//System.out.println(lFilterss[i]);
		//}
		//System.out.println(length + " record(s) retrieved.");
	}
	
	public void listByCriteria() throws PersistentException  {
		System.out.println("Listing DefectClasses by Criteria...");
		edu.mgupi.pass.db.defects.DefectClassesCriteria defectClassesCriteria = new edu.mgupi.pass.db.defects.DefectClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//defectClassesCriteria.idDefectClass.eq();
		defectClassesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.defects.DefectClasses[] edumgupipassdbdefectsDefectClasseses = defectClassesCriteria.listDefectClasses();
		int length =edumgupipassdbdefectsDefectClasseses== null ? 0 : Math.min(edumgupipassdbdefectsDefectClasseses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbdefectsDefectClasseses[i]);
		}
		System.out.println(length + " DefectClasses record(s) retrieved."); 
		
		System.out.println("Listing DefectTypes by Criteria...");
		edu.mgupi.pass.db.defects.DefectTypesCriteria defectTypesCriteria = new edu.mgupi.pass.db.defects.DefectTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//defectTypesCriteria.idDefectType.eq();
		defectTypesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.defects.DefectTypes[] edumgupipassdbdefectsDefectTypeses = defectTypesCriteria.listDefectTypes();
		length =edumgupipassdbdefectsDefectTypeses== null ? 0 : Math.min(edumgupipassdbdefectsDefectTypeses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbdefectsDefectTypeses[i]);
		}
		System.out.println(length + " DefectTypes record(s) retrieved."); 
		
		System.out.println("Listing Defects by Criteria...");
		edu.mgupi.pass.db.defects.DefectsCriteria defectsCriteria = new edu.mgupi.pass.db.defects.DefectsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//defectsCriteria.idDefect.eq();
		defectsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.defects.Defects[] edumgupipassdbdefectsDefectses = defectsCriteria.listDefects();
		length =edumgupipassdbdefectsDefectses== null ? 0 : Math.min(edumgupipassdbdefectsDefectses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbdefectsDefectses[i]);
		}
		System.out.println(length + " Defects record(s) retrieved."); 
		
		System.out.println("Listing DefectTypeOptions by Criteria...");
		edu.mgupi.pass.db.defects.DefectTypeOptionsCriteria defectTypeOptionsCriteria = new edu.mgupi.pass.db.defects.DefectTypeOptionsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//defectTypeOptionsCriteria.ID.eq();
		defectTypeOptionsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.defects.DefectTypeOptions[] edumgupipassdbdefectsDefectTypeOptionses = defectTypeOptionsCriteria.listDefectTypeOptions();
		length =edumgupipassdbdefectsDefectTypeOptionses== null ? 0 : Math.min(edumgupipassdbdefectsDefectTypeOptionses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbdefectsDefectTypeOptionses[i]);
		}
		System.out.println(length + " DefectTypeOptions record(s) retrieved."); 
		
		System.out.println("Listing LModules by Criteria...");
		edu.mgupi.pass.db.locuses.LModulesCriteria lModulesCriteria = new edu.mgupi.pass.db.locuses.LModulesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lModulesCriteria.idLModule.eq();
		lModulesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LModules[] edumgupipassdblocusesLModuleses = lModulesCriteria.listLModules();
		length =edumgupipassdblocusesLModuleses== null ? 0 : Math.min(edumgupipassdblocusesLModuleses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLModuleses[i]);
		}
		System.out.println(length + " LModules record(s) retrieved."); 
		
		System.out.println("Listing LFilters by Criteria...");
		edu.mgupi.pass.db.locuses.LFiltersCriteria lFiltersCriteria = new edu.mgupi.pass.db.locuses.LFiltersCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lFiltersCriteria.idLFilter.eq();
		lFiltersCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LFilters[] edumgupipassdblocusesLFilterses = lFiltersCriteria.listLFilters();
		length =edumgupipassdblocusesLFilterses== null ? 0 : Math.min(edumgupipassdblocusesLFilterses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLFilterses[i]);
		}
		System.out.println(length + " LFilters record(s) retrieved."); 
		
		System.out.println("Listing Locuses by Criteria...");
		edu.mgupi.pass.db.locuses.LocusesCriteria locusesCriteria = new edu.mgupi.pass.db.locuses.LocusesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusesCriteria.idLocus.eq();
		locusesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.Locuses[] edumgupipassdblocusesLocuseses = locusesCriteria.listLocuses();
		length =edumgupipassdblocusesLocuseses== null ? 0 : Math.min(edumgupipassdblocusesLocuseses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocuseses[i]);
		}
		System.out.println(length + " Locuses record(s) retrieved."); 
		
		System.out.println("Listing LocusSources by Criteria...");
		edu.mgupi.pass.db.locuses.LocusSourcesCriteria locusSourcesCriteria = new edu.mgupi.pass.db.locuses.LocusSourcesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusSourcesCriteria.idLocusSource.eq();
		locusSourcesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusSources[] edumgupipassdblocusesLocusSourceses = locusSourcesCriteria.listLocusSources();
		length =edumgupipassdblocusesLocusSourceses== null ? 0 : Math.min(edumgupipassdblocusesLocusSourceses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusSourceses[i]);
		}
		System.out.println(length + " LocusSources record(s) retrieved."); 
		
		System.out.println("Listing LocusAppliedFilters by Criteria...");
		edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria locusAppliedFiltersCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedFiltersCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusAppliedFiltersCriteria.idLocusFilter.eq();
		locusAppliedFiltersCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusAppliedFilters[] edumgupipassdblocusesLocusAppliedFilterses = locusAppliedFiltersCriteria.listLocusAppliedFilters();
		length =edumgupipassdblocusesLocusAppliedFilterses== null ? 0 : Math.min(edumgupipassdblocusesLocusAppliedFilterses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusAppliedFilterses[i]);
		}
		System.out.println(length + " LocusAppliedFilters record(s) retrieved."); 
		
		System.out.println("Listing LocusModuleData by Criteria...");
		edu.mgupi.pass.db.locuses.LocusModuleDataCriteria locusModuleDataCriteria = new edu.mgupi.pass.db.locuses.LocusModuleDataCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusModuleDataCriteria.idModuleParam.eq();
		locusModuleDataCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusModuleData[] edumgupipassdblocusesLocusModuleDatas = locusModuleDataCriteria.listLocusModuleData();
		length =edumgupipassdblocusesLocusModuleDatas== null ? 0 : Math.min(edumgupipassdblocusesLocusModuleDatas.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusModuleDatas[i]);
		}
		System.out.println(length + " LocusModuleData record(s) retrieved."); 
		
		System.out.println("Listing LocusAppliedModule by Criteria...");
		edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria locusAppliedModuleCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedModuleCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusAppliedModuleCriteria.idLocusModule.eq();
		locusAppliedModuleCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusAppliedModule[] edumgupipassdblocusesLocusAppliedModules = locusAppliedModuleCriteria.listLocusAppliedModule();
		length =edumgupipassdblocusesLocusAppliedModules== null ? 0 : Math.min(edumgupipassdblocusesLocusAppliedModules.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusAppliedModules[i]);
		}
		System.out.println(length + " LocusAppliedModule record(s) retrieved."); 
		
		System.out.println("Listing LocusAppliedModuleParams by Criteria...");
		edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsCriteria locusAppliedModuleParamsCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedModuleParamsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusAppliedModuleParamsCriteria.ID.eq();
		locusAppliedModuleParamsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusAppliedModuleParams[] edumgupipassdblocusesLocusAppliedModuleParamses = locusAppliedModuleParamsCriteria.listLocusAppliedModuleParams();
		length =edumgupipassdblocusesLocusAppliedModuleParamses== null ? 0 : Math.min(edumgupipassdblocusesLocusAppliedModuleParamses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusAppliedModuleParamses[i]);
		}
		System.out.println(length + " LocusAppliedModuleParams record(s) retrieved."); 
		
		System.out.println("Listing LocusAppliedFilterParams by Criteria...");
		edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria locusAppliedFilterParamsCriteria = new edu.mgupi.pass.db.locuses.LocusAppliedFilterParamsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//locusAppliedFilterParamsCriteria.ID.eq();
		locusAppliedFilterParamsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.locuses.LocusAppliedFilterParams[] edumgupipassdblocusesLocusAppliedFilterParamses = locusAppliedFilterParamsCriteria.listLocusAppliedFilterParams();
		length =edumgupipassdblocusesLocusAppliedFilterParamses== null ? 0 : Math.min(edumgupipassdblocusesLocusAppliedFilterParamses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdblocusesLocusAppliedFilterParamses[i]);
		}
		System.out.println(length + " LocusAppliedFilterParams record(s) retrieved."); 
		
		System.out.println("Listing Sensors by Criteria...");
		edu.mgupi.pass.db.sensors.SensorsCriteria sensorsCriteria = new edu.mgupi.pass.db.sensors.SensorsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//sensorsCriteria.idSensor.eq();
		sensorsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.sensors.Sensors[] edumgupipassdbsensorsSensorses = sensorsCriteria.listSensors();
		length =edumgupipassdbsensorsSensorses== null ? 0 : Math.min(edumgupipassdbsensorsSensorses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsensorsSensorses[i]);
		}
		System.out.println(length + " Sensors record(s) retrieved."); 
		
		System.out.println("Listing SensorClasses by Criteria...");
		edu.mgupi.pass.db.sensors.SensorClassesCriteria sensorClassesCriteria = new edu.mgupi.pass.db.sensors.SensorClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//sensorClassesCriteria.idSensorClass.eq();
		sensorClassesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.sensors.SensorClasses[] edumgupipassdbsensorsSensorClasseses = sensorClassesCriteria.listSensorClasses();
		length =edumgupipassdbsensorsSensorClasseses== null ? 0 : Math.min(edumgupipassdbsensorsSensorClasseses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsensorsSensorClasseses[i]);
		}
		System.out.println(length + " SensorClasses record(s) retrieved."); 
		
		System.out.println("Listing SensorTypes by Criteria...");
		edu.mgupi.pass.db.sensors.SensorTypesCriteria sensorTypesCriteria = new edu.mgupi.pass.db.sensors.SensorTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//sensorTypesCriteria.idSensorType.eq();
		sensorTypesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.sensors.SensorTypes[] edumgupipassdbsensorsSensorTypeses = sensorTypesCriteria.listSensorTypes();
		length =edumgupipassdbsensorsSensorTypeses== null ? 0 : Math.min(edumgupipassdbsensorsSensorTypeses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsensorsSensorTypeses[i]);
		}
		System.out.println(length + " SensorTypes record(s) retrieved."); 
		
		System.out.println("Listing SurfaceClasses by Criteria...");
		edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria surfaceClassesCriteria = new edu.mgupi.pass.db.surfaces.SurfaceClassesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//surfaceClassesCriteria.idSurfaceClass.eq();
		surfaceClassesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.surfaces.SurfaceClasses[] edumgupipassdbsurfacesSurfaceClasseses = surfaceClassesCriteria.listSurfaceClasses();
		length =edumgupipassdbsurfacesSurfaceClasseses== null ? 0 : Math.min(edumgupipassdbsurfacesSurfaceClasseses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsurfacesSurfaceClasseses[i]);
		}
		System.out.println(length + " SurfaceClasses record(s) retrieved."); 
		
		System.out.println("Listing Surfaces by Criteria...");
		edu.mgupi.pass.db.surfaces.SurfacesCriteria surfacesCriteria = new edu.mgupi.pass.db.surfaces.SurfacesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//surfacesCriteria.idSurface.eq();
		surfacesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.surfaces.Surfaces[] edumgupipassdbsurfacesSurfaceses = surfacesCriteria.listSurfaces();
		length =edumgupipassdbsurfacesSurfaceses== null ? 0 : Math.min(edumgupipassdbsurfacesSurfaceses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsurfacesSurfaceses[i]);
		}
		System.out.println(length + " Surfaces record(s) retrieved."); 
		
		System.out.println("Listing SurfaceTypes by Criteria...");
		edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria surfaceTypesCriteria = new edu.mgupi.pass.db.surfaces.SurfaceTypesCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//surfaceTypesCriteria.idSurfaceType.eq();
		surfaceTypesCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.surfaces.SurfaceTypes[] edumgupipassdbsurfacesSurfaceTypeses = surfaceTypesCriteria.listSurfaceTypes();
		length =edumgupipassdbsurfacesSurfaceTypeses== null ? 0 : Math.min(edumgupipassdbsurfacesSurfaceTypeses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsurfacesSurfaceTypeses[i]);
		}
		System.out.println(length + " SurfaceTypes record(s) retrieved."); 
		
		System.out.println("Listing Materials by Criteria...");
		edu.mgupi.pass.db.surfaces.MaterialsCriteria materialsCriteria = new edu.mgupi.pass.db.surfaces.MaterialsCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//materialsCriteria.idSurfaceMaterial.eq();
		materialsCriteria.setMaxResults(ROW_COUNT);
		edu.mgupi.pass.db.surfaces.Materials[] edumgupipassdbsurfacesMaterialses = materialsCriteria.listMaterials();
		length =edumgupipassdbsurfacesMaterialses== null ? 0 : Math.min(edumgupipassdbsurfacesMaterialses.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(edumgupipassdbsurfacesMaterialses[i]);
		}
		System.out.println(length + " Materials record(s) retrieved."); 
		
	}
	
	public static void main(String[] args) {
		try {
			ListPassData listPassData = new ListPassData();
			try {
				listPassData.listTestData();
				//listPassData.listLFiltersBycodename();
				//listPassData.listByCriteria();
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
