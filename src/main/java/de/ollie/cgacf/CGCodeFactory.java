package de.ollie.cgacf;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import archimedes.acf.checker.ModelChecker;
import archimedes.acf.event.CodeFactoryListener;
import archimedes.gui.checker.ModelCheckerMessageListFrameListener;
import archimedes.legacy.acf.event.CodeFactoryProgressionEvent;
import archimedes.legacy.acf.event.CodeFactoryProgressionEventProvider;
import archimedes.legacy.acf.event.CodeFactoryProgressionListener;
import archimedes.legacy.acf.gui.StandardCodeFactoryProgressionFrameUser;
import archimedes.model.CodeFactory;
import archimedes.model.DataModel;
import baccara.gui.GUIBundle;
import de.ollie.acf.utils.DataModelToSOConverter;
import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.NamesProvider;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.DatabaseSO;
import de.ollie.archimedes.alexandrian.service.SchemeSO;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.service.KeySOClassGenerator;
import de.ollie.cgacf.service.KeyStorageServiceImplClassGenerator;
import de.ollie.cgacf.service.KeyStorageServiceInterfaceGenerator;
import de.ollie.cgacf.service.PersistAsHistoryServiceInterfaceGenerator;

/**
 * A basic class for all CG code generators.
 *
 * @author O.Lieshoff (09.10.2019)
 */
