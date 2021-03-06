package de.ollie.cgacf.service;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;

/**
 * A code generator for service impl classes in key storage mode.
 *
 * @author ollie (10.10.2019)
 */
public class KeyStorageServiceImplClassGenerator extends AbstractCodeGenerator {

	public KeyStorageServiceImplClassGenerator(NameManager nameManager, TypeManager typeManager) {
		super("KeyStorageServiceImplClassGenerator", nameManager, typeManager);
	}

	@Override
	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(templatePath + "/KeyStorageServiceImplClass.vm");
		VelocityContext context = new VelocityContext();
		context.put("BasePackageName", basePackageName);
		context.put("KeySONames", this.nameManager.getKeySONamesProvider(table));
		context.put("NamesProvider", this.nameManager.getServiceImplClassNamesProvider(table));
		context.put("PluralName", this.nameManager.getPluralName(table));
		context.put("ServiceInterface", this.nameManager.getServiceInterfaceNamesProvider(table));
		context.put("SingularName", this.nameManager.getClassName(table).toLowerCase());
		context.put("SONames", this.nameManager.getSONamesProvider(table));
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}

	@Override
	public Set<String> getGenerationTypes() {
		return new HashSet<>(Arrays.asList(GenerationType.KEY_STORAGE.name()));
	}

}