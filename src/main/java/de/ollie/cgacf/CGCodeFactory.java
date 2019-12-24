package de.ollie.cgacf;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.log4j.Logger;

import archimedes.acf.checker.ModelChecker;
import archimedes.acf.event.CodeFactoryListener;
import archimedes.gui.checker.ModelCheckerMessageListFrameListener;
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
import de.ollie.cgacf.service.ServiceImplClassGenerator;
import de.ollie.cgacf.service.ServiceInterfaceGenerator;

/**
 * A basic class for all CG code generators.
 *
 * @author O.Lieshoff (09.10.2019)
 */
public class CGCodeFactory implements CodeFactory {

	private static final String TEMPLATE_PATH = "src/main/resources/templates";

	private static final Logger LOG = Logger.getLogger(CGCodeFactory.class);

	private static final String PATH_SEPARATOR = "/";

	private DataModel dataModel = null;
	private GUIBundle guiBundle = null;
	private List<CodeFactoryListener> listeners = new ArrayList<>();
	private NameManager nameManager = new NameManager();
	private TypeManager typeManager = new TypeManager();

	@Override
	public void addCodeFactoryListener(CodeFactoryListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public boolean generate(String path) {
		LOG.info("Started code generation");
		new File(path).mkdirs();
		DatabaseSO database = new DataModelToSOConverter().convert(this.dataModel);
		String basePackageName = this.dataModel.getBasePackageName();
		createSourceFiles(database, path, basePackageName,
				new ServiceImplClassGenerator(this.nameManager, this.typeManager),
				this.nameManager::getServiceImplClassNamesProvider);
		createSourceFiles(database, path, basePackageName,
				new ServiceInterfaceGenerator(this.nameManager, this.typeManager),
				this.nameManager::getServiceInterfaceNamesProvider);
		createSourceFiles(database, path, basePackageName, new KeySOClassGenerator(this.nameManager, this.typeManager),
				this.nameManager::getKeySONamesProvider);
		return false;
	}

	private void createSourceFiles(DatabaseSO database, String path, String basePackageName,
			AbstractCodeGenerator generator, Function<TableSO, NamesProvider> namesProviderGetter) {
		for (SchemeSO scheme : database.getSchemes()) {
			for (TableSO table : scheme.getTables()) {
				if (table.getOptionWithName("DO_NOT_GENERATE").isEmpty()) {
					try {
						NamesProvider namesProvider = namesProviderGetter.apply(table);
						String code = generator.generate(TEMPLATE_PATH, basePackageName, table);
						String fileName = namesProvider.getClassName() + ".java";
						String p = path + PATH_SEPARATOR + basePackageName.replace(".", PATH_SEPARATOR) + PATH_SEPARATOR
								+ namesProvider.getPackageName().replace(".", PATH_SEPARATOR);
						if (new File(p + PATH_SEPARATOR + fileName).exists()) {
							String existingFileContent = Files.readString(Paths.get(p + PATH_SEPARATOR + fileName));
							if (!existingFileContent.contains("GENERATED CODE !!! DO NOT CHANGE !!!")) {
								LOG.info("ignored: " + p + PATH_SEPARATOR + fileName);
								continue;
							}
						}
						LOG.info("creating: " + p);
						new File(p).mkdirs();
						Files.write(Paths.get(p + PATH_SEPARATOR + fileName), code.getBytes(), StandardOpenOption.WRITE,
								StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
						LOG.info(p + PATH_SEPARATOR + fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
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
	public void setGUIBundle(GUIBundle guiBundle) {
		this.guiBundle = guiBundle;
	}

	@Override
	public void setModelCheckerMessageListFrameListeners(ModelCheckerMessageListFrameListener... l) {
		// OLI Will be filled in time.
	}

}