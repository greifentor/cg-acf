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

import de.ollie.acf.utils.ImportManager;
import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;
import de.ollie.cgacf.service.data.SimpleAttributeData;

/**
 * A code generator for service impl class in persist as history mode.
 *
 * @author ollie (19.02.2020)
 */
public class PersistAsHistoryServiceImplClassGenerator extends AbstractCodeGenerator {

	public PersistAsHistoryServiceImplClassGenerator(NameManager nameManager, TypeManager typeManager) {
		super("PersistAsHistoryServiceImplClassGenerator", nameManager, typeManager);
	}

	@Override
	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(templatePath + "/PersistAsHistoryServiceImplClass.vm");
		VelocityContext context = new VelocityContext();
		ImportManager importManager = new ImportManager();
		context.put("BasePackageName", basePackageName);
		context.put("BasicClassName", this.nameManager.getClassName(table));
		context.put("GeneratedServiceInterface", this.nameManager.getGeneratedServiceInterfaceNamesProvider(table));
		context.put("KeySONames", this.nameManager.getKeySONamesProvider(table));
		context.put("NamesProvider", this.nameManager.getGeneratedServiceImplClassNamesProvider(table));
		context.put("PluralName", this.nameManager.getPluralName(table));
		context.put("ServiceImplClass", this.nameManager.getServiceImplClassNamesProvider(table));
		context.put("SimpleAttributes", this.getSimpleAttributes(table, importManager));
		context.put("SingularName", this.nameManager.getClassName(table).toLowerCase());
		context.put("SONames", this.nameManager.getSONamesProvider(table));
		context.put("AdditionalImports", importManager);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}

	private List<SimpleAttributeData> getSimpleAttributes(TableSO table, ImportManager importManager) {
		return table.getColumns() //
				.stream() //
				.filter(column -> !column.isPkMember()) //
				.map(column -> {
					getReferencedTable(column).ifPresentOrElse(rt -> {
						importManager.add(this.nameManager.getKeySONamesProvider(rt).getQualifiedName());
						importManager.add("service.so.actions.adventurer.Adventurer" + nameManager.getClassName(rt)
								+ "ChangeActionSO");
					}, () -> importManager.add("service.so.actions.adventurer.Adventurer"
							+ nameManager.getClassName(column.getName()) + "ChangeActionSO"));
					return new SimpleAttributeData( //
							this.nameManager.columnNameToAttributeName(column), //
							this.nameManager.getClassName(column.getName()), //
							this.nameManager.getKeySONamesProvider(table).getClassName(), //
							"set" + this.nameManager.getClassName(column.getName()), //
							getTypeName(column));
				}) //
				.collect(Collectors.toList()) //
		;
	}

	@Override
	public Set<String> getGenerationTypes() {
		return new HashSet<>(Arrays.asList(GenerationType.PERSIST_AS_HISTORY.name()));
	}

}