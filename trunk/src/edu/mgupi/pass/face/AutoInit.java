package edu.mgupi.pass.face;

import java.util.Collection;

import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.util.ClassesHelper;

public class AutoInit {

	private void init() throws Exception {

		PersistentTransaction t = PassPersistentManager.instance().getSession().beginTransaction();

		try {

			Collection<Class<?>> filterClasses = ClassesHelper.getAvailableClasses(IFilter.class);

			System.out.println("Registering filters (" + filterClasses.size() + " expected)...");
			for (Class<?> clazz : filterClasses) {
				IFilter filter = (IFilter) clazz.newInstance();
				System.out.print("Checking filter class " + filter.getClass().getCanonicalName() + " ("
						+ filter.getName() + ")...");

				LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '"
						+ filter.getClass().getCanonicalName() + "'", null);
				if (dbFilter == null) {
					System.out.println(" CREATE");
					dbFilter = LFiltersFactory.createLFilters();
					dbFilter.setName(filter.getName());
					dbFilter.setCodename(filter.getClass().getCanonicalName());
					dbFilter.save();
				} else {
					System.out.println(" SKIP");
				}
			}

			Collection<Class<?>> moduleClasses = ClassesHelper.getAvailableClasses(IModule.class);
			System.out.println("Registering modules (" + moduleClasses.size() + " expected)...");
			for (Class<?> clazz : moduleClasses) {
				IModule module = (IModule) clazz.newInstance();
				System.out.print("Checking module class " + module.getClass().getCanonicalName() + " ("
						+ module.getName() + ")...");

				LModules dbModule = LModulesFactory.loadLModulesByQuery("codename = '"
						+ module.getClass().getCanonicalName() + "'", null);
				if (dbModule == null) {
					System.out.println(" CREATE");
					dbModule = LModulesFactory.createLModules();
					dbModule.setName(module.getName());
					dbModule.setCodename(module.getClass().getCanonicalName());
					dbModule.save();
				} else {
					System.out.println(" SKIP");
				}
			}

			t.commit();

			System.out.println("Done. Commit.");
		} catch (Exception e) {
			t.rollback();
			throw e;
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println("Pattern Analytics at Single Surface v.0.1");
		System.out.println("auto-init");

		try {
			new AutoInit().init();
		} finally {
			PassPersistentManager.instance().disposePersistentManager();
		}
	}
}
