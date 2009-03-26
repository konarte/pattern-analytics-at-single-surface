package edu.mgupi.pass.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesCriteria;
import edu.mgupi.pass.db.defects.DefectClassesFactory;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.DefectTypesFactory;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.util.Utils;

// Well, chained transaction is not good idea :)
public class TransactionsTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		this.clearDB();
	}

	private void clearDB() throws PersistentException {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();

		DefectTypesCriteria tc = new DefectTypesCriteria();
		tc.name.ilike("TEST-%");
		for (DefectTypes t : DefectTypesFactory.listDefectTypesByCriteria(tc)) {
			t.delete();
		}

		DefectClassesCriteria cc = new DefectClassesCriteria();
		cc.name.ilike("TEST-%");
		for (DefectClasses c : DefectClassesFactory.listDefectClassesByCriteria(cc)) {
			c.delete();
		}

		transaction.commit();
		PassPersistentManager.instance().disposePersistentManager();
	}

	@Test
	public void testSampleTwoTransactions() throws Exception {
		System.out.println("Begin first transaction");
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();

		System.out.println("Begin second transaction");
		Session session = PassPersistentManager.instance().getSession().getSessionFactory().openSession();
		Transaction transaction2 = PassPersistentManager.instance().getSession().getSessionFactory().openSession()
				.beginTransaction();
		try {

			System.out.println("transaction = " + transaction);
			System.out.println("transaction2 = " + transaction2);
			System.out.println(transaction == transaction2);

			assertFalse(transaction == transaction2);

			assertFalse(transaction.wasCommitted());
			assertFalse(transaction.wasRolledBack());

			transaction2.rollback();
			assertTrue(transaction2.wasRolledBack());
			assertTrue(session.isOpen());
		} finally {
			session.close();
			assertFalse(session.isOpen());
		}

		assertFalse(transaction.wasCommitted());
		assertFalse(transaction.wasRolledBack());

		transaction.commit();
		assertTrue(transaction.wasCommitted());
	}

	@Test
	public void testChainedTransactions() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();

		//		Session session = PassPersistentManager.instance().getSession().getSessionFactory().openSession();
		//		Transaction transaction2 = PassPersistentManager.instance().getSession().getSessionFactory().openSession()
		//				.beginTransaction();

		try {

			System.out.println("Creating defect type 1...");

			DefectTypes defectType = DefectTypesFactory.createDefectTypes();
			defectType.setName("TEST-Мой тип");
			defectType.setDefectImage(Utils.loadFromResource("resources/splash/slow.gif"));

			System.out.println("Creating defect class 1...");
			DefectClasses defectClass = DefectClassesFactory.createDefectClasses();
			assertEquals(0, defectClass.getIdDefectClass());
			defectClass.setName("TEST-Новый отличный дефект");
			defectClass.save();
			//			session.save(defectClass);
			assertFalse(0 == defectClass.getIdDefectClass());

			System.out.println("Creating defect class 2...");
			DefectClasses defectClass2 = DefectClassesFactory.createDefectClasses();
			defectClass2.setName("TEST-Еще один прекрасный дефект");
			defectClass2.save();
			//session.save(defectClass2);
			assertFalse(0 == defectClass2.getIdDefectClass());
			assertFalse(defectClass.getIdDefectClass() == defectClass2.getIdDefectClass());

			//System.out.println("Commit classes...");
			//transaction2.commit();
			//assertTrue(transaction2.wasCommitted());

			defectType.setDefectClass(defectClass);
			System.out.println("Saving type 1...");
			defectType.save();

			System.out.println("Creating defect type 2...");
			DefectTypes defectType2 = DefectTypesFactory.createDefectTypes();
			defectType2.setName("TEST-Мой второй тип дефекта");
			defectType2.setDefectImage(Utils.loadFromResource("resources/splash/cool.jpg"));

			System.out.println("Creating defect class 3 auto...");
			DefectClasses defectClass3 = DefectClassesFactory.createDefectClasses();
			defectClass3.setName("TEST-Третий класс");
			defectType2.setDefectClass(defectClass3);

			System.out.println("Saving type 2...");
			defectType2.save();

			System.out.println("Commit types...");
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//transaction2.rollback();
			transaction.rollback();
			//		} finally {
			//
			//			//session.close();
			//
			//			transaction = PassPersistentManager.instance().getSession().beginTransaction();
			//
			//			DefectTypesCriteria tc = new DefectTypesCriteria();
			//			tc.name.ilike("TEST-%");
			//			for (DefectTypes t : DefectTypesFactory.listDefectTypesByCriteria(tc)) {
			//				t.delete();
			//			}
			//
			//			DefectClassesCriteria cc = new DefectClassesCriteria();
			//			cc.name.ilike("TEST-%");
			//			for (DefectClasses c : DefectClassesFactory.listDefectClassesByCriteria(cc)) {
			//				c.delete();
			//			}
			//
			//			transaction.commit();

		}

	}

	@Test
	public void testBigWork() throws Exception {
		PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
		
		System.out.println("BIGWORK");

		for (int i = 0; i < 100; i++) {
			DefectClasses defectClass = DefectClassesFactory.createDefectClasses();
			defectClass.setName("TEST-sample-x" + i);
			defectClass.save();
			
			System.out.println("Saved " + i);
			Thread.sleep(10);
			
		}

		transaction.commit();
	}
}
