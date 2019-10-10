package de.ollie.chalkous9cp.service;

import java.util.Optional;

import de.ollie.chalkous9cp.service.so.FolkKeySO;
import de.ollie.chalkous9cp.service.so.FolkSO;

/**
 * A service for folk data management.
 *
 * @author ollie (generated)
 */
public interface FolkService {

	/**
	 * Creates a new folk for the passed key.
	 * 
	 * @param key The key which the folk is to create for.
	 * @return A folk key service object for the new folk.
	 * @throws IllegalArgumentException     If an invalid key is passed (empty or null).
	 * @throws ObjectAlreadyExistsException If an object is already existing for the passed key.
	 */
	FolkKeySO createFolk(String key) throws ObjectAlreadyExistsException;

	/**
	 * Returns the folk service object for the passed folk key service object.
	 * 
	 * @param key The folk key service object which the folk is to return for.
	 * @return An optional with the folk service object for the passed folk key service object.
	 * @throws IllegalArgumentException If an invalid key is passed (empty or null).
	 */
	Optional<FolkSO> getFolk(FolkKeySO key);

	/**
	 * Sets a new id for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which id is to change for.
	 * @param id The id to set.
	 * @throws IllegalArgumentException If key or id is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setId(FolkKeySO key, long id);

	/**
	 * Sets a new baseau for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which baseau is to change for.
	 * @param baseau The baseau to set.
	 * @throws IllegalArgumentException If key or baseau is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseAu(FolkKeySO key, Integer baseau);

	/**
	 * Sets a new basech for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basech is to change for.
	 * @param basech The basech to set.
	 * @throws IllegalArgumentException If key or basech is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseCh(FolkKeySO key, Integer basech);

	/**
	 * Sets a new basege for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basege is to change for.
	 * @param basege The basege to set.
	 * @throws IllegalArgumentException If key or basege is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseGe(FolkKeySO key, Integer basege);

	/**
	 * Sets a new basein for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basein is to change for.
	 * @param basein The basein to set.
	 * @throws IllegalArgumentException If key or basein is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseIn(FolkKeySO key, Integer basein);

	/**
	 * Sets a new baseko for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which baseko is to change for.
	 * @param baseko The baseko to set.
	 * @throws IllegalArgumentException If key or baseko is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseKo(FolkKeySO key, Integer baseko);

	/**
	 * Sets a new basema for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basema is to change for.
	 * @param basema The basema to set.
	 * @throws IllegalArgumentException If key or basema is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseMa(FolkKeySO key, Integer basema);

	/**
	 * Sets a new basesd for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basesd is to change for.
	 * @param basesd The basesd to set.
	 * @throws IllegalArgumentException If key or basesd is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseSd(FolkKeySO key, Integer basesd);

	/**
	 * Sets a new basest for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which basest is to change for.
	 * @param basest The basest to set.
	 * @throws IllegalArgumentException If key or basest is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setBaseSt(FolkKeySO key, Integer basest);

	/**
	 * Sets a new name for the folk with the passed key.
	 * 
	 * @param key  The folk key service object which name is to change for.
	 * @param name The name to set.
	 * @throws IllegalArgumentException If key or name is passed as null.
	 * @throws ObjectNotFoundException  If there is no folk for the passed key.
	 */
	void setName(FolkKeySO key, String name);

}