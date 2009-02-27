package edu.mgupi.pass.face;

import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.orm.ORMDatabaseInitiator;
import org.orm.PersistentTransaction;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.locuses.LocusFilters;
import edu.mgupi.pass.db.locuses.LocusFiltersFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.util.ClassesHelper;

/**
 * Common db-initialization class for PASS system
 * 
 * @author raidan
 *
 */
public class AutoInit {

	private void initRows() throws Exception {

		PersistentTransaction t = PassPersistentManager.instance().getSession().beginTransaction();

		try {

			Collection<Class<?>> filterClasses = ClassesHelper.getAvailableClasses(IFilter.class);

			StringBuilder items = new StringBuilder();
			System.out.println("Registering filters (" + filterClasses.size() + " expected)...");
			for (Class<?> clazz : filterClasses) {
				IFilter filter = (IFilter) clazz.newInstance();
				String codename = filter.getClass().getCanonicalName();

				System.out.print("Checking filter class " + codename + " (" + filter.getName() + ")...");

				LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '" + codename + "'", null);
				if (dbFilter == null) {
					System.out.println(" CREATE");
					dbFilter = LFiltersFactory.createLFilters();
					dbFilter.setName(filter.getName());
					dbFilter.setCodename(codename);
					dbFilter.save();
				} else {
					System.out.println(" SKIP");
				}
				if (items.length() > 0) {
					items.append(", ");
				}
				items.append("'").append(codename).append("'");
			}

			if (items.length() > 0) {
				for (LFilters checkFilter : LFiltersFactory
						.listLFiltersByQuery("codename not in (" + items + ")", null)) {
					LocusFilters found = LocusFiltersFactory.loadLocusFiltersByQuery("LFiltersIdLFilter = "
							+ checkFilter.getIdLFilter(), null);
					if (found == null) {
						System.out.println("Filter " + checkFilter.getCodename() + " (" + checkFilter.getName()
								+ ") stored in database does not have reference "
								+ "in LocusFilters table. Removing unexisted class reference.");
						checkFilter.delete();
					}
				}
			}

			items = new StringBuilder();
			Collection<Class<?>> moduleClasses = ClassesHelper.getAvailableClasses(IModule.class);
			System.out.println("Registering modules (" + moduleClasses.size() + " expected)...");
			for (Class<?> clazz : moduleClasses) {
				IModule module = (IModule) clazz.newInstance();
				String codename = module.getClass().getCanonicalName();

				System.out.print("Checking module class " + codename + " (" + module.getName() + ")...");

				LModules dbModule = LModulesFactory.loadLModulesByQuery("codename = '" + codename + "'", null);
				if (dbModule == null) {
					System.out.println(" CREATE");
					dbModule = LModulesFactory.createLModules();
					dbModule.setName(module.getName());
					dbModule.setCodename(codename);
					dbModule.save();
				} else {
					System.out.println(" SKIP (already exists)");
				}
				if (items.length() > 0) {
					items.append(", ");
				}
				items.append("'").append(codename).append("'");
			}

			if (items.length() > 0) {
				for (LModules checkModule : LModulesFactory
						.listLModulesByQuery("codename not in (" + items + ")", null)) {
					Locuses found = LocusesFactory.loadLocusesByQuery("LModulesIDLModule = "
							+ checkModule.getIdLModule(), null);
					if (found == null) {
						System.out.println("Module " + checkModule.getCodename() + " (" + checkModule.getName()
								+ ") stored in database does not have reference "
								+ "in Locuses table. Removing unexisted class reference.");
						checkModule.delete();
					}
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
		System.out.println("auto-init module");

		Options options = new Options();
		options
				.addOption(
						"type",
						true,
						"[create|drop|init] -- allowed operations; 'create' will create new database installation, "
								+ "'drop' will totally clear this database  and 'init' scans applied filters and modules and will register "
								+ "them in database");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		try {

			String value = cmd.getOptionValue("type");
			if ("create".equals(value)) {
				System.out.println("Creating new database schema...");
				ORMDatabaseInitiator.createSchema(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance());
				System.out.println("Done. Successfully created.");
			} else if ("drop".equals(value)) {
				System.out.println("Dropping existing database schema...");
				System.out.println("Are you sure to drop table(s)? (Y/N)");
				java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
				if (reader.readLine().trim().toUpperCase().equals("Y")) {
					ORMDatabaseInitiator.dropSchema(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance());
					System.out.println("Done. Successfully dropped.");
				} else {
					System.out.println("Done. Cancel drop.");
				}
			} else if ("init".equals(value)) {
				new AutoInit().initRows();
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -classpath=... edu.mgupi.pass.face.AutoInit", options);
			}
		} finally {
			PassPersistentManager.instance().disposePersistentManager();
		}
	}
}
