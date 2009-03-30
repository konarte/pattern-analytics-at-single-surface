package edu.mgupi.pass.face.gui.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.face.gui.SwingTestHelper;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.ParamHelper;
import edu.mgupi.pass.filters.ParamTest;
import edu.mgupi.pass.filters.TestFilter;
import edu.mgupi.pass.filters.Param.ParamType;
import edu.mgupi.pass.util.Utils;

public class ParametersEditorPanelTest {

	private JDialog parent;
	private ParametersEditorPanel panel;

	private static Collection<Param> fullParams = ParamTest.fillParameters(new ArrayList<Param>());

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
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testPrimarySetParameters() throws Exception {

		TestFilter filter = new TestFilter();
		panel.setModelData(filter.getParams());

		SwingTestHelper.showMeBackground(this.parent);

	}

	@Test
	public void testFullRenderVariants() throws Exception {

		panel.setModelData(fullParams);

		System.out.println("\n");
		SwingTestHelper.printChildHierarchy(panel);

		SwingTestHelper.showMeBackground(this.parent);

		JPanel place = (JPanel) Utils.getChildNamed(panel, "paramPlace");
		assertNotNull(place);
		//		TitledBorder border = (TitledBorder) place.getBorder();
		//		assertEquals("Sample params", border.getTitle());

		assertNotNull(Utils.getChildNamed(panel, "p1_label"));
		assertNotNull(Utils.getChildNamed(panel, "p2_label"));
		assertNotNull(Utils.getChildNamed(panel, "p3_label"));
		assertNotNull(Utils.getChildNamed(panel, "p4_label"));
		assertNotNull(Utils.getChildNamed(panel, "p5_label"));
		assertNotNull(Utils.getChildNamed(panel, "p5.1_label"));
		assertNotNull(Utils.getChildNamed(panel, "p6_label"));
		assertNotNull(Utils.getChildNamed(panel, "p7_label"));
		assertNotNull(Utils.getChildNamed(panel, "p8_label"));
		assertNotNull(Utils.getChildNamed(panel, "p9_label"));
		assertNotNull(Utils.getChildNamed(panel, "p10_label"));

		JFormattedTextField fmt = (JFormattedTextField) Utils.getChildNamed(panel, "p1_int_field");
		assertNotNull(fmt);
		assertEquals(11, fmt.getValue());

		JSpinner spinner = (JSpinner) Utils.getChildNamed(panel, "p2_spinner");
		assertNotNull(spinner);
		assertEquals(1, spinner.getValue());

		fmt = (JFormattedTextField) Utils.getChildNamed(panel, "p3_double_field");
		assertNotNull(fmt);
		assertEquals(2.0, fmt.getValue());

		fmt = (JFormattedTextField) Utils.getChildNamed(panel, "p4_double_field");
		assertNotNull(fmt);
		assertEquals(5.0, fmt.getValue());

		JTextField field = (JTextField) Utils.getChildNamed(panel, "p5_field");
		assertNotNull(field);
		assertEquals("", field.getText());

		field = (JTextField) Utils.getChildNamed(panel, "p5.1_field");
		assertNotNull(field);
		assertEquals("", field.getText());

		field = (JTextField) Utils.getChildNamed(panel, "p6_field");
		assertNotNull(field);
		assertEquals("Тестовая строка", field.getText());

		JLabel label = (JLabel) Utils.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertNotNull(label.getBackground());

		label = (JLabel) Utils.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		JComboBox combo = (JComboBox) Utils.getChildNamed(panel, "p9_combo_box");
		assertNotNull(combo);
		assertEquals("Первый", combo.getSelectedItem());

		combo = (JComboBox) Utils.getChildNamed(panel, "p10_combo_box");
		assertNotNull(combo);
		assertEquals("Второй", combo.getSelectedItem());
	}

