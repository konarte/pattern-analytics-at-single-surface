package edu.mgupi.pass.db.sensors;

import edu.mgupi.pass.db.surfaces.Materials;

public class Sensors {
	private int idSensor;
	edu.mgupi.pass.db.sensors.SensorTypes sensorType;
	Materials mpathMaterial;

	public void setIdSensor(int aIdSensor) {
		this.idSensor = aIdSensor;
	}

	public int getIdSensor() {
		return this.idSensor;
	}
}