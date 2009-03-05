package edu.mgupi.pass.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SecundomerTest {

	private Secundomer secundomer = null;

	@Before
	public void setUp() throws Exception {
		secundomer = new Secundomer("Test");
	}

	@After
	public void tearDown() throws Exception {
		if (secundomer != null) {
			secundomer.stop();
			secundomer = null;
		}
	}

	@Test
	public void testSecundomer() {
		assertEquals("Test", secundomer.getName());
	}

	@Test
	public void testStart() {
		secundomer.start();
		assertEquals(0, secundomer.getTotalCalls());
	}

	@Test
	public void testStop() {
		secundomer.stop();
		assertEquals(0, secundomer.getTotalCalls());
		assertEquals(0, secundomer.getTotalTime());

		secundomer.start();
		secundomer.stop();
		secundomer.start();
		assertEquals(1, secundomer.getTotalCalls());

		secundomer.stop();
		assertEquals(2, secundomer.getTotalCalls());
	}

	@Test
	public void testReset() {

		secundomer.start();
		secundomer.stop();
		assertEquals(1, secundomer.getTotalCalls());

		secundomer.reset();
		assertEquals(0, secundomer.getTotalCalls());
		assertEquals(0, secundomer.getTotalTime());

		secundomer.start();
		secundomer.stop();
		assertEquals(1, secundomer.getTotalCalls());

	}

	@Test
	public void testGetTotalTime() throws InterruptedException {
		assertEquals(0, secundomer.getTotalTime());

		secundomer.start();
		Thread.sleep(200);
		secundomer.stop();
		assertTrue(secundomer.getTotalTime() > 0);
	}

	@Test
	public void testGetTotalCalls() {
		secundomer.stop();
		assertEquals(0, secundomer.getTotalCalls());

		secundomer.start();
		secundomer.start();
		secundomer.start();
		secundomer.stop();

		assertEquals(3, secundomer.getTotalCalls());

		secundomer.stop();
		secundomer.stop();
		secundomer.start();

		assertEquals(3, secundomer.getTotalCalls());

	}

	@Test
	public void testToString() throws InterruptedException {
		secundomer.start();
		Thread.sleep(200);
		secundomer.stop();

		assertNotNull(secundomer.toString());
		System.out.println(secundomer.toString());
	}

}
