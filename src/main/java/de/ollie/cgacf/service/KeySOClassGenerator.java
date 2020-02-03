package de.ollie.cgacf.service;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.AbstractCodeGenerator;

/**
 * A code generator for key service object classes.
 *
 * @author ollie (19.12.2019)
 */
public class KeySOClassGenerator extends AbstractCodeGenerator {

	public KeySOClassGenerator(NameManager nameManager, TypeManager typeManager) {
		super(nameManager, typeManager);
	}

	public String generate(String templatePath, String basePackageName, TableSO table) throws Exception {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(templatePath + "/KeySOClass.vm");
		VelocityContext context = new VelocityContext();
		context.put("BasePackageName", basePackageName);
		context.put("NamesProvider", this.nameManager.getKeySONamesProvider(table));
		context.put("EnumIdentifier", getKeyValues(table));
		context.put("PluralName", this.nameManager.getPluralName(table));
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}

	private List<String> getKeyValues(TableSO table) {
		return table.getMetaInfo().getOptions() //
				.stream() //
				.filter(option -> option.getName().equals("KEY_VALUES")) //
				.flatMap(option -> Arrays.asList(StringUtils.split(option.getValue(), ",\n\t ")).stream()) //
				.collect(Collectors.toList()) //
		;
	}

}