package edu.mgupi.pass.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.template.ParametersEditorPanelTest;

public class ParamTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClone() throws CloneNotSupportedException, NoSuchParamException {
		Collection<Param> params = new ArrayList<Param>();
		ParametersEditorPanelTest.fillParameters(params);

		assertFalse(params.isEmpty());

		Collection<Param> cloned = new ArrayList<Param>();
		for (Param param : params) {
			cloned.add((Param) param.clone());
		}

		ParamTest.compareInCollections(params, cloned, false);
	}

	public static void compareTwoParameters(Param firstParam, Param secondParam) {
		compareTwoParametersDefinitions(firstParam, secondParam);
		assertEquals(firstParam.getValue(), secondParam.getValue());
	}

	public static void compareTwoParametersDefinitions(Param firstParam, Param secondParam) {
		assertTrue(firstParam.getAllowed_values() == secondParam.getAllowed_values());
		assertEquals(firstParam.getDefault_(), secondParam.getDefault_());
		assertEquals(firstParam.getHi_border(), secondParam.getHi_border());
		assertEquals(firstParam.getLow_border(), secondParam.getLow_border());
		assertEquals(firstParam.getName(), secondParam.getName());
		assertEquals(firstParam.getTitle(), secondParam.getTitle());
		assertTrue(firstParam.getType() == secondParam.getType());
		assertTrue(firstParam.getVisual_values() == secondParam.getVisual_values());
	}

	public static void compareInCollections(Collection<Param> first, Collection<Param> second, boolean definitionOnly)
			throws NoSuchParamException {
		if (first == null && second == null) {
			return;
		}
		assertEquals(first.size(), second.size());
		for (Param sourceParam : first) {
			Param clonedParam = ParamHelper.getParameter(sourceParam.getName(), second);
			assertNotNull(clonedParam);
			assertFalse(sourceParam == clonedParam);

			if (definitionOnly) {
				ParamTest.compareTwoParametersDefinitions(sourceParam, clonedParam);
			} else {
				ParamTest.compareTwoParameters(sourceParam, clonedParam);
			}
		}
	}

}
