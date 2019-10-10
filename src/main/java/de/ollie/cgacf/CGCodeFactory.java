package de.ollie.cgacf;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import archimedes.acf.checker.ModelChecker;
import archimedes.acf.event.CodeFactoryListener;
import archimedes.gui.checker.ModelCheckerMessageListFrameListener;
import archimedes.model.CodeFactory;
import archimedes.model.DataModel;
import baccara.gui.GUIBundle;
import de.ollie.acf.utils.DataModelToSOConverter;
import de.ollie.acf.utils.NameManager;
import de.ollie.acf.utils.TypeManager;
import de.ollie.archimedes.alexandrian.service.DatabaseSO;
import de.ollie.archimedes.alexandrian.service.SchemeSO;
import de.ollie.archimedes.alexandrian.service.TableSO;
import de.ollie.cgacf.service.ServiceInterfaceGenerator;

/**
 * A basic class for all CG code generators.
 *
 * @author O.Lieshoff (09.10.2019)
 */
public class CGCodeFactory implements CodeFactory {

	private static final Logger LOG = Logger.getLogger(CGCodeFactory.class);

	private DataModel dataModel = null;
	private GUIBundle guiBundle = null;
	private List<CodeFactoryListener> listeners = new ArrayList<>();

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
		createServiceInterface(database, path, basePackageName);
		return false;
	}

	private void createServiceInterface(DatabaseSO database, String path, String basePackageName) {
		ServiceInterfaceGenerator generator = new ServiceInterfaceGenerator(new NameManager(), new TypeManager());
		for (SchemeSO scheme : database.getSchemes()) {
			for (TableSO table : scheme.getTables()) {
				try {
					String code = generator.generate("src/main/resources/templates", basePackageName, table);
					String p = path + "/" + basePackageName.replace(".", "/") + "/"
							+ new NameManager().getServiceInterfacePackageSuffix().replace(".", "/");
					System.out.println("creating: " + p);
					new File(p).mkdirs();
					String fileName = new NameManager().getServiceInterfaceName(table) + ".java";
					Files.write(Paths.get(p + "/" + fileName), code.getBytes());
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
