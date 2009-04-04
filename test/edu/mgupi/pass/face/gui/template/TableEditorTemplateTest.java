package edu.mgupi.pass.face.gui.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.hibernate.Criteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;

import com.mysql.jdbc.Connection;

import edu.mgupi.pass.db.defects.DefectClasses;
import edu.mgupi.pass.db.defects.DefectClassesCriteria;
import edu.mgupi.pass.db.defects.DefectClassesFactory;
import edu.mgupi.pass.db.defects.DefectTypes;
import edu.mgupi.pass.db.defects.DefectTypesCriteria;
import edu.mgupi.pass.db.defects.DefectTypesFactory;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.face.gui.AppHelper;
import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.face.gui.WaitCondition;
import edu.mgupi.pass.face.gui.WorkSet;
import edu.mgupi.pass.face.gui.template.TableEditorTemplateTest.TestTableEditorTemplate.MyTableModel;
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.Config.DeletionCheckMode;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.TestTransactionMode;

public class TableEditorTemplateTest {

	private JFrame parentFrame = null;
	private TestTableEditorTemplate tableEditor = null;

	private final static String MY_SAMPLE_NAME = "my-sample-name";

	@Before
	public void setUp() throws Exception {

		tableEditor = (TestTableEditorTemplate) AppHelper.getInstance().getDialogImpl(
				null,TestTableEditorTemplate.class);

		parentFrame = new JFrame();
		JButton button = new JButton();
		button.setAction(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				returnValue = false;
				returnValue = tableEditor.showWindow();

			}
		});
		button.setName(MY_SAMPLE_NAME);
		parentFrame.add(button);

		Config.getInstance().setDebugVirualMode();
		removeAllData();

	}

	@After
	public void tearDown() throws Exception {

		/*
		 * Reset
		 */
		SwingTestHelper.closeAllWindows();
		AppHelper.reset();
		removeAllData();
	}

	private Connection getConnection() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Properties properties = new Properties();
		properties.put("user", "pass");
		properties.put("password", "adesroot");

		Connection conn = (Connection) DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/pass_db", properties);
		return conn;
	}

	private void removeAllData() throws PersistentException {

		PassPersistentManager.instance().getSession().close();
		try {
			Connection conn = this.getConnection();
			try {
				conn.setAutoCommit(false);

				Statement st = conn.createStatement();
				st.executeUpdate("delete from DefectTypes where name like 'TEST-%'");
				st.executeUpdate("delete from DefectClasses where name like 'TEST-%'");

				conn.commit();
			} finally {
				conn.close();
			}

		} catch (Exception e) {

			PersistentTransaction transaction = PassPersistentManager.instance().getSession()
					.beginTransaction();

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
		} finally {
			PassPersistentManager.instance().disposePersistentManager();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWorkEveryRowCancel() throws Exception {
		Config.getInstance().setTransactionMode(TestTransactionMode.COMMIT_EVERY_ROW);

		assertNotNull(tableEditor);

		assertFalse(tableEditor.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		assertTrue(tableEditor.isVisible());

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		RecordEditorTemplate editForm = (RecordEditorTemplate) SwingTestHelper
				.clickOpenDialogButton(tableEditor, null, "add", TestRecordEditorTemplate.class);
		assertNotNull(editForm);

		JTextField text = (JTextField) Utils.getChildNamed(editForm, "className");
		assertNotNull(text);

		assertEquals("", text.getText());
		text.setText("TEST-временный");

		SwingTestHelper.clickCloseDialogButton(editForm, "cancel");

		assertEquals(0, table.getRowCount());
	}

	private void openWindowAndSave(int rowIndex, String value) throws Exception {
		this.openWindowAndSave(rowIndex, value, null, null);
	}

	@SuppressWarnings("unchecked")
	private void openWindowAndSave(final int rowIndex, String value,
			final String expectedMessageButton, final String expectedSecondCloseButton)
			throws Exception {

		/*
		 * if rowIndex == -1 -- this is add new row, othersiwe -- it is edit
		 */
		if (rowIndex != -1) {
			JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
			assertNotNull(table);
			table.setRowSelectionInterval(rowIndex, rowIndex);
			//table.setEditingRow(rowIndex);
		}

		/*
		 * Do click
		 */
		final RecordEditorTemplate editForm = (RecordEditorTemplate) SwingTestHelper
				.clickOpenDialogButton(tableEditor, null, rowIndex == -1 ? "add" : "edit",
						TestRecordEditorTemplate.class);
		assertNotNull(editForm);

		JTextField text = (JTextField) Utils.getChildNamed(editForm, "className");
		assertNotNull(text);

		text.setText(value);
		text.postActionEvent();

		final JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);

		final int rowcount = table.getModel().getRowCount();

		/*
		 * Work for successfully clicked.
		 */
		SwingTestHelper.clickCloseDialogButton(editForm, "OK", expectedMessageButton != null);

		if (expectedMessageButton != null) {
			/*
			 * Time to clicked for expected button
			 */
			Window window = SwingTestHelper.searchAnyOpenedWindow(editForm);
			assertNotNull(window);
			SwingTestHelper.clickCloseDialogButton(window, expectedMessageButton);
		}

		if (expectedSecondCloseButton != null) {
			/*
			 * Time to click for actually edit form
			 */
			assertTrue(editForm.isVisible());
			SwingTestHelper.clickCloseDialogButton(editForm, expectedSecondCloseButton);
		}

		/*
		 * Wait while rows successfully added
		 */
		SwingTestHelper.waitWhile(new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return rowIndex == -1 && expectedSecondCloseButton == null ? table.getModel()
						.getRowCount() == rowcount : false;
			}
		});
	}

	private final static int MAX_ROWS = 4;

	private List<Integer> createTestData(JTable table) throws Exception {

		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < MAX_ROWS; i++) {
			String text = "TEST-очередной класс-" + i;

			openWindowAndSave(-1, text);

			assertEquals(i + 1, table.getRowCount());

			int index = (Integer) table.getModel().getValueAt(i, 0);
			id.add(index);

			assertFalse(0 == index);
			assertEquals(text, table.getModel().getValueAt(i, 1));
		}

		return id;
	}

	private void checkTestData(JTable table, PreparedStatement statement, TestTransactionMode mode,
			Collection<Integer> idList, boolean checkInterfaceTable) throws SQLException {
		int idx = 0;

		for (Integer id : idList) {

			String text = "TEST-очередной класс-" + idx;

			int index = id;

			System.out.println("Checking index row " + index);

			boolean cycled = false;

			// Two jobs for price one ;)
			do {

				statement.setInt(1, index);
				ResultSet rs = statement.executeQuery();

				// for COMMIT_EVERY_ROW all data must stay
				// for COMMIT_BULK -- it is disappear
				// We use mode = null only once -- when process 'save' event
				if (mode == null || mode == TestTransactionMode.COMMIT_EVERY_ROW) {
					if (rs.next()) {
						assertEquals(index, rs.getInt(1));
						assertEquals(text, rs.getString(2));
					} else {

						Statement st = statement.getConnection().createStatement();
						rs = st.executeQuery("select idDefectClass from defectclasses");
						while (rs.next()) {
							System.out.println("NXT = '" + rs.getInt(1) + "'.");
						}
						rs.close();
						st.close();

						fail("Unable to get next by statement " + statement);
					}
				} else {
					assertFalse(rs.next());
				}

				rs.close();

				if (checkInterfaceTable && !cycled) {
					index = (Integer) table.getModel().getValueAt(idx, 0);
					System.out.println("Checking index from table " + index);
					cycled = true;
				} else {
					cycled = false;
				}
			} while (cycled);

			idx++;
		}
	}

	private boolean returnValue = false;

	public void testChangeData(TestTransactionMode mode) throws Exception {
		Config.getInstance().setTransactionMode(mode);
		assertNotNull(tableEditor);

		assertFalse(tableEditor.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		assertTrue(tableEditor.isVisible());

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		openWindowAndSave(-1, "TEST-мой первый текстовый класс");
		assertEquals(1, table.getRowCount());

		assertFalse(Utils.equals("0", table.getModel().getValueAt(0, 0)));
		assertEquals("TEST-мой первый текстовый класс", table.getModel().getValueAt(0, 1));

		openWindowAndSave(0, "TEST-Отредактированный класс()");
		assertEquals(1, table.getRowCount());
		assertEquals("TEST-Отредактированный класс()", table.getModel().getValueAt(0, 1));

		SwingTestHelper.clickCloseDialogButton(tableEditor, "cancel");
		removeAllData();

		/*
		 * 
		 * 
		 * 
		 * Reopen and try to click 'OK'
		 */
		assertFalse(tableEditor.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		assertTrue(tableEditor.isVisible());

		Connection conn = this.getConnection();
		conn.setAutoCommit(true);
		PreparedStatement statement = conn
				.prepareStatement("select idDefectClass, name from DefectClasses where idDefectClass = ?");

		/*
		 * Fill data from database
		 */
		List<Integer> id = createTestData(table);

		// Check current data
		checkTestData(table, statement, mode, id, true);
		assertEquals(MAX_ROWS, table.getRowCount());

		/*
		 * Do cancel
		 * 
		 * for COMMIT_EVERY_ROW all data must stay; for COMMIT_BULK -- it is
		 * disappear
		 */
		SwingTestHelper.clickCloseDialogButton(tableEditor, "cancel");
		assertFalse(returnValue);

		/*
		 * Well, check data
		 */
		checkTestData(table, statement, mode, id, false);

		removeAllData();

		/*
		 * 
		 * 
		 * Reopen and try to click 'OK'
		 */

		if (mode == TestTransactionMode.COMMIT_EVERY_ROW) {
			assertFalse(tableEditor.isVisible());
			SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
					TestTableEditorTemplate.class);
			assertTrue(tableEditor.isVisible());

			id = createTestData(table);

			/*
			 * This is check for properly saved data.
			 */
			Random rand = new Random();
			for (int i = 0; i < id.size() / 2; i++) {
				int row = rand.nextInt(id.size() - 1);
				String text = "TEST-очередной класс-" + rand.nextInt();

				System.out.println("Updaing record " + text + " ? row " + row);
				openWindowAndSave(row, text);

				int index = id.get(row);
				statement.setInt(1, index);
				ResultSet rs = statement.executeQuery();

				/*
				 * for COMMIT_EVERY_ROW all data must stay, for COMMIT_BULK --
				 * it is disappear.
				 * 
				 * We use 'mode = null' only once -- when process 'save' event
				 */
				if (rs.next()) {
					assertEquals(index, rs.getInt(1));
					assertEquals(text, rs.getString(2));
				}

			}
		}

		if (mode == TestTransactionMode.COMMIT_BULK) {
			assertFalse(tableEditor.isVisible());
			SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
					TestTableEditorTemplate.class);
			assertTrue(tableEditor.isVisible());

			id = createTestData(table);

			/*
			 * Check
			 */
			checkTestData(table, statement, mode, id, true);
			assertEquals(MAX_ROWS, table.getRowCount());

			SwingTestHelper.clickCloseDialogButton(tableEditor, "OK");

			/*
			 * Now we must read all values from database!
			 * 
			 * If we have COMMIT_BULK -- all data must be in database just now
			 * 
			 * So use null for mode
			 */
			checkTestData(table, statement, null, id, false);
			assertTrue(returnValue);
		}

		statement.close();
		conn.close();
	}

	@Test
	public void testChangeDataWorkEveryRow() throws Exception {
		this.testChangeData(TestTransactionMode.COMMIT_EVERY_ROW);
	}

	//	@Test
	//	public void testChangeDataWorkBulk() throws Exception {
	//		this.testChangeData(TransactionMode.COMMIT_BULK);
	//	}

	private void deleteRows(String expectedFirstButton, boolean expectDeletedRows, int... rows)
			throws Exception {
		this.deleteRows(expectedFirstButton, null, false, expectDeletedRows, rows);
	}

	private void deleteRows(String expectedFirstMessageButton, String expectedSecondMessageButton,
			final boolean expectOwnerIsSecond, final boolean expectDeletedRows, int... rows)
			throws Exception {
		assertNotNull(rows);
		assertTrue(rows.length > 0);

		final TestTableEditorTemplate tableEditor = (TestTableEditorTemplate) AppHelper
				.getInstance().searchWindow(TestTableEditorTemplate.class);

		assertNotNull(tableEditor);

		final JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);

		table.setRowSelectionInterval(rows[0], rows[rows.length - 1]);

		final int rowcount = table.getModel().getRowCount();

		if (expectedFirstMessageButton != null) {

			/*
			 * If we expected first message button -- wait for it
			 */

			final TestRecordEditorTemplate recordEditor = (TestRecordEditorTemplate) AppHelper
					.getInstance().getDialogImpl(null,TestRecordEditorTemplate.class);

			/*
			 * When use expectOwnerIsSecond -- I means, that owner of message
			 * will be editor form, not the table form! This may happen if row
			 * DeletionCheckMode = CHECK_THEN_ACQUIRE
			 */
			Window window = SwingTestHelper.clickOpenDialogButton(tableEditor,
					expectOwnerIsSecond ? recordEditor : tableEditor, "delete");
			assertNotNull(window);
			SwingTestHelper.clickCloseDialogButton(window, expectedFirstMessageButton);

			if (expectedSecondMessageButton != null) {

				/*
				 * But we can expect second button -- press it, but before we
				 * must wait for its opening :)
				 */
				SwingTestHelper.waitWhile(new WaitCondition() {
					@Override
					public boolean keepWorking() {
						return SwingTestHelper
								.searchAnyOpenedWindow(expectOwnerIsSecond ? tableEditor
										: recordEditor) == null;
					}
				});
				window = SwingTestHelper.searchAnyOpenedWindow(recordEditor);
				assertNotNull(window);
				SwingTestHelper.clickCloseDialogButton(window, expectedSecondMessageButton);
			}

			if (expectDeletedRows) {
				/*
				 * Wait for table refresh
				 */
				SwingTestHelper.waitWhile(new WaitCondition() {
					@Override
					public boolean keepWorking() {
						return table.getModel().getRowCount() == rowcount;
					}
				});
			}
		} else {

			/*
			 * If no buttons expected -- call 'delete' method and wait until row
			 * count of table will decrease.
			 */
			SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					AbstractButton button = (AbstractButton) Utils.getChildNamed(tableEditor,
							"delete");
					assertNotNull(button);
					button.doClick();
				}
			}, new WaitCondition() {
				@Override
				public boolean keepWorking() {
					return expectDeletedRows ? table.getModel().getRowCount() == rowcount : false;
				}
			});
		}

		Thread.sleep(100);
	}

	@Test
	public void testEditData() throws Exception {

		//Config.getInstance().setTransactionMode(TransactionMode.COMMIT_BULK);
		Config.getInstance().setRowsDeleteMode(DeletionMode.NO_CONFIRM);

		assertNotNull(tableEditor);

		assertFalse(tableEditor.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		assertTrue(tableEditor.isVisible());

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		/*
		 * Create test data
		 */

		openWindowAndSave(-1, "TEST-редактируемый0");// 0 -- 0 -- 0 -- (3)
		openWindowAndSave(-1, "TEST-редактируемый1");// 1 -- (1)
		openWindowAndSave(-1, "TEST-редактируемый2");// 2 -- 1 -- 1 -- 0 -- (4)
		openWindowAndSave(-1, "TEST-редактируемый3");// 3 -- 2 -- 2 -- 1 -- (4)

		assertEquals(4, table.getRowCount());

		/*
		 * Step 1
		 */
		deleteRows(null, true, 1);

		assertEquals(3, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(1, 1));

		openWindowAndSave(-1, "TEST-редактируемый4");//  -- 3 -- 3 -- 2 -- 0 -- (5)
		openWindowAndSave(-1, "TEST-редактируемый5");//  -- 4 -- 4 -- 3 -- 1 -- 0 -- (6)
		openWindowAndSave(-1, "TEST-редактируемый6");//  -- 5 -- 5 -- 4 -- 2 -- 1 -- (6)
		openWindowAndSave(-1, "TEST-редактируемый7");//  -- 6 -- (2)
		openWindowAndSave(-1, "TEST-редактируемый8");//  -- 7 -- (2)
		openWindowAndSave(-1, "TEST-редактируемый9");//  -- 8 -- (2)

		/*
		 * Step 2
		 */
		deleteRows(null, true, 6, 7, 8);

		assertEquals(6, table.getRowCount());
		assertEquals("TEST-редактируемый6", table.getModel().getValueAt(5, 1));

		Config.getInstance().setRowsDeleteMode(DeletionMode.CONFIRM);

		/*
		 * Step 3
		 */
		deleteRows("Yes|Да", true, 0);
		assertEquals(5, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(0, 1));

		deleteRows("No|Нет", false, 0);
		assertEquals(5, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(0, 1));

		/*
		 * Step 4
		 */
		deleteRows("Yes|Да", true, 0, 1);
		assertEquals(3, table.getRowCount());
		assertEquals("TEST-редактируемый4", table.getModel().getValueAt(0, 1));
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(1, 1));
		assertEquals("TEST-редактируемый6", table.getModel().getValueAt(2, 1));

		Config.getInstance().setRowsDeleteMode(DeletionMode.CONFIRM_MULTPLES);

		/*
		 * Step 5
		 */
		deleteRows(null, true, 0);
		assertEquals(2, table.getRowCount());
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(0, 1));

		deleteRows("No|Нет", false, 0, 1);
		assertEquals(2, table.getRowCount());
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(0, 1));

		/*
		 * Step 6
		 */
		deleteRows("Yes|Да", true, 0, 1);
		assertEquals(0, table.getRowCount());

		SwingTestHelper.clickCloseDialogButton(tableEditor, "cancel");

	}

	public void testCheckBeforeAsk(TestTransactionMode mode) throws Exception {
		Config.getInstance().setTransactionMode(mode);
		Config.getInstance().setRowsDeleteMode(DeletionMode.CONFIRM);

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);

		/*
		 * Creating classes
		 */

		openWindowAndSave(-1, "TEST-редактируемый-0");
		openWindowAndSave(-1, "TEST-редактируемый-1");//-
		openWindowAndSave(-1, "TEST-редактируемый-2");//-
		openWindowAndSave(-1, "TEST-редактируемый-3");

		PersistentTransaction trans = null;
		if (mode == TestTransactionMode.COMMIT_EVERY_ROW) {
			trans = PassPersistentManager.instance().getSession().beginTransaction();
		}
		assertTrue(PassPersistentManager.instance().getSession().getTransaction().isActive());

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);

		/*
		 * Creating types
		 */

		DefectTypes type = DefectTypesFactory.createDefectTypes();
		type.setName("TEST-ссылка-1-0");
		type.setDefectClass(((MyTableModel) table.getModel()).getRowAt(0));
		type.save();

		type = DefectTypesFactory.createDefectTypes();
		type.setName("TEST-ссылка-2-0");
		type.setDefectClass(((MyTableModel) table.getModel()).getRowAt(0));
		type.save();

		DefectTypes prevType = DefectTypesFactory.createDefectTypes();
		prevType.setName("TEST-ссылка-1-1");
		prevType.setDefectClass(((MyTableModel) table.getModel()).getRowAt(1));
		prevType.save();

		DefectTypes lastType = DefectTypesFactory.createDefectTypes();
		lastType.setName("TEST-ссылка-1-2");
		lastType.setDefectClass(((MyTableModel) table.getModel()).getRowAt(2));
		lastType.save();

		assertTrue(PassPersistentManager.instance().getSession().getTransaction().isActive());

		SwingTestHelper.clickCloseDialogButton(tableEditor,
				mode == TestTransactionMode.COMMIT_BULK ? "OK" : "cancel");
		if (trans != null) {

			/*
			 * Do not forget about commit for bulk processing
			 */
			trans.commit();
			trans = PassPersistentManager.instance().getSession().beginTransaction();
		}

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);

		/*
		 * Check for can't delete
		 */
		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.ACQUIRE_THEN_CHECK);
		deleteRows("Yes|Да", "OK", false, false, 0);
		assertEquals(4, table.getRowCount());

		deleteRows("No|Нет", null, false, false, 0);
		assertEquals(4, table.getRowCount());

		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.CHECK_THEN_ACQUIRE);
		deleteRows("OK", null, true, false, 0);
		assertEquals(4, table.getRowCount());

		/*
		 * Removing referenced types
		 */
		lastType.delete();
		prevType.delete();
		if (trans != null) {
			trans.commit();
			trans = PassPersistentManager.instance().getSession().beginTransaction();
		}

		/*
		 * Successfully removing
		 */
		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.ACQUIRE_THEN_CHECK);
		deleteRows("Yes|Да", null, false, true, 1);
		assertEquals(3, table.getRowCount());

		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.CHECK_THEN_ACQUIRE);
		deleteRows("Yes|Да", null, false, true, 1);
		assertEquals(2, table.getRowCount());

		/*
		 * Check for can't delete again
		 */
		deleteRows("OK", null, true, false, 0, 1);
		assertEquals(2, table.getRowCount());

		/*
		 * Reopening interface We have 2 rows
		 */
		SwingTestHelper.clickCloseDialogButton(tableEditor,
				mode == TestTransactionMode.COMMIT_BULK ? "OK" : "cancel");

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);

		/*
		 * Check for records existing. This row is really exists :)
		 */
		openWindowAndSave(-1, "TEST-редактируемый-3", "OK", "cancel");

		/*
		 * Check for multiple rows deletion
		 */
	}

	//	@Test
	//	public void testCheckBeforeAskBulk() throws Exception {
	//		this.testCheckBeforeAsk(TransactionMode.COMMIT_BULK);
	//	}

	@Test
	public void testCheckBeforeAskEveryRow() throws Exception {
		this.testCheckBeforeAsk(TestTransactionMode.COMMIT_EVERY_ROW);
	}

	public void testForErrors(TestTransactionMode mode) throws Exception {
		Config.getInstance().setTransactionMode(mode);
		Config.getInstance().setRowsDeleteMode(DeletionMode.NO_CONFIRM);
		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.NO_CHECK);

		TestRecordEditorTemplate template = (TestRecordEditorTemplate) AppHelper.getInstance()
				.getDialogImpl(null,TestRecordEditorTemplate.class);
		template.setUseCriterias(false);

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);

		/*
		 * Creating classes
		 */

		openWindowAndSave(-1, "TEST-редактируемый-0-0");
		openWindowAndSave(-1, "TEST-редактируемый-0-1");
		openWindowAndSave(-1, "TEST-редактируемый-0-2");
		openWindowAndSave(-1, "TEST-редактируемый-0-3");

		PersistentTransaction trans = null;
		if (mode == TestTransactionMode.COMMIT_EVERY_ROW) {
			trans = PassPersistentManager.instance().getSession().beginTransaction();
		}
		assertTrue(PassPersistentManager.instance().getSession().getTransaction().isActive());

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);

		DefectTypes type = DefectTypesFactory.createDefectTypes();
		type.setName("TEST-ссылка-1-0");
		type.setDefectClass(((MyTableModel) table.getModel()).getRowAt(0));
		type.save();

		type = DefectTypesFactory.createDefectTypes();
		type.setName("TEST-ссылка-2-0");
		type.setDefectClass(((MyTableModel) table.getModel()).getRowAt(0));
		type.save();

		DefectTypes prevType = DefectTypesFactory.createDefectTypes();
		prevType.setName("TEST-ссылка-1-1");
		prevType.setDefectClass(((MyTableModel) table.getModel()).getRowAt(1));
		prevType.save();

		DefectTypes lastType = DefectTypesFactory.createDefectTypes();
		lastType.setName("TEST-ссылка-1-2");
		lastType.setDefectClass(((MyTableModel) table.getModel()).getRowAt(2));
		lastType.save();

		assertTrue(PassPersistentManager.instance().getSession().getTransaction().isActive());

		SwingTestHelper.clickCloseDialogButton(tableEditor,
				mode == TestTransactionMode.COMMIT_BULK ? "OK" : "cancel");
		if (trans != null) {

			/*
			 * Do not forget about commit for bulk processing
			 */
			trans.commit();
			trans = PassPersistentManager.instance().getSession().beginTransaction();
		}

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);

		/*
		 * Step 1.
		 * 
		 * Check for errors
		 */
		openWindowAndSave(-1, "TEST-редактируемый-0-3", "OK", "cancel");

		/*
		 * Step 2.
		 * 
		 * successfully added and modified
		 */
		openWindowAndSave(-1, "TEST-редактируемый-1-0");
		assertEquals("TEST-редактируемый-1-0", table.getModel().getValueAt(4, 1));

		openWindowAndSave(1, "TEST-редактируемый-1-1");

		assertEquals("TEST-редактируемый-1-1", table.getModel().getValueAt(1, 1));

		//Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.NO_CHECK);
		//template.setUseCriterias(false);
		/*
		 * Step 3.
		 * 
		 * Check for errors
		 */
		openWindowAndSave(-1, "TEST-редактируемый-1-1", "OK", "cancel");

		/*
		 * Step 4.
		 * 
		 * Do delete with errors.
		 */
		this.deleteRows("OK", false, 0);

		this.deleteRows("OK", false, 0, 1, 2);

		assertEquals(5, table.getModel().getRowCount());

		this.deleteRows(null, true, 4);
		assertEquals(4, table.getModel().getRowCount());

		/*
		 * Step 5.
		 * 
		 * Reopen after exceptions.
		 */
		openWindowAndSave(1, "TEST-редактируемый-2-1");
		openWindowAndSave(1, "TEST-редактируемый-2-1");

		assertEquals("TEST-редактируемый-2-1", table.getModel().getValueAt(1, 1));

		this.deleteRows("OK", false, 0, 1, 2);

		openWindowAndSave(-1, "TEST-редактируемый-2-2");
		assertEquals("TEST-редактируемый-2-2", table.getModel().getValueAt(4, 1));
		assertEquals(5, table.getModel().getRowCount());

		/*
		 * Step 6 again, but in new window
		 */

		SwingTestHelper.clickCloseDialogButton(tableEditor,
				mode == TestTransactionMode.COMMIT_BULK ? "OK" : "cancel");

		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		//		Thread.sleep(5000);
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			System.out.println(" " + i + " = " + table.getModel().getValueAt(i, 0) + ", "
					+ table.getModel().getValueAt(i, 1));
		}

		/*
		 * Step 6.4.
		 * 
		 * Do delete with errors.
		 */
		this.deleteRows("OK", false, 0);

		this.deleteRows("OK", false, 0, 1, 2);

		assertEquals("TEST-редактируемый-2-2", table.getModel().getValueAt(4, 1));
		this.deleteRows(null, true, 4);
		assertEquals(4, table.getModel().getRowCount());

		/*
		 * Step 6.5.
		 * 
		 * Reopen after exceptions.
		 */
		openWindowAndSave(1, "TEST-редактируемый-3-1");

		assertEquals("TEST-редактируемый-3-1", table.getModel().getValueAt(1, 1));

		this.deleteRows("OK", false, 0, 1, 2);

		openWindowAndSave(-1, "TEST-редактируемый-3-2");
		assertEquals(5, table.getModel().getRowCount());
		assertEquals("TEST-редактируемый-3-2", table.getModel().getValueAt(4, 1));

		SecundomerList.printToOutput(System.out);
	}

	@Test
	public void testForErrorsEveryRow() throws Exception {
		this.testForErrors(TestTransactionMode.COMMIT_EVERY_ROW);
	}

	@Test
	public void testHugeAmountOfData() throws Exception {

		Config.getInstance().setRowsDeleteMode(DeletionMode.NO_CONFIRM);
		Config.getInstance().setDeletionCheckModeMode(DeletionCheckMode.CHECK_THEN_ACQUIRE);

		JTable table = (JTable) Utils.getChildNamed(tableEditor, "data");
		assertNotNull(table);

		Connection conn = this.getConnection();
		conn.setAutoCommit(false);

		PreparedStatement psClasses = conn
				.prepareStatement("insert into DefectClasses (IDDefectClass, Name) values (?,?)");
		PreparedStatement psTypes = conn
				.prepareStatement("insert into DefectTypes (IdDefectType, Name, DefectImage, "
						+ "DefectClassesIdDefectClass) values (?, ?, ?, ?)");

		/*
		 * Loading data into database
		 */
		int LOWIDX = 1000000;
		int ROWCOUNT = 10000;
		for (int i = 0; i < ROWCOUNT; i++) {
			int idx = LOWIDX + i;
			psClasses.setInt(1, idx);
			psClasses.setString(2, "TEST-супер-класс-" + idx);
			psClasses.addBatch();
		}
		psClasses.executeBatch();

		System.out.println("Classes inserted...");

		Random rand = new Random();
		for (int i = 0; i < ROWCOUNT * 2; i++) {
			int idx = LOWIDX + rand.nextInt(ROWCOUNT);
			psTypes.setInt(1, LOWIDX + i);
			psTypes.setString(2, "TEST-супер-тип-" + (LOWIDX + i) + "-для-супер-класса-" + idx);
			psTypes.setNull(3, Types.BLOB);
			psTypes.setInt(4, idx);
			psTypes.addBatch();
		}
		psTypes.executeBatch();

		System.out.println("Types inserted...");

		conn.commit();

		/*
		 * Open window
		 */
		SwingTestHelper.clickOpenDialogButton(parentFrame, null, MY_SAMPLE_NAME,
				TestTableEditorTemplate.class);
		//SwingTestHelper.waitMe(AppHelper.getInstance().searchWindow(TestTableEditorTemplate.class));

		int RANDOM_EDIT = 10;

		/*
		 * Random edition
		 */
		int changedIdx[] = new int[RANDOM_EDIT];
		
		// Emergency wait until properly repaint and sort...
		Thread.sleep(500);

		for (int i = 0; i < RANDOM_EDIT; i++) {
			int pos = rand.nextInt(ROWCOUNT);
			changedIdx[i] = pos;
			assertEquals(LOWIDX + pos, table.getModel().getValueAt(pos, 0));
			this.openWindowAndSave(pos, "TEST-супер-волшебные-значения-" + pos);
			assertEquals("TEST-супер-волшебные-значения-" + pos, table.getModel()
					.getValueAt(pos, 1));
		}
		//		conn.close();
		//		conn = this.getConnection();
		//		conn.setAutoCommit(false);

		PreparedStatement psSel = conn
				.prepareStatement("select Name from DefectClasses where IdDefectClass = ?");
		for (int i = 0; i < changedIdx.length; i++) {
			int pos = changedIdx[i];
			psSel.setInt(1, LOWIDX + pos);
			String str = "TEST-супер-волшебные-значения-" + pos;
			assertEquals(str, table.getModel().getValueAt(pos, 1));
			assertEquals(LOWIDX + pos, table.getModel().getValueAt(pos, 0));

			ResultSet rs = psSel.executeQuery();
			if (rs.next()) {
				String value = rs.getString(1);
				assertEquals(str, value);
			}
			rs.close();

		}
		//
		//		/*
		//		 * Generate deletion rows
		//		 */
		//		int CHECKTYPES = 10;
		//		ResultSet rs = conn.createStatement().executeQuery(
		//				"select IdDefectClass from DefectClasses where Name like 'TEST-%' and IdDefectClass not in ("
		//						+ "select DefectClassesIdDefectClass from DefectTypes) limit 200, 210");
		//
		//		int rowPos[] = new int[CHECKTYPES];
		//		int rowIdx[] = new int[CHECKTYPES];
		//		for (int i = 0; i < CHECKTYPES; i++) {
		//			rs.next();
		//			rowIdx[i] = rs.getInt(1);
		//			rowPos[i] = rowIdx[i] - LOWIDX;
		//
		//		}
		//
		//		System.out.println("Found rows: " + Arrays.toString(rowPos));
		//		for (int i = 0; i < rowPos.length; i++) {
		//			int pos = rowPos[i];
		//			System.out.println("Try to delete row: " + pos);
		//			assertEquals(rowIdx[i], table.getModel().getValueAt(pos, 0));
		//			deleteRows(null, true, pos);
		//			for (int j = i + 1; j < rowIdx.length; j++) {
		//				if (rowPos[j] >= rowPos[i]) {
		//					rowPos[j]--;
		//				}
		//			}
		//		}
		//		int rowCount = table.getModel().getRowCount();
		//		assertEquals(ROWCOUNT - rowPos.length, rowCount);
		//
		//		conn.close();
		//		conn = this.getConnection();
		//		conn.setAutoCommit(false);
		//
		//		rs = conn.createStatement().executeQuery(
		//				"select IdDefectClass from DefectClasses where Name like 'TEST-%' and IdDefectClass not in ("
		//						+ "select DefectClassesIdDefectClass from DefectTypes)");
		//		rowPos = new int[200];
		//		rowIdx = new int[200];
		//		for (int i = 0; i < 200; i++) {
		//			rs.next();
		//			rowIdx[i] = rs.getInt(1);
		//			rowPos[i] = -1;
		//			for (int x = 0; x < table.getModel().getRowCount(); x++) {
		//				if (table.getModel().getValueAt(x, 0).equals(rowIdx[i])) {
		//					rowPos[i] = x;
		//					break;
		//				}
		//			}
		//			if (rowPos[i] == -1) {
		//				fail("Unable to found pos for object " + rowIdx[i]);
		//			}
		//		}
		//		deleteRows(null, true, rowPos);
		//		assertEquals(rowCount - rowPos.length, table.getModel().getRowCount());

		conn.close();
		SecundomerList.printToOutput(System.out);

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Test sample of table editor
	 * 
	 * 
	 * 
	 * @author raidan
	 * 
	 */
	public static class TestTableEditorTemplate extends TableViewerTemplate<DefectClasses> {

		public TestTableEditorTemplate(Frame owner) {
			super(owner, "test", "Тест");
			inited = false;
		}

		public boolean isInited() {
			return inited;
		}

		private boolean inited = false;

		@Override
		protected void tablePostInit(JTable owner) {

			//owner.setAutoCreateRowSorter(true);
			assertNotNull(owner);

			assertFalse(inited);
			inited = true;
			System.out.println("Table post init impl. " + this.hashCode() + " inited = " + inited);

			AbstractEditorTableModel model = (AbstractEditorTableModel) owner.getModel();
			model.setHorizontalAlignMode(0, JLabel.CENTER);
			model.setColumnWidth(1, 200);

		}

		/**
		 * 
		 */

		static class MyTableModel extends CommonEditorTableModel<DefectClasses> {

			public MyTableModel(JTable owner) {
				super(owner, TestRecordEditorTemplate.class);
			}

			@Override
			protected DefectClasses createInstanceImpl() {
				return DefectClassesFactory.createDefectClasses();
			}

			@Override
			protected String[] getColumns() {
				return new String[] { "ID", "Класс дефекта" };
			}

			private final static Secundomer DATA_SEC = SecundomerList
					.registerSecundomer("Load data from database");

			@SuppressWarnings("unchecked")
			@Override
			protected List<DefectClasses> getDataImpl() throws Exception {

				System.out.println("LOADING CURRENT DEFECTS...");

				DATA_SEC.start();
				try {
					DefectClassesCriteria criteria = new DefectClassesCriteria();
					criteria.name.like("TEST-%");
					List<DefectClasses> classes = new ArrayList<DefectClasses>();
					classes.addAll(criteria.list());
					return classes;
				} finally {
					DATA_SEC.stop();
				}
			}

			private final static Secundomer DATA_RET = SecundomerList
					.registerSecundomer("Get value for column");

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				DATA_RET.start();
				try {
					DefectClasses defect = super.getRowAt(rowIndex);

					return columnIndex == 0 ? defect.getIdDefectClass() : defect.getName();
				} finally {
					DATA_RET.stop();
				}
			}

		}

		private MyTableModel tableModel = null;

		@Override
		protected CommonEditorTableModel<DefectClasses> getTableModelImpl(JTable owner) {
			if (tableModel == null) {
				tableModel = new MyTableModel(owner);
			}
			return tableModel;
		}

	}

	/**
	 * 
	 * 
	 * Test sample of record editor
	 * 
	 * 
	 * 
	 * @author raidan
	 * 
	 */

	public static class TestRecordEditorTemplate extends RecordEditorTemplate<DefectClasses> {

		/**
		 * Default constructor.
		 * 
		 * @param owner
		 * 
		 */
		public TestRecordEditorTemplate(Frame owner) {
			super(owner, "tst", "Тест");
			super.setFormPanel(getJPanel());
		}

		private JPanel rootPanel = null;
		private JTextField field = null;

		protected JPanel getJPanel() {
			if (rootPanel == null) {

				rootPanel = new JPanel();
				rootPanel.setLayout(new GridBagLayout());

				field = new JTextField();
				field.setName("className");

				super.putUniqueComponentPair(rootPanel, "Название класса", field);

				System.out.println("return " + rootPanel);

			}
			return rootPanel;
		}

		@Override
		protected String getDenyDeletionMessage(Object foundObject) {
			DefectTypes type = (DefectTypes) foundObject;
			return "Тип дефекта '" + type.getName() + "' использует класс '"
					+ type.getDefectClass().getName() + "'.";
		}

		private boolean useCriterias = true;

		public void setUseCriterias(boolean useCriterias) {
			this.useCriterias = useCriterias;
		}

		@Override
		protected Criteria getMultipleDeleteCriteria(Collection<DefectClasses> objects)
				throws Exception {
			if (!useCriterias) {
				return null;
			}
			DefectTypesCriteria criteria = new DefectTypesCriteria();
			int[] idx = new int[objects.size()];
			int index = 0;
			for (DefectClasses def : objects) {
				idx[index++] = def.getIdDefectClass();
			}
			criteria.createDefectClassCriteria().idDefectClass.in(idx);
			return criteria;
		}

		@Override
		protected Criteria getUniqueCheckCriteria(DefectClasses object, String newValue)
				throws Exception {
			if (!useCriterias) {
				return null;
			}
			DefectClassesCriteria criteria = new DefectClassesCriteria();
			criteria.name.eq(newValue);
			if (object.getIdDefectClass() != 0) {
				criteria.idDefectClass.eq(object.getIdDefectClass());
			}
			return criteria;
		}

		private String lastName = null;

		@Override
		protected boolean loadFormFromObjectImpl(DefectClasses object) throws Exception {
			assertNotNull(object);
			assertNotNull(field);

			DefectClasses defectObject = ((DefectClasses) object);
			System.out.println("Opening " + defectObject.getIdDefectClass() + " ("
					+ defectObject.getName() + ")");
			field.setText(((DefectClasses) object).getName());
			lastName = field.getText();

			return true;
		}

		@Override
		protected void putFormToObjectImpl(DefectClasses object) throws Exception {
			assertNotNull(object);
			assertNotNull(field);

			DefectClasses defectObject = ((DefectClasses) object);
			defectObject.setName(field.getText());
			System.out.println("Saving " + defectObject.getIdDefectClass() + " ("
					+ defectObject.getName() + ")");
		}

		@Override
		protected void restoreObjectImpl(DefectClasses object) throws Exception {

			assertNotNull(object);

			DefectClasses defectObject = ((DefectClasses) object);
			defectObject.setName(lastName);
		}

	}

}
