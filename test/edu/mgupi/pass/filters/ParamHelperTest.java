package edu.mgupi.pass.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
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
		assertEquals(44.56, ParamHelper.getParameterM("key3", sampleMap));
		assertEquals("14.55", ParamHelper.getParameterM("key1", sampleMap));
		assertEquals(6, ParamHelper.getParameterM("key2", sampleMap));

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
		sampleMap.put("key2", 6);
		sampleMap.put("key3", 44.56);

		this.testGetParameter(sampleMap);
	}

	@Test
	public void testConvertParamsToValues() throws NoSuchParamException {
		ArrayList<Param> paramList = new ArrayList<Param>();
		paramList.add(new Param("key1", "Ключ 1", TYPES.STRING, "14.55"));
		paramList.add(new Param("key2", "Ключ 2", TYPES.INT, 6));
		paramList.add(new Param("key3", "Ключ 3", TYPES.INT, 12.22));

		paramList.get(paramList.size() - 1).setValue(44.56);

		assertNotNull(ParamHelper.convertParamsToValues(paramList));
		assertNotNull(ParamHelper.convertParamsToValues(null));

		this.testGetParameter(ParamHelper.convertParamsToValues(paramList));
	}

	@Test
	public void testSearchParameter() throws NoSuchParamException {
		ArrayList<Param> paramList = new ArrayList<Param>();
		paramList.add(new Param("key1", "Ключ 1", TYPES.STRING, "14.55"));
		paramList.add(new Param("key2", "Ключ 2", TYPES.INT, 6));
		paramList.add(new Param("key3", "Ключ 3", TYPES.INT, 12.22));

		assertNotNull(ParamHelper.searchParameterL("key1", paramList));
		assertNull(ParamHelper.searchParameterL("key1", null));
		assertNull(ParamHelper.searchParameterL("key56", paramList));

		try {
			ParamHelper.searchParameterL(null, paramList);
			fail("No IllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			System.out.println("Received expected exception: " + iae);
		}

		try {
			ParamHelper.getParameterL("key66", paramList);
			fail("No NoSuchParamException");
		} catch (NoSuchParamException iae) {
			System.out.println("Received expected exception: " + iae);
		}
	}

}
