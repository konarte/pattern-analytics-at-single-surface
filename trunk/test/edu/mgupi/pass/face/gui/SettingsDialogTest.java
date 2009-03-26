package edu.mgupi.pass.face.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import edu.mgupi.pass.util.Config;
import edu.mgupi.pass.util.Utils;
import edu.mgupi.pass.util.WaitCondition;
import edu.mgupi.pass.util.WorkSet;
import edu.mgupi.pass.util.Config.DeletionMode;
import edu.mgupi.pass.util.Config.SourceMode;

public class SettingsDialogTest {

	private SettingsDialog dialog = null;

	@Before
	public void setUp() throws Exception {
		Config.setDebugInstance();
		dialog = (SettingsDialog) AppHelper.getInstance().getDialogImpl(SettingsDialog.class);
	}

	@After
	public void tearDown() throws Exception {
		SwingTestHelper.closeAllWindows();
	}

	@Test
	public void testOpenDialog() throws Exception {
		SwingTestHelper.showMeBackground(dialog);
	}

	private boolean resultButton = false;

	@Test
	public void testCurrentSettings() throws Exception {
		Config.getInstance().setCurrentSourceMode(SourceMode.LEFT_TOP);
		Config.getInstance().setCurrentBackground(Color.RED);

		dialog.resetControls();

		JTabbedPane tabbed = (JTabbedPane) Utils.getChildNamed(dialog, "settingsPane");
		assertNotNull(tabbed);
		tabbed.setSelectedComponent(Utils.getChildNamed(dialog, "settingsCurrent"));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				resultButton = dialog.openDialog();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return dialog.isVisible() == false;
			}
		});

		//
		JComboBox comboSM = (JComboBox) Utils.getChildNamed(dialog, "sourceMode");
		assertNotNull(comboSM);

		assertEquals(SourceMode.LEFT_TOP, comboSM.getSelectedItem());

		//
		JLabel labelB = (JLabel) Utils.getChildNamed(dialog, "backgroundSample");
		assertNotNull(labelB);

		assertEquals(Color.RED, labelB.getBackground());

		// ------------------------------
		comboSM.setSelectedItem(SourceMode.SCALE);

		SwingTestHelper.clickOpenDialogButton(dialog, "backgroundColor");
		SwingTestHelper.clickColorChooserOK(dialog, Color.GREEN);
		assertEquals(Color.GREEN, labelB.getBackground());

		SwingTestHelper.clickCloseDialogButton(dialog, "OK");

		assertEquals(SourceMode.SCALE, Config.getInstance().getCurrentSourceMode());
		assertEquals(Color.GREEN, Config.getInstance().getCurrentBackground());
		assertTrue(resultButton);

	}

	@Test
	public void testCurrentSettingsCancel() throws Exception {
		Config.getInstance().setCurrentSourceMode(SourceMode.LEFT_TOP);
		Config.getInstance().setCurrentBackground(Color.RED);

		dialog.resetControls();

		JTabbedPane tabbed = (JTabbedPane) Utils.getChildNamed(dialog, "settingsPane");
		assertNotNull(tabbed);
		tabbed.setSelectedComponent(Utils.getChildNamed(dialog, "settingsCurrent"));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				resultButton = dialog.openDialog();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return dialog.isVisible() == false;
			}
		});

		//
		JComboBox comboSM = (JComboBox) Utils.getChildNamed(dialog, "sourceMode");
		assertNotNull(comboSM);
		JLabel labelB = (JLabel) Utils.getChildNamed(dialog, "backgroundSample");
		assertNotNull(labelB);

		comboSM.setSelectedItem(SourceMode.SCALE);

		SwingTestHelper.clickOpenDialogButton(dialog, "backgroundColor");
		SwingTestHelper.clickColorChooserOK(dialog, Color.GREEN);
		assertEquals(Color.GREEN, labelB.getBackground());

		SwingTestHelper.clickCloseDialogButton(dialog, "cancel");

		assertEquals(SourceMode.LEFT_TOP, Config.getInstance().getCurrentSourceMode());
		assertEquals(Color.RED, Config.getInstance().getCurrentBackground());
		assertFalse(resultButton);

	}

	@Test
	public void testCurrentSettingsNoChanged() throws Exception {
		Config.getInstance().setCurrentSourceMode(SourceMode.LEFT_TOP);
		Config.getInstance().setCurrentBackground(Color.RED);

		dialog.resetControls();

		JTabbedPane tabbed = (JTabbedPane) Utils.getChildNamed(dialog, "settingsPane");
		assertNotNull(tabbed);
		tabbed.setSelectedComponent(Utils.getChildNamed(dialog, "settingsCurrent"));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				resultButton = dialog.openDialog();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return dialog.isVisible() == false;
			}
		});

		//
		JComboBox comboSM = (JComboBox) Utils.getChildNamed(dialog, "sourceMode");
		assertNotNull(comboSM);
		JLabel labelB = (JLabel) Utils.getChildNamed(dialog, "backgroundSample");
		assertNotNull(labelB);

		comboSM.setSelectedItem(SourceMode.LEFT_TOP);

		SwingTestHelper.clickOpenDialogButton(dialog, "backgroundColor");
		SwingTestHelper.clickColorChooserOK(dialog, Color.RED);
		assertEquals(Color.RED, labelB.getBackground());

		SwingTestHelper.clickCloseDialogButton(dialog, "OK");

		assertEquals(SourceMode.LEFT_TOP, Config.getInstance().getCurrentSourceMode());
		assertEquals(Color.RED, Config.getInstance().getCurrentBackground());
		assertFalse(resultButton);

	}

	@Test
	public void testCommonSettings() throws Exception {
		Config.getInstance().setFilterDeleteConfirm(DeletionMode.CONFIRM);
		AppHelper.getInstance().updateUI(WindowsLookAndFeel.class.getName());

		dialog.resetControls();

		JTabbedPane tabbed = (JTabbedPane) Utils.getChildNamed(dialog, "settingsPane");
		assertNotNull(tabbed);
		tabbed.setSelectedComponent(Utils.getChildNamed(dialog, "settingsCommon"));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				resultButton = dialog.openDialog();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return dialog.isVisible() == false;
			}
		});

		//
		JComboBox combo = (JComboBox) Utils.getChildNamed(dialog, "laf");
		assertNotNull(combo);

		assertEquals(new WindowsLookAndFeel().getName(), combo.getSelectedItem());

		//
		for (DeletionMode mode : DeletionMode.values()) {
			JRadioButton radio = (JRadioButton) Utils.getChildNamed(dialog, mode.name());
			assertNotNull(radio);
			if (mode == DeletionMode.CONFIRM) {
				assertTrue(radio.isSelected());
			} else {
				assertFalse(radio.isSelected());
			}
		}
		JRadioButton radio = (JRadioButton) Utils.getChildNamed(dialog, DeletionMode.NO_CONFIRM.name());
		assertNotNull(radio);
		radio.setSelected(true);

		combo.setSelectedItem(new MotifLookAndFeel().getName());

		SwingTestHelper.clickCloseDialogButton(dialog, "OK");

		assertEquals(DeletionMode.NO_CONFIRM, Config.getInstance().getFilterDeleteMode());
		assertTrue(MotifLookAndFeel.class == UIManager.getLookAndFeel().getClass());
		assertFalse(resultButton);
	}

	@Test
	public void testCommonSettingsCancel() throws Exception {
		Config.getInstance().setFilterDeleteConfirm(DeletionMode.CONFIRM);
		AppHelper.getInstance().updateUI(WindowsLookAndFeel.class.getName());
		dialog.resetControls();

		JTabbedPane tabbed = (JTabbedPane) Utils.getChildNamed(dialog, "settingsPane");
		assertNotNull(tabbed);
		tabbed.setSelectedComponent(Utils.getChildNamed(dialog, "settingsCommon"));

		SwingTestHelper.addWorkAndWaitThis(new WorkSet() {
			@Override
			public void workImpl() throws Exception {
				resultButton = dialog.openDialog();
			}
		}, new WaitCondition() {
			@Override
			public boolean keepWorking() {
				return dialog.isVisible() == false;
			}
		});

		//
		JComboBox combo = (JComboBox) Utils.getChildNamed(dialog, "laf");
		assertNotNull(combo);

		JRadioButton radio = (JRadioButton) Utils.getChildNamed(dialog, DeletionMode.NO_CONFIRM.name());
		assertNotNull(radio);
		radio.setSelected(true);

		combo.setSelectedItem(new MotifLookAndFeel().getName());

		SwingTestHelper.clickCloseDialogButton(dialog, "cancel");

		assertEquals(DeletionMode.CONFIRM, Config.getInstance().getFilterDeleteMode());
		assertTrue(WindowsLookAndFeel.class == UIManager.getLookAndFeel().getClass());
		assertFalse(resultButton);
	}

}
