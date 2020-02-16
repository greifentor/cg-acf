package de.ollie.cgacf;

import java.util.HashSet;
import java.util.Set;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;

/**
 * A base class for CG code generators.
 *
 * @author ollie (10.10.2019)
 */
abstract public class AbstractCodeGenerator { // NOSONAR

	protected final NameManager nameManager;
	protected final TypeManager typeManager;

	private final String name;

	public AbstractCodeGenerator(String name, NameManager nameManager, TypeManager typeManager) {
		super();
		this.name = name;
		this.nameManager = nameManager;
		this.typeManager = typeManager;
	}

	public abstract String generate(String templatePath, String basePackageName, TableSO table) throws Exception;

	public Set<String> getGenerationTypes() {
		return new HashSet<>();
	}

	public String getName() {
		return this.name;
	}

}