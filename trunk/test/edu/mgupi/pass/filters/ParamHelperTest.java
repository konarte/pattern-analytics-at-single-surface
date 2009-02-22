package edu.mgupi.pass.filters;

import static org.junit.Assert.assertEquals;

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
		assertEquals(44.56, ParamHelper.getParameter("key3", sampleMap));
		assertEquals("14.55", ParamHelper.getParameter("key1", sampleMap));
		assertEquals(6, ParamHelper.getParameter("key2", sampleMap));

		try {
			assertEquals("NO", ParamHelper.getParameter("key-x", sampleMap));
		} catch (NoSuchParamException nspe) {
			System.out.println("Expected exception was thrown: " + nspe);
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

		this.testGetParameter(ParamHelper.convertParamsToValues(paramList));
	}

}
