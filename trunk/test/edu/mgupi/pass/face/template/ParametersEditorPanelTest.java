package edu.mgupi.pass.face.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.ConditionSet;
import edu.mgupi.pass.face.SwingHelper;
import edu.mgupi.pass.face.WorkSet;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.TestFilter;
import edu.mgupi.pass.filters.Param.TYPES;

public class ParametersEditorPanelTest {

	private JDialog parent;
	private ParametersEditorPanel panel;

	private static Collection<Param> fullParams = fillParameters(new ArrayList<Param>());

	public static Collection<Param> fillParameters(Collection<Param> fullParams) {
		fullParams.add(new Param("p1", "Параметр1", TYPES.INT, 11));
		fullParams.add(new Param("p2", "Параметр2", TYPES.INT, 1, 0, 10));

		fullParams.add(new Param("p3", "Параметр3", TYPES.DOUBLE, 2.0));
		fullParams.add(new Param("p4", "Параметр4", TYPES.DOUBLE, 5.0, 0, 10));

		fullParams.add(new Param("p5", "Параметр5", TYPES.STRING, null));
		fullParams.add(new Param("p5.1", "Параметр5.1", TYPES.STRING, ""));
		fullParams.add(new Param("p6", "Параметр6", TYPES.STRING, "Тестовая строка"));

		fullParams.add(new Param("p7", "Параметр7", TYPES.COLOR, null));
		fullParams.add(new Param("p8", "Параметр8", TYPES.COLOR, Color.BLUE));

		fullParams.add(new Param("p9", "Параметр9", null, new Object[] { "айн", "цвай", "драй" }, new String[] {
				"Первый", "Второй", "Третий" }));
		fullParams.add(new Param("p10", "Параметр10", "цвай", new Object[] { "айн", "цвай", "драй" }, new String[] {
				"Первый", "Второй", "Третий" }));

		return fullParams;
	}

