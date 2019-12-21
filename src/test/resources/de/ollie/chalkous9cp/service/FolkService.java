package de.ollie.chalkous9cp.service;

import java.util.Optional;

import de.ollie.chalkous9cp.service.so.FolkKeySO;
import de.ollie.chalkous9cp.service.so.FolkSO;

/**
 * A service for folk data management.
 */
@Generated
public interface FolkService {

	/**
	 * Returns the folk service object for the passed folk key service object.
	 * 
	 * @param key The folk key service object which the folk is to return for.
	 * @return An optional with the folk service object for the passed folk key service object.
	 * @throws IllegalArgumentException If an invalid key is passed (empty or null).
	 */
	Optional<FolkSO> findForKey(FolkKeySO key);

	/**
	 * Returns all available folk keys.
	 * 
	 * @return All available folk keys.
	 */
	FolkKeySO[] getAllKeys();

}