public class CGCodeFactory
		implements CodeFactory, CodeFactoryProgressionEventProvider, StandardCodeFactoryProgressionFrameUser {

	public static final String DO_NOT_GENERATE_OPTION = "DO_NOT_GENERATE";
	public static final String NOT_TO_OVERRIDE_MARK = "GENERATED CODE !!! DO NOT CHANGE !!!";

	private static final String TEMPLATE_PATH = "src/main/resources/templates";

	private static final String FACTORY_NAME = "Charakter Generator Code Factory";
	private static final String LABEL_STARTING = "starting";
	private static final int MAX_PROCESSES = 4;

	private static final Logger LOG = Logger.getLogger(CGCodeFactory.class);

	private static final String PATH_SEPARATOR = "/";

	private DataModel dataModel = null;
	private GUIBundle guiBundle = null;
	private List<CodeFactoryListener> listeners = new ArrayList<>();
	private List<CodeFactoryProgressionListener> progressionListener = new ArrayList<>();
	private NameManager nameManager = new NameManager();
	private TypeManager typeManager = new TypeManager();

	@Override
	public void addCodeFactoryListener(CodeFactoryListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void addCodeFactoryProgressionListener(CodeFactoryProgressionListener listener) {
		this.progressionListener.add(listener);
	}

	@Override
	public boolean generate(String path) {
		LOG.info("Started code generation");
		new File(path).mkdirs();
		DatabaseSO database = new DataModelToSOConverter().convert(this.dataModel);
		String basePackageName = this.dataModel.getBasePackageName();
		fireCodeFactoryProgressionEvent(new CodeFactoryProgressionEvent(FACTORY_NAME, "Service Impl Classes",
				LABEL_STARTING + " service impl classes generation", 0, null, MAX_PROCESSES, null));
		createSourceFiles(database, path, basePackageName,
				new KeyStorageServiceImplClassGenerator(this.nameManager, this.typeManager),
				this.nameManager::getServiceImplClassNamesProvider);
		fireCodeFactoryProgressionEvent(new CodeFactoryProgressionEvent("Service Interfaces", null,
				LABEL_STARTING + " service interfaces generation", null, 1, null, MAX_PROCESSES));
		createSourceFiles(database, path, basePackageName,
				new KeyStorageServiceInterfaceGenerator(this.nameManager, this.typeManager),
				this.nameManager::getServiceInterfaceNamesProvider);
		fireCodeFactoryProgressionEvent(new CodeFactoryProgressionEvent("Key SO Classes", null,
				LABEL_STARTING + " key SO classes generation", null, 2, null, MAX_PROCESSES));
		createSourceFiles(database, path, basePackageName, new KeySOClassGenerator(this.nameManager, this.typeManager),
				this.nameManager::getKeySONamesProvider);
		fireCodeFactoryProgressionEvent(
				new CodeFactoryProgressionEvent("Service Impl Classes", null, "done", null, 3, null, MAX_PROCESSES));
		createSourceFiles(database, path, basePackageName,
				new PersistAsHistoryServiceInterfaceGenerator(this.nameManager, this.typeManager),
				this.nameManager::getGeneratedServiceInterfaceNamesProvider);
		fireCodeFactoryProgressionEvent(new CodeFactoryProgressionEvent("Generated Service Interface", null, "done",
				null, 4, null, MAX_PROCESSES));
		return false;
	}

	private void createSourceFiles(DatabaseSO database, String path, String basePackageName,
			AbstractCodeGenerator generator, Function<TableSO, NamesProvider> namesProviderGetter) {
		fireCodeFactoryProgressionEvent(
				new CodeFactoryProgressionEvent(null, null, null, 1, null, countTables(database), null));
		int counter = 1;
		for (SchemeSO scheme : database.getSchemes()) {
			for (TableSO table : scheme.getTables()) {
				final MessageProvider message = new MessageProvider();
				if (table.getOptionWithName(DO_NOT_GENERATE_OPTION).isEmpty()) {
					table.getOptionWithName("GENERATE").ifPresent(s -> {
						if (generator.getGenerationTypes().contains(s.getValue())) {
							message.setMessage(
									generateForTable(table, path, basePackageName, generator, namesProviderGetter));
						} else {
							message.setMessage("excluded table '" + table.getName() + "' with generation type: "
									+ s.getValue() + " not in " + generator.getGenerationTypes());
						}
					});
				} else {
					message.setMessage("no generate: table=" + table.getName() + ", generator=" + generator.getName());
				}
				fireCodeFactoryProgressionEvent(new CodeFactoryProgressionEvent(null, table.getName(),
						message.getMessage(), counter++, null, null, null));
			}
		}
	}

	private int countTables(DatabaseSO database) {
		return database.getSchemes() //
				.stream() //
				.map(scheme -> scheme.getTables().size()) //
				.collect(Collectors.summingInt(Integer::intValue));
	}

	private void fireCodeFactoryProgressionEvent(CodeFactoryProgressionEvent event) {
		this.progressionListener.forEach(listener -> {
			try {
				listener.progressionDetected(event);
			} catch (Exception e) {
				LOG.error("error while firing code factory progression event: " + event + ", listener: " + listener
						+ ", exception: " + e.getClass().getSimpleName() + "(" + e.getMessage() + ")");
			}
		});
	}

	private String generateForTable(TableSO table, String path, String basePackageName, AbstractCodeGenerator generator,
			Function<TableSO, NamesProvider> namesProviderGetter) {
		String message = null;
		try {
			NamesProvider namesProvider = namesProviderGetter.apply(table);
			String code = generator.generate(TEMPLATE_PATH, basePackageName, table);
			String fileName = namesProvider.getClassName() + ".java";
			String p = path + PATH_SEPARATOR + basePackageName.replace(".", PATH_SEPARATOR) + PATH_SEPARATOR
					+ namesProvider.getPackageName().replace(".", PATH_SEPARATOR);
			if (new File(p + PATH_SEPARATOR + fileName).exists()) {
				String existingFileContent = Files.readString(Paths.get(p + PATH_SEPARATOR + fileName));
				if (!existingFileContent.contains(NOT_TO_OVERRIDE_MARK)) {
					LOG.info("ignored: " + p + PATH_SEPARATOR + fileName);
					return "ignored: " + p + PATH_SEPARATOR + fileName;
				}
			}
			LOG.info("creating: " + p);
			new File(p).mkdirs();
			Files.write(Paths.get(p + PATH_SEPARATOR + fileName), code.getBytes(), StandardOpenOption.WRITE,
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			LOG.info(p + PATH_SEPARATOR + fileName);
			message = "written: " + p + PATH_SEPARATOR + fileName;
		} catch (Exception e) {
			LOG.error("error while generating codr for table '" + table + "' with generator: " + generator, e);
			message = "error while generating codr for table '" + table + "' with generator: " + generator;
		}
		return message;
	}

	@Override
	public void removeCodeFactoryListener(CodeFactoryListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	@Override
	public GUIBundle getGUIBundle() {
		return this.guiBundle;
	}

	@Override
	public ModelChecker[] getModelCheckers() {
		return new ModelChecker[] { //
		};
	}

	@Override
	public String getName() {
		return "Character Generator Code Factory";
	}

	@Override
	public String[] getResourceBundleNames() {
		return new String[] { //
				"cgcf" //
		};
	}

	@Override
	public String getVersion() {
		return "1.1.1";
	}

	@Override
	public void removeCodeFactoryProgressionListener(CodeFactoryProgressionListener listener) {
		this.progressionListener.add(listener);
	}

	@Override
	public void setGUIBundle(GUIBundle guiBundle) {
		this.guiBundle = guiBundle;
	}

	@Override
	public void setModelCheckerMessageListFrameListeners(ModelCheckerMessageListFrameListener... arg0) {
		// TODO Auto-generated method stub

	}

}

class MessageProvider {

	private String message = null;

	MessageProvider() {
		super();
	}

	String getMessage() {
		return this.message;
	}

	void setMessage(String message) {
		this.message = message;
	}

}