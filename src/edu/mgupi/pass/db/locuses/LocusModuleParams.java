package edu.mgupi.pass.db.locuses;

/**
 * Каталог параметров примененного модуля к годографу
 */
public class LocusModuleParams {
	private int idModuleParam;
	private String paramName;
	private byte[] paramData;

	public void setIdModuleParam(int aIdModuleParam) {
		this.idModuleParam = aIdModuleParam;
	}

	public int getIdModuleParam() {
		return this.idModuleParam;
	}

	public void setParamName(String aParamName) {
		this.paramName = aParamName;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamData(byte[] aParamData) {
		this.paramData = aParamData;
	}

	public byte[] getParamData() {
		return this.paramData;
	}
}