	@Test
	public void testEditColors() throws Exception {
		panel.setModelData(fullParams);

		SwingTestHelper.showMeBackground(this.parent);

		SwingTestHelper.clickOpenDialogButton(parent, "p7_color_selector");
		SwingTestHelper.clickColorChooserOK(parent, Color.GREEN);

		JLabel label = (JLabel) Utils.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.GREEN, label.getBackground());

		SwingTestHelper.clickOpenDialogButton(parent, "p7_color_selector");
		SwingTestHelper.clickColorChooserCancel(parent);
		label = (JLabel) Utils.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.GREEN, label.getBackground());

		label = (JLabel) Utils.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		SwingTestHelper.clickOpenDialogButton(parent, "p8_color_selector");
		SwingTestHelper.clickColorChooserOK(parent, Color.YELLOW);
		assertEquals(Color.YELLOW, label.getBackground());

		SwingTestHelper.clickOpenDialogButton(parent, "p7_color_selector");
		SwingTestHelper.clickColorChooserOK(parent, Color.RED);

		label = (JLabel) Utils.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertEquals(Color.RED, label.getBackground());

		panel.resetParameterValues();
		label = (JLabel) Utils.getChildNamed(panel, "p7_color_sample");
		assertNotNull(label);
		assertNotSame(Color.RED, label.getBackground());

		label = (JLabel) Utils.getChildNamed(panel, "p8_color_sample");
		assertNotNull(label);
		assertEquals(Color.BLUE, label.getBackground());

		Collection<Param> params = panel.saveModelData();
		assertTrue(params == fullParams);

		assertNull(ParamHelper.getParameter("p7", params).getValue());
		assertEquals(Color.BLUE, ParamHelper.getParameter("p8", params).getValue());

		SwingTestHelper.clickOpenDialogButton(parent, "p7_color_selector");
		SwingTestHelper.clickColorChooserOK(parent, Color.RED);
		SwingTestHelper.clickOpenDialogButton(parent, "p8_color_selector");
		SwingTestHelper.clickColorChooserOK(parent, Color.YELLOW);

		params = panel.saveModelData();
		assertTrue(params == fullParams);

		assertEquals(Color.RED, ParamHelper.getParameter("p7", params).getValue());
		assertEquals(Color.YELLOW, ParamHelper.getParameter("p8", params).getValue());
	}

	@Test
	public void testEditStrings() throws Exception {
		panel.setModelData(fullParams);
		SwingTestHelper.showMeBackground(this.parent);

		JTextField field5 = (JTextField) Utils.getChildNamed(panel, "p5_field");
		assertNotNull(field5);
		field5.setText("processing!");
		field5.postActionEvent();

		JTextField field51 = (JTextField) Utils.getChildNamed(panel, "p5.1_field");
		assertNotNull(field51);
		field51.setText("j is good");
		field51.postActionEvent();

		JTextField field6 = (JTextField) Utils.getChildNamed(panel, "p6_field");
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

		Collection<Param> params = panel.saveModelData();
		assertEquals("Evening post", ParamHelper.getParameter("p5", params).getValue());
		assertEquals("", ParamHelper.getParameter("p5.1", params).getValue());
		assertEquals("ISGREAT", ParamHelper.getParameter("p6", params).getValue());
	}

	@Test
	public void testEditInt() throws Exception {
		panel.setModelData(fullParams);
		SwingTestHelper.showMeBackground(this.parent);

		JFormattedTextField field = (JFormattedTextField) Utils
				.getChildNamed(panel, "p1_int_field");
		assertNotNull(field);
		field.setValue(99);
		field.postActionEvent();

		JSpinner spinner = (JSpinner) Utils.getChildNamed(panel, "p2_spinner");
		assertNotNull(spinner);
		spinner.setValue(5);

		panel.resetParameterValues();

		assertEquals(11, field.getValue());
		assertEquals(1, spinner.getValue());

		field.setValue(15);
		field.postActionEvent();

		spinner.setValue(6);

		Collection<Param> params = panel.saveModelData();
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

		params = panel.saveModelData();
		assertEquals(31, ParamHelper.getParameter("p1", params).getValue());
		assertEquals(6, ParamHelper.getParameter("p2", params).getValue());

	}

	@Test
	public void testEditDouble() throws Exception {
		panel.setModelData(fullParams);
		SwingTestHelper.showMeBackground(this.parent);

		JFormattedTextField field3 = (JFormattedTextField) Utils.getChildNamed(panel,
				"p3_double_field");
		assertNotNull(field3);
		field3.setValue(99);
		field3.postActionEvent();

		JFormattedTextField field4 = (JFormattedTextField) Utils.getChildNamed(panel,
				"p4_double_field");
		assertNotNull(field4);
		field4.setValue(5);

		panel.resetParameterValues();

		assertEquals(2.0, field3.getValue());
		assertEquals(5.0, field4.getValue());

		field3.setValue(15.0);
		field4.setValue(-6.0);

		Collection<Param> params = panel.saveModelData();
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

		params = panel.saveModelData();
		assertEquals(222.2222222222, ParamHelper.getParameter("p3", params).getValue());
		assertEquals(0.0, ParamHelper.getParameter("p4", params).getValue());

	}

	@Test
	public void testEditCombobox() throws Exception {
		panel.setModelData(fullParams);
		SwingTestHelper.showMeBackground(this.parent);

		JComboBox combo9 = (JComboBox) Utils.getChildNamed(panel, "p9_combo_box");
		assertNotNull(combo9);
		combo9.setSelectedItem("Третий");

		JComboBox combo10 = (JComboBox) Utils.getChildNamed(panel, "p10_combo_box");
		assertNotNull(combo10);
		combo10.setSelectedItem("Первый");

		panel.resetParameterValues();

		assertEquals("Первый", combo9.getSelectedItem());
		assertEquals("Второй", combo10.getSelectedItem());

		combo9.setSelectedItem("Второй");
		combo10.setSelectedItem("Третий");

		Collection<Param> params = panel.saveModelData();
		assertEquals("цвай", ParamHelper.getParameter("p9", params).getValue());
		assertEquals("драй", ParamHelper.getParameter("p10", params).getValue());

		combo9.setSelectedItem("UNKNOWN");

		params = panel.saveModelData();
		assertEquals("цвай", ParamHelper.getParameter("p9", params).getValue());
		assertEquals("драй", ParamHelper.getParameter("p10", params).getValue());
	}

	@Test
	public void testMultipleInstancing() throws Exception {
		Collection<Param> params1 = new ArrayList<Param>();
		params1.add(new Param("param1", "Параметр1", ParamType.INT, 5));

		Collection<Param> params2 = new ArrayList<Param>();
		params2.add(new Param("param2", "Параметр2", ParamType.INT, 5));

		panel.setModelData(params1);
		SwingTestHelper.showMeBackground(this.parent);

		JFormattedTextField fmt = (JFormattedTextField) Utils.getChildNamed(panel,
				"param1_int_field");
		assertNotNull(fmt);

		parent.setVisible(false);
		SwingTestHelper.waitMe(this.parent);

		panel.setModelData(params2);
		SwingTestHelper.showMeBackground(this.parent);

		fmt = (JFormattedTextField) Utils.getChildNamed(panel, "param1_int_field");
		assertNull(fmt);

		fmt = (JFormattedTextField) Utils.getChildNamed(panel, "param2_int_field");
		assertNotNull(fmt);

		parent.setVisible(false);
		SwingTestHelper.waitMe(this.parent);

		panel.setModelData(params2);
		SwingTestHelper.showMeBackground(this.parent);

		// Check for skipping recreating objects if parameters are the same as previous!

		JFormattedTextField newFmt = (JFormattedTextField) Utils.getChildNamed(panel,
				"param2_int_field");
		assertNotNull(newFmt);
		assertTrue(fmt == newFmt);
	}

	public void testSample() throws Exception {
		panel.setModelData(fullParams);
		SwingTestHelper.showMeBackground(this.parent);

		SwingTestHelper.waitMe(this.parent);
	}
}
