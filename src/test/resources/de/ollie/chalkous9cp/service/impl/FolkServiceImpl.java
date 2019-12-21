package de.ollie.chalkous9cp.service.impl;

import static de.ollie.utils.Check.ensure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import de.ollie.chalkous9cp.service.FolkService;
import de.ollie.chalkous9cp.service.ObjectAlreadyExistsException;
import de.ollie.chalkous9cp.service.ObjectNotFoundException;
import de.ollie.chalkous9cp.service.ObjectType;
import de.ollie.chalkous9cp.service.so.FolkKeySO;
import de.ollie.chalkous9cp.service.so.FolkSO;
import de.ollie.chalkous9cp.service.so.actions.folk.FolkChangeActionSO;
import de.ollie.chalkous9cp.service.so.actions.folk.FolkNameChangeActionSO;

/**
 * An implementation of the interface "FolkService".
 *
 * @author ollie (03.10.2019)
 */
public class FolkServiceImpl implements FolkService {

	private Map<FolkKeySO, List<FolkChangeActionSO>> folks = new HashMap<>();

	@Override
	public FolkKeySO createFolk(String key) throws ObjectAlreadyExistsException {
		ensure(key != null, "key cannot be null.");
		ensure(!key.isEmpty(), "key cannot be empty.");
		FolkKeySO folkKey = new FolkKeySO(key);
		if (this.folks.containsKey(folkKey)) {
			throw new ObjectAlreadyExistsException(key, ObjectType.FOLK);
		}
		List<FolkChangeActionSO> l = new ArrayList<>();
		l.add(new FolkChangeActionSO("Created."));
		this.folks.put(folkKey, l);
		return folkKey;
	}

	private Optional<List<FolkChangeActionSO>> getChangeActions(FolkKeySO key) {
		ensure(key != null, "key cannot be null.");
		List<FolkChangeActionSO> l = this.folks.get(key);
		if (l != null) {
			return Optional.of(l);
		}
		return Optional.empty();
	}

	@Override
	public Optional<FolkSO> getFolk(FolkKeySO key) {
		Optional<FolkSO> result = Optional.empty();
		Optional<List<FolkChangeActionSO>> l = getChangeActions(key);
		if (l.isPresent()) {
			result = Optional.of(getFolkFromChangeActions(l.get()));
		}
		return result;
	}

	private FolkSO getFolkFromChangeActions(List<FolkChangeActionSO> cas) {
		FolkSO folk = new FolkSO();
		for (FolkChangeActionSO ca : cas) {
			if (ca instanceof FolkNameChangeActionSO) {
				folk.setName(((FolkNameChangeActionSO) ca).getName());
			}
		}
		return folk;
	}

	@Override
	public void setName(final FolkKeySO key, final String name) {
		ensure(name != null, "name cannot be null.");
		getChangeActions(key).ifPresentOrElse(new Consumer<List<FolkChangeActionSO>>() {
			@Override
			public void accept(List<FolkChangeActionSO> f) {
				f.add(new FolkNameChangeActionSO(name));
			}
		}, new Runnable() {
			@Override
			public void run() {
				throw new ObjectNotFoundException(key.getValue(), ObjectType.FOLK);
			}
		});
	}

}