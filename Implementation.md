

## Введение ##
Предлагается выбрать средством решения язык **Java 1.6** (см. [последнюю версию JRE](http://java.sun.com/javase/downloads/index.jsp)), с использованием стандартного способа подключения к базам данных (JDBC). Данное решение позволит использовать программу как в среде Windows, так и в среде Linux (и любой другой, которая позволит запустить виртуальную машину Java 1.6 и выше).

В качестве БД можно будет использовать любую СУБД. Мы будем использовать СУБД **MySQL 5.1** (см. [последнюю версию ветки 5.1](http://www.mysql.ru/download/)). Данное решение позволит использовать единое хранилище данных для множества операторов данной системы. Нам нельзя забыть также о разграничении прав пользователей на просмотр и изменение данных. Предлагается реализовать классическую схему с защитой ролями необходимых элементов интерфейса и данных.

Для хранения работы со схемой БД (и ORM-маппингом) будет использоваться продукт **Visual Paradigm for UML 6.4** (см. [Visual Paradigm for UML](http://www.visual-paradigm.com/product/vpuml/)).

Кодирование будем выполнять в **Eclispe 3.4 Ganymede** (см. [Eclipse downloads](http://www.eclipse.org/downloads/)).

Предварительные фильтры изображения должны настраиваться в виде подключаемых модулей обработки – пользователь должен сам указать, в каком порядке и с какими параметрами эти фильтры будут применяться.

Как вы помните, нам необходимо иметь 5 видов данных – датчики, дефекты, материалы, поверхности и графики дефектов на определенных датчиках. В случае отсутствия дефекта будет фиксироваться сигнал от бездефектной поверхности.

## 2.1 Подсистема безопасности ##
Задача подсистемы безопасности – разграничить пользователей по виду выполняемых действий. Т.к. у нас достаточно простое приложение, то предлагается ввести жестко заданные классы + жестко заданные роли.
> В системе вводится три типа пользователей:
    * Администраторы системы (они могут добавлять модули, фильтры, датчики, дефекты, поверхности, управлять безопасностью, заносить новых пользователей и т.д., и т.п.)
    * Пользователи системы (они добавляют и изменяют годографы). В текущей реализации персонализация данных не требуется. В дальнейшем она должна быть решена на уровне БД (вместо копания с view-представлениями, хранимыми процедурами и т.д. предлагается просто нормально проработать слой обращения к базам данных, т.е. закодировать новое поведение схем безопасности должно быть элементарно).
    * Гости системы. Они работают в режиме только просмотра.

Предлагается хранить каждого пользователя системы на уровне базы данных. Т.е. логины и пароли хранить не в приложении, а в БД.

@todo: Описать таблицы для хранение безопасности…

## 2.2 Описание таблиц ##

_Надо не забыть, чтобы последняя введенная цифра запоминалась, когда много вводишь всего._

```
 Поле M -- это Mandatory, т.е. обязательное.
 Поле Uq -- это Unique, т.е. уникальное.
 Поле Idx -- это Indexed, т.е. индексированное
```

Поскольку каждая таблица имеет PK (первичный ключ), он не будет каждый раз специально прописываться.

### 2.2 Датчики ###

_Далее геометрия датчика относительно дефекта (из-за разной геометрии может быть разная картинка одного и того-же дефекта но впрочем это не важно, это проблема оборудования):_

_Угол наклона в градусах от 0 до 45, может и 90 как частный случай перпендикулярного датчика положенного на бок._

_Зазор между датчиком и поверхностью контроля в миллиметрах от 0,1 до 2 – больше никакого отклика не получим._

_Как мерить сдвиг датчика относительно дефекта – предполагаю по максимуму искажения первоначального сигнала. В общем надо в резерв по умолчанию 0, в мм_


**Q: Геометрия относительно дефекта -- она относится к датчику или всё-таки к годографу, т.е. конкретному анализу дефекта данным датчиком?**

_На геометрические свойства датчика нам по большому счёту наплевать, нам важно поймать сигнал от дефекта, который можно различить и распознать. Т.е. некий отличный от обычного сигнал и по его форме что-то сказать._

**Q: Имеет ли смысл хранить каталог магнитопроводов, ссылка на который будет вставляться в датчик?**

_Имеет смысл, но в будущих версиях программы. Когда уже пройдут первые испытания на железе._

**Q: Имеет ли смысл хранить единый каталог материалов, для датчиков и для поверхностей?**

_Материалы датчиков и материалы поверхности можно свести в два каталога. Ибо, материалы датчиков сами по себе не могут быть, но только включенные в каталог составных ингредиентов датчиков._

**Q: Имеет ли смысл хранить каталог катушек, ссылка на который будет вставляться в датчик?**
_Эффективнее будет хранение описания катушек (как составной части датчика)в едином каталоге датчиков_

**SensorClasses** (классы датчиков)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название класса|varchar(255)    |+      |+       |         |

**SensorTypes** (типы датчиков)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название типа |varchar(255)    |+      |+       |         |
|image         |Изображение датчика 256x256 px, PNG|Blob            |       |        |         |
|class         |Класс датчика, FK (SensorClasses)|number(10)      |+      |        |         |

**Sensors** (датчики)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название конкретного датчика|varchar(255)    |+      |+       |         |
|type          |Тип датчика, FK (SensorTypes)|number(10)      |+      |        |         |
|material      |Материал магнитопровода, FK( Materials)|number(10)      |+      |        |         |

### 2.3 Поверхности ###

**SurfaceClasses** (классы/формы поверхностей)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название класса|varchar(255)    |+      |+       |         |
|image         |Изображение поверхности 256x256 px, PNG|Blob            |       |        |         |

**SurfaceTypes** (типы/конфигурации поверхостей)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название типа |varchar(255)    |+      |+       |         |
|class         |Класс датчика, FK (SurfaceClasses)|number(10)      |+      |        |         |
|material      |Материал поверхности, FK( Materials)|number(10)      |+      |        |         |

**Surfaces** (поверхности)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|width         |ширина, мм    |float           |+      |        |         |
|height        |высота, мм    |float           |+      |        |         |
|length        |длина, мм     |float           |+      |        |         |
|type          |Тип поверхности, FK (SurfaceTypes)|number(10)      |+      |        |         |
|multiLayer    |Многослойный объект (например печатная плата)|boolean         |+      |        |         |

**Q: Тут я запамятовал что к чему (может просто писать труба ХГк10 или плита Сталь45-01).**
_Тип поверхности тоже важен!_
### 2.4 Дефекты ###

**DefectClasses** (классы дефектов)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название класса|varchar(255)    |+      |+       |         |

**DefectTypes** (типы дефектов)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название типа |varchar(255)    |+      |+       |         |
|image         |Изображение дефекта 256x256 px, PNG|Blob            |       |        |         |
|class         |Класс дефекта, FK (DefectClasses)|number(10)      |+      |        |         |
|additionalOptions|Дополнительные параметры данного типа дефекта в JSON-формате|varchar(4096)   |       |        |         |

**Defects** (дефекты)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|beddingDepth  |Глубина залегания, мм|float           |+      |        |         |
|depth         |Глубина, мм   |float           |+      |        |         |
|width         |Ширина (радиус), мм|float           |+      |        |         |
|width         |Длина, мм     |float           |+      |        |         |
|type          |Тип дефекта, FK (DefectTypes)|number(10)      |+      |        |         |


### 2.5 Годографы ###

**LModules** (зарегистрированные модули)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название модуля|varchar(255)    |+      |+       |         |
|codename      |Уникальный код модуля (пакет+имя)|varchar(255)    |+      |        |         |

**LFilters** (зарегистрированные фильтры)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название фильтра|varchar(255)    |+      |+       |         |
|codename      |Уникальный код фильтра(пакет+имя)|varchar(255)    |+      |        |         |

**LocusSources** (оригинальные годографы)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|filename      |Название файла, откуда был загружен годограф|varchar(255)    |+      |        |         |
|image         |Оригинальный файл (набор байтов, идентичных прочитанным из файла, фактически)|MediumBlob      |+      |        |         |


**Locuses** (годографы)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Уникальное наименование годографа|varchar(255)    |+      |+       |         |
|thumb         |Предпросмотр с примененными фильтрами, 256x256 px, PNG|Blob            |+      |        |         |
|histogram     |Гистограмма изображения с фильтрами, хранящаяся в виде плоского массива значений яркости на диапазоне 0..255|Blob            |+      |        |         |
|filteredImage |Изображение с примененными фильтрами и нужного масштаба, 1024x1024 (т.е. заранее заданный общий размер всех изображений), PNG|MediumBlob      |+      |        |         |
|module        |Используемый модуль анализа, FK (LModules)|number(10)      |+      |        |         |
|surface       |Поверхость, FK (Surfaces)|number(10)      |+      |        |         |
|defect        |Дефект, FK (Defects)|number(10)      |       |        |         |
|sensor        |Датчик, FK (Sensors)|number(10)      |+      |        |         |
|source        |Исходное изображение, FK (LocusSources)|number(10)      |+      |        |         |

_Помните, что поверхность может быть бездефектной!_

**LocusFilterOptions** (параметры фильтров, который были применены к изображению)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|options       |Параметры в JSON-формате|varchar(4096)   |+      |        |         |
|locus         |Годограф, FK (Locuses)|number(10)      |       |        |         |
|filter        |Зарегистрированный фильтр, FK (LFilters)|number(10)      |       |        |         |


**LocusModuleParams** (параметры модуля, который выполнял анализ изображения)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название параметра|varchar(255)    |+      |        |         |
|data          |Данные        |MediumBlob      |+      |        |         |
|locus         |Годограф, FK (Locuses)|number(10)      |       |        |         |



### 2.6 Материалы ###

**Materials** (материалы)
| **Название** | **Описание** | **Тип данных** | **M** | **Uq** | **Idx** |
|:-------------|:-------------|:---------------|:------|:-------|:--------|
|name          |Название материала|varchar(255)    |+      |+       |         |
|electricalConduction|Электрическая проводимость, сименсы|float           |+      |        |         |
|magneticConductivity|Магнитная проницаемость|float           |+      |        |         |