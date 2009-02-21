package edu.mgupi.pass.db.sensors;

/**
 * Вид датчика (вид системы контроля).
 * 
 * вихретоковая(показывает дефекты до 5-7 мм в поверхности металла), магнитошумовая, 
 * ультразвуковая, магнитоиндукционная, акустическая.
 * 
 * Для каждой из систем соответственно свои датчики со своими параметрами – 
 * на будущее предусмотреть выбор системы контроля, 
 * например подкл соотв базу данных по системе контроля и датчикам – 
 * пока  делаем только вихретоковую)
 */
public class SensorClasses {
	private int idSensorClass;
	private String name;
	edu.mgupi.pass.db.sensors.SensorTypes unnamed_SensorTypes_;

	public void setIdSensorClass(int aIdSensorClass) {
		this.idSensorClass = aIdSensorClass;
	}

	public int getIdSensorClass() {
		return this.idSensorClass;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}
}