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
				try {
					NamesProvider namesProvider = namesProviderGetter.apply(table);
					String code = generator.generate(TEMPLATE_PATH, basePackageName, table);
					String fileName = namesProvider.getClassName() + ".java";
					String p = path + "/" + basePackageName.replace(".", "/") + "/"
							+ namesProvider.getPackageName().replace(".", "/");
					if (new File(p + "/" + fileName).exists()) {
						String existingFileContent = Files.readString(Paths.get(p + "/" + fileName));
						if (!existingFileContent.contains("GENERATED CODE !!! DO NOT CHANGE !!!")) {
							System.out.println("ignored: " + p + "/" + fileName);
							continue;
						}
					}
					System.out.println("creating: " + p);
					new File(p).mkdirs();
					Files.write(Paths.get(p + "/" + fileName), code.getBytes(), StandardOpenOption.WRITE,
							StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
					System.out.println(p + "/" + fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void createApplicationClass(DatabaseSO databaseSO, String path, String basePackageName) {
//		ApplicationClassGenerator applicationClassGenerator = new ApplicationClassGenerator(
//				new ClassSourceModelUtils(new NameConverter(), new TypeConverter()), new NameConverter(),
//				new TypeConverter());
//		ClassSourceModel csm = applicationClassGenerator.generate(databaseSO, "rest-acf");
//		csm.getPackageModel().setPackageName(
//				csm.getPackageModel().getPackageName().replace("${base.package.name}", basePackageName));
//		String p = path + "/" + csm.getPackageModel().getPackageName().replace(".", "/");
//		new File(p).mkdirs();
//		String code = new ModelToJavaSourceCodeConverter().classSourceModelToJavaSourceCode(csm);
//		code = code.replace("${base.package.name}", basePackageName);
//		try {
//			Files.write(Paths.get(p + "/" + csm.getName() + ".java"), code.getBytes());
//			System.out.println(p + "/" + csm.getName() + ".java");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void createApplicationProperties(DatabaseSO databaseSO, String path) {
//		String p = path + "/../resources";
//		String fileName = "application.properties";
//		new File(p).mkdirs();
//		String code = new ApplicationPropertiesGenerator(
//				new ClassSourceModelUtils(new NameConverter(), new TypeConverter()), new NameConverter())
//						.generate(databaseSO);
//		try {
//			Files.write(Paths.get(p + "/" + fileName), code.getBytes());
//			System.out.println(p + "/" + fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void createInitialDBXML(DatabaseSO databaseSO, String path, String authorName) {
//		String p = path + "/../resources/db/change-log/InitialDB";
//		String fileName = "InitialDB.xml";
//		new File(p).mkdirs();
//		String code = new InitialDBXMLGenerator(new ClassSourceModelUtils(new NameConverter(), new TypeConverter()),
//				new NameConverter(), new TypeConverter()).generate(databaseSO, authorName);
//		try {
//			Files.write(Paths.get(p + "/" + fileName), code.getBytes());
//			System.out.println(p + "/" + fileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelChecker[] getModelCheckers() {
		// TODO Auto-generated method stub
		return new ModelChecker[0];
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getResourceBundleNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGUIBundle(GUIBundle arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModelCheckerMessageListFrameListeners(ModelCheckerMessageListFrameListener... arg0) {
		// TODO Auto-generated method stub

	}

}
