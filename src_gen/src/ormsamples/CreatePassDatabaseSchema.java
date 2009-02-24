/**
 * Licensee: Raidan Flk
 * License Type: Evaluation
 */
package ormsamples;

import org.orm.*;
public class CreatePassDatabaseSchema {
	public static void main(String[] args) {
		try {
			ORMDatabaseInitiator.createSchema(edu.mgupi.pass.db.surfaces.PassPersistentManager.instance());
			edu.mgupi.pass.db.surfaces.PassPersistentManager.instance().disposePersistentManager();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