	@Before
	public void setUp() throws Exception {

		parent = new JDialog((Frame) null, true);

		parent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		parent.setBounds(300, 100, 500, 500);

		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(new BorderLayout());
		parent.add(jContentPane);
		JButton closeButton = new JButton();
		closeButton.setAction(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(false);

			}
		});
		closeButton.setText("OK");

		jContentPane.add(closeButton, BorderLayout.SOUTH);

		panel = new ParametersEditorPanel();
		jContentPane.add(panel, BorderLayout.CENTER);

	}

	@After
	public void tearDown() throws Exception {
		if (parent != null) {
			parent.setVisible(false);
			parent.dispose();
			parent = null;
		}
	}

	private void showMeBackground() throws Exception {
		SwingHelper.addWorkAndWaitThis(new WorkSet() {

			@Override
			public void workImpl() throws Exception {
				parent.setVisible(true);
			}
		}, new ConditionSet() {

			@Override
			public boolean keepWorking() {
				return parent.isVisible() == false;
			}
		});
	}

	private void waitMe() throws Exception {
		SwingHelper.waitUntil(new ConditionSet() {

			@Override
			public boolean keepWorking() {
				return parent.isVisible() == true;
			}
		});
	}

	@Test
	public void testPrimarySetParameters() throws Exception {

		TestFilter filter = new TestFilter();
		panel.setParameters(filter.getName(), filter.getParams());

		this.showMeBackground();

	}

	@Test
	public void testFullRenderVariants() throws Exception {

		panel.setParameters("Sample params", fullParams);

		System.out.println("\n");
		SwingHelper.printChildHierarchy(panel);

		this.showMeBackground();

		JPanel place = (JPanel) SwingHelper.getChildNamed(panel, "paramPlace");
		assertNotNull(place);
		TitledBorder border = (TitledBorder) place.getBorder();
		assertEquals("Sample params", border.getTitle());

		assertNotNull(SwingHelper.getChildNamed(panel, "p1_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p2_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p3_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p4_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p5_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p5.1_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p6_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p7_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p8_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p9_label"));
		assertNotNull(SwingHelper.getChildNamed(panel, "p10_label"));

		JFormattedTextField fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p1_int_field");
		assertNotNull(fmt);
		assertEquals(11, fmt.getValue());

		JSpinner spinner = (JSpinner) SwingHelper.getChildNamed(panel, "p2_spinner");
		assertNotNull(spinner);
		assertEquals(1, spinner.getValue());

		fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p3_double_field");
		assertNotNull(fmt);
		assertEquals(2.0, fmt.getValue());

		fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p4_double_field");
		assertNotNull(fmt);
		assertEquals(5.0, fmt.getValue());

		JTextField field = (JTextField) SwingHelper.getChildNamed(panel, "p5_field");
		assertNotNull(field);
		assertEquals("", field.getText());

		field = (JTextField) SwingHelper.getChildNamed(panel, "p5.1_field");
		assertNotNull(field);
		assertEquals("", field.getText());

		field = (JTextField) SwingHelper.getChildNamed(panel, "p6_field");
		assertNotNull(field);
		assertEquals("Тестовая строка", field.getText());

		JLabel label = (JLabel) SwingHelper.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertNotNull(label.getBackground());

		label = (JLabel) SwingHelper.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		JComboBox combo = (JComboBox) SwingHelper.getChildNamed(panel, "p9_combo_box");
		assertNotNull(combo);
		assertEquals("Первый", combo.getSelectedItem());

		combo = (JComboBox) SwingHelper.getChildNamed(panel, "p10_combo_box");
		assertNotNull(combo);
		assertEquals("Второй", combo.getSelectedItem());
	}

	private boolean allChildrenClosed() {
		for (Window window : parent.getOwnedWindows()) {
			if (window.isVisible()) {
				return false;
			}
		}
		return true;
	}

	private Window getOpenedWindow() {
		for (Window window : parent.getOwnedWindows()) {
			if (window.isVisible()) {
				return window;
			}
		}
		fail("No opened windows found.");
		return null;
	}

	private void clickButton(final String buttonName) throws Exception {
		assertTrue(allChildrenClosed());
		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				JButton button = (JButton) SwingHelper.getChildNamed(panel, buttonName);
				assertNotNull(button);
				button.doClick();
			}
		}, new ConditionSet() {
			@Override
			public boolean keepWorking() {
				return allChildrenClosed();
			}
		});
		assertFalse(allChildrenClosed());
	}

	private void clickEmulatedColor(final Color color) throws Exception {
		assertFalse(allChildrenClosed());

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				Window chooserDialog = getOpenedWindow();

				JColorChooser chooser = (JColorChooser) SwingHelper.getReflectedFieldAccess(chooserDialog,
						"chooserPane");
				chooser.setColor(color);

				JButton button = SwingHelper.getButtonByActionCommand(chooserDialog, "OK");
				assertNotNull(button);
				button.doClick();
			}
		}, new ConditionSet() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed();
			}
		});
	}

	private void clickCancel() throws Exception {
		assertFalse(allChildrenClosed());

		SwingHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				Window chooserDialog = getOpenedWindow();
				JButton button = SwingHelper.getButtonByActionCommand(chooserDialog, "cancel");
				assertNotNull(button);
				button.doClick();
			}
		}, new ConditionSet() {
			@Override
			public boolean keepWorking() {
				return !allChildrenClosed();
			}
		});
	}

	@Test
	public void testEditColors() throws Exception {
		panel.setParameters("Sample params", fullParams);

		this.showMeBackground();

		this.clickButton("p7_color_selector");
		this.clickEmulatedColor(Color.GREEN);

		JLabel label = (JLabel) SwingHelper.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.GREEN, label.getBackground());

		this.clickButton("p7_color_selector");
		this.clickCancel();
		label = (JLabel) SwingHelper.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.GREEN, label.getBackground());

		label = (JLabel) SwingHelper.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		this.clickButton("p8_color_selector");
		this.clickEmulatedColor(Color.YELLOW);
		assertEquals(Color.YELLOW, label.getBackground());

		this.clickButton("p7_color_selector");
		this.clickEmulatedColor(Color.RED);

		label = (JLabel) SwingHelper.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.RED, label.getBackground());

		panel.resetParameterValues();
		label = (JLabel) SwingHelper.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertNotSame(Color.RED, label.getBackground());

		label = (JLabel) SwingHelper.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		Collection<Param> params = panel.saveParameterValues();
		assertNull(ParamHelper.getParameter("p7", params).getValue());
		assertEquals(Color.BLUE, ParamHelper.getParameter("p8", params).getValue());

		this.clickButton("p7_color_selector");
		this.clickEmulatedColor(Color.RED);
		this.clickButton("p8_color_selector");
		this.clickEmulatedColor(Color.YELLOW);

		params = panel.saveParameterValues();

		assertEquals(Color.RED, ParamHelper.getParameter("p7", params).getValue());
		assertEquals(Color.YELLOW, ParamHelper.getParameter("p8", params).getValue());
	}

	@Test
	public void testEditStrings() throws Exception {
		panel.setParameters("Sample params", fullParams);
		this.showMeBackground();

		JTextField field5 = (JTextField) SwingHelper.getChildNamed(panel, "p5_field");
		assertNotNull(field5);
		field5.setText("processing!");
		field5.postActionEvent();

		JTextField field51 = (JTextField) SwingHelper.getChildNamed(panel, "p5.1_field");
		assertNotNull(field51);
		field51.setText("j is good");
		field51.postActionEvent();

		JTextField field6 = (JTextField) SwingHelper.getChildNamed(panel, "p6_field");
		assertNotNull(field6);
		field6.setText("NO-doubt");
		field6.postActionEvent();

		panel.resetParameterValues();

		assertEquals("", field5.getText());
		assertEquals("", field51.getText());
		assertEquals("Тестовая строка", field6.getText());

		field5.setText("Evening post");
		field5.postActionEvent();

		field6.setText("ISGREAT");

		Collection<Param> params = panel.saveParameterValues();
		assertEquals("Evening post", ParamHelper.getParameter("p5", params).getValue());
		assertEquals("", ParamHelper.getParameter("p5.1", params).getValue());
		assertEquals("ISGREAT", ParamHelper.getParameter("p6", params).getValue());
	}

	@Test
	public void testEditInt() throws Exception {
		panel.setParameters("Sample params", fullParams);
		this.showMeBackground();

		JFormattedTextField field = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p1_int_field");
		assertNotNull(field);
		field.setValue(99);
		field.postActionEvent();

		JSpinner spinner = (JSpinner) SwingHelper.getChildNamed(panel, "p2_spinner");
		assertNotNull(spinner);
		spinner.setValue(5);

		panel.resetParameterValues();

		assertEquals(11, field.getValue());
		assertEquals(1, spinner.getValue());

		field.setValue(15);
		field.postActionEvent();

		spinner.setValue(6);

		Collection<Param> params = panel.saveParameterValues();
		assertEquals(15, ParamHelper.getParameter("p1", params).getValue());
		assertEquals(6, ParamHelper.getParameter("p2", params).getValue());

		try {
			field.setValue("IGNORED");
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
		field.setValue(31);

		try {
			spinner.setValue("ANOTHER-IGNORED");
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}

		params = panel.saveParameterValues();
		assertEquals(31, ParamHelper.getParameter("p1", params).getValue());
		assertEquals(6, ParamHelper.getParameter("p2", params).getValue());

	}

	@Test
	public void testEditDouble() throws Exception {
		panel.setParameters("Sample params", fullParams);
		this.showMeBackground();

		JFormattedTextField field3 = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p3_double_field");
		assertNotNull(field3);
		field3.setValue(99);
		field3.postActionEvent();

		JFormattedTextField field4 = (JFormattedTextField) SwingHelper.getChildNamed(panel, "p4_double_field");
		assertNotNull(field4);
		field4.setValue(5);

		panel.resetParameterValues();

		assertEquals(2.0, field3.getValue());
		assertEquals(5.0, field4.getValue());

		field3.setValue(15.0);
		field4.setValue(-6.0);

		Collection<Param> params = panel.saveParameterValues();
		assertEquals(15.0, ParamHelper.getParameter("p3", params).getValue());
		assertEquals(-6.0, ParamHelper.getParameter("p4", params).getValue());

		try {
			field3.setValue("IGNORED");
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
		field3.setValue(222.2222222222);

		try {
			field4.setValue("ANOTHER-IGNORED");
			fail("No exception thrown!");
		} catch (IllegalArgumentException e) {
			System.out.println("Received expected exception: " + e);
		}
		field4.setValue(0.0);

		params = panel.saveParameterValues();
		assertEquals(222.2222222222, ParamHelper.getParameter("p3", params).getValue());
		assertEquals(0.0, ParamHelper.getParameter("p4", params).getValue());

	}

	@Test
	public void testEditCombobox() throws Exception {
		panel.setParameters("Sample params", fullParams);
		this.showMeBackground();

		JComboBox combo9 = (JComboBox) SwingHelper.getChildNamed(panel, "p9_combo_box");
		assertNotNull(combo9);
		combo9.setSelectedItem("Третий");

		JComboBox combo10 = (JComboBox) SwingHelper.getChildNamed(panel, "p10_combo_box");
		assertNotNull(combo10);
		combo10.setSelectedItem("Первый");

		panel.resetParameterValues();

		assertEquals("Первый", combo9.getSelectedItem());
		assertEquals("Второй", combo10.getSelectedItem());

		combo9.setSelectedItem("Второй");
		combo10.setSelectedItem("Третий");

		Collection<Param> params = panel.saveParameterValues();
		assertEquals("цвай", ParamHelper.getParameter("p9", params).getValue());
		assertEquals("драй", ParamHelper.getParameter("p10", params).getValue());

		combo9.setSelectedItem("UNKNOWN");

		params = panel.saveParameterValues();
		assertEquals("цвай", ParamHelper.getParameter("p9", params).getValue());
		assertEquals("драй", ParamHelper.getParameter("p10", params).getValue());
	}

	@Test
	public void testMultipleInstancing() throws Exception {
		Collection<Param> params1 = new ArrayList<Param>();
		params1.add(new Param("param1", "Параметр1", TYPES.INT, 5));

		Collection<Param> params2 = new ArrayList<Param>();
		params2.add(new Param("param2", "Параметр2", TYPES.INT, 5));

		panel.setParameters("Sample params1", params1);
		this.showMeBackground();

		JFormattedTextField fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "param1_int_field");
		assertNotNull(fmt);

		parent.setVisible(false);
		this.waitMe();

		panel.setParameters("Sample params2", params2);
		this.showMeBackground();

		fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "param1_int_field");
		assertNull(fmt);

		fmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "param2_int_field");
		assertNotNull(fmt);

		parent.setVisible(false);
		this.waitMe();

		panel.setParameters("Sample params2", params2);
		this.showMeBackground();

		// Check for skipping recreating objects if parameters are the same as previous!

		JFormattedTextField newFmt = (JFormattedTextField) SwingHelper.getChildNamed(panel, "param2_int_field");
		assertNotNull(newFmt);
		assertTrue(fmt == newFmt);
	}

	public void testSample() throws Exception {
		panel.setParameters("Sample params", fullParams);
		this.showMeBackground();

		this.waitMe();
	}
}
