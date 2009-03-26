package edu.mgupi.pass.face.gui.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

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
import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.TransactionMode;

public class TableEditorTemplateTest {

	private JFrame parentFrame = null;
	private TableEditorTemplateImpl listForm = null;

	@Before
	public void setUp() throws Exception {

		listForm = (TableEditorTemplateImpl) AppHelper.getInstance().getDialogImpl(TableEditorTemplateImpl.class);

		parentFrame = new JFrame();
		JButton button = new JButton();
		button.setAction(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				returnValue = false;
				returnValue = listForm.openDialog();

			}
		});
		button.setName("action");
		parentFrame.add(button);

		Config.setDebugInstance();

	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
		removeAllData();
	}

	private void removeAllData() throws PersistentException, InterruptedException {

		try {
			//			System.out.println("Transaction = " + PassPersistentManager.instance().getSession().getTransaction()
			//					+ ", Rollback ? " + PassPersistentManager.instance().getSession().getTransaction().wasRolledBack());

			PassPersistentManager.instance().getSession().close();

			PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();
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

	@Test
	public void testWorkEveryRowCancel() throws Exception {
		Config.getInstance().setTransactionMode(TransactionMode.COMMIT_EVERY_ROW);

		assertNotNull(listForm);

		assertFalse(listForm.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
		assertTrue(listForm.isVisible());

		JTable table = (JTable) Utils.getChildNamed(listForm, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		RecordEditorTemplate editForm = (RecordEditorTemplate) SwingTestHelper.clickOpenDialogButton(listForm, "add",
				RecordEditorTemplateImpl.class);
		assertNotNull(editForm);

		JTextField text = (JTextField) Utils.getChildNamed(editForm, "className");
		assertNotNull(text);

		assertEquals("", text.getText());
		text.setText("TEST-временный");

		SwingTestHelper.clickCloseDialogButton(editForm, "cancel");

		assertEquals(0, table.getRowCount());
	}

	private void openWindowAndSave(final boolean add, String value) throws Exception {
		TableEditorTemplateImpl listForm = (TableEditorTemplateImpl) AppHelper.getInstance().searchWindow(
				TableEditorTemplateImpl.class);
		assertNotNull(listForm);

		RecordEditorTemplate editForm = (RecordEditorTemplate) SwingTestHelper.clickOpenDialogButton(listForm,
				add ? "add" : "edit", RecordEditorTemplateImpl.class);
		assertNotNull(editForm);

		JTextField text = (JTextField) Utils.getChildNamed(editForm, "className");
		assertNotNull(text);

		text.setText(value);
		text.postActionEvent();

		final JTable table = (JTable) Utils.getChildNamed(listForm, "data");
		assertNotNull(table);

		final int rowcount = table.getModel().getRowCount();

		SwingTestHelper.clickCloseDialogButton(editForm, "OK");
		SwingTestHelper.waitUntil(new WaitCondition() {

			@Override
			public boolean keepWorking() {
				return add ? table.getModel().getRowCount() == rowcount : false;
			}
		});
	}

	private final static int MAX_ROWS = 10;

	private List<Integer> fillData(JTable table) throws Exception {

		List<Integer> id = new ArrayList<Integer>();
		for (int i = 0; i < MAX_ROWS; i++) {
			String text = "TEST-очередной класс-" + i;

			openWindowAndSave(true, text);

			assertEquals(i + 1, table.getRowCount());

			int index = Integer.parseInt((String) table.getModel().getValueAt(i, 0));
			id.add(index);

			assertFalse(0 == index);
			assertEquals(text, table.getModel().getValueAt(i, 1));
		}

		return id;
	}

	private void checkData(JTable table, PreparedStatement statement, TransactionMode mode, Collection<Integer> idList,
			boolean checkTable) throws SQLException {
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
				if (mode == null || mode == TransactionMode.COMMIT_EVERY_ROW) {
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

				if (checkTable && !cycled) {
					index = Integer.parseInt((String) table.getModel().getValueAt(idx, 0));
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

	public void testChangeData(TransactionMode mode) throws Exception {
		Config.getInstance().setTransactionMode(mode);
		assertNotNull(listForm);

		assertFalse(listForm.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
		assertTrue(listForm.isVisible());

		JTable table = (JTable) Utils.getChildNamed(listForm, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		openWindowAndSave(true, "TEST-мой первый текстовый класс");
		assertEquals(1, table.getRowCount());

		assertFalse(Utils.equals("0", table.getModel().getValueAt(0, 0)));
		assertEquals("TEST-мой первый текстовый класс", table.getModel().getValueAt(0, 1));

		table.setRowSelectionInterval(0, 0);
		openWindowAndSave(false, "TEST-Отредактированный класс()");
		assertEquals(1, table.getRowCount());
		assertEquals("TEST-Отредактированный класс()", table.getModel().getValueAt(0, 1));

		SwingTestHelper.clickCloseDialogButton(listForm, "cancel");
		removeAllData();

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Reopen and try to click 'OK'
		assertFalse(listForm.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
		assertTrue(listForm.isVisible());

		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		Properties properties = new Properties();
		properties.put("user", "pass");
		properties.put("password", "adesroot");

		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pass_db", properties);
		conn.setAutoCommit(true);
		PreparedStatement statement = conn
				.prepareStatement("select idDefectClass, name from DefectClasses where idDefectClass = ?");

		// Fill data from database
		List<Integer> id = fillData(table);

		// Check current data
		checkData(table, statement, mode, id, true);
		assertEquals(MAX_ROWS, table.getRowCount());

		// Do cancel
		// for COMMIT_EVERY_ROW all data must stay
		// for COMMIT_BULK -- it is disappear
		SwingTestHelper.clickCloseDialogButton(listForm, "cancel");
		assertFalse(returnValue);

		// Well, check data
		checkData(table, statement, mode, id, false);

		removeAllData();

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Reopen and try to click 'OK'

		if (mode == TransactionMode.COMMIT_EVERY_ROW) {
			assertFalse(listForm.isVisible());
			SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
			assertTrue(listForm.isVisible());

			id = fillData(table);

			// This is check for edit!
			Random rand = new Random();
			for (int i = 0; i < id.size() / 2; i++) {
				int row = rand.nextInt(id.size() - 1);
				String text = "TEST-очередной класс-" + rand.nextInt();

				System.out.println("Updaing record " + text + " ? row " + row);
				table.setRowSelectionInterval(row, row);
				openWindowAndSave(false, text);

				int index = id.get(row);
				statement.setInt(1, index);
				ResultSet rs = statement.executeQuery();

				// for COMMIT_EVERY_ROW all data must stay
				// for COMMIT_BULK -- it is disappear
				// We use mode = null only once -- when process 'save' event
				if (rs.next()) {
					assertEquals(index, rs.getInt(1));
					assertEquals(text, rs.getString(2));
				}

			}
		}

		if (mode == TransactionMode.COMMIT_BULK) {
			assertFalse(listForm.isVisible());
			SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
			assertTrue(listForm.isVisible());

			id = fillData(table);

			// Fill
			//PassPersistentManager.instance().getSession().flush();

			// Check
			checkData(table, statement, mode, id, true);
			assertEquals(MAX_ROWS, table.getRowCount());

			SwingTestHelper.clickCloseDialogButton(listForm, "OK");
			assertTrue(returnValue);

			// Now we must read all values from database!
			// If we have COMMIT_BULK -- all data must be in database just now
			// So use null for mode
			checkData(table, statement, null, id, false);
		}

		statement.close();
		conn.close();
	}

	@Test
	public void testChangeDataWorkEveryRow() throws Exception {
		this.testChangeData(TransactionMode.COMMIT_EVERY_ROW);
	}

	@Test
	public void testChangeDataWorkBulk() throws Exception {
		this.testChangeData(TransactionMode.COMMIT_BULK);
	}

	private void deleteRows(String expectedButton, int... rows) throws Exception {
		assertNotNull(rows);
		assertTrue(rows.length > 0);

		final TableEditorTemplateImpl listForm = (TableEditorTemplateImpl) AppHelper.getInstance().searchWindow(
				TableEditorTemplateImpl.class);

		assertNotNull(listForm);

		final JTable table = (JTable) Utils.getChildNamed(listForm, "data");
		assertNotNull(table);

		table.setRowSelectionInterval(rows[0], rows[rows.length - 1]);

		if (expectedButton != null) {
			SwingTestHelper.clickOpenDialogButton(listForm, "delete");
			Window window = SwingTestHelper.searchAnyOpenedWindow(listForm);
			assertNotNull(window);
			//SwingTestHelper.printChildHierarchy(window);
			SwingTestHelper.clickCloseDialogButton(window, expectedButton);
		} else {
			final int rowcount = table.getModel().getRowCount();

			SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
				@Override
				public void workImpl() throws Exception {
					AbstractButton button = (AbstractButton) Utils.getChildNamed(listForm, "delete");
					assertNotNull(button);
					button.doClick();
				}
			}, new WaitCondition() {
				@Override
				public boolean keepWorking() {
					return table.getModel().getRowCount() == rowcount;
				}
			});
		}
	}

	@Test
	public void testEditData() throws Exception {

		Config.getInstance().setTransactionMode(TransactionMode.COMMIT_BULK);
		Config.getInstance().setRowsDeleteMode(DeletionMode.NO_CONFIRM);

		assertNotNull(listForm);

		assertFalse(listForm.isVisible());
		SwingTestHelper.clickOpenDialogButton(parentFrame, "action", TableEditorTemplateImpl.class);
		assertTrue(listForm.isVisible());

		JTable table = (JTable) Utils.getChildNamed(listForm, "data");
		assertNotNull(table);
		assertEquals(0, table.getRowCount());

		openWindowAndSave(true, "TEST-редактируемый0");//-
		openWindowAndSave(true, "TEST-редактируемый1");//-
		openWindowAndSave(true, "TEST-редактируемый2");//-
		openWindowAndSave(true, "TEST-редактируемый3");//-

		assertEquals(4, table.getRowCount());

		deleteRows(null, 1);

		assertEquals(3, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(1, 1));

		openWindowAndSave(true, "TEST-редактируемый4");
		openWindowAndSave(true, "TEST-редактируемый5");
		openWindowAndSave(true, "TEST-редактируемый6");
		openWindowAndSave(true, "TEST-редактируемый7");//-
		openWindowAndSave(true, "TEST-редактируемый8");//-
		openWindowAndSave(true, "TEST-редактируемый9");//-

		deleteRows(null, 6, 7, 8);

		assertEquals(6, table.getRowCount());
		assertEquals("TEST-редактируемый6", table.getModel().getValueAt(5, 1));

		Config.getInstance().setRowsDeleteMode(DeletionMode.CONFIRM);

		deleteRows("Yes", 0);
		assertEquals(5, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(0, 1));

		deleteRows("No", 0);
		assertEquals(5, table.getRowCount());
		assertEquals("TEST-редактируемый2", table.getModel().getValueAt(0, 1));

		deleteRows("Yes", 0, 1);
		assertEquals(3, table.getRowCount());
		assertEquals("TEST-редактируемый4", table.getModel().getValueAt(0, 1));
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(1, 1));
		assertEquals("TEST-редактируемый6", table.getModel().getValueAt(2, 1));

		Config.getInstance().setRowsDeleteMode(DeletionMode.CONFIRM_MULTPLES);

		deleteRows(null, 0);
		assertEquals(2, table.getRowCount());
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(0, 1));

		deleteRows("No", 0, 1);
		assertEquals(2, table.getRowCount());
		assertEquals("TEST-редактируемый5", table.getModel().getValueAt(0, 1));

		deleteRows("Yes", 0, 1);
		assertEquals(0, table.getRowCount());

		SwingTestHelper.clickCloseDialogButton(listForm, "cancel");

	}

	public static class TableEditorTemplateImpl extends TableEditorTemplate {

		public TableEditorTemplateImpl(Frame owner) {
			super(owner);
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
		private static final long serialVersionUID = 1L;

		static class MyTableModel extends CommonEditorTableModel {
			private static final long serialVersionUID = 1L;

			public MyTableModel(JTable owner) {
				super(owner, RecordEditorTemplateImpl.class);
			}

			@Override
			protected Object createInstanceImpl() {
				return DefectClassesFactory.createDefectClasses();
			}

			@Override
			protected String[] getColumns() {
				return new String[] { "ID", "Класс дефекта" };
			}

			@SuppressWarnings("unchecked")
			@Override
			protected List getDataImpl() throws Exception {
				DefectClassesCriteria criteria = new DefectClassesCriteria();
				criteria.name.like("TEST-%");
				List<DefectClasses> classes = new ArrayList<DefectClasses>();
				classes.addAll(Arrays.asList(DefectClassesFactory.listDefectClassesByCriteria(criteria)));
				return classes;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				DefectClasses defect = (DefectClasses) data.get(rowIndex);

				return columnIndex == 0 ? String.valueOf(defect.getIdDefectClass()) : defect.getName();
			}

		}

		private MyTableModel tableModel = null;

		@Override
		protected AbstractEditorTableModel getTableModelImpl(JTable owner) {
			if (tableModel == null) {
				tableModel = new MyTableModel(owner);
			}
			return tableModel;
		}

	}

	public static class RecordEditorTemplateImpl extends RecordEditorTemplate {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RecordEditorTemplateImpl(Frame owner) {
			super(owner);
		}

		private JPanel rootPanel = null;
		private JTextField field = null;
		private JLabel text = null;

		@Override
		protected JPanel getPanelImpl() {
			if (rootPanel == null) {

				rootPanel = new JPanel();
				rootPanel.setLayout(new FlowLayout());

				text = new JLabel();
				text.setText("Название класса");
				rootPanel.add(text);

				field = new JTextField();
				field.setPreferredSize(new Dimension(200, 20));
				field.setName("className");
				rootPanel.add(field);

				System.out.println("return " + rootPanel);

			}
			return rootPanel;
		}

		protected Map<JTextComponent, JLabel> getRequiredFields() {
			Map<JTextComponent, JLabel> map = new HashMap<JTextComponent, JLabel>();
			map.put(field, text);
			return map;
		}

		@Override
		protected void deleteObjectsImpl(Object objects[]) throws Exception {

			assertNotNull(objects);
			assertFalse(objects.length == 0);
			System.out.println("Received for delete: " + objects.length);

			super.deleteObjectsImpl(objects);
		}

		@Override
		protected boolean isDeleteAllowed(Object objects[]) throws Exception {

			assertNotNull(objects);
			assertFalse(objects.length == 0);

			System.out.println("Received for check: " + objects.length);
			for (Object object : objects) {
				DefectClasses defectObject = ((DefectClasses) object);
				int id = defectObject.getIdDefectClass();

				if (id == 0) {
					continue;
				}

				DefectTypesCriteria criteria = new DefectTypesCriteria();
				criteria.createDefectClassCriteria().idDefectClass.eq(defectObject.getIdDefectClass());

				DefectTypes foundType = DefectTypesFactory.loadDefectTypesByCriteria(criteria);
				if (foundType != null) {
					JOptionPane.showMessageDialog(null, "Класс дефекта " + defectObject.getName()
							+ " удалить нельзя. К нему привязан дефект с типом " + foundType.getName() + ".");
					return false;
				}
			}

			return true;
		}

		@Override
		protected boolean isSaveAllowed(Object object) throws Exception {
			DefectClasses defectObject = ((DefectClasses) object);

			assertNotNull(field);
			assertNotNull(defectObject);

			if (Utils.equals(field.getText(), defectObject.getName())) {
				return true;
			}

			String name = field.getText();

			DefectClassesCriteria criteria = new DefectClassesCriteria();
			criteria.name.eq(name);
			if (defectObject.getIdDefectClass() != 0) {
				criteria.idDefectClass.ne(defectObject.getIdDefectClass());
			}

			DefectClasses foundClass = DefectClassesFactory.loadDefectClassesByCriteria(criteria);
			if (foundClass != null) {
				JOptionPane.showMessageDialog(null, "Класс дефекта с названием " + name + " уже существует.");
				return false;
			}

			return true;
		}

		private String lastName = null;

		@Override
		protected void loadObject(Object object) throws Exception {
			assertNotNull(object);
			assertNotNull(field);
			
			DefectClasses defectObject = ((DefectClasses) object);
			System.out.println("Opening " + defectObject.getIdDefectClass() + " (" + defectObject.getName() + ")");
			field.setText(((DefectClasses) object).getName());
			lastName = field.getText();
		}

		@Override
		protected void saveObjectImpl(Object object) throws Exception {
			assertNotNull(object);
			assertNotNull(field);
			
			DefectClasses defectObject = ((DefectClasses) object);
			defectObject.setName(field.getText());
			System.out.println("Saving " + defectObject.getIdDefectClass() + " (" + defectObject.getName() + ")");
			defectObject.save();
		}

		@Override
		protected void restoreObjectImpl(Object object) throws Exception {
			
			assertNotNull(object);
			
			DefectClasses defectObject = ((DefectClasses) object);
			defectObject.setName(lastName);
		}

	}

}
