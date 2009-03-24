package edu.mgupi.pass.face.gui;

import javax.swing.UIManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.util.ClassesHelper;

public class FilterEditorTest {

	private FiltersEditor dialog = null;

	@Before
	public void setUp() throws Exception {
		AppHelper.getInstance().updateUI(UIManager.getSystemLookAndFeelClassName());
		dialog = new FiltersEditor(null);
		dialog.setLocation(300, 400);
	}

	@After
	public void tearDown() throws Exception {
		if (dialog != null) {
			dialog.setVisible(false);
			dialog.dispose();
			dialog = null;
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void commonTest() throws Exception {

		//		ModuleProcessor processor = new ModuleProcessor();
		//		processor.setModule(TestModule.class);

		FilterChainsaw mySaw = new FilterChainsaw();
		//		processor.setChainsaw(mySaw);

		for (Class<?> filterClass : ClassesHelper.getAvailableClasses(IFilter.class)) {
			mySaw.appendFilter((Class<IFilter>) filterClass);
		}

		dialog.setFilters("Тестовый показ", mySaw);
		SwingTestHelper.showMeBackground(dialog);

		//		dialog.setVisible(true);
		//		//SwingTestHelper.waitMe(dialog);
		////
		//		dialog.setFilters("Тестовый показ номер два!!!", mySaw);
		//		dialog.setVisible(true);

		//		SwingTestHelper.showMeBackground(dialog);
		//
		//SwingTestHelper.waitMe(dialog);
	}

	@SuppressWarnings("unchecked")
	public void test() throws Exception {
		FilterChainsaw mySaw = new FilterChainsaw();
		//		processor.setChainsaw(mySaw);

		for (Class<?> filterClass : ClassesHelper.getAvailableClasses(IFilter.class)) {
			mySaw.appendFilter((Class<IFilter>) filterClass);
		}

		dialog.setFilters("Тестовый показ", mySaw);
		dialog.setVisible(true);
	}
}
