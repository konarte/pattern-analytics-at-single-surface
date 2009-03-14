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
import edu.mgupi.pass.db.locuses.LocusFilterOptions;
import edu.mgupi.pass.db.locuses.LocusFilterOptionsFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.db.surfaces.PassPersistentManager;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.util.ClassesHelper;
import edu.mgupi.pass.util.Const;

/**
 * Common db-initialization class for PASS system.
 * 
 * Support three type of operation: create database schema, create records for
 * existing filters and modules, finally drop database schema (full drop!).
 * 
 * @author raidan
 * 
 */
public class AutoInit {

	private void initSchema() throws Exception {
		try {
			System.out.println("Creating new database schema...");
			ORMDatabaseInitiator.createSchema(PassPersistentManager.instance());
			System.out.println("Done. Successfully created.");
		} finally {
			PassPersistentManager.instance().disposePersistentManager();
		}
	}

	private boolean dropSchema() throws Exception {
		System.out.println("Dropping existing database schema...");
		System.out.println("Are you sure to drop table(s)? (Y/N)");
		java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		if (reader.readLine().trim().toUpperCase().equals("Y")) {
			try {
				ORMDatabaseInitiator.dropSchema(PassPersistentManager.instance());
				System.out.println("Done. Successfully dropped.");
				return true;
			} finally {
				PassPersistentManager.instance().disposePersistentManager();
			}
		} else {
			System.out.println("Done. Cancel drop.");
			return false;

		}
	}

	/**
	 * Initiation -- create records with filters and modules
	 * 
	 * @throws Exception
	 */
	private void initRows() throws Exception {

		try {

			PersistentTransaction transaction = PassPersistentManager.instance().getSession().beginTransaction();

			try {

				// Filters first
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

				// Now we must clear non-existed filters (records with codenames
				// not
				// present in our classes).
				// And only if that filters not using in table data
				if (items.length() > 0) {
					for (LFilters checkFilter : LFiltersFactory.listLFiltersByQuery("codename not in (" + items + ")",
							null)) {
						LocusFilterOptions found = LocusFilterOptionsFactory.loadLocusFilterOptionsByQuery(
								"LFiltersIdLFilter = " + checkFilter.getIdLFilter(), null);
						if (found == null) {
							System.out.println("Filter " + checkFilter.getCodename() + " (" + checkFilter.getName()
									+ ") stored in database does not have reference "
									+ "in LocusFilters table. Removing unexisted class reference.");
							checkFilter.delete();
						}
					}
				}

				// Processing modules, the same way
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

				// And removing unnecessary modules
				if (items.length() > 0) {
					for (LModules checkModule : LModulesFactory.listLModulesByQuery("codename not in (" + items + ")",
							null)) {
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

				transaction.commit();

				System.out.println("Done. Commit.");
			} catch (Exception e) {
				transaction.rollback();
				throw e;
			}
		} finally {
			PassPersistentManager.instance().disposePersistentManager();
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.println(Const.PROGRAM_NAME + " v." + Const.PROGRAM_VERSION);
		System.out.println("auto-init module");

		Options options = new Options();
		options
				.addOption(
						"cmd",
						true,
						"[create|drop|init|full-recreate] -- allowed operations; 'create' will create new database installation, "
								+ "'drop' will totally clear this database  and 'init' scans applied filters and modules and will register "
								+ "them in database; finally 'full-recreate' will call 'drop', 'create' and 'init'");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		String command = cmd.getOptionValue("cmd");

		AutoInit init = new AutoInit();
		if ("create".equals(command)) {
			init.initSchema();
		} else if ("drop".equals(command)) {
			init.dropSchema();
		} else if ("init".equals(command)) {
			init.initRows();
		} else if ("full-recreate".equals(command)) {
			System.out.println("Full-cycle processing...");
			if (init.dropSchema()) {
				init.initSchema();
				init.initRows();
			}
		} else {
			// show help
			init = null;
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -classpath=... edu.mgupi.pass.face.AutoInit", options);
		}
	}
}
