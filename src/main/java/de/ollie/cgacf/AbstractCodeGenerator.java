package de.ollie.cgacf;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;

/**
 * A base class for CG code generators.
 *
 * @author ollie (10.10.2019)
 */
abstract public class AbstractCodeGenerator {

	protected final NameManager nameManager;
	protected final TypeManager typeManager;

	public AbstractCodeGenerator(NameManager nameManager, TypeManager typeManager) {
		super();
		this.nameManager = nameManager;
		this.typeManager = typeManager;
	}

	public abstract String generate(String templatePath, String basePackageName, TableSO table) throws Exception;

}