package de.ollie.cgacf.service;

import java.io.IOException;

import de.ollie.acf.utils.ImportManager;
import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.ReplaceManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;
import de.ollie.cgacf.GeneralReplaceManager;

/**
 * A class generator for service interfaces.
 *
 * @author ollie (09.10.2019)
 */
public class ServiceInterfaceGenerator extends AbstractCodeGenerator {

	public ServiceInterfaceGenerator(NameManager nameManager, TypeManager typeManager) {
		super(nameManager, typeManager);
	}

	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		String code = readTemplate(templatePath, "ServiceInterface.template");
		ImportManager imports = new ImportManager() //
				.add("${BasePackageName}." + this.nameManager.getKeySOPackageNameSuffix() + "."
						+ this.nameManager.getKeySOClassName(table)) //
				.add("${BasePackageName}." + this.nameManager.getSOPackageNameSuffix() + "."
						+ this.nameManager.getSOClassName(table));
		code = new ReplaceManager() //
				.add("ServiceInterface-SettersCodeBlock", getSettersCodeBlock(templatePath, table)) //
				.replace(code);
		code = new ReplaceManager() //
				.add("DynamicImports", imports.toCode()) //
				.replace(code);
		code = GeneralReplaceManager.create(table) //
				.add("AuthorName", "ollie") //
				.add("BasePackageName", basePackageName) //
				.add("ClassName", this.nameManager.getServiceInterfaceName(table)) //
				.add("ClassPackageName", this.nameManager.getServiceInterfacePackageSuffix()) //
				.replace(code);
		return code;
	}

	private String getSettersCodeBlock(String templatePath, TableSO table) throws IOException {
		StringBuilder code = new StringBuilder();
		String template = readTemplate(templatePath, "ServiceInterface-SettersCodeBlock.template");
		table.getColumns().forEach(column -> {
			String attributeName = nameManager.columnNameToAttributeName(column);
			String attributeJavaTypeName = typeManager.typeSOToTypeString(column);
			code.append(new ReplaceManager() //
					.add("AttributeJavaType", attributeJavaTypeName) //
					.add("CamilCaseAttributeName", nameManager.firstCharToUpperCase(attributeName)) //
					.add("KeySOClassName", nameManager.getKeySOClassName(table)) //
					.add("LowerCaseAttributeName", attributeName.toLowerCase()) //
					.add("LowerCaseName", nameManager.getClassName(table).toLowerCase()) //
					.replace(template)).append("\n\n");
		});
		return code.toString();
	}

}