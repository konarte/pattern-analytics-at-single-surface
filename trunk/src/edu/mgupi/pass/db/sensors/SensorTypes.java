package edu.mgupi.pass.db.sensors;

/**
 * Тип датчика для вихретоков: с перпендикулярным расположением катушки, 
 * с параллельным расположением катушки (проходной), 
 * многокатушечный. 
 * 
 * http://www.acta-ndt.ru/?id=2007, пленочный плоский, многосекционный(под вопросом).
 * 
 * Для других  видов датчиков типы будут другие, разумеется.
 */
public class SensorTypes {
	private int idSensorType;
	private String name;
	/**
	 * Изображение (конструктивная схема) датчика в виде картинки. 
	 * Картинка должна быть стандартизированного размера. 256x256 пикселей.
	 * 
	 * Формат хранения -- PNG
	 */
	private byte[] sensorImage;
	edu.mgupi.pass.db.sensors.SensorClasses sensorClass;

	public void setIdSensorType(int aIdSensorType) {
		this.idSensorType = aIdSensorType;
	}

	public int getIdSensorType() {
		return this.idSensorType;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setSensorImage(byte[] aSensorImage) {
		this.sensorImage = aSensorImage;
	}

	public byte[] getSensorImage() {
		return this.sensorImage;
	}
}