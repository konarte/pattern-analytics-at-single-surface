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