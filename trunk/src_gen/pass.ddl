create table DefectClasses (IdDefectClass int(11) not null auto_increment, Name varchar(255) not null unique, primary key (IdDefectClass)) type=InnoDB CHARACTER SET UTF8;
create table DefectTypes (IdDefectType int(11) not null auto_increment, Name varchar(255) not null unique, DefectImage blob, DefectClassesIdDefectClass int(11) not null, primary key (IdDefectType)) type=InnoDB CHARACTER SET UTF8;
create table Defects (IdDefect int(11) not null auto_increment, BeddingDepth float not null, Depth float not null, Width float not null, Length float not null, DefectTypesIdDefectType int(11) not null, primary key (IdDefect)) type=InnoDB CHARACTER SET UTF8;
create table LModules (IdLModule int(11) not null auto_increment, Name varchar(255) not null unique, Codename varchar(255) not null, primary key (IdLModule)) type=InnoDB CHARACTER SET UTF8;
create table LFilters (IdLFilter int(11) not null auto_increment, Name varchar(255) not null unique, Codename varchar(255) not null, ServiceFilter tinyint(1) not null, primary key (IdLFilter)) type=InnoDB CHARACTER SET UTF8;
create table Locuses (IdLocus int(11) not null auto_increment, Name varchar(255), ThumbImage blob not null, FilteredImage mediumblob not null, SurfacesIdSurface int(11) not null, DefectsIdDefect int(11), LocusSourcesIdLocusSource int(11) not null, SensorsIdSensor int(11) not null, LocusAppliedModuleIdLocusModule int(11) not null, primary key (IdLocus)) type=InnoDB CHARACTER SET UTF8;
create table LocusSources (IdLocusSource int(11) not null auto_increment, Filename varchar(255) not null, SourceImage mediumblob not null, primary key (IdLocusSource)) type=InnoDB CHARACTER SET UTF8;
create table LocusAppliedFilters (IdLocusFilter int(11) not null auto_increment, LFiltersIdLFilter int(11) not null, LocusesIdLocus int(11), LocusesIndex int(11), primary key (IdLocusFilter)) type=InnoDB CHARACTER SET UTF8;
create table LocusModuleData (IdModuleParam int(11) not null auto_increment, ParamName varchar(255) not null, ParamData mediumblob not null, DataType int(11) not null, LocusAppliedModuleIdLocusModule int(11), LocusAppliedModuleIndex int(11), primary key (IdModuleParam)) type=InnoDB CHARACTER SET UTF8;
create table Sensors (IdSensor int(11) not null auto_increment, Name varchar(255) not null unique, SensorTypesIdSensorType int(11) not null, MaterialsIdSurfaceMaterial int(11) not null, primary key (IdSensor)) type=InnoDB CHARACTER SET UTF8;
create table SensorClasses (IdSensorClass int(11) not null auto_increment, Name varchar(255) not null unique, primary key (IdSensorClass)) type=InnoDB CHARACTER SET UTF8;
create table SensorTypes (IdSensorType int(11) not null auto_increment, Name varchar(255) not null unique, SensorImage blob, SensorClassesIdSensorClass int(11) not null, primary key (IdSensorType)) type=InnoDB CHARACTER SET UTF8;
create table SurfaceClasses (IdSurfaceClass int(11) not null auto_increment, Name varchar(255) not null unique, SurfaceImage blob, primary key (IdSurfaceClass)) type=InnoDB CHARACTER SET UTF8;
create table Surfaces (IdSurface int(11) not null auto_increment, Height float not null, Width float not null, Length float not null, MultiLayer tinyint(1) not null, SurfaceTypesIdSurfaceType int(11) not null, primary key (IdSurface)) type=InnoDB CHARACTER SET UTF8;
create table SurfaceTypes (IdSurfaceType int(11) not null auto_increment, Name varchar(255) not null unique, SurfaceClassesIdSurfaceClass int(11) not null, MaterialsIdSurfaceMaterial int(11), primary key (IdSurfaceType)) type=InnoDB CHARACTER SET UTF8;
create table Materials (IdSurfaceMaterial int(11) not null auto_increment, Name varchar(255) not null unique, ElectricalConduction float not null, MagneticConductivity float not null, primary key (IdSurfaceMaterial)) type=InnoDB CHARACTER SET UTF8;
create table LocusAppliedModule (IdLocusModule int(11) not null auto_increment, LModulesIdLModule int(11) not null, primary key (IdLocusModule)) type=InnoDB CHARACTER SET UTF8;
create table LocusAppliedModuleParams (ID int(11) not null auto_increment, Name varchar(255) not null unique, Value varchar(255) not null, LocusAppliedModuleIdLocusModule int(11), LocusAppliedModuleIndex int(11), primary key (ID)) type=InnoDB CHARACTER SET UTF8;
create table DefectTypeOptions (ID int(11) not null auto_increment, Name varchar(255) not null unique, Value varchar(255) not null, DefectTypesIdDefectType int(11), DefectTypesIndex int(11), primary key (ID)) type=InnoDB CHARACTER SET UTF8;
create table LocusAppliedFilterParams (ID int(11) not null auto_increment, Name varchar(255) not null unique, Value varchar(255) not null, LocusAppliedFiltersIdLocusFilter int(11), LocusAppliedFiltersIndex int(11), primary key (ID)) type=InnoDB CHARACTER SET UTF8;
alter table Surfaces add index FKSurfaces726673 (SurfaceTypesIdSurfaceType), add constraint FKSurfaces726673 foreign key (SurfaceTypesIdSurfaceType) references SurfaceTypes (IdSurfaceType);
alter table Defects add index FKDefects361213 (DefectTypesIdDefectType), add constraint FKDefects361213 foreign key (DefectTypesIdDefectType) references DefectTypes (IdDefectType);
alter table Locuses add index FKLocuses363463 (SurfacesIdSurface), add constraint FKLocuses363463 foreign key (SurfacesIdSurface) references Surfaces (IdSurface);
alter table Locuses add index FKLocuses478057 (DefectsIdDefect), add constraint FKLocuses478057 foreign key (DefectsIdDefect) references Defects (IdDefect);
alter table Locuses add index FKLocuses454467 (LocusSourcesIdLocusSource), add constraint FKLocuses454467 foreign key (LocusSourcesIdLocusSource) references LocusSources (IdLocusSource);
alter table LocusAppliedFilters add index FKLocusAppli208796 (LFiltersIdLFilter), add constraint FKLocusAppli208796 foreign key (LFiltersIdLFilter) references LFilters (IdLFilter);
alter table LocusAppliedFilters add index FKLocusAppli289042 (LocusesIdLocus), add constraint FKLocusAppli289042 foreign key (LocusesIdLocus) references Locuses (IdLocus);
alter table DefectTypes add index FKDefectType271305 (DefectClassesIdDefectClass), add constraint FKDefectType271305 foreign key (DefectClassesIdDefectClass) references DefectClasses (IdDefectClass);
alter table SensorTypes add index FKSensorType37521 (SensorClassesIdSensorClass), add constraint FKSensorType37521 foreign key (SensorClassesIdSensorClass) references SensorClasses (IdSensorClass);
alter table Sensors add index FKSensors19727 (SensorTypesIdSensorType), add constraint FKSensors19727 foreign key (SensorTypesIdSensorType) references SensorTypes (IdSensorType);
alter table Locuses add index FKLocuses194860 (SensorsIdSensor), add constraint FKLocuses194860 foreign key (SensorsIdSensor) references Sensors (IdSensor);
alter table SurfaceTypes add index FKSurfaceTyp645510 (SurfaceClassesIdSurfaceClass), add constraint FKSurfaceTyp645510 foreign key (SurfaceClassesIdSurfaceClass) references SurfaceClasses (IdSurfaceClass);
alter table Sensors add index FKSensors732005 (MaterialsIdSurfaceMaterial), add constraint FKSensors732005 foreign key (MaterialsIdSurfaceMaterial) references Materials (IdSurfaceMaterial);
alter table SurfaceTypes add index FKSurfaceTyp941311 (MaterialsIdSurfaceMaterial), add constraint FKSurfaceTyp941311 foreign key (MaterialsIdSurfaceMaterial) references Materials (IdSurfaceMaterial);
alter table Locuses add index FKLocuses42294 (LocusAppliedModuleIdLocusModule), add constraint FKLocuses42294 foreign key (LocusAppliedModuleIdLocusModule) references LocusAppliedModule (IdLocusModule);
alter table LocusAppliedModule add index FKLocusAppli247389 (LModulesIdLModule), add constraint FKLocusAppli247389 foreign key (LModulesIdLModule) references LModules (IdLModule);
alter table LocusAppliedModuleParams add index FKLocusAppli80459 (LocusAppliedModuleIdLocusModule), add constraint FKLocusAppli80459 foreign key (LocusAppliedModuleIdLocusModule) references LocusAppliedModule (IdLocusModule);
alter table LocusModuleData add index FKLocusModul831182 (LocusAppliedModuleIdLocusModule), add constraint FKLocusModul831182 foreign key (LocusAppliedModuleIdLocusModule) references LocusAppliedModule (IdLocusModule);
alter table DefectTypeOptions add index FKDefectType236002 (DefectTypesIdDefectType), add constraint FKDefectType236002 foreign key (DefectTypesIdDefectType) references DefectTypes (IdDefectType);
alter table LocusAppliedFilterParams add index FKLocusAppli875942 (LocusAppliedFiltersIdLocusFilter), add constraint FKLocusAppli875942 foreign key (LocusAppliedFiltersIdLocusFilter) references LocusAppliedFilters (IdLocusFilter);
