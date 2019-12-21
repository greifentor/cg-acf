package de.ollie.cgacf.service;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;

/**
 * A code generator for service impl classes.
 *
 * @author ollie (10.10.2019)
 */
public class ServiceImplClassGenerator extends AbstractCodeGenerator {

	public ServiceImplClassGenerator(NameManager nameManager, TypeManager typeManager) {
		super(nameManager, typeManager);
	}

	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		/*
		 * String code = readTemplate(templatePath, "ServiceImplClass.template"); ImportManager imports = new
		 * ImportManager() // .add("${BasePackageName}." + this.nameManager.getKeySOPackageNameSuffix() + "." +
		 * this.nameManager.getKeySOClassName(table)) // .add("${BasePackageName}." +
		 * this.nameManager.getSOPackageNameSuffix() + "." + this.nameManager.getSOClassName(table)); // code = new
		 * ReplaceManager() // // .add("ServiceInterface-SettersCodeBlock", getSettersCodeBlock(templatePath, table)) //
		 * // .replace(code); code = new ReplaceManager() // .add("DynamicImports", imports.toCode()) // .replace(code);
		 * code = GeneralReplaceManager.create(table) // .add("AuthorName", "ollie") // .add("BasePackageName",
		 * basePackageName) // .add("ClassName", this.nameManager.getServiceInterfaceName(table)) //
		 * .add("ClassPackageName", this.nameManager.getServiceInterfacePackageSuffix()) // .replace(code); return code;
		 */
		return "";
	}
}