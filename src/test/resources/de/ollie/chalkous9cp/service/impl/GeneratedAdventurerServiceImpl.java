package de.ollie.chalkous9cp.service.impl;

import static de.ollie.utils.Check.ensure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.ollie.chalkous9cp.service.GeneratedAdventurerService;
import de.ollie.chalkous9cp.service.ObjectNotFoundException;
import de.ollie.chalkous9cp.service.ObjectType;
import de.ollie.chalkous9cp.service.so.AdventurerKeySO;
import de.ollie.chalkous9cp.service.so.actions.adventurer.AdventurerChangeActionSO;
import de.ollie.chalkous9cp.service.so.WayOfLifeKeySO;
import de.ollie.chalkous9cp.service.so.actions.adventurer.AdventurerWayOfLifeChangeActionSO;
import de.ollie.chalkous9cp.service.so.actions.adventurer.AdventurerNameChangeActionSO;
import lombok.Generated;

/**
 * A basic service for adventurer data management.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class GeneratedAdventurerServiceImpl implements GeneratedAdventurerService {

	protected Map<AdventurerKeySO, List<AdventurerChangeActionSO>> adventurers = new HashMap<>();

	@Override
	public void setWayOfLife(final AdventurerKeySO key, final WayOfLifeKeySO wayOfLife) {
		ensure(wayOfLife != null, "wayOfLife cannot be null.");
		getChangeActions(key).ifPresentOrElse( //
				actions -> actions.add(new AdventurerWayOfLifeChangeActionSO(wayOfLife)), //
				() -> {
					throw new ObjectNotFoundException(key.getValue(), ObjectType.ADVENTURER);
				});
	}

	@Override
	public void setName(final AdventurerKeySO key, final String name) {
		ensure(name != null, "name cannot be null.");
		getChangeActions(key).ifPresentOrElse( //
				actions -> actions.add(new AdventurerNameChangeActionSO(name)), //
				() -> {
					throw new ObjectNotFoundException(key.getValue(), ObjectType.ADVENTURER);
				});
	}

	protected Optional<List<AdventurerChangeActionSO>> getChangeActions(AdventurerKeySO key) {
		ensure(key != null, "key cannot be null.");
		List<AdventurerChangeActionSO> l = this.adventurers.get(key);
		if (l != null) {
			return Optional.of(l);
		}
		return Optional.empty();
	}

}