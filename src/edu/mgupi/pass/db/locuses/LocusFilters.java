package edu.mgupi.pass.db.locuses;

/**
 * Каталог примененных фильтров к годографу
 */
public class LocusFilters {
	private int idLocusFilter;
	private String options;
	private int order;
	edu.mgupi.pass.db.locuses.LFilters filter;

	public void setIdLocusFilter(int aIdLocusFilter) {
		this.idLocusFilter = aIdLocusFilter;
	}

	public int getIdLocusFilter() {
		return this.idLocusFilter;
	}

	public void setOptions(String aOptions) {
		this.options = aOptions;
	}

	public String getOptions() {
		return this.options;
	}

	public void setOrder(int aOrder) {
		this.order = aOrder;
	}

	public int getOrder() {
		return this.order;
	}
}