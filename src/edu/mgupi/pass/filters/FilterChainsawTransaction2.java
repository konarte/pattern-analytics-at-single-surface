package edu.mgupi.pass.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterChainsawTransaction2 extends FilterChainsaw {

	private final static Logger logger = LoggerFactory.getLogger(FilterChainsawTransaction2.class);

	private FilterChainsaw source;
	private Map<IFilter, FilterStore> stores = new HashMap<IFilter, FilterStore>();

	public FilterChainsawTransaction2(FilterChainsaw source) {
		super(source.singleInstanceCaching);
		
		if (source == null) {
			throw new IllegalArgumentException("Internal error. 'source' must be not null.");
		}

		logger.trace("CREATE");
		this.source = source;
		this.filterList.addAll(source.getFilters());
	}

	public void close() {
		super.close();

		stores.clear();
		stores = null;

		source = null;

	}

	public FilterChainsaw getSource() {
		return this.source;
	}

	@Override
	public IFilter getFilter(int pos) {
		throw new IllegalStateException("Method 'getFilter' is prohibited for FilterChainsawTransaction2.");
	}

	public FilterStore getFilterStore(int pos) {

		logger.trace("TRY TO RETURN FILTER AT POS {} FROM {}", pos, this.filterList.size());

		IFilter filter = super.getFilter(pos);
		if (filter == null) {
			logger.debug("NOT FOUND");
			return null;
		}

		FilterStore store = stores.get(filter);
		if (store == null) {
			//throw new IllegalStateException("Unexpected error. Unable to find stored reference for filter " + filter);
			store = new FilterStore(filter.getName());
			stores.put(filter, store);
		}

		if (store.parameters == EMPTY) {

			if (filter.getParams() == null) {
				store.parameters = null;
			} else {
				try {
					Collection<Param> cloned = new ArrayList<Param>(filter.getParams().size());
					for (Param param : filter.getParams()) {
						cloned.add((Param) param.clone());
					}
					store.parameters = cloned;
				} catch (CloneNotSupportedException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return store;
	}

	public void commitChanges() throws NoSuchParamException, IllegalParameterValueException {

		for (int sourceIdx = 0; sourceIdx < source.filterList.size(); sourceIdx++) {
			IFilter sourceFilter = source.filterList.get(sourceIdx);

			boolean foundHere = false;
			for (int current = 0; current < this.filterList.size(); current++) {
				IFilter currentFilter = this.filterList.get(current);
				if (currentFilter == sourceFilter) {
					foundHere = true;
					break;
				}
			}

			if (!foundHere) {
				logger.debug("REMOVE OTHER " + sourceFilter + ", CAUSE NOT FOUND HERE");
				source.removeFilter(sourceIdx);
				sourceIdx--;
			}

		}

		for (int current = 0; current < this.filterList.size(); current++) {
			IFilter currentFilter = this.filterList.get(current);

			boolean foundThere = false;
			for (int sourceIdx = 0; sourceIdx < source.filterList.size(); sourceIdx++) {
				IFilter sourceFilter = source.filterList.get(sourceIdx);
				if (currentFilter == sourceFilter) {
					foundThere = true;
					break;
				}
			}

			if (!foundThere) {
				logger.debug("ADD CURRENT " + currentFilter + ", CAUSE NOT FOUND IN SOURCE");
				source.filterList.add(currentFilter);
			}
		}

		if (this.filterList.size() != source.filterList.size()) {
			throw new IllegalStateException("Internal error. After recombination of filters, sizes are not equals.");
		}

		for (IFilter filter : this.filterList) {
			FilterStore store = this.stores.get(filter);
			if (store == null) {
				throw new IllegalStateException("Internal error. After recombination of filters, can't find filter "
						+ filter + ".");
			}
			if (store.parameters != EMPTY && filter.getParams() != null) {
				for (Param param : filter.getParams()) {
					Param storeParam = ParamHelper.getParameter(param.getName(), store.parameters);
					param.setValue(storeParam.getValue());
				}
			}
		}

		source.filterList.clear();
		source.filterList.addAll(this.filterList);

		//		for (int current = 0; current < this.filterList.size(); current++) {
		//			IFilter currentFilter = this.filterList.get(current);
		//
		//			int foundThere = -1;
		//			for (int sourceIdx = 0; sourceIdx < source.filterList.size(); sourceIdx++) {
		//				IFilter sourceFilter = source.filterList.get(sourceIdx);
		//				if (currentFilter == sourceFilter) {
		//					foundThere = sourceIdx;
		//					break;
		//				}
		//			}
		//
		//			if (foundThere == -1) {
		//				throw new IllegalStateException("Internal error. After recombination of filters, can't find filter "
		//						+ currentFilter);
		//			}
		//		}

	}

	private static Collection<Param> EMPTY = new ArrayList<Param>();

	public static class FilterStore {
		private FilterStore(String name) {
			this.name = name;
		}

		public String name;
		public Collection<Param> parameters = EMPTY;

		public String toString() {
			return name;
		}
	}

}
