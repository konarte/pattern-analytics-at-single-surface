package edu.mgupi.pass.db.locuses;

/**
 * Каталог модулей анализа
 */
public class LFilters {
	private int idModule;
	/**
	 * Название фильтра
	 */
	private String name;
	/**
	 * Уникальный код фильтра (пакет+имя)
	 */
	private String codename;

	public void setIdModule(int aIdModule) {
		this.idModule = aIdModule;
	}

	public int getIdModule() {
		return this.idModule;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getName() {
		return this.name;
	}

	public void setCodename(String aCodename) {
		this.codename = aCodename;
	}

	public String getCodename() {
		return this.codename;
	}
}