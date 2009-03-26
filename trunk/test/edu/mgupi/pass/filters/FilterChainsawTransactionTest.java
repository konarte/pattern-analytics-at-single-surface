package edu.mgupi.pass.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.FilterChainsawTransaction.FilterStore;
import edu.mgupi.pass.util.MyFilter;

public class FilterChainsawTransactionTest {

	private FilterChainsaw chainsaw = null;
	private FilterChainsawTransaction chainsawTrans = null;

	@Before
	public void setUp() throws Exception {
		chainsaw = new FilterChainsaw();
	}

	@After
	public void tearDown() throws Exception {
		if (chainsawTrans != null) {
			chainsawTrans.close();
			chainsawTrans = null;
		}
		if (chainsaw != null) {
			chainsaw.close();
			chainsaw = null;
		}
	}

	@Test
	public void testContainData() throws Exception {
		TestFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);

		assertTrue(filter == chainsaw.getFilter(0));

		TestFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter == chainsaw.getFilter(0));
		assertTrue(filter2 == chainsaw.getFilter(1));

		MyFilter filter3 = (MyFilter) chainsaw.appendFilter(MyFilter.class);
		assertTrue(filter3 == chainsaw.getFilter(2));

		chainsawTrans = new FilterChainsawTransaction(chainsaw);

		assertFalse(chainsaw.filterList == chainsawTrans.filterList);
		assertTrue(chainsaw.singleInstanceCaching == chainsawTrans.singleInstanceCaching);

		Iterator<IFilter> c = chainsaw.filterList.iterator();
		Iterator<IFilter> t = chainsawTrans.filterList.iterator();
		while (c.hasNext()) {
			assertTrue(c.next() == t.next());
		}

		try {
			chainsawTrans.getFilter(0);
			fail("No exception thrown!");
		} catch (IllegalStateException e) {
			System.out.println("Received expected exception: " + e);
		}

		FilterStore store = chainsawTrans.getFilterStore(0);
		assertEquals(filter.getName(), store.name);
		ParamTest.compareInClonedCollections(filter.getParams(), store.parameters, false);

		FilterStore store2 = chainsawTrans.getFilterStore(1);
		assertEquals(filter2.getName(), store2.name);
		ParamTest.compareInClonedCollections(filter2.getParams(), store2.parameters, false);

		FilterStore store3 = chainsawTrans.getFilterStore(2);
		assertEquals(filter3.getName(), store3.name);
		ParamTest.compareInClonedCollections(filter3.getParams(), store3.parameters, false);

		Param origParam = ParamHelper.getParameter("p2_string", filter2.getParams());
		Param param = ParamHelper.getParameter("p2_string", store2.parameters);
		ParamTest.compareTwoClonedParameters(origParam, param);
		param.setValue("Мухлобойка");

		ParamTest.compareTwoClonedParametersDefinitions(origParam, param);
		ParamTest.compareTwoClonedParametersDefinitions(ParamHelper.getParameter("p2_string", filter2.getParams()),
				param);

		assertFalse(origParam.getValue().equals(param.getValue()));

		chainsawTrans.commitChanges();

		origParam = ParamHelper.getParameter("p2_string", filter2.getParams());
		param = ParamHelper.getParameter("p2_string", store2.parameters);

		// Bingo!
		ParamTest.compareTwoClonedParameters(origParam, param);
	}

	@Test
	public void testMoves() throws Exception {
		IFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter == chainsaw.getFilter(0));

		IFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter2 == chainsaw.getFilter(1));

		IFilter filter3 = (MyFilter) chainsaw.appendFilter(MyFilter.class);
		assertTrue(filter3 == chainsaw.getFilter(2));

		chainsawTrans = new FilterChainsawTransaction(chainsaw);

		FilterStore store = chainsawTrans.getFilterStore(0);
		FilterStore store2 = chainsawTrans.getFilterStore(1);

		assertEquals(filter.getName(), store.name);
		assertEquals(filter2.getName(), store2.name);

		printFilters(chainsaw.filterList, "MAIN");
		printFilters(chainsawTrans.filterList, "COPIED");

		chainsawTrans.moveUp(1);

		printFilters(chainsawTrans.filterList, "COPIED");

		store = chainsawTrans.getFilterStore(0);
		store2 = chainsawTrans.getFilterStore(1);
		assertEquals(filter.getName(), store2.name);
		assertEquals(filter2.getName(), store.name);

		chainsawTrans.moveDown(1);

		printFilters(chainsawTrans.filterList, "COPIED");

		store = chainsawTrans.getFilterStore(0);
		store2 = chainsawTrans.getFilterStore(1);
		FilterStore store3 = chainsawTrans.getFilterStore(2);

		assertEquals(filter.getName(), store3.name);
		assertEquals(filter2.getName(), store.name);
		assertEquals(filter3.getName(), store2.name);

		chainsawTrans.commitChanges();
		filter = (TestFilter) chainsaw.getFilter(0);
		filter2 = (MyFilter) chainsaw.getFilter(1);
		filter3 = (TestFilter) chainsaw.getFilter(2);

		assertEquals(filter.getName(), store.name);
		assertEquals(filter2.getName(), store2.name);
		assertEquals(filter3.getName(), store3.name);
	}

	@Test
	public void testAddRemove() throws Exception {
		IFilter filter = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter == chainsaw.getFilter(0));

		IFilter filter2 = (TestFilter) chainsaw.appendFilter(TestFilter.class);
		assertTrue(filter2 == chainsaw.getFilter(1));

		IFilter filter3 = (MyFilter) chainsaw.appendFilter(MyFilter.class);
		assertTrue(filter3 == chainsaw.getFilter(2));

		chainsawTrans = new FilterChainsawTransaction(chainsaw);

		chainsawTrans.appendFilter(MyFilter.class);

		FilterStore store = chainsawTrans.getFilterStore(0);
		FilterStore store2 = chainsawTrans.getFilterStore(1);
		FilterStore store3 = chainsawTrans.getFilterStore(2);
		FilterStore store4 = chainsawTrans.getFilterStore(3);

		assertEquals(filter.getName(), store.name);
		assertEquals(filter2.getName(), store2.name);
		assertEquals(filter3.getName(), store3.name);

		chainsawTrans.removeFilter(0);

		assertTrue(store2 == chainsawTrans.getFilterStore(0));
		assertTrue(store3 == chainsawTrans.getFilterStore(1));
		assertTrue(store4 == chainsawTrans.getFilterStore(2));

		chainsawTrans.commitChanges();

		assertTrue(filter2 == chainsaw.getFilter(0));
		assertTrue(filter3 == chainsaw.getFilter(1));
		IFilter filter4 = chainsaw.getFilter(2);
		assertFalse(filter == filter4);
		assertFalse(filter2 == filter4);
		assertFalse(filter3 == filter4);

	}

	private void printFilters(List<IFilter> filters, String title) {
		System.out.println(" --------------- " + title);
		int idx = 0;
		for (IFilter tmp : filters) {
			System.out.println("" + idx++ + " " + tmp + " " + tmp.hashCode());

		}
	}

}
