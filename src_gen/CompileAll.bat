@echo off
if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

set _SOURCEPATH=src
set _CLASSES=classes
set _CLASSPATH=%CLASSPATH%;"lib\orm.jar;lib\annotations.jar"
if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
goto javaHome

:noJavaHome
echo The JAVA_HOME environment variable not find.
echo if compile fail, please set the JAVA_HOME environment variable!
set _JAVACCMD=javac.exe
set _JAVACMD=java.exe
goto compile

:javaHome
set _JAVACCMD=%JAVA_HOME%\bin\javac.exe
set _JAVACMD=%JAVA_HOME%\bin\java.exe
goto compile

:compile
if not exist "%_CLASSES%" md %_CLASSES%
"%_JAVACCMD%" -d %_CLASSES% -classpath %_CLASSPATH% %_SOURCEPATH%\edu\mgupi\pass\db\*.java %_SOURCEPATH%\edu\mgupi\pass\db\surfaces\*.java %_SOURCEPATH%\edu\mgupi\pass\db\defects\*.java %_SOURCEPATH%\edu\mgupi\pass\db\locuses\*.java %_SOURCEPATH%\edu\mgupi\pass\db\sensors\*.java %_SOURCEPATH%\ormsamples\*.java 
if not exist "%_CLASSES%\ormmapping" md %_CLASSES%\ormmapping
if not %_SOURCEPATH% == %_CLASSES% xcopy %_SOURCEPATH%\ormmapping %_CLASSES%\ormmapping /s

"%_JAVACMD%" -cp lib\orm.jar org.orm.util.LazyPropInstrument %_CLASSES%\edu\mgupi\pass\db\NameMapping.class %_CLASSES%\edu\mgupi\pass\db\surfaces\SurfaceClasses.class %_CLASSES%\edu\mgupi\pass\db\surfaces\Surfaces.class %_CLASSES%\edu\mgupi\pass\db\surfaces\SurfaceTypes.class %_CLASSES%\edu\mgupi\pass\db\surfaces\Materials.class %_CLASSES%\edu\mgupi\pass\db\defects\DefectClasses.class %_CLASSES%\edu\mgupi\pass\db\defects\DefectTypes.class %_CLASSES%\edu\mgupi\pass\db\defects\Defects.class %_CLASSES%\edu\mgupi\pass\db\locuses\LModules.class %_CLASSES%\edu\mgupi\pass\db\locuses\LFilters.class %_CLASSES%\edu\mgupi\pass\db\locuses\Locuses.class %_CLASSES%\edu\mgupi\pass\db\locuses\LocusSources.class %_CLASSES%\edu\mgupi\pass\db\locuses\LocusFilterOptions.class %_CLASSES%\edu\mgupi\pass\db\locuses\LocusModuleParams.class %_CLASSES%\edu\mgupi\pass\db\sensors\Sensors.class %_CLASSES%\edu\mgupi\pass\db\sensors\SensorClasses.class %_CLASSES%\edu\mgupi\pass\db\sensors\SensorTypes.class 
set _JAVACCMD=
set _JAVACMD=
set _CLASSES=
set _CLASSPATH=
set _SOURCEPATH=
if "%OS%"=="Windows_NT" @endlocal
if "%OS%"=="WINNT" @endlocal
