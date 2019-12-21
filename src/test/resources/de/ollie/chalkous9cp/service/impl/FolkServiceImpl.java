package de.ollie.chalkous9cp.service.impl;

import static de.ollie.utils.Check.ensure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.ollie.chalkous9cp.service.FolkService;
import de.ollie.chalkous9cp.service.so.FolkKeySO;
import de.ollie.chalkous9cp.service.so.FolkSO;

/**
 * An implementation of the interface "FolkService".
 */
public class FolkServiceImpl implements FolkService {

	private Map<FolkKeySO, FolkSO> folks = new HashMap<>();

	public FolkServiceImpl() {
		super();
		FolkServiceInitializer.addSOs(this);
	}

	public void add(FolkSO so) {
		this.folks.put(so.getKey(), so);
	}

	@Override
	public Optional<FolkSO> findForKey(FolkKeySO key) {
		ensure(key != null, "key cannot be null.");
		FolkSO so = this.folks.get(key);
		if (so != null) {
			return Optional.of(so);
		}
		return Optional.empty();
	}

	@Override
	public FolkKeySO[] getAllKeys() {
		return this.folks.keySet().toArray(new FolkKeySO[this.folks.size()]);
	}

}