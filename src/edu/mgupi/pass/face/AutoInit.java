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
import edu.mgupi.pass.db.locuses.LFiltersCriteria;
import edu.mgupi.pass.db.locuses.LFiltersFactory;
import edu.mgupi.pass.db.locuses.LModules;
import edu.mgupi.pass.db.locuses.LModulesCriteria;
import edu.mgupi.pass.db.locuses.LModulesFactory;
import edu.mgupi.pass.db.locuses.LocusAppliedFilters;
import edu.mgupi.pass.db.locuses.LocusAppliedFiltersFactory;
import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory;
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

				//				StringBuilder items = new StringBuilder();
				System.out.println("Registering filters (" + filterClasses.size() + " expected)...");

				LFiltersCriteria fNICriteria = new LFiltersCriteria();
				for (Class<?> clazz : filterClasses) {
					IFilter filter = (IFilter) clazz.newInstance();
					String name = filter.getName();
					String codename = filter.getClass().getCanonicalName();

					System.out.print("Checking filter class " + codename + " (" + filter.getName() + ")...");

					///LFiltersFactory.loadLFiltersByCriteria(LFiltersDeCriteria.)

					//LFilters dbFilter = LFiltersFactory.loadLFiltersByQuery("codename = '" + codename + "'", null);
					LFiltersCriteria fCriteria = new LFiltersCriteria();
					fCriteria.codename.eq(codename);
					LFilters dbFilter = LFiltersFactory.loadLFiltersByCriteria(fCriteria);
					if (dbFilter == null) {
						System.out.println(" CREATE");
						dbFilter = LFiltersFactory.createLFilters();
						dbFilter.setName(filter.getName());
						dbFilter.setCodename(codename);
						dbFilter.save();
					} else {
						if (!dbFilter.getName().equals(name)) {
							dbFilter.setName(name);
							dbFilter.save();
							System.out.println(" UPDATE (changed name)");
						} else {
							System.out.println(" SKIP (already exists)");
						}
					}

					fNICriteria.codename.ne(codename);
					//					
					//					if (items.length() > 0) {
					//						items.append(", ");
					//					}
					//					items.append("'").append(codename).append("'");
				}

				// Now we must clear non-existed filters (records with codenames
				// not
				// present in our classes).
				// And only if that filters not using in table data
				//				if (items.length() > 0) {
				for (LFilters checkFilter : LFiltersFactory.listLFiltersByCriteria(fNICriteria)) {
					LocusAppliedFilters found = LocusAppliedFiltersFactory.loadLocusAppliedFiltersByQuery(
							"LFiltersIdLFilter = " + checkFilter.getIdLFilter(), null);
					if (found == null) {
						System.out.println("Filter " + checkFilter.getCodename() + " (" + checkFilter.getName()
								+ ") stored in database does not have reference "
								+ "in LocusFilters table. Removing unexisted class reference.");
						checkFilter.delete();
					}
				}
				//				}

				// Processing modules, the same way
				//				items = new StringBuilder();
				Collection<Class<?>> moduleClasses = ClassesHelper.getAvailableClasses(IModule.class);
				System.out.println("Registering modules (" + moduleClasses.size() + " expected)...");

				LModulesCriteria mNICriteria = new LModulesCriteria();
				for (Class<?> clazz : moduleClasses) {
					IModule module = (IModule) clazz.newInstance();
					String name = module.getName();
					String codename = module.getClass().getCanonicalName();

					System.out.print("Checking module class " + codename + " (" + module.getName() + ")...");

					LModulesCriteria mCriteria = new LModulesCriteria();
					mCriteria.codename.eq(codename);
					//LModules dbModule = LModulesFactory.loadLModulesByQuery("codename = '" + codename + "'", null);
					LModules dbModule = LModulesFactory.loadLModulesByCriteria(mCriteria);
					if (dbModule == null) {
						System.out.println(" CREATE");
						dbModule = LModulesFactory.createLModules();
						dbModule.setName(module.getName());
						dbModule.setCodename(codename);
						dbModule.save();
					} else {
						if (!dbModule.getName().equals(name)) {
							dbModule.setName(name);
							dbModule.save();
							System.out.println(" UPDATE (changed name)");
						} else {
							System.out.println(" SKIP (already exists)");
						}

					}

					mNICriteria.codename.ne(codename);

					//					if (items.length() > 0) {
					//						items.append(", ");
					//					}
					//					items.append("'").append(codename).append("'");
				}

				// And removing unnecessary modules
				//				if (items.length() > 0) {
				for (LModules checkModule : LModulesFactory.listLModulesByCriteria(mNICriteria)) {
					LocusAppliedModule found = LocusAppliedModuleFactory.loadLocusAppliedModuleByQuery(
							"LModulesIDLModule = " + checkModule.getIdLModule(), null);
					if (found == null) {
						System.out.println("Module " + checkModule.getCodename() + " (" + checkModule.getName()
								+ ") stored in database does not have reference "
								+ "in Locuses table. Removing unexisted class reference.");
						checkModule.delete();
					}
				}
				//				}

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
		System.out.println(Const.FULL_PROGRAM_NAME);
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
