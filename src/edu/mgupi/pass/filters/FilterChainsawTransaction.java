package edu.mgupi.pass.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterChainsawTransaction  {

	private List<IFilter> filterPositions = new ArrayList<IFilter>();
	private Map<IFilter, Collection<Param>> filterMap = new LinkedHashMap<IFilter, Collection<Param>>();
	private FilterChainsaw source;
	private Collection<Param> EMPTY = new ArrayList<Param>();

	public FilterChainsawTransaction(FilterChainsaw source) {
		this.source = source;
		for (IFilter filter : source.getFilters()) {
			filterPositions.add(filter);
			filterMap.put(filter, EMPTY);
		}
	}

	public String getFilterName(int index) {
		IFilter filter = filterPositions.get(index);
		return filter == null ? null : filter.getName();
	}

	public Collection<Param> getFilterData(int index) {
		IFilter filter = filterPositions.get(index);
		if (filter == null) {
			return null;
		}
		if (filter.getParams() == null) {
			return null;
		}

		Collection<Param> params = filterMap.get(filter);
		if (params == EMPTY) {
			Collection<Param> cloned = new ArrayList<Param>(filter.getParams().size());
			for (Param param : params) {
				cloned.add(param);
			}
			filterMap.put(filter, cloned);
			params = cloned;
		}

		return params;

	}

	public void moveUp(int pos) {
		if (pos > 0 && pos < filterPositions.size()) {
			IFilter old = filterPositions.get(pos - 1);
			filterPositions.set(pos - 1, filterPositions.get(pos));
			filterPositions.set(pos, old);
		}

	}

	public void moveDown(int pos) {
		if (pos >= 0 && pos < filterPositions.size() - 1) {
			IFilter old = filterPositions.get(pos + 1);
			filterPositions.set(pos + 1, filterPositions.get(pos));
			filterPositions.set(pos, old);
		}
	}

	public void removeFilter(int pos) {
		if (pos >= 0 && pos < filterPositions.size()) {
			filterPositions.remove(pos);
		}
	}
	
	

	public void commitChanged() {
		for (IFilter filter : source.getFilters()) {
			System.out.println(filter);
		}
	}

}
