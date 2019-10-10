package de.ollie.cgacf;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.ReplaceManager;
import de.ollie.archimedes.alexandrian.service.TableSO;

/**
 * A general replace manager for the CG code factory.
 *
 * @author ollie (09.10.2019)
 */
public class GeneralReplaceManager {

	private static NameManager nameManager = new NameManager();

	/**
	 * Returns a new general replace manager for the CG code factory.
	 * 
	 * @param table The table which the general replace manager is to create for.
	 * @return A new general replace manager for the CG code factory.
	 */
	public static ReplaceManager create(TableSO table) {
		return new ReplaceManager() //
				.add("CamilCaseName", nameManager.getClassName(table)) //
				.add("KeySOClassName", nameManager.getKeySOClassName(table)) //
				.add("KeySOPackageName", nameManager.getKeySOPackageNameSuffix()) //
				.add("LowerCaseName", table.getName().toLowerCase()) //
				.add("SOClassName", nameManager.getSOClassName(table)) //
				.add("SOPackageName", nameManager.getSOPackageNameSuffix()) //
		;
	}

}