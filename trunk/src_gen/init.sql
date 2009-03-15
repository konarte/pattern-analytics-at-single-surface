-- Материалы

insert into Materials (Name, ElectricalConduction, MagneticConductivity)
values ('Серебро', 0.016, 3800);

insert into Materials (Name, ElectricalConduction, MagneticConductivity)
values ('Медь', 0.0175, 7860);

insert into Materials (Name, ElectricalConduction, MagneticConductivity)
values ('Алюминий', 0.03, 6510);



-- Формы поверхностей
insert into SurfaceClasses (Name, SurfaceImage)
values ('Тело вращения', null);

insert into SurfaceClasses (Name, SurfaceImage)
values ('Плоскость', null);




-- Типы (конфигурации) поверхностей
insert into SurfaceTypes(Name, SurfaceClassesIdSurfaceType, MaterialsIdSurfaceMaterial)
select 'Алюминиевая пластина', c.IdSurfaceType, m.IdSurfaceMaterial
from SurfaceClasses c, Materials m
where c.Name = 'Плоскость' and m.Name = 'Алюминий';


insert into SurfaceTypes(Name, SurfaceClassesIdSurfaceType, MaterialsIdSurfaceMaterial)
select 'Медный цилиндр', c.IdSurfaceType, m.IdSurfaceMaterial
from SurfaceClasses c, Materials m
where c.Name = 'Тело вращения' and m.Name = 'Медь';



-- Виды датчиков (системы контроля)
insert into SensorClasses (IdSensorClass, Name)
values (1, 'Вихретоковая');

-- Типы датчиков
insert into SensorTypes (Name, SensorClassesIdSensorClass, SensorImage)
values ('С перпендикулярным расположением катушки', 1, null);

-- Датчики
insert into Sensors (Name, SensorTypesIdSensorType, MaterialsIdSurfaceMaterial)
select 'ДТСП-14', t.IdSensorType, m.IdSurfaceMaterial
from SensorTypes t, Materials m
where t.Name = 'С перпендикулярным расположением катушки'
and m.Name = 'Алюминий';



-- Классы дефектов
insert into DefectClasses(IdDefectClass, Name)
values (1, 'Поверхностый');

insert into DefectClasses(IdDefectClass, Name)
values (2, 'Внутренний');


-- Типы дефектов
insert into DefectTypes (Name, DefectImage, DefectClassesIdDefectClass)
values ('Каверна', null, 1);

insert into DefectTypes (Name, DefectImage, DefectClassesIdDefectClass)
values ('Окалина', null, 1);

insert into DefectTypes (Name, DefectImage, DefectClassesIdDefectClass)
values ('Прижог', null, 1);


-- Маппинг типов
-- nothing