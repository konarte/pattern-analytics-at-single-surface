package edu.mgupi.pass.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.filters.TestFilter;
import edu.mgupi.pass.filters.TestMyFilter;

public class CacheInitiableTest {

	private final static Logger logger = LoggerFactory.getLogger(CacheInitiableTest.class);

	private AbstractCacheInitiable<IInitiable> cache;

	@Before
	public void setUp() throws Exception {
		//cache = new CacheInitiable<IFilter>();
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("Closing...");
		if (cache != null) {
			cache.close();
			cache = null;
		}
	}

	@Test
	public void testGetInstanceSingle() throws Exception {
		cache = new CacheSingleInstance<IInitiable>();
		logger.debug("--------------------");

		TestMyFilter filter = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter);
		assertTrue(filter.isInit());

		TestFilter test = (TestFilter) cache.getInstance(TestFilter.class);
		assertNotNull(test);
		assertTrue(test.isInit());

		TestFilter test2 = (TestFilter) cache.getInstance(TestFilter.class);
		assertNotNull(test2);
		assertTrue(test == test2);

		TestMyFilter filter2 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter2);

		assertTrue(filter == filter2);

		cache.close();
		assertTrue(filter.isDone());
		assertTrue(test.isDone());

	}

	@Test
	public void testGetInstanceMultiple() throws Exception {
		cache = new CacheFreeLists<IInitiable>();
		logger.debug("--------------------");

		TestMyFilter filter = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter);
		assertTrue(filter.isInit());

		TestMyFilter filter2 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter2);
		assertTrue(filter2.isInit());

		TestMyFilter filter3 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter3);
		assertTrue(filter3.isInit());

		assertFalse(filter == filter2);
		assertFalse(filter == filter3);
		assertFalse(filter2 == filter3);

		cache.close();
		assertTrue(filter.isDone());
		assertTrue(filter2.isDone());
		assertTrue(filter3.isDone());

	}

	@Test
	public void testPutDeletedSingle() throws Exception {
		cache = new CacheSingleInstance<IInitiable>();
		logger.debug("--------------------");

		TestMyFilter filter = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter);
		assertTrue(filter.isInit());

		TestMyFilter filter2 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter2);

		assertTrue(filter == filter2);

		cache.releaseInstance(filter);
		assertFalse(filter.isDone());

		TestMyFilter filter3 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter3);
		assertTrue(filter == filter2);
		assertTrue(filter == filter3);
		assertTrue(filter2 == filter3);

		cache.close();
		assertTrue(filter3.isDone());
	}

	@Test
	public void testPutDeletedMultiple() throws Exception {
		cache = new CacheFreeLists<IInitiable>();
		logger.debug("--------------------");

		TestMyFilter filter = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter);
		assertTrue(filter.isInit());

		TestMyFilter filter2 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter2);
		assertTrue(filter2.isInit());

		TestMyFilter filter3 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter3);
		assertTrue(filter3.isInit());

		assertFalse(filter == filter2);
		assertFalse(filter == filter3);
		assertFalse(filter2 == filter3);

		cache.releaseInstance(filter);
		TestMyFilter filter11 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter11);
		assertTrue(filter11.isInit());

		assertTrue(filter == filter11);

		cache.releaseInstance(filter);
		cache.releaseInstance(filter);
		cache.releaseInstance(filter2);

		TestMyFilter filter12 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter12);
		assertTrue(filter == filter12);
		assertTrue(filter12.isInit());

		TestMyFilter filter21 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter21);
		assertTrue(filter2 == filter21);
		assertTrue(filter21.isInit());

		TestMyFilter filter6 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter6);
		assertFalse(filter2 == filter6);
		assertTrue(filter6.isInit());

		cache.releaseInstance(filter21);

		TestMyFilter filter22 = (TestMyFilter) cache.getInstance(TestMyFilter.class);
		assertNotNull(filter22);
		assertTrue(filter2 == filter22);
		assertTrue(filter21 == filter22);

		assertTrue(filter22.isInit());

		cache.close();

		assertTrue(filter.isDone());
		assertTrue(filter2.isDone());
		assertTrue(filter3.isDone());
		assertTrue(filter6.isDone());
	}

}
