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
		SwingTestHelper.closeAllWindows();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void commonTest() throws Exception {

		FilterChainsaw mySaw = new FilterChainsaw();

		for (Class<?> filterClass : ClassesHelper.getAvailableClasses(IFilter.class)) {
			mySaw.appendFilter((Class<IFilter>) filterClass);
		}

		dialog.setFilters("Тестовый показ", mySaw);
		SwingTestHelper.showMeBackground(dialog);
	}

	@SuppressWarnings("unchecked")
	public void test() throws Exception {
		FilterChainsaw mySaw = new FilterChainsaw();
		//		processor.setChainsaw(mySaw);

		for (Class<?> filterClass : ClassesHelper.getAvailableClasses(IFilter.class)) {
			mySaw.appendFilter((Class<IFilter>) filterClass);
		}

		dialog.openDialog("Тестовый показ", mySaw);
	}
}
