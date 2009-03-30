package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleException;
import edu.mgupi.pass.modules.ModuleNotFoundException;
import edu.mgupi.pass.modules.basic.SimpleMatrixModule;

public class AppDataStorageTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		AppDataStorage storage = AppDataStorage.getInstance();
		assertTrue(storage == AppDataStorage.getInstance());
	}

	@Test
	public void testListLModulesImpl() throws Exception {
		LModules modules[] = AppDataStorage.getInstance().listLModulesImpl();

		assertNotNull(modules);
		assertTrue(modules.length > 0);

		assertTrue(modules == AppDataStorage.getInstance().listLModulesImpl());

	}

	@Test
	public void testListLFiltersImpl() throws Exception {
		LFilters filters[] = AppDataStorage.getInstance().listLFiltersImpl();

		assertNotNull(filters);
		assertTrue(filters.length > 0);

		assertTrue(filters == AppDataStorage.getInstance().listLFiltersImpl());
	}

	@Test
	public void testSearchModuleByClass() throws Exception {
		LModules module = AppDataStorage.getInstance()
				.searchModuleByClass(SimpleMatrixModule.class);
		assertNotNull(module);
		assertEquals(SimpleMatrixModule.class.getName(), module.getCodename());

		assertNull(AppDataStorage.getInstance().searchModuleByClass(new IModule() {

			@Override
			public void analyze(BufferedImage filteredImage, Locuses store) throws IOException,
					ModuleException {
				fail("Not implemented.");
			}

			@Override
			public double compare(Locuses graph1, Locuses graph2) throws IOException,
					ModuleException {
				fail("Not implemented.");
				return 0;
			}

			@Override
			public String getName() {
				return "my test module";
			}

			@Override
			public Collection<Param> getParams() {
				return null;
			}
		}.getClass()));

		try {
			AppDataStorage.getInstance().searchModuleByClass(null);
			fail("No exception throws!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
	}

	@Test
	public void testGetModuleByClass() throws Exception {
		LModules module = AppDataStorage.getInstance().getModuleByClass(SimpleMatrixModule.class);
		assertNotNull(module);
		assertEquals(SimpleMatrixModule.class.getName(), module.getCodename());

		try {
			assertNull(AppDataStorage.getInstance().getModuleByClass(new IModule() {

				@Override
				public void analyze(BufferedImage filteredImage, Locuses store) throws IOException,
						ModuleException {
					fail("Not implemented.");
				}

				@Override
				public double compare(Locuses graph1, Locuses graph2) throws IOException,
						ModuleException {
					fail("Not implemented.");
					return 0;
				}

				@Override
				public String getName() {
					return "my test module";
				}

				@Override
				public Collection<Param> getParams() {
					return null;
				}
			}.getClass()));

			fail("No exception thrown!");
		} catch (ModuleNotFoundException e) {
			System.out.println("Received expected exception: " + e);
		}

		try {
			AppDataStorage.getInstance().getModuleByClass(null);
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
	}

}
