package de.ollie.cgacf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;

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

	/**
	 * Reads the template with the passed name from the template path.
	 * 
	 * @param templatePath     The path of the template files.
	 * @param templateFileName The name of the template file to read.
	 * @return Returns the read template file content as a string.
	 * @throws IOException If an error occurs while reading the file.
	 */
	protected String readTemplate(String templatePath, String templateFileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(templatePath + "/" + templateFileName));
		StringBuilder code = new StringBuilder();
		lines.forEach(line -> code.append(line).append("\n"));
		return (code.length() > 0 ? code.toString().substring(0, code.length() - 1) : "");
	}

}