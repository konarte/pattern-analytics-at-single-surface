package edu.mgupi.pass.filters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.Param.ParamType;

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
		ParamTest.fillParameters(params);

		assertFalse(params.isEmpty());

		Collection<Param> cloned = new ArrayList<Param>();
		for (Param param : params) {
			cloned.add((Param) param.clone());
		}

		ParamTest.compareInClonedCollections(params, cloned, false);
	}

	public static void compareTwoClonedParameters(Param firstParam, Param secondParam) {
		compareTwoParameters(firstParam, secondParam, true);
	}

	public static void compareTwoParameters(Param firstParam, Param secondParam, boolean cloned) {
		compareTwoParametersDefinitions(firstParam, secondParam, cloned);
		assertEquals(firstParam.getValue(), secondParam.getValue());
	}

	public static void compareTwoClonedParametersDefinitions(Param firstParam, Param secondParam) {
		compareTwoParametersDefinitions(firstParam, secondParam, true);
	}

	public static void compareTwoParametersDefinitions(Param firstParam, Param secondParam,
			boolean cloned) {
		if (cloned) {
			assertTrue(firstParam.getAllowed_values() == secondParam.getAllowed_values());
			assertTrue(firstParam.getVisual_values() == secondParam.getVisual_values());
		} else {
			assertArrayEquals(firstParam.getAllowed_values(), secondParam.getAllowed_values());
			assertArrayEquals(firstParam.getVisual_values(), secondParam.getVisual_values());
		}

		assertEquals(firstParam.getDefault_(), secondParam.getDefault_());
		assertEquals(firstParam.getHi_border(), secondParam.getHi_border());
		assertEquals(firstParam.getLow_border(), secondParam.getLow_border());
		assertEquals(firstParam.getName(), secondParam.getName());
		assertEquals(firstParam.getTitle(), secondParam.getTitle());
		assertTrue(firstParam.getType() == secondParam.getType());

	}

	public static void compareInClonedCollections(Collection<Param> first,
			Collection<Param> second, boolean definitionOnly) throws NoSuchParamException {
		compareInCollections(first, second, definitionOnly, true);
	}

	public static void compareInCollections(Collection<Param> first, Collection<Param> second,
			boolean definitionOnly) throws NoSuchParamException {
		compareInCollections(first, second, definitionOnly, false);
	}

	public static void compareInCollections(Collection<Param> first, Collection<Param> second,
			boolean definitionOnly, boolean cloned) throws NoSuchParamException {
		if (first == null || second == null) {
			return;
		}
		assertEquals(first.size(), second.size());
		for (Param sourceParam : first) {
			Param clonedParam = ParamHelper.getParameter(sourceParam.getName(), second);
			assertNotNull(clonedParam);
			assertFalse(sourceParam == clonedParam);

			if (definitionOnly) {
				ParamTest.compareTwoParametersDefinitions(sourceParam, clonedParam, cloned);
			} else {
				ParamTest.compareTwoParameters(sourceParam, clonedParam, cloned);
			}
		}
	}

	public static Collection<Param> fillParameters(Collection<Param> fullParams) {
		fullParams.add(new Param("p1", "Параметр1", ParamType.INT, 11));
		fullParams.add(new Param("p2", "Параметр2", ParamType.INT, 1, 0, 10));

		fullParams.add(new Param("p3", "Параметр3", ParamType.DOUBLE, 2.0));
		fullParams.add(new Param("p4", "Параметр4", ParamType.DOUBLE, 5.0, 0, 10));

		fullParams.add(new Param("p5", "Параметр5", ParamType.STRING, null));
		fullParams.add(new Param("p5.1", "Параметр5.1", ParamType.STRING, ""));
		fullParams.add(new Param("p6", "Параметр6", ParamType.STRING, "Тестовая строка"));

		fullParams.add(new Param("p7", "Параметр7", ParamType.COLOR, null));
		fullParams.add(new Param("p8", "Параметр8", ParamType.COLOR, Color.BLUE));

		fullParams.add(new Param("p9", "Параметр9", ParamType.STRING, null, new Object[] { "айн",
				"цвай", "драй" }, new String[] { "Первый", "Второй", "Третий" }));
		fullParams.add(new Param("p10", "Параметр10", ParamType.STRING, "цвай", new Object[] {
				"айн", "цвай", "драй" }, new String[] { "Первый", "Второй", "Третий" }));

		return fullParams;
	}
}
