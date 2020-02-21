package de.ollie.chalkous9cp.service;

import de.ollie.chalkous9cp.service.so.AdventurerKeySO;
import de.ollie.chalkous9cp.service.so.WayOfLifeKeySO;
import lombok.Generated;

/**
 * A service for adventurer data management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface GeneratedAdventurerService extends AdventurerService {

	void setWayOfLife(AdventurerKeySO key, WayOfLifeKeySO wayOfLife);

	void setName(AdventurerKeySO key, String name);

}