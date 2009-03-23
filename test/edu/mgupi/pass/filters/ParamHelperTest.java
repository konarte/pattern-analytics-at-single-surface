package edu.mgupi.pass.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.Param.TYPES;

public class ParamHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private void testGetParameter(Map<String, Object> sampleMap) throws NoSuchParamException {
		assertEquals("44.56", ParamHelper.getParameterM("key3", sampleMap));
		assertEquals("14.55", ParamHelper.getParameterM("key1", sampleMap));
		assertEquals("6", ParamHelper.getParameterM("key2", sampleMap));

		try {
			assertEquals("NO", ParamHelper.getParameterM("key-x", sampleMap));
			fail("No NoSuchParamException");
		} catch (NoSuchParamException nspe) {
			System.out.println("Expected exception was thrown: " + nspe);
		}

		try {
			ParamHelper.getParameterM(null, sampleMap);
			fail("No IllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}

		try {
			ParamHelper.getParameterM("key1", null);
			fail("No IllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}
	}

	@Test
	public void testGetParameter() throws NoSuchParamException {
		Map<String, Object> sampleMap = new HashMap<String, Object>();
		sampleMap.put("key1", "14.55");
		sampleMap.put("key2", "6");
		sampleMap.put("key3", "44.56");

		this.testGetParameter(sampleMap);
	}

	private class MyFilter implements IFilter {
		private Collection<Param> paramList = null;

		public MyFilter(Collection<Param> paramList) {
			this.paramList = paramList;
		}

		public Collection<Param> getParams() {
			return this.paramList;
		}

		public BufferedImage convert(BufferedImage source) throws FilterException {
			fail("Not implemented.");
			return null;
		}

		public String getName() {
			fail("Not implemented.");
			return null;
		}

		public void onAttachToImage(BufferedImage source) {
			fail("Not implemented.");
		}

		public void onDetachFromImage(BufferedImage source) {
			fail("Not implemented.");
		}
	}

	@Test
	public void testConvertParamsToValues() throws FilterException {
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new Param("key1", "Ключ 1", TYPES.STRING, "14.55"));
		paramList.add(new Param("key2", "Ключ 2", TYPES.INT, 6));
		paramList.add(new Param("key3", "Ключ 3", TYPES.DOUBLE, 12.22));

		paramList.get(paramList.size() - 1).setValue(44.56);

		MyFilter myFilter = new MyFilter(paramList);

		assertNotNull(ParamHelper.convertParamsToValues(myFilter));
		assertNotNull(ParamHelper.convertParamsToValues((IFilter) null));

		this.testGetParameter(ParamHelper.convertParamsToValues(myFilter));
	}

	@Test
	public void testSearchParameter() throws NoSuchParamException {
		ArrayList<Param> paramList = new ArrayList<Param>();
		paramList.add(new Param("key1", "Ключ 1", TYPES.STRING, "14.55"));
		paramList.add(new Param("key2", "Ключ 2", TYPES.INT, 6));
		paramList.add(new Param("key3", "Ключ 3", TYPES.DOUBLE, 12.22));

		MyFilter myFilter = new MyFilter(paramList);

		assertNotNull(ParamHelper.searchParameter("key1", myFilter, false));
		assertNull(ParamHelper.searchParameter("key1", (IFilter) null, false));
		assertNull(ParamHelper.searchParameter("key56", myFilter, false));

		try {
			ParamHelper.searchParameter(null, myFilter, false);
			fail("No IllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}

		try {
			ParamHelper.searchParameter("key66", myFilter, true);
			fail("No NoSuchParamException");
		} catch (NoSuchParamException iae) {
			System.out.println("Received expected exception: " + iae);
		}
	}

	@Test
	public void testConvertParamsToJSON() {
		ArrayList<Param> paramList = new ArrayList<Param>();
		paramList.add(new Param("key1", "Ключ 1", TYPES.STRING, "14.55"));
		paramList.add(new Param("key2", "Ключ 2", TYPES.INT, 6));
		paramList.add(new Param("key3", "Ключ 3", TYPES.DOUBLE, 12.22));

		MyFilter myFilter = new MyFilter(paramList);
		assertEquals("{\"key1\":\"14.55\",\"key2\":\"6\",\"key3\":\"12.22\"}", ParamHelper
				.convertParamsToJSON(myFilter));
	}

	@Test
	public void testConvertParamsToJSONFull() throws Exception {
		Collection<Param> paramList = new ArrayList<Param>();
		ParamTest.fillParameters(paramList);

		MyFilter myFilter = new MyFilter(paramList);
		String converted = ParamHelper.convertParamsToJSON(myFilter);
		System.out.println(converted);

		Collection<Param> paramList2 = new ArrayList<Param>();
		ParamTest.fillParameters(paramList2);
		MyFilter myFilter2 = new MyFilter(paramList2);

		ParamHelper.fillParametersFromJSON(myFilter2, converted);

		ParamTest.compareInCollections(myFilter.getParams(), myFilter2.getParams(), true);
		ParamTest.compareInCollections(myFilter.getParams(), myFilter2.getParams(), false);
	}
}
