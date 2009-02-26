create table SurfaceClasses (IdSurfaceType int(11) not null auto_increment, Name varchar(255), SurfaceImage blob not null, primary key (IdSurfaceType)) type=InnoDB CHARACTER SET UTF8;
create table Surfaces (IdSurface int(11) not null auto_increment, Height float not null, Width float not null, Length float not null, MultiLayer tinyint(1) not null, SurfaceClassesIdSurfaceType int(11) not null, SurfaceTypesIdSurfaceMode int(11) not null, SurfaceTypesIdSurfaceMode2 int(11) not null, primary key (IdSurface)) type=InnoDB CHARACTER SET UTF8;
create table SurfaceTypes (IdSurfaceMode int(11) not null auto_increment, Name varchar(255), SurfaceClassesIdSurfaceType int(11) not null, MaterialsIdSurfaceMaterial int(11) not null, primary key (IdSurfaceMode)) type=InnoDB CHARACTER SET UTF8;
create table Materials (IdSurfaceMaterial int(11) not null auto_increment, Name varchar(255), ElectricalConduction float not null, MagneticConductivity float not null, SensorsIdSensor int(11) not null, primary key (IdSurfaceMaterial)) type=InnoDB CHARACTER SET UTF8;
create table DefectClasses (IdDefectClass int(11) not null auto_increment, Name varchar(255), primary key (IdDefectClass)) type=InnoDB CHARACTER SET UTF8;
create table DefectTypes (IdDefectType int(11) not null auto_increment, Name varchar(255), DefectImage blob not null, DefectClassesIdDefectClass int(11) not null, primary key (IdDefectType)) type=InnoDB CHARACTER SET UTF8;
create table Defects (IdDefect int(11) not null auto_increment, BeddingDepth float not null, Depth float not null, Width float not null, Length float not null, DefectTypesIdDefectType int(11) not null, primary key (IdDefect)) type=InnoDB CHARACTER SET UTF8;
create table LModules (IdModule int(11) not null auto_increment, Name varchar(255), Codename varchar(255), primary key (IdModule)) type=InnoDB CHARACTER SET UTF8;
create table LFilters (IdModule int(11) not null auto_increment, Name varchar(255), Codename varchar(255), primary key (IdModule)) type=InnoDB CHARACTER SET UTF8;
create table Locuses (IdLocus int(11) not null auto_increment, Name varchar(255), ThumbImage blob not null, Histogram blob not null, FilteredImage blob not null, LModulesIdModule int(11) not null, SurfacesIdSurface int(11) not null, DefectsIdDefect int(11) not null, LocusSourcesIdLocusSource int(11) not null, SensorsIdSensor int(11) not null, primary key (IdLocus)) type=InnoDB CHARACTER SET UTF8;
create table LocusSources (IdLocusSource int(11) not null auto_increment, Filename varchar(255), SourceImage blob not null, primary key (IdLocusSource)) type=InnoDB CHARACTER SET UTF8;
create table LocusFilters (IdLocusFilter int(11) not null auto_increment, Options varchar(255), `Order` int(11) not null, LFiltersIdModule int(11) not null, LocusesIdLocus int(11), LocusesIndex int(11), primary key (IdLocusFilter)) type=InnoDB CHARACTER SET UTF8;
create table LocusModuleParams (IdModuleParam int(11) not null auto_increment, ParamName varchar(255), ParamData blob not null, LocusesIdLocus int(11), primary key (IdModuleParam)) type=InnoDB CHARACTER SET UTF8;
create table Sensors (IdSensor int(11) not null auto_increment, SensorTypesIdSensorType int(11) not null, primary key (IdSensor)) type=InnoDB CHARACTER SET UTF8;
create table SensorClasses (IdSensorClass int(11) not null auto_increment, Name varchar(255), primary key (IdSensorClass)) type=InnoDB CHARACTER SET UTF8;
create table SensorTypes (IdSensorType int(11) not null auto_increment, Name varchar(255), SensorImage blob not null, SensorClassesIdSensorClass int(11) not null, primary key (IdSensorType)) type=InnoDB CHARACTER SET UTF8;
alter table Surfaces add index FKSurfaces111857 (SurfaceClassesIdSurfaceType), add constraint FKSurfaces111857 foreign key (SurfaceClassesIdSurfaceType) references SurfaceClasses (IdSurfaceType);
alter table Surfaces add index FKSurfaces289635 (SurfaceTypesIdSurfaceMode), add constraint FKSurfaces289635 foreign key (SurfaceTypesIdSurfaceMode) references SurfaceTypes (IdSurfaceMode);
alter table Defects add index FKDefects361213 (DefectTypesIdDefectType), add constraint FKDefects361213 foreign key (DefectTypesIdDefectType) references DefectTypes (IdDefectType);
alter table Locuses add index FKLocuses83341 (LModulesIdModule), add constraint FKLocuses83341 foreign key (LModulesIdModule) references LModules (IdModule);
alter table Locuses add index FKLocuses363463 (SurfacesIdSurface), add constraint FKLocuses363463 foreign key (SurfacesIdSurface) references Surfaces (IdSurface);
alter table Locuses add index FKLocuses478057 (DefectsIdDefect), add constraint FKLocuses478057 foreign key (DefectsIdDefect) references Defects (IdDefect);
alter table Locuses add index FKLocuses454467 (LocusSourcesIdLocusSource), add constraint FKLocuses454467 foreign key (LocusSourcesIdLocusSource) references LocusSources (IdLocusSource);
alter table LocusFilters add index FKLocusFilte871909 (LFiltersIdModule), add constraint FKLocusFilte871909 foreign key (LFiltersIdModule) references LFilters (IdModule);
alter table LocusModuleParams add index FKLocusModul955724 (LocusesIdLocus), add constraint FKLocusModul955724 foreign key (LocusesIdLocus) references Locuses (IdLocus);
alter table LocusFilters add index FKLocusFilte404385 (LocusesIdLocus), add constraint FKLocusFilte404385 foreign key (LocusesIdLocus) references Locuses (IdLocus);
alter table DefectTypes add index FKDefectType271305 (DefectClassesIdDefectClass), add constraint FKDefectType271305 foreign key (DefectClassesIdDefectClass) references DefectClasses (IdDefectClass);
alter table SensorTypes add index FKSensorType37521 (SensorClassesIdSensorClass), add constraint FKSensorType37521 foreign key (SensorClassesIdSensorClass) references SensorClasses (IdSensorClass);
alter table Sensors add index FKSensors19727 (SensorTypesIdSensorType), add constraint FKSensors19727 foreign key (SensorTypesIdSensorType) references SensorTypes (IdSensorType);
alter table Locuses add index FKLocuses194860 (SensorsIdSensor), add constraint FKLocuses194860 foreign key (SensorsIdSensor) references Sensors (IdSensor);
alter table Surfaces add index FKSurfaces147867 (SurfaceTypesIdSurfaceMode2), add constraint FKSurfaces147867 foreign key (SurfaceTypesIdSurfaceMode2) references SurfaceTypes (IdSurfaceMode);
alter table SurfaceTypes add index FKSurfaceTyp34584 (SurfaceClassesIdSurfaceType), add constraint FKSurfaceTyp34584 foreign key (SurfaceClassesIdSurfaceType) references SurfaceClasses (IdSurfaceType);
alter table SurfaceTypes add index FKSurfaceTyp941311 (MaterialsIdSurfaceMaterial), add constraint FKSurfaceTyp941311 foreign key (MaterialsIdSurfaceMaterial) references Materials (IdSurfaceMaterial);
alter table Materials add index FKMaterials628917 (SensorsIdSensor), add constraint FKMaterials628917 foreign key (SensorsIdSensor) references Sensors (IdSensor);
