package edu.mgupi.pass.util;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;

public class CacheIFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testInstances() throws Exception {
		CacheInitiable<IFilter> data = CacheIFactory.getFreeListFilters();
		assertNotNull(data);
		assertTrue(data == CacheIFactory.getFreeListFilters());
		
		CacheInitiable<IFilter> data2 = CacheIFactory.getSingleInstanceFilters();
		assertNotNull(data2);
		assertFalse(data == data2);
		assertTrue(data2 == CacheIFactory.getSingleInstanceFilters());
		
		CacheInitiable<IModule> data3 = CacheIFactory.getSingleInstanceModules();
		assertNotNull(data3);
		assertTrue(data3 == CacheIFactory.getSingleInstanceModules());
		
		CacheIFactory.close();
	}

}
