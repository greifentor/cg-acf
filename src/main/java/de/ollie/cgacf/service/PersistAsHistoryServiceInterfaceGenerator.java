package de.ollie.cgacf.service;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;
import de.ollie.cgacf.service.data.SimpleAttributeData;

/**
 * A code generator for service interfaces in persist as history mode.
 *
 * @author ollie (17.02.2020)
 */
public class PersistAsHistoryServiceInterfaceGenerator extends AbstractCodeGenerator {

	public PersistAsHistoryServiceInterfaceGenerator(NameManager nameManager, TypeManager typeManager) {
		super("PersistAsHistoryServiceInterfaceGenerator", nameManager, typeManager);
	}

	@Override
	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(templatePath + "/PersistAsHistoryServiceInterface.vm");
		VelocityContext context = new VelocityContext();
		context.put("BasePackageName", basePackageName);
		context.put("KeySONames", this.nameManager.getKeySONamesProvider(table));
		context.put("NamesProvider", this.nameManager.getGeneratedServiceInterfaceNamesProvider(table));
		context.put("PluralName", this.nameManager.getPluralName(table));
		context.put("ServiceInterface", this.nameManager.getServiceInterfaceNamesProvider(table));
		context.put("SimpleAttributes", this.getSimpleAttributes(table));
		context.put("SingularName", this.nameManager.getClassName(table).toLowerCase());
		context.put("SONames", this.nameManager.getSONamesProvider(table));
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}

	private List<SimpleAttributeData> getSimpleAttributes(TableSO table) {
		return table.getColumns() //
				.stream() //
				.filter(column -> column.getName().equals("NAME")) //
				.map(column -> new SimpleAttributeData("name", "AdventurerKeySO", "setName", "String")) //
				.collect(Collectors.toList()) //
		;
	}

	@Override
	public Set<String> getGenerationTypes() {
		return new HashSet<>(Arrays.asList(GenerationType.PERSIST_AS_HISTORY.name()));
	}